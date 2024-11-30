package Controller;

import BUS.CancellationBUS;
import BUS.CancellationProductBUS;
import DTO.CancellationDTO;
import DTO.CancellationProductDTO;
import DTO.SessionManager;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import java.sql.Connection;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
public class CancellationController {

    @FXML
    private ImageView btnClose;

    @FXML
    private ComboBox<String> cmbProductName;

    @FXML
    private TextField txtProductID, txtProductType, txtCancellationQuantity, txtEmployeeName, txtEmployeeID, txtProductTypeID;

    @FXML
    private DatePicker dtpCancellationDay;

    @FXML
    private TableView<CancellationProductDTO> tblCancellationProducts;

    @FXML
    private TableColumn<CancellationProductDTO, String> colProductName;

    @FXML
    private TableColumn<CancellationProductDTO, Integer> colProductID, colCancellationQuantity,colProductTypeID;

    private Connection connection;
    private CancellationProductBUS cancellationProductBUS = new CancellationProductBUS();
    private CancellationBUS cancellationBUS = new CancellationBUS();
    private ObservableList<CancellationProductDTO> cancellationList = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        SessionManager session = SessionManager.getInstance();
        txtEmployeeName.setText(session.getEmployeeName());
        txtEmployeeID.setText(String.valueOf(session.getEmployeeID()));
        setupDatePicker();
        setupTableColumns();
        setupComboBoxWithAutocomplete();
        setupProductSelectionHandler();
        tblCancellationProducts.setItems(cancellationList);
    }

    /** Thiết lập giá trị mặc định cho DatePicker */
    private void setupDatePicker() {
        dtpCancellationDay.setValue(LocalDate.now());
    }

    /** Gán các cột trong bảng với dữ liệu từ CancellationProductDTO */
    private void setupTableColumns() {
        colProductID.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getProductId()).asObject());
        colProductName.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getProductName()));
        colProductTypeID.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getProductTypeID()).asObject());
        colCancellationQuantity.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getQuantity()).asObject());
    }

    /** Lấy dữ liệu từ DAO và điền vào ComboBox */
    private void setupComboBoxWithAutocomplete() {
        List<CancellationProductDTO> products = cancellationProductBUS.getAllProducts();
    
        cmbProductName.getItems().addAll(
            products.stream().map(CancellationProductDTO::getProductName).collect(Collectors.toList())
        );
    
        cmbProductName.setEditable(true);
    
        cmbProductName.getEditor().textProperty().addListener((observable, oldValue, newValue) -> {
            List<String> filteredProducts = products.stream()
                .map(CancellationProductDTO::getProductName)
                .filter(name -> name.toLowerCase().contains(newValue.toLowerCase()))
                .collect(Collectors.toList());
            cmbProductName.getItems().setAll(filteredProducts);
        });
    
        cmbProductName.setOnAction(event -> {
            String selectedProductName = cmbProductName.getValue();
            if (selectedProductName != null) {
                CancellationProductDTO product = cancellationProductBUS.getProductByName(selectedProductName);
                if (product != null) {
                    txtProductID.setText(String.valueOf(product.getProductId()));
                    txtProductType.setText(String.valueOf(cancellationProductBUS.getProductType(product.getProductTypeID())));
                    txtProductTypeID.setText(String.valueOf(product.getProductTypeID()));
                }
            }
        });
    }

    private void setupProductSelectionHandler() {
        cmbProductName.setOnAction(event -> {
            String selectedProductName = cmbProductName.getValue();
            CancellationProductDTO product = cancellationProductBUS.getProductByName(selectedProductName);
    
            // Nếu sản phẩm được tìm thấy, tự động điền thông tin
            if (product != null) {
                txtProductID.setText(String.valueOf(product.getProductId()));
                txtProductType.setText(String.valueOf(cancellationProductBUS.getProductType(product.getProductTypeID())));
                txtProductTypeID.setText(String.valueOf(product.getProductTypeID()));
            }
        });
    }

    @FXML
    private void btnAdd_Clicked(MouseEvent event) {
        try{
            int productID = Integer.parseInt(txtProductID.getText());
            String productName = cmbProductName.getValue();
            int productTypeid = Integer.parseInt(txtProductTypeID.getText());
            String quantityText = txtCancellationQuantity.getText();
            if (quantityText == null || quantityText.trim().isEmpty()) {
                showAlert(Alert.AlertType.ERROR, "Lỗi dữ liệu", "Số lượng hủy không được để trống!");
                return;
            }
            int quantity;
            try {
                // Kiểm tra xem người dùng có nhập giá trị hợp lệ cho số lượng không
                quantity = Integer.parseInt(quantityText.trim());
            } catch (NumberFormatException e) {
                showAlert(Alert.AlertType.ERROR, "Lỗi dữ liệu", "Số lượng hủy phải là một số nguyên hợp lệ!");
                return;
            }
            cancellationList.add(new CancellationProductDTO(productID, productName, productTypeid, quantity));
            clearInputs();
        } catch (Exception e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Lỗi", "Có lỗi xảy ra: " + e.getMessage());
        }
    }

    @FXML
    private void btnDelete_Clicked(MouseEvent event) {
        CancellationProductDTO selectedProduct = tblCancellationProducts.getSelectionModel().getSelectedItem();
        if (selectedProduct != null) {
            cancellationList.remove(selectedProduct);
        }
    }

    @FXML
    private void btnCreate_Clicked(MouseEvent event) {
     if (cancellationList.isEmpty()) {
        showAlert(Alert.AlertType.ERROR, "Lỗi dữ liệu", "Danh sách sản phẩm không được để trống!");
        return;
        }

    try {
        CancellationDTO cancellationDTO = new CancellationDTO(
            0,
            LocalDateTime.now(),
            Integer.parseInt(txtEmployeeID.getText())
        );

        cancellationBUS.createCancellation(cancellationDTO, cancellationList);

        showAlert(Alert.AlertType.INFORMATION, "Thành công", "Phiếu hủy đã được tạo thành công!");
        cancellationList.clear();
        tblCancellationProducts.refresh();
        } catch (Exception e) {
        e.printStackTrace();
        showAlert(Alert.AlertType.ERROR, "Lỗi", "Có lỗi xảy ra trong quá trình tạo phiếu hủy: " + e.getMessage());
        }
    }

    @FXML
    private void btnClose_Clicked (MouseEvent event){
        closePopup();
    }

    @FXML
    private void closePopup() {
        Stage stage = (Stage) btnClose.getScene().getWindow();
        stage.close();
    }

    private void clearInputs() {
        txtProductID.clear();
        cmbProductName.getEditor().clear();
        txtProductType.clear();
        txtCancellationQuantity.clear();
    }

    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}