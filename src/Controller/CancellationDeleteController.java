package Controller;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import Controller.CancellationEditController;
import DTO.CancellationDTO;
import DTO.CancellationDetailsDTO;
import DTO.CancellationProductDTO;
import DTO.SessionManager;
import BUS.CancellationBUS;
import BUS.CancellationDetailsBUS;

public class CancellationDeleteController {

    @FXML
    private Button buttonCreate;

    @FXML
    private TableView<CancellationDTO> tblCancellation; 

    @FXML
    private DatePicker dtpDate;
    
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
    private Stage popupStage;

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
    private void btnDelete_Clicked(MouseEvent event) {
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

    @FXML
    public void btnSearch_Clicked(MouseEvent event) {
        LocalDate selectedDate = dtpDate.getValue();

        // Nếu không chọn ngày, hiển thị danh sách ban đầu
        if (selectedDate == null) {
            tblCancellation.setItems(listCancellation);
            return;
        }

        // Lọc danh sách theo ngày
        List<CancellationDTO> filteredList = CancellationBUS.getCancellationsByDate(selectedDate);

        // Nếu danh sách kết quả rỗng, hiển thị danh sách ban đầu
        if (filteredList.isEmpty()) {
            tblCancellation.setItems(listCancellation);
        } else {
            tblCancellation.setItems(FXCollections.observableArrayList(filteredList));
        }
    }

    @FXML
    private void btnEdit_Clicked (MouseEvent event){
        CancellationDTO selectedCancellation = tblCancellation.getSelectionModel().getSelectedItem();
    
        if (selectedCancellation != null) {
            try {
                // Tải form sửa từ FXML
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/EditCancellationGUI.fxml"));
                Parent root = loader.load();

                // Lấy controller của form sửa
                CancellationEditController editController = loader.getController();

                // Truyền ID phiếu hủy và các chi tiết liên quan vào form sửa
                editController.setCancellationDTO(selectedCancellation);

                if (popupStage == null) {
                    popupStage = new Stage();
                    popupStage.initModality(Modality.APPLICATION_MODAL); // Đặt dạng modal trước khi hiển thị
                    popupStage.setTitle("Sửa Phiếu Hủy");
                }
    
                // Đặt Scene cho popup
                popupStage.setScene(new Scene(root));
    
                // Hiển thị Stage sau khi đã thiết lập tất cả
                popupStage.showAndWait();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            // Thông báo nếu không chọn phiếu hủy nào
            Alert alert = new Alert(Alert.AlertType.WARNING, "Vui lòng chọn một phiếu hủy để sửa!", ButtonType.OK);
            alert.showAndWait();
        }
    }
    
    private void openCancellationScreen() {
        if (popupStage != null && popupStage.isShowing())
            return;
        try {   
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/CreateCancellationGUI.fxml"));
            Parent parent = loader.load();
            Scene scene = new Scene(parent);
            popupStage = new Stage();
            popupStage.setScene(scene);
            popupStage.initModality(Modality.APPLICATION_MODAL); // Đặt dạng modal (chặn tương tác với cửa sổ khác)
            popupStage.setTitle("Xóa Phiếu Hủy");
            popupStage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}