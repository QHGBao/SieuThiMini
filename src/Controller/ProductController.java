package Controller;

import BUS.ProductBUS;
import DTO.ProductDTO;
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
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javax.swing.JOptionPane;

public class ProductController {
    @FXML
    private Label btnThem;
    @FXML
    private Label btnSua;
    @FXML
    private Label btnXoa;
    @FXML
    private TextField txtTimKiem;
    @FXML
    private TableView<ProductDTO> tbBang;
    @FXML
    private TableColumn<ProductDTO, Integer> tbMASP;
    @FXML
    private TableColumn<ProductDTO, String> tbTENSP;
    @FXML
    private TableColumn<ProductDTO, String> tbMOTA;
    @FXML
    private TableColumn<ProductDTO, Integer> tbSOLUONG;
    @FXML
    private TableColumn<ProductDTO, Integer> tbGIABAN;
    @FXML
    private TableColumn<ProductDTO, Integer> tbMALOAI;
    @FXML
    private TableColumn<ProductDTO, String> tbTINHTRANG;

    private ObservableList<ProductDTO> list = FXCollections.observableArrayList();
    private ProductBUS prBUS = new ProductBUS();
    private Stage popupStage;

    @FXML
    public void initialize() {
        setupTableColumns();
        loadProductData();
        setupSearch();
        setupButtonHoverEffects();
    }

    private void setupButtonHoverEffects() {
        // Add hover effects for all buttons
        setupButtonEffect(btnThem);
        setupButtonEffect(btnSua);
        setupButtonEffect(btnXoa);
    }

    private void setupButtonEffect(Label button) {
        button.setOnMouseEntered(e -> {
            button.setStyle("-fx-background-color: #e6c018;"); // Darker yellow when hovered
        });
        button.setOnMouseExited(e -> {
            button.setStyle(""); // Reset to default style
        });
    }

    private void setupTableColumns() {
        tbMASP.setCellValueFactory(data -> new SimpleIntegerProperty(data.getValue().getMaSP()).asObject());
        tbTENSP.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getTenSP()));
        tbMOTA.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getMoTa()));
        tbSOLUONG.setCellValueFactory(data -> new SimpleIntegerProperty(data.getValue().getSoLuong()).asObject());
        tbGIABAN.setCellValueFactory(data -> new SimpleIntegerProperty(data.getValue().getGia()).asObject());
        tbMALOAI.setCellValueFactory(data -> new SimpleIntegerProperty(data.getValue().getMaLoai()).asObject());
        tbTINHTRANG.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getIsDeleted() == 0 ? "Còn hàng" : "Ngừng kinh doanh"));
    }

    private void loadProductData() {
        list.clear();
        list.addAll(prBUS.getAllProducts());
        tbBang.setItems(list);
    }

    private void setupSearch() {
        FilteredList<ProductDTO> filteredData = new FilteredList<>(list, p -> true);
        txtTimKiem.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(product -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                String lowerCaseFilter = newValue.toLowerCase();
                if (product.getTenSP().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (String.valueOf(product.getMaSP()).contains(lowerCaseFilter)) {
                    return true;
                }
                return false;
            });
        });

        SortedList<ProductDTO> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(tbBang.comparatorProperty());
        tbBang.setItems(sortedData);
    }

    private void showButtonClickEffect(Label button) {
        button.setStyle("-fx-background-color: #cc9900;"); // Darker shade when clicked
        new Thread(() -> {
            try {
                Thread.sleep(100);
                javafx.application.Platform.runLater(() -> {
                    button.setStyle(""); // Reset to default style
                });
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }

    @FXML
    void ThemSP(MouseEvent event) {
        // Verify that the click came from btnThem
        if (event.getSource() != btnThem) {
            return;
        }

        // Visual feedback for click
        showButtonClickEffect(btnThem);

        // Check if popup is already showing
        if (popupStage != null && popupStage.isShowing()) {
            popupStage.requestFocus();
            return;
        }

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/ProductForm.fxml"));
            Parent root = loader.load();
            
            ProductFormController formController = loader.getController();
            formController.setMode("ADD");
            formController.setOnSuccessCallback(() -> {
                loadProductData();
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Thành công");
                alert.setHeaderText(null);
                alert.setContentText("Thêm sản phẩm thành công!");
                alert.showAndWait();
            });

            popupStage = new Stage();
            Scene scene = new Scene(root);
            popupStage.setScene(scene);
            popupStage.initStyle(StageStyle.TRANSPARENT);
            popupStage.setResizable(false);
            popupStage.centerOnScreen();
            popupStage.show();

        } catch (Exception e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Lỗi");
            alert.setHeaderText(null);
            alert.setContentText("Không thể mở form thêm sản phẩm!");
            alert.showAndWait();
        }
    }

    @FXML
    void SuaSP(MouseEvent event) {
        // Verify that the click came from btnSua
        if (event.getSource() != btnSua) {
            return;
        }

        // Visual feedback for click
        showButtonClickEffect(btnSua);

        // Get selected product
        ProductDTO selectedProduct = tbBang.getSelectionModel().getSelectedItem();
        if (selectedProduct == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Cảnh báo");
            alert.setHeaderText(null);
            alert.setContentText("Vui lòng chọn sản phẩm cần sửa!");
            alert.showAndWait();
            return;
        }

        // Check if popup is already showing
        if (popupStage != null && popupStage.isShowing()) {
            popupStage.requestFocus();
            return;
        }

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../GUI/ProductFormGUI.fxml"));
            Parent root = loader.load();
            
            ProductFormController formController = loader.getController();
            formController.setMode("EDIT");
            formController.setProduct(selectedProduct);
            formController.setOnSuccessCallback(() -> {
                loadProductData();
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Thành công");
                alert.setHeaderText(null);
                alert.setContentText("Cập nhật sản phẩm thành công!");
                alert.showAndWait();
            });

            popupStage = new Stage();
            Scene scene = new Scene(root);
            popupStage.setScene(scene);
            popupStage.initStyle(StageStyle.TRANSPARENT);
            popupStage.setResizable(false);
            popupStage.centerOnScreen();
            popupStage.show();

        } catch (Exception e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Lỗi");
            alert.setHeaderText(null);
            alert.setContentText("Không thể mở form sửa sản phẩm!");
            alert.showAndWait();
        }
    }

    @FXML
    void XoaSP(MouseEvent event) {
        // Verify that the click came from btnXoa
        if (event.getSource() != btnXoa) {
            return;
        }

        // Visual feedback for click
        showButtonClickEffect(btnXoa);

        ProductDTO selectedProduct = tbBang.getSelectionModel().getSelectedItem();
        if (selectedProduct == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Cảnh báo");
            alert.setHeaderText(null);
            alert.setContentText("Vui lòng chọn sản phẩm cần ngừng kinh doanh!");
            alert.showAndWait();
            return;
        }

        // Check if product is already marked as deleted
        if (selectedProduct.getIsDeleted() == 1) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Cảnh báo");
            alert.setHeaderText(null);
            alert.setContentText("Sản phẩm này đã ngừng kinh doanh!");
            alert.showAndWait();
            return;
        }

        Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmAlert.setTitle("Xác nhận ngừng kinh doanh");
        confirmAlert.setHeaderText("Bạn có chắc chắn muốn ngừng kinh doanh sản phẩm này?");
        confirmAlert.setContentText("Sản phẩm sẽ được đánh dấu là ngừng kinh doanh.");

        if (confirmAlert.showAndWait().get() == ButtonType.OK) {
            // Update the isDeleted status to 1
            //selectedProduct.setIsDeleted(1);
            boolean success = prBUS.deleteProduct(selectedProduct.getMaSP());
            
            if (success) {
                loadProductData(); // Reload the table data
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Thành công");
                alert.setHeaderText(null);
                alert.setContentText("Đã ngừng kinh doanh sản phẩm thành công!");
                alert.showAndWait();
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Lỗi");
                alert.setHeaderText(null);
                alert.setContentText("Không thể ngừng kinh doanh sản phẩm!");
                alert.showAndWait();
            }
        }
    }
}