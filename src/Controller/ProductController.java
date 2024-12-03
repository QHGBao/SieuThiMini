package Controller;


import BUS.ProductBUS;
import DTO.ProductDTO;


import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JOptionPane;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

public class ProductController {
    @FXML
    private Label btnThem;
    
    @FXML
    private Label btnSua;

    @FXML
    private Label btnXoa;

    @FXML
    private TableView<ProductDTO> tbBang;

    @FXML
    private TableColumn<ProductDTO, String> tbMASP;

    @FXML
    private TableColumn<ProductDTO, String> tbTENSP;

    @FXML
    private TableColumn<ProductDTO, String> tbLOAISP;

    @FXML
    private TableColumn<ProductDTO, String> tbSOLUONG;

    @FXML
    private TableColumn<ProductDTO, String> tbMOTA;

    @FXML
    private TableColumn<ProductDTO, Double> tbGIA;

    @FXML
    private TableColumn<ProductDTO, String> tbTINHTRANG;

    @FXML
    private TableColumn<ProductDTO, String> tbHINHANH; 

    @FXML
    private TextField txtTimKiem;

    private ProductBUS productBUS = new ProductBUS();
    private ObservableList<ProductDTO> list = FXCollections.observableArrayList();

    public void hienThiSanPham() {
        list.clear();

        //lấy danh sách sản phẩm từ BUS
        ArrayList<ProductDTO> arr = productBUS.getAllProducts();

        //duyệt mảng sản phẩm
        for (ProductDTO product : arr) {
            list.add(product);
        }
        

    }

}