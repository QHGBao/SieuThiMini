package Controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import BUS.NhaCungCapBUS;
import DTO.NhaCungCapDTO;

public class NhaCungCapController implements Initializable{
    @FXML
    private ImageView btnAdd;

    @FXML
    private ImageView btnDelete;

    @FXML
    private ImageView btnRepair;

    @FXML
    private ImageView btnSearch;

    @FXML
    private Pane containAdd;

    @FXML
    private Pane containDelete;

    @FXML
    private Pane containRepair;

    @FXML
    private TableColumn<NhaCungCapDTO, String> colDiaChi;

    @FXML
    private TableColumn<NhaCungCapDTO, String> colMaNCC;

    @FXML
    private TableColumn<NhaCungCapDTO, String> colNguoiLH;

    @FXML
    private TableColumn<NhaCungCapDTO, Integer> colSdt;

    @FXML
    private TableColumn<NhaCungCapDTO, String> colTenNCC;

    @FXML
    private TableView<NhaCungCapDTO> tableNCC;

    @FXML
    private TextField txtInput;

    NhaCungCapBUS nccBus = new NhaCungCapBUS();
    ObservableList<NhaCungCapDTO> dsNhaCungCap = FXCollections.observableArrayList(nccBus.getAllNCC());

    public void loadDataNCC(){
        colMaNCC.setCellValueFactory(new PropertyValueFactory<NhaCungCapDTO, String>("maNCC"));
        colTenNCC.setCellValueFactory(new PropertyValueFactory<NhaCungCapDTO, String>("tenNCC"));
        colDiaChi.setCellValueFactory(new PropertyValueFactory<NhaCungCapDTO, String>("diaChi"));
        colSdt.setCellValueFactory(new PropertyValueFactory<NhaCungCapDTO, Integer>("sdt"));
        colNguoiLH.setCellValueFactory(new PropertyValueFactory<NhaCungCapDTO, String>("nguoiLH"));
        tableNCC.setItems(dsNhaCungCap);
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        loadDataNCC();
    }

    private Stage popupStage;
    @FXML
    void btnAddClicked(MouseEvent event) {
        if (popupStage != null && popupStage.isShowing())
            return;
        try {   
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/formNCC.fxml"));
            Parent parent = loader.load();
            Scene scene = new Scene(parent);
            popupStage = new Stage();
            popupStage.setScene(scene);
            popupStage.initStyle(StageStyle.UNDECORATED);
            popupStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void btnAddEntered(MouseEvent event) {
        String currentStyle = containAdd.getStyle();
        containAdd.setStyle(currentStyle + "-fx-background-color: #efe8d4;");
    }

    @FXML
    void btnAddExited(MouseEvent event) {
        String currentStyle = containAdd.getStyle();
        containAdd.setStyle(currentStyle + "-fx-background-color: transparent;");
    }

    @FXML
    void btnDeleteClicked(MouseEvent event) {

    }

    @FXML
    void btnDeleteEntered(MouseEvent event) {
        String currentStyle = containDelete.getStyle();
        containDelete.setStyle(currentStyle + "-fx-background-color: #efe8d4;");
    }

    @FXML
    void btnDeleteExited(MouseEvent event) {
        String currentStyle = containDelete.getStyle();
        containDelete.setStyle(currentStyle + "-fx-background-color: transparent;");
    }

     @FXML
    void btnRepairClicked(MouseEvent event) {

    }

    @FXML
    void btnRepairEntered(MouseEvent event) {
        String currentStyle = containRepair.getStyle();
        containRepair.setStyle(currentStyle + "-fx-background-color: #efe8d4;");
    }

    @FXML
    void btnRepairExited(MouseEvent event) {
        String currentStyle = containRepair.getStyle();
        containRepair.setStyle(currentStyle + "-fx-background-color: transparent;");
    }

    @FXML
    void btnSearchClicked(MouseEvent event) {

    }

}

