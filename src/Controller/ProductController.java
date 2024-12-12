package Controller;

import BUS.ProductBUS;
import DTO.ProductDTO;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.collections.FXCollections;
import java.io.IOException;
import java.util.Optional;
import javafx.beans.property.SimpleStringProperty;


public class ProductController {
    @FXML private TableView<ProductDTO> tbBang;
    @FXML private TableColumn<ProductDTO, Integer> tbMASP;
    @FXML private TableColumn<ProductDTO, String> tbTENSP;
    @FXML private TableColumn<ProductDTO, String> tbMOTA;
    @FXML private TableColumn<ProductDTO, Integer> tbSOLUONG;
    @FXML private TableColumn<ProductDTO, Integer> tbGIABAN;
    @FXML private TableColumn<ProductDTO, Integer> tbMALOAI;
    @FXML private TableColumn<ProductDTO, String> tbTINHTRANG;
    @FXML private TextField txtTimKiem;

    private ProductBUS productBUS;

    @FXML
    public void initialize() {
        productBUS = new ProductBUS();

        // Lắng nghe sự thay đổi trong txtTimKiem
        txtTimKiem.textProperty().addListener((observable, oldValue, newValue) -> {
            searchAndUpdateTable(newValue); // Gọi hàm tìm kiếm và cập nhật bảng
        });
    
        setupTable();
        loadTableData(); // Hiển thị tất cả sản phẩm khi bắt đầu
        
    }

    private void searchAndUpdateTable(String query) {
        // Kiểm tra nếu ô tìm kiếm trống thì tải lại tất cả sản phẩm
        if (query == null || query.isEmpty()) {
            loadTableData(); // Hiển thị tất cả sản phẩm nếu ô tìm kiếm trống
        } else {
            // Gọi phương thức tìm kiếm và lấy kết quả
            var searchResults = productBUS.searchProductsByName(query);
            // Cập nhật bảng với kết quả tìm kiếm
            tbBang.setItems(FXCollections.observableArrayList(searchResults));
        }
    }

    private void setupTable() {
        tbMASP.setCellValueFactory(new PropertyValueFactory<>("maSP"));
        tbTENSP.setCellValueFactory(new PropertyValueFactory<>("tenSP"));
        tbMOTA.setCellValueFactory(new PropertyValueFactory<>("moTa"));
        tbSOLUONG.setCellValueFactory(new PropertyValueFactory<>("soLuong"));
        tbGIABAN.setCellValueFactory(new PropertyValueFactory<>("gia"));
        tbMALOAI.setCellValueFactory(new PropertyValueFactory<>("maLoai"));
        tbTINHTRANG.setCellValueFactory(cellData -> {
            int isDeleted = cellData.getValue().getIsDeleted();
            return new SimpleStringProperty(isDeleted == 0 ? "Đang bán" : "Ngừng bán");
        });
    }

    private void loadTableData() {
        tbBang.setItems(FXCollections.observableArrayList(productBUS.getAllProducts()));
    }

    @FXML
    private void ThemSP() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/ProductForm.fxml"));
            Parent root = loader.load();
            
            ProductFormController controller = loader.getController();
            controller.setEditMode(false);
            
            Stage stage = new Stage();
            stage.initStyle(StageStyle.UNDECORATED);
            stage.setScene(new Scene(root));
            stage.showAndWait();
            
            loadTableData(); // Refresh table after adding
        } catch (IOException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Lỗi", "Không thể mở form thêm sản phẩm");
        }
    }

    @FXML
    private void SuaSP() {
        ProductDTO selectedProduct = tbBang.getSelectionModel().getSelectedItem();
        if (selectedProduct == null) {
            showAlert(Alert.AlertType.WARNING, "Cảnh báo", "Vui lòng chọn sản phẩm cần sửa");
            return;
        }

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/ProductForm.fxml"));
            Parent root = loader.load();
            
            ProductFormController controller = loader.getController();
            controller.setProduct(selectedProduct);
            controller.setEditMode(true);
            Stage stage = new Stage();
            stage.initStyle(StageStyle.UNDECORATED);
            stage.setScene(new Scene(root));
            stage.showAndWait();
            
            loadTableData(); // Refresh table after editing
        } catch (IOException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Lỗi", "Không thể mở form sửa sản phẩm");
        }
    }

    @FXML
    private void XoaSP() {
        ProductDTO selectedProduct = tbBang.getSelectionModel().getSelectedItem();
        if (selectedProduct == null) {
            showAlert(Alert.AlertType.WARNING, "Cảnh báo", "Vui lòng chọn sản phẩm cần xóa");
            return;
        }

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Xác nhận xóa");
        alert.setHeaderText(null);
        alert.setContentText("Bạn có chắc muốn xóa sản phẩm này?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            boolean success = productBUS.deleteProduct(selectedProduct.getMaSP());
            if (success) {
                loadTableData();
                showAlert(Alert.AlertType.INFORMATION, "Thành công", "Xóa sản phẩm thành công");
            } else {
                showAlert(Alert.AlertType.ERROR, "Lỗi", "Không thể xóa sản phẩm");
            }
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
