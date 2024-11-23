package Controller;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import DTO.CancellationDTO;
import DTO.CancellationDetailsDTO;
import DTO.SessionManager;
import BUS.CancellationBUS;
import BUS.CancellationDetailsBUS;

public class CancellationDeleteController {

    @FXML
    private Button buttonCreate;

    @FXML
    private TableView<CancellationDTO> tblCancellation; 

    @FXML
    private TableColumn<CancellationDTO, Integer> colCancellationID;

    @FXML
    private TableColumn<CancellationDTO, String> colCancellationDay;

    @FXML
    private TableColumn<CancellationDTO, Integer> colEmployeeID;

    @FXML
    private TableView<CancellationDetailsDTO> tblCancellationDetail; // TableView chi tiết phiếu hủy

    @FXML
    private TableColumn<CancellationDetailsDTO, Integer> colCancellationDetailsID;

    @FXML
    private TableColumn<CancellationDetailsDTO, Integer> colProductID;

    @FXML
    private TableColumn<CancellationDetailsDTO, String> colProductName;

    @FXML
    private TableColumn<CancellationDetailsDTO, String> colProductType;

    @FXML
    private TableColumn<CancellationDetailsDTO, Integer> colCancellationQuantity;

    @FXML
    private TextField txtEmployeeName;

    @FXML
    private TextField txtEmployeeID;

    // Biến xử lý logic liên quan tới phiếu hủy và chi tiết phiếu hủy
    private CancellationBUS CancellationBUS = new CancellationBUS();
    private CancellationDetailsBUS CancellationDetailsBUS = new CancellationDetailsBUS();

    private ObservableList<CancellationDTO> listCancellation;
    private ObservableList<CancellationDetailsDTO> listCancellationDetails;

    @FXML
    public void initialize() {
        SessionManager session = SessionManager.getInstance();
        // Dữ liệu Tên Nhân Viên và Mã Nhân Viên
        txtEmployeeName.setText(session.getEmployeeName());
        txtEmployeeID.setText(String.valueOf(session.getEmployeeID()));;
        // Liên kết cột dữ liệu trong TableView phiếu hủy
        colCancellationID.setCellValueFactory(data -> new SimpleIntegerProperty(data.getValue().getCancellationID()).asObject());
        colCancellationDay.setCellValueFactory(data -> {
            LocalDateTime cancellationDay = data.getValue().getCancellationDay();
            String formattedDate = cancellationDay.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            return new SimpleStringProperty(formattedDate);
        });
        colEmployeeID.setCellValueFactory(data -> new SimpleIntegerProperty(data.getValue().getEmployeeID()).asObject());

        // Liên kết cột dữ liệu trong TableView chi tiết phiếu hủy
        colCancellationDetailsID.setCellValueFactory(data -> new SimpleIntegerProperty(data.getValue().getCancellationID()).asObject());
        colProductID.setCellValueFactory(data -> new SimpleIntegerProperty(data.getValue().getProductID()).asObject());
        colProductName.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getProductName()));
        colProductType.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getProductType()));;
        colCancellationQuantity.setCellValueFactory(data -> new SimpleIntegerProperty(data.getValue().getCancellationQuantity()).asObject());

        // Tải dữ liệu ban đầu
        loadCancellationTable();
    }

    private void loadCancellationTable() {
        listCancellation = FXCollections.observableArrayList(CancellationBUS.getAllCancellation());
        tblCancellation.setItems(listCancellation);
    }

    @FXML
    private void handleCancellationClick(MouseEvent event) {
        CancellationDTO selected = tblCancellation.getSelectionModel().getSelectedItem();
        if (selected != null) {
            listCancellationDetails = FXCollections.observableArrayList(CancellationDetailsBUS.getCancellationDetailsByCancellationID(selected.getCancellationID()));
            tblCancellationDetail.setItems(listCancellationDetails);
        }
    }

    @FXML
    private void btnDelete_Clicked(ActionEvent event) {
        CancellationDTO selected = tblCancellation.getSelectionModel().getSelectedItem();
        if (selected != null) {
            try {
                if(CancellationBUS.deleteCancellation(selected.getCancellationID())){
                    loadCancellationTable();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            tblCancellationDetail.getItems().clear();
        }
    }

    @FXML
    private void btnCreate_Clicked (MouseEvent event){
        openCancellationScreen();
    }

    private void openCancellationScreen() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/CreateCancellationGUI.fxml"));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();

            Stage currentStage = (Stage) buttonCreate.getScene().getWindow();
            currentStage.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}