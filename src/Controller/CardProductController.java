package Controller;

import BUS.QuanLyGiamGiaSpBUS;
import DTO.ProductDTO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

public class CardProductController {

    @FXML
    private AnchorPane cardForm;

    @FXML
    private Button productAddBTN;

    @FXML
    private ImageView productImage;

    @FXML
    private Label productName;

    @FXML
    private Label productPrice;

    @FXML
    private Spinner<Integer> productSpinner;

    private QLBHController qlbhController; // Tham chiếu đến QLBHController
    private ProductDTO currentProduct; // Sản phẩm hiện tại
    private QuanLyGiamGiaSpBUS quanLyGiamGiaSpBUS = new QuanLyGiamGiaSpBUS();

    public void setProductInfo(String name, int price, String imageUrl, int maxQuantity, ProductDTO product,
            int promotionId) {
        this.currentProduct = product;
        productName.setText(name);
        updatePriceWithPromotion(price, promotionId); // Cập nhật giá dựa trên khuyến mãi
        if (maxQuantity > 0) {
            // Nếu số lượng lớn hơn 0, cho phép chọn số lượng trong Spinner
            SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1,
                    maxQuantity, 1);
            productSpinner.setValueFactory(valueFactory);

            // Thiết lập giá trị mặc định là 1
            productSpinner.getValueFactory().setValue(1);

            // Bật Spinner và nút Add
            productSpinner.setDisable(false);
            productAddBTN.setDisable(false);
        } else {
            // Nếu số lượng bằng 0, hiển thị thông báo "Hết hàng" và vô hiệu hóa Spinner và
            // nút Add
            productSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 0, 0));
            productSpinner.getValueFactory().setValue(0);
            productSpinner.setDisable(true); // Vô hiệu hóa Spinner
            productAddBTN.setDisable(true); // Vô hiệu hóa nút Add

            // Hiển thị thông báo hết hàng thay cho giá
            productPrice.setText("Hết hàng");
        }
        try {
            // Dùng đường dẫn tuyệt đối để tải ảnh
            Image image = new Image("file:" + imageUrl); // Dùng "file:" để chỉ định đây là đường dẫn đến file ảnh
            productImage.setImage(image); // Gán ảnh vào ImageView
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }

        // Thiết lập currentProduct
        this.currentProduct = product;
    }

    private void updatePriceWithPromotion(int originalPrice, int promotionId) {
        if (promotionId != -1 && currentProduct != null) {
            int discountedPrice = quanLyGiamGiaSpBUS.getDiscountedPrice(promotionId, currentProduct.getMaSP());
            if (discountedPrice > 0) {
                productPrice.setText(String.format("%,d VNĐ (giảm giá)", discountedPrice));
                return;
            }
        }
        // Không có giảm giá hoặc không thuộc chương trình khuyến mãi
        productPrice.setText(String.format("%,d VNĐ", originalPrice));
    }

    public String getProductName() {
        return productName.getText();
    }

    public int getProductPrice() {
        return Integer.parseInt(productPrice.getText());
    }

    public Spinner<Integer> getQuantitySpinner() {
        return productSpinner;
    }

    public Button getAddButton() {
        return productAddBTN;
    }

    public void setQLBHController(QLBHController controller) {
        this.qlbhController = controller;
    }

    @FXML
    void handleproductAddBTN(ActionEvent event) {

        Integer quantity = productSpinner.getValue();
        // Kiểm tra nếu spinner không có giá trị (null) hoặc giá trị nhỏ hơn hoặc bằng 0
        if (quantity == null || quantity <= 0) {
            // Hiển thị thông báo lỗi nếu số lượng không hợp lệ
            showError("Số lượng không được để trống và phải lớn hơn 0!");
            return;
        }

        // Kiểm tra số lượng không vượt quá số lượng sản phẩm còn lại
        if (currentProduct != null && quantity > currentProduct.getSoLuong()) {
            // Hiển thị thông báo nếu số lượng vượt quá số lượng sản phẩm còn lại
            showError("Số lượng không thể lớn hơn số lượng sản phẩm còn lại!");
            return;
        }
        if (qlbhController != null && currentProduct != null) {
            
            qlbhController.addProductToTable(new ProductDTO(
                    currentProduct.getMaSP(),
                    currentProduct.getTenSP(),
                    currentProduct.getMaLoai(),
                    currentProduct.getMoTa(),
                    Integer.parseInt(productPrice.getText().replaceAll("[^0-9]", "")),
                    quantity, // Cập nhật số lượng
                    currentProduct.getHinhAnh(),
                    currentProduct.getIsDeleted()), quantity);
        }
    }

    private void showError(String message) {
        // Hiển thị thông báo lỗi (có thể sử dụng Alert hoặc Label tùy ý)
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Lỗi");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

}
