package Controller;

import java.sql.Connection;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import BUS.CancellationBUS;
import BUS.CancellationDetailsBUS;
import BUS.CancellationProductBUS;
import DTO.CancellationDetailsDTO;
import DTO.CancellationProductDTO;
import DTO.CancellationDTO;
import DTO.SessionManager;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class CancellationEditController {
    @FXML
    private ImageView btnClose;

    @FXML
    private ComboBox<String> cmbProductName;

    @FXML
    private TextField txtProductID, txtProductType, txtCancellationQuantity, txtEmployeeID, txtProductTypeID;

    @FXML
    private DatePicker dtpCancellationDay;

    @FXML
    private TableView<CancellationProductDTO> tblCancellationProducts;

    @FXML
    private TableColumn<CancellationProductDTO, String> colProductName;

    @FXML
    private TableColumn<CancellationProductDTO, Integer> colProductID, colCancellationQuantity,colProductTypeID;

    private CancellationDTO cancellationDTO;
    private CancellationProductBUS cancellationProductBUS = new CancellationProductBUS();
    private CancellationBUS cancellationBUS = new CancellationBUS();
    private ObservableList<CancellationProductDTO> cancellationList = FXCollections.observableArrayList();

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

    public void loadCancellationDetails(int cancellationID) {
        // Lấy chi tiết phiếu hủy từ database bằng ID
        List<CancellationProductDTO> details = cancellationBUS.getCancellationDetails(cancellationID);
        
        if (details != null) {
            cancellationList = FXCollections.observableArrayList(details);
            tblCancellationProducts.setItems(cancellationList);
        }
        
        // Cấu hình các cột trong TableView
        colProductID.setCellValueFactory(data -> new SimpleIntegerProperty(data.getValue().getProductId()).asObject());
        colProductName.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getProductName()));
        colProductTypeID.setCellValueFactory(data -> new SimpleIntegerProperty(data.getValue().getProductTypeID()).asObject());
        colCancellationQuantity.setCellValueFactory(data -> new SimpleIntegerProperty(data.getValue().getQuantity()).asObject());

        dtpCancellationDay.setValue(cancellationDTO.getCancellationDay().toLocalDate());
        txtEmployeeID.setText(String.valueOf(cancellationDTO.getEmployeeID()));

        setupComboBoxWithAutocomplete();
        setupProductSelectionHandler();

        cancellationList = FXCollections.observableArrayList(details);
        tblCancellationProducts.setItems(cancellationList);
    }

    public void setCancellationDTO(CancellationDTO dto) {
        this.cancellationDTO = dto;
        // Gọi hàm loadCancellationDetails ngay sau khi nhận được DTO
        loadCancellationDetails(dto.getCancellationID());
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
    private void btnUpdate_Clicked(MouseEvent event) {
        if (cancellationList.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Lỗi dữ liệu", "Danh sách sản phẩm không được để trống!");
            return;
            }
        try {
            cancellationBUS.updateCancellation(cancellationDTO.getCancellationID(), cancellationList);
            showAlert(Alert.AlertType.INFORMATION, "Thành công", "Phiếu hủy đã được cập nhật thành công!");
            cancellationList.clear();
            tblCancellationProducts.refresh();
        } catch (Exception e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Lỗi", "Có lỗi xảy ra trong quá trình cập nhật phiếu hủy: " + e.getMessage());
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
