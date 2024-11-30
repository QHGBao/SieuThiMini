package Controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.print.PrinterJob;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Spinner;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.collections.ListChangeListener;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
//import java.util.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import DAO.*;
import DTO.*;
import BUS.*;

public class QLBHController implements javafx.fxml.Initializable {

    @FXML
    private TableColumn<ProductDTO, Integer> sellColPrice;

    @FXML
    private TableColumn<ProductDTO, String> sellColProduct;

    @FXML
    private TableColumn<ProductDTO, Integer> sellColQuantity;

    @FXML
    private AnchorPane sellForm;

    @FXML
    private GridPane sellGridPane;

    @FXML
    private Button sellInBTN;

    @FXML
    private ScrollPane sellScrollPane;

    @FXML
    private TableView<ProductDTO> sellTableView;

    @FXML
    private Label sellThanhTien;

    @FXML
    private Button sellThanhToanBTN;

    @FXML
    private Label sellTienGiam;

    @FXML
    private TextField sellTienKhachTra;

    @FXML
    private Label sellTienTraLai;

    @FXML
    private Label sellTongTien;

    @FXML
    private Button sellXoaBTN;

    @FXML
    private Button sellSearchBTN;

    @FXML
    private TextField sellSearchBar;

    @FXML
    private ComboBox<String> sellSearchCB;

    @FXML
    private TextField sellSoDienThoai;

    @FXML
    private ComboBox<String> sellHinhThucCB;

    @FXML
    private Label sellMaNV;

    @FXML
    private Label sellTenNV;

    private ObservableList<ProductDTO> sellItems = FXCollections.observableArrayList();

    private ProductTypeDAO productTypeDAO = new ProductTypeDAO();

    private ProductDAO productDAO = new ProductDAO();

    private KhachHangBUS khachhangBUS = new KhachHangBUS();

    private HoaDonDAO hoadonDAO = new HoaDonDAO();

    private HoaDonBUS hoaDonBUS = new HoaDonBUS();

    private NhanVienBUS nhanVienBUS = new NhanVienBUS();

    private NhanVienDTO nv;

    public void setNV(NhanVienDTO nv) {
        sellMaNV.setText(String.valueOf(nv.getMaNV())); 
        sellTenNV.setText(nv.getTenNV());
    }

    @SuppressWarnings("static-access")
    public void showProduct(List<ProductDTO> products) {
        sellGridPane.getChildren().clear(); // Xóa hết các sản phẩm hiện tại trong GridPane

        int column = 0;
        int row = 1;

        try {
            for (ProductDTO product : products) {
                // Tải FXML của mỗi sản phẩm (Card Product)
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/CardProductGUI.fxml"));
                AnchorPane productCard = loader.load();

                // Lấy controller của Card Product
                CardProductController cardController = loader.getController();
                cardController.setProductInfo(product.getTenSP(), product.getGia(),
                        "D:/Code/SieuThiMini/Assets/Img/Product/" + product.getHinhAnh(), product.getSoLuong(),
                        product);

                cardController.setQLBHController(this);

                // Thêm mỗi card sản phẩm vào GridPane
                sellGridPane.add(productCard, column++, row);
                sellGridPane.setMinSize(Region.USE_COMPUTED_SIZE, Region.USE_COMPUTED_SIZE);
                sellGridPane.setPrefSize(Region.USE_COMPUTED_SIZE, Region.USE_COMPUTED_SIZE);
                sellGridPane.setMaxSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);
                sellGridPane.setMargin(productCard, new Insets(10));

                // Nếu 2 sản phẩm trên mỗi hàng thì chuyển sang hàng mới
                if (column == 2) {
                    column = 0;
                    row++;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void handleSearchBTN(ActionEvent event) {
        String keyword = sellSearchBar.getText().trim(); // Lấy từ khóa tìm kiếm từ thanh nhập liệu
        List<ProductDTO> searchedProducts = productDAO.searchProductsByName(keyword); // Tìm sản phẩm theo tên

        // Gọi phương thức để hiển thị các sản phẩm tìm được lên GridPane
        showProduct(searchedProducts);
    }

    @FXML
    void handleSearchCB(ActionEvent event) {

    }

    @FXML
    void handleThanhToanBTN(ActionEvent event) {
        if (sellHinhThucCB.getSelectionModel().isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Chưa chọn hình thức", "Hãy chọn hình thức thanh toán.");
            return;
        }

        java.util.Date today = new java.util.Date();
        Date sqlDate = new Date(today.getTime());
        // 1. Lấy thông tin từ các trường nhập liệu
        String sdt = sellSoDienThoai.getText(); // hoặc bất kỳ thông tin khách hàng nào bạn cần
        int tongTien = (int) Double.parseDouble(sellTongTien.getText().replace(" VNĐ", "").replace(",", ""));
        int thanhTien = (int) Double.parseDouble(sellThanhTien.getText().replace(" VNĐ", "").replace(",", ""));
        int tienGiam = (int) Double.parseDouble(sellTienGiam.getText().replace(" VNĐ", "").replace(",", ""));
        int tienKhachTra = (int) Double.parseDouble(sellTienKhachTra.getText().replace(" VNĐ", "").replace(",", ""));
        int tienTraLai = (int) Double.parseDouble(sellTienTraLai.getText().replace(" VNĐ", "").replace(",", ""));
        int maKH = khachhangBUS.getMaKHBySDT(sdt);
        int maNV = Integer.parseInt(sellMaNV.getText());
        // 2. Tạo đối tượng hóa đơn
        HoaDonDTO hoaDon = new HoaDonDTO();
        hoaDon.setNgayLap(sqlDate);
        hoaDon.setHinhThuc(sellHinhThucCB.getSelectionModel().getSelectedItem());
        hoaDon.setTongTien(tongTien);
        hoaDon.setTienGiam(tienGiam);
        hoaDon.setThanhTien(thanhTien);
        hoaDon.setTienKhachDua(tienKhachTra);
        hoaDon.setTienTraLai(tienTraLai);
        hoaDon.setMaNV(maNV);
        hoaDon.setMaKH(maKH);
        // 3. Gọi hàm thêm hóa đơn vào database
        int invoiceId = hoadonDAO.addHoaDon(hoaDon); // Hàm này trả về ID của hóa đơn đã được tạo trong DB

        // 4. Thêm chi tiết hóa đơn vào database
        for (ProductDTO product : sellItems) {
            // Tạo đối tượng chi tiết hóa đơn cho mỗi sản phẩm
            CTHoaDonDTO ctHoaDon = new CTHoaDonDTO();
            ctHoaDon.setMaHD(invoiceId); // Mã hóa đơn từ bước trước
            ctHoaDon.setMaSP(product.getMaSP()); // ID sản phẩm
            ctHoaDon.setSoLuong(product.getSoLuong()); // Số lượng sản phẩm
            ctHoaDon.setGiaBan(product.getGia()); // Giá sản phẩm

            // Gọi hàm thêm chi tiết hóa đơn vào database
            hoaDonBUS.addCTHoaDon(ctHoaDon);
        }

        // 5. Thông báo hoàn thành và reset bảng thanh toán
        showAlert(Alert.AlertType.INFORMATION, "Thông báo", "Thanh toán thành công!");

        // Reset thông tin sau thanh toán
        sellItems.clear(); // Xóa danh sách sản phẩm trong bảng
        updateTotalPrice(); // Cập nhật lại tổng tiền về 0
        sellTienKhachTra.clear(); // Xóa trường tiền khách trả
        sellTienTraLai.setText("0 VNĐ"); // Xóa trường tiền trả lại
        sellTienGiam.setText("0 VNĐ"); // Xóa trường tiền giảm
        sellThanhTien.setText("0 VNĐ"); // Xóa trường thành tiền
    }

    @FXML
    void handleXoaBTN(ActionEvent event) {
        // Lấy dòng được chọn trong TableView
        ProductDTO selectedProduct = sellTableView.getSelectionModel().getSelectedItem();

        if (selectedProduct != null) {
            // Xác nhận việc xóa từ người dùng
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Xóa sản phẩm");
            alert.setHeaderText("Bạn có chắc chắn muốn xóa sản phẩm này?");

            // Xử lý sự kiện khi người dùng xác nhận
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                // Xóa sản phẩm khỏi TableView
                sellTableView.getItems().remove(selectedProduct);
            }
        } else {
            showAlert(Alert.AlertType.WARNING, "Chưa chọn sản phẩm", "Hãy chọn sản phẩm cần xóa.");
        }
    }

    @FXML
    void handleInBTN(ActionEvent event) {
        // Tạo nội dung hóa đơn cần in
        String invoiceContent = createInvoiceContent();

        // Hiển thị hộp thoại in
        PrinterJob printerJob = PrinterJob.createPrinterJob();
        if (printerJob != null && printerJob.showPrintDialog(sellForm.getScene().getWindow())) {
            // Tạo đối tượng Node (Label) chứa nội dung hóa đơn
            Label invoiceLabel = new Label(invoiceContent);
            invoiceLabel.setPadding(new Insets(20));
            invoiceLabel.setStyle("-fx-font-size: 12px; -fx-font-family: Arial;");

            // Thực hiện in
            boolean success = printerJob.printPage(invoiceLabel);
            if (success) {
                printerJob.endJob();
                showAlert(Alert.AlertType.INFORMATION, "In hóa đơn", "Hóa đơn đã được in thành công!");
            } else {
                showAlert(Alert.AlertType.ERROR, "In hóa đơn", "Không thể in hóa đơn.");
            }
        }
    }

    @FXML
    void handleSoDienThoaiBTN(ActionEvent event) {
        String phoneNumber = sellSoDienThoai.getText().trim();
        if (isValidPhoneNumber(phoneNumber)) {
            int Points = khachhangBUS.getDiemTichLuyBySoDienThoai(phoneNumber);
            // showAlert(AlertType.INFORMATION, "Thông báo", "Số điện thoại hợp lệ!");
            if (Points >= 0) {
                // Nếu tìm thấy khách hàng, quy đổi điểm thưởng thành tiền giảm giá
                int discountAmount = convertPointsToDiscount(Points);
                sellTienGiam.setText(String.format("%,d VNĐ", discountAmount));

                showAlert(Alert.AlertType.INFORMATION, "Thông báo",
                        "Khách hàng có " + Points + " điểm thưởng.\nGiảm giá: " + discountAmount + " VNĐ");
            } else {
                // Không tìm thấy khách hàng
                sellTienGiam.setText("0 VNĐ");
                showAlert(Alert.AlertType.WARNING, "Thông báo", "Không tìm thấy khách hàng với số điện thoại này.");
            }
        } else {
            sellTienGiam.setText("0 VNĐ");
            showAlert(AlertType.ERROR, "Lỗi", "Số điện thoại không hợp lệ!");
        }
    }

    private String createInvoiceContent() {
        StringBuilder sb = new StringBuilder();

        sb.append("======= HÓA ĐƠN BÁN HÀNG =======\n");
        sb.append("Số điện thoại: ").append(sellSoDienThoai.getText()).append("\n");
        sb.append("--------------------------------\n");
        sb.append(String.format("%-20s %-10s %-10s\n", "Sản phẩm", "SL", "Giá (VNĐ)"));
        sb.append("--------------------------------\n");

        for (ProductDTO product : sellItems) {
            sb.append(String.format("%-20s %-10d %,.0d\n", product.getTenSP(), product.getSoLuong(), product.getGia()));
        }

        sb.append("--------------------------------\n");
        sb.append("Tổng tiền: ").append(sellTongTien.getText()).append("\n");
        sb.append("Tiền giảm: ").append(sellTienGiam.getText()).append("\n");
        sb.append("Thành tiền: ").append(sellThanhTien.getText()).append("\n");
        sb.append("Tiền khách trả: ").append(sellTienKhachTra.getText()).append("\n");
        sb.append("Tiền trả lại: ").append(sellTienTraLai.getText()).append("\n");
        sb.append("================================\n");
        sb.append("Cảm ơn Quý khách đã mua hàng!\n");

        return sb.toString();
    }

    private boolean isValidPhoneNumber(String phoneNumber) {
        // Kiểm tra số điện thoại phải có 10 chữ số
        return phoneNumber.matches("\\d{10}");
    }

    // Hàm quy đổi điểm thưởng thành tiền giảm giá
    private int convertPointsToDiscount(int points) {
        int conversionRate = 1000; // Ví dụ: 1 điểm = 1000 VNĐ
        return points * conversionRate;
    }

    private void showAlert(AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void loadProductTypes() {
        List<String> productTypes = productTypeDAO.getAllProductTypesName(); // Gọi đến Service để lấy danh sách loại
                                                                             // sản phẩm
        ObservableList<String> observableProductTypes = FXCollections.observableArrayList(productTypes); // Chuyển đổi
                                                                                                         // thành
                                                                                                         // ObservableList
        sellSearchCB.setItems(observableProductTypes); // Đưa danh sách vào ComboBox
    }

    public void addProductToTable(ProductDTO product, int quantity) {
        // Cập nhật số lượng và giá của sản phẩm
        int totalPrice = product.getGia() * quantity;
        product.setSoLuong(quantity);
        product.setGia(totalPrice);

        sellItems.add(product);
    }

    private void updateTotalPrice() {
        int total = 0;

        // Duyệt qua tất cả các sản phẩm trong ObservableList và tính tổng tiền
        for (ProductDTO product : sellItems) {
            total += product.getGia(); // Tổng tiền = giá của sản phẩm (đã tính giá x số lượng)
        }

        // Cập nhật giá trị tổng tiền vào label
        sellTongTien.setText(String.format("%,d VNĐ", total));
    }

    @FXML
    void updateThanhTien() {
        try {
            // Lấy giá trị từ label sellTongTien và sellTienGiam, loại bỏ ký tự VNĐ, chuyển
            // sang số thực (double)
            int tongTien = (int) Double.parseDouble(sellTongTien.getText().replace(" VNĐ", "").replace(",", ""));
            int tienGiam = (int) Double.parseDouble(sellTienGiam.getText().replace(" VNĐ", "").replace(",", ""));

            // Tính toán thành tiền sau khi giảm
            int thanhTien = tongTien - tienGiam;

            // Hiển thị kết quả lên label sellThanhTien
            sellThanhTien.setText(String.format("%,d VNĐ", thanhTien));
        } catch (NumberFormatException e) {
            // Xử lý nếu có lỗi khi chuyển đổi giá trị
            sellThanhTien.setText("0 VNĐ");
            showAlert(Alert.AlertType.ERROR, "Lỗi", "Dữ liệu không hợp lệ!");
        }
    }

    @FXML
    void updateTienTraLai() {
        try {
            // Lấy giá trị từ label sellTongTien và sellTienGiam, loại bỏ ký tự VNĐ, chuyển
            // sang số thực (double)
            int thanhTien = (int) Double.parseDouble(sellThanhTien.getText().replace(" VNĐ", "").replace(",", ""));
            int tienKhachTra = (int) Double.parseDouble(sellTienKhachTra.getText().replace(" VNĐ", "").replace(",", ""));

            // Tính toán thành tiền sau khi giảm
            int tienTraLai = tienKhachTra - thanhTien;

            // Hiển thị kết quả lên label sellThanhTien
            sellTienTraLai.setText(String.format("%,d VNĐ", tienTraLai));
        } catch (NumberFormatException e) {
            // Xử lý nếu có lỗi khi chuyển đổi giá trị
            sellTienTraLai.setText("0 VNĐ");
            // showAlert(Alert.AlertType.ERROR, "Lỗi", "Dữ liệu không hợp lệ!");
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        showProduct(productDAO.getAllProducts());
        sellHinhThucCB.setItems(FXCollections.observableArrayList("Tien mat", "Chuyen khoan"));
        sellSearchBar.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                handleSearchBTN(null);
            }
        });
        loadProductTypes();

        sellColProduct.setCellValueFactory(new PropertyValueFactory<>("tenSP"));
        sellColQuantity.setCellValueFactory(new PropertyValueFactory<>("soLuong"));
        sellColPrice.setCellValueFactory(new PropertyValueFactory<>("gia"));

        sellTableView.setItems(sellItems);

        // Sử dụng ListChangeListener với sellItems để theo dõi sự thay đổi
        sellItems.addListener(new ListChangeListener<ProductDTO>() {
            @Override
            public void onChanged(Change<? extends ProductDTO> change) {
                updateTotalPrice(); // Cập nhật tổng tiền mỗi khi có sự thay đổi
            }
        });

        // Cập nhật tổng tiền ngay khi ứng dụng bắt đầu
        updateTotalPrice();

        // Listener theo dõi sự thay đổi giá trị của sellTongTien và sellTienGiam
        sellTongTien.textProperty().addListener((observable, oldValue, newValue) -> updateThanhTien());
        sellTienGiam.textProperty().addListener((observable, oldValue, newValue) -> updateThanhTien());
        sellTienKhachTra.textProperty().addListener((observable, oldValue, newValue) -> updateTienTraLai());
        sellThanhTien.textProperty().addListener((observable, oldValue, newValue) -> updateThanhTien());
    }

}
