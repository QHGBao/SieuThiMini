package Controller;

import BUS.ProductBUS;
import BUS.ProductTypeBUS;
import DTO.ProductDTO;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.image.ImageView;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.regex.Pattern;

public class ProductFormController {
    @FXML private TextField txtTenSanPham;
    @FXML private TextField txtMaSanPham;
    @FXML private TextField txtMoTa;
    @FXML private TextField txtGiaBan;
    @FXML private ComboBox<String> cmbLoaiSanPham;
    @FXML private ImageView btnClose;
    @FXML private Canvas canvas;

    private ProductBUS productBUS;
    private ProductTypeBUS productTypeBUS;
    private boolean isEditMode = false;
    private String selectedImagePath = "";
    private static final String IMAGE_DIRECTORY = "src/main/resources/Assets/ProductImages/";
    private static final Pattern PRICE_PATTERN = Pattern.compile("^[0-9]+$");

    @FXML
    public void initialize() {
        productBUS = new ProductBUS();
        productTypeBUS = new ProductTypeBUS();
        setupCanvas();
        loadProductTypes();
        setupTextFields();
        
        // Set up canvas click event for image selection
        canvas.setOnMouseClicked(event -> selectImage());
    }

    private void setupCanvas() {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.setFill(javafx.scene.paint.Color.WHITE);
        gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
    }

    private void loadProductTypes() {
        cmbLoaiSanPham.getItems().addAll(productTypeBUS.getAllProductTypesName());
    }

    private void setupTextFields() {
        txtTenSanPham.setEditable(!isEditMode);
        txtMoTa.setEditable(!isEditMode);
        txtGiaBan.setEditable(!isEditMode);
        txtMaSanPham.setEditable(false);
        
        if (!isEditMode) {
            // Auto-generate new product ID
            int newId = getNextProductId();
            txtMaSanPham.setText(String.valueOf(newId));
        }
    }

    private int getNextProductId() {
        // Get the maximum product ID from the database and add 1
        return productBUS.getAllProducts().stream()
                .mapToInt(ProductDTO::getMaSP)
                .max()
                .orElse(0) + 1;
    }

    @FXML
    private void btnClose_Clicked() {
        Stage stage = (Stage) btnClose.getScene().getWindow();
        stage.close();
    }

    private void selectImage() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(
            new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg")
        );
        
        File selectedFile = fileChooser.showOpenDialog(canvas.getScene().getWindow());
        if (selectedFile != null) {
            try {
                // Copy image to project directory
                String fileName = System.currentTimeMillis() + "_" + selectedFile.getName();
                Path destination = Path.of(IMAGE_DIRECTORY + fileName);
                Files.createDirectories(Path.of(IMAGE_DIRECTORY));
                Files.copy(selectedFile.toPath(), destination, StandardCopyOption.REPLACE_EXISTING);
                
                // Save path and display image
                selectedImagePath = fileName;
                displayImage(selectedFile.toURI().toString());
            } catch (IOException e) {
                e.printStackTrace();
                showAlert(Alert.AlertType.ERROR, "Lỗi", "Không thể lưu hình ảnh");
            }
        }
    }

    private void displayImage(String imagePath) {
        Image image = new Image(imagePath);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        gc.drawImage(image, 0, 0, canvas.getWidth(), canvas.getHeight());
    }

    @FXML
    private void btnCreate_Clicked() {
        if (!validateInputs()) {
            return;
        }

        ProductDTO product = new ProductDTO();
        product.setTenSP(txtTenSanPham.getText());
        product.setMaLoai(Integer.parseInt(cmbLoaiSanPham.getValue()));
        product.setMoTa(txtMoTa.getText());
        product.setGia(Integer.parseInt(txtGiaBan.getText()));
        product.setSoLuong(0); // Default value
        product.setHinhAnh(selectedImagePath);
        product.setIsDeleted(0); // Default value

        if (isEditMode) {
            product.setMaSP(Integer.parseInt(txtMaSanPham.getText()));
            if (productBUS.updateProduct(product)) {
                showAlert(Alert.AlertType.INFORMATION, "Thành công", "Cập nhật sản phẩm thành công");
                btnClose_Clicked();
            } else {
                showAlert(Alert.AlertType.ERROR, "Lỗi", "Không thể cập nhật sản phẩm");
            }
        } else {
            if (productBUS.addProduct(product)) {
                showAlert(Alert.AlertType.INFORMATION, "Thành công", "Thêm sản phẩm thành công");
                btnClose_Clicked();
            } else {
                showAlert(Alert.AlertType.ERROR, "Lỗi", "Không thể thêm sản phẩm");
            }
        }
    }

    private boolean validateInputs() {
        if (txtTenSanPham.getText().isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Cảnh báo", "Vui lòng nhập tên sản phẩm");
            return false;
        }
        if (cmbLoaiSanPham.getValue() == null) {
            showAlert(Alert.AlertType.WARNING, "Cảnh báo", "Vui lòng chọn loại sản phẩm");
            return false;
        }
        if (txtGiaBan.getText().isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Cảnh báo", "Vui lòng nhập giá bán");
            return false;
        }
        if (!PRICE_PATTERN.matcher(txtGiaBan.getText()).matches()) {
            showAlert(Alert.AlertType.WARNING, "Cảnh báo", "Giá bán phải là số nguyên dương");
            return false;
        }
        return true;
    }

    public void setEditMode(boolean editMode) {
        this.isEditMode = editMode;
        setupTextFields();
    }

    public void setProduct(ProductDTO product) {
        txtMaSanPham.setText(String.valueOf(product.getMaSP()));
        txtTenSanPham.setText(product.getTenSP());
        cmbLoaiSanPham.setValue(String.valueOf(product.getMaLoai()));
        txtMoTa.setText(product.getMoTa());
        txtGiaBan.setText(String.valueOf(product.getGia()));
        
        if (product.getHinhAnh() != null && !product.getHinhAnh().isEmpty()) {
            selectedImagePath = product.getHinhAnh();
            displayImage("file:" + IMAGE_DIRECTORY + product.getHinhAnh());
        }
    }

    private void showAlert(Alert.AlertType type, String title, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
