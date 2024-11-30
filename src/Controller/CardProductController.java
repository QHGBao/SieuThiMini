package Controller;

import DTO.ProductDTO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
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

    public void setProductInfo(String name, int price, String imageUrl, int maxQuantity, ProductDTO product) {
        productName.setText(name);
        productPrice.setText(String.format("%,d VNĐ", price));
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
        int quantity = productSpinner.getValue();
        if (qlbhController != null && currentProduct != null) {
            qlbhController.addProductToTable(new ProductDTO(
                    currentProduct.getMaSP(),
                    currentProduct.getTenSP(),
                    currentProduct.getMaLoai(),
                    currentProduct.getMoTa(),
                    currentProduct.getGia(),
                    quantity, // Cập nhật số lượng
                    currentProduct.getHinhAnh(),
                    currentProduct.getIsDeleted()), quantity);
        }
    }

}
