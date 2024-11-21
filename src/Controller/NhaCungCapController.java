package Controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.ResourceBundle;

import DTO.NhaCungCapDTO;

public class NhaCungCapController implements Initializable{
    @FXML
    private ImageView btnAdd;

    @FXML
    private ImageView btnDelete;

    @FXML
    private ImageView btnReset;

    @FXML
    private ImageView btnSearch;

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

    

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        // TODO Auto-generated method stub
        
    }

    @FXML
    void btnAddClicked(MouseEvent event) {

    }

    @FXML
    void btnAddEntered(MouseEvent event) {

    }

    @FXML
    void btnAddExited(MouseEvent event) {

    }

    @FXML
    void btnDeleteClicked(MouseEvent event) {

    }

    @FXML
    void btnDeleteEntered(MouseEvent event) {

    }

    @FXML
    void btnDeleteExited(MouseEvent event) {

    }

    @FXML
    void btnResetClicked(MouseEvent event) {

    }

    @FXML
    void btnResetEntered(MouseEvent event) {

    }

    @FXML
    void btnResetExited(MouseEvent event) {

    }

    @FXML
    void btnSearchClicked(MouseEvent event) {

    }
}
