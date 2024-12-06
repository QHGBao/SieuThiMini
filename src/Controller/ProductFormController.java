package Controller;

import BUS.ProductBUS;
import BUS.ProductTypeBUS;
import DTO.ProductDTO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.util.List;
import java.util.function.Consumer;

public class ProductFormController {
    @FXML
    private TextField txtTenSanPham;
    @FXML
    private TextField txtMaSanPham;
    @FXML
    private TextField txtMoTa;
    @FXML
    private TextField txtGiaBan;
    @FXML
    private ComboBox<String> cmbLoaiSanPham;
    @FXML
    private ImageView btnClose;

    private ProductBUS prBUS = new ProductBUS();
    private ProductTypeBUS productTypeBUS = new ProductTypeBUS();
    private String mode = "ADD"; // or "EDIT"
    private ProductDTO currentProduct;
    private Runnable onSuccessCallback;

    @FXML
    public void initialize() {
        loadProductTypes();
        setupControls();
    }

    private void loadProductTypes() {
        List<String> productTypes = productTypeBUS.getAllProductTypesName(); // Gọi đến Service để lấy danh sách loại
                                                                             // sản phẩm
        ObservableList<String> observableProductTypes = FXCollections.observableArrayList(productTypes); 

        cmbLoaiSanPham.setItems(observableProductTypes); // Đưa danh sách vào ComboBox

        //cmbLoaiSanPham.setValue("Tat ca");
    }

    private void setupControls() {
        // Enable text fields for editing
        txtTenSanPham.setEditable(true);
        txtMoTa.setEditable(true);
        txtGiaBan.setEditable(true);

        // Setup numeric validation for price
        txtGiaBan.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                txtGiaBan.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });
    }

    public void setMode(String mode) {
        this.mode = mode;
        if ("EDIT".equals(mode)) {
            txtMaSanPham.setDisable(true);
        }
    }

    public void setProduct(ProductDTO product) {
        this.currentProduct = product;
        txtMaSanPham.setText(String.valueOf(product.getMaSP()));
        txtTenSanPham.setText(product.getTenSP());
        txtMoTa.setText(product.getMoTa());
        txtGiaBan.setText(String.valueOf(product.getGia()));
        // Set other fields...
    }

    public void setOnSuccessCallback(Runnable callback) {
        this.onSuccessCallback = callback;
    }

    @FXML
    void btnClose_Clicked(MouseEvent event) {
        Stage stage = (Stage) btnClose.getScene().getWindow();
        stage.close();
    }

    @FXML
    void btnCreate_Clicked(MouseEvent event) {
        if (!validateInputs()) {
            return;
        }

        ProductDTO product = new ProductDTO();
        if ("EDIT".equals(mode)) {
            product.setMaSP(Integer.parseInt(txtMaSanPham.getText()));
        }
        product.setTenSP(txtTenSanPham.getText());
        product.setMoTa(txtMoTa.getText());
        product.setGia(Integer.parseInt(txtGiaBan.getText()));
        // Set other fields...

        boolean success;
        if ("ADD".equals(mode)) {
            success = prBUS.addProduct(product);
        } else {
            success = prBUS.updateProduct(product);
        }

        if (success) {
            if (onSuccessCallback != null) {
                onSuccessCallback.run();
            }
            Stage stage = (Stage) btnClose.getScene().getWindow();
            stage.close();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Lỗi");
            alert.setHeaderText(null);
            alert.setContentText("Không thể " + ("ADD".equals(mode) ? "thêm" : "cập nhật") + " sản phẩm!");
            alert.showAndWait();
        }
    }

    private boolean validateInputs() {
        if (txtTenSanPham.getText().trim().isEmpty()) {
            showError("Vui lòng nhập tên sản phẩm!");
            return false;
        }
        if (txtGiaBan.getText().trim().isEmpty()) {
            showError("Vui lòng nhập giá bán!");
            return false;
        }
        // Add other validations as needed
        return true;
    }

    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Lỗi");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
