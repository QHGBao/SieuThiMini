package Controller;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.print.PrinterJob;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.converter.LocalDateStringConverter;
import javafx.collections.ListChangeListener;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;

import DTO.*;
import BUS.*;

public class QLBHController implements javafx.fxml.Initializable {

    @FXML
    private ComboBox<String> sellKMCB;

    @FXML
    private TextField sellSearchLSBar;

    @FXML
    private DatePicker startLSDatePicker;

    @FXML
    private DatePicker endLSDatePicker;

    @FXML
    private ComboBox<String> searchLichSuCB;

    @FXML
    private TableView<LichSuDiemDTO> LichSuDiemTable;

    @FXML
    private TableColumn<LichSuDiemDTO, Integer> colLSDiem;

    @FXML
    private TableColumn<LichSuDiemDTO, String> colLSLoaiGiaoDich;

    @FXML
    private TableColumn<LichSuDiemDTO, Integer> colLSMaDD;

    @FXML
    private TableColumn<LichSuDiemDTO, Integer> colLSMaHD;

    @FXML
    private TableColumn<LichSuDiemDTO, Integer> colLSMaKH;

    @FXML
    private TableColumn<LichSuDiemDTO, Date> colLSNgayTichLuy;

    @FXML
    private DatePicker startHDDatePicker;

    @FXML
    private DatePicker endHDDatePicker;

    @FXML
    private TextField sellSearchHDBar;

    @FXML
    private ComboBox<String> sellSearchHoaDonCB;

    @FXML
    private TableView<HoaDonDTO> HoaDonTable;

    @FXML
    private TableColumn<HoaDonDTO, Integer> colMaHD;

    @FXML
    private TableColumn<HoaDonDTO, Date> colNgayLap;

    @FXML
    private TableColumn<HoaDonDTO, String> colHinhThuc;

    @FXML
    private TableColumn<HoaDonDTO, Integer> colTongTien;

    @FXML
    private TableColumn<HoaDonDTO, Integer> colTienGiam;

    @FXML
    private TableColumn<HoaDonDTO, Integer> colThanhTien;

    @FXML
    private TableColumn<HoaDonDTO, Integer> colTienKhachDua;

    @FXML
    private TableColumn<HoaDonDTO, Integer> colTienTraLai;

    @FXML
    private TableColumn<HoaDonDTO, Integer> colMaNV;

    @FXML
    private TableColumn<HoaDonDTO, Integer> colMaKH;

    @FXML
    private TableView<DoiTienThanhDiemDTO> tienToDiemTable;

    @FXML
    private TableColumn<DoiTienThanhDiemDTO, Integer> colMaDT;

    @FXML
    private TableColumn<DoiTienThanhDiemDTO, Integer> colMucTienMin;

    @FXML
    private TableColumn<DoiTienThanhDiemDTO, Integer> colMucDiemDT;

    @FXML
    private TableColumn<DoiDiemThanhTienDTO, Integer> colMaDD;

    @FXML
    private TableColumn<DoiDiemThanhTienDTO, Integer> colMucDiemDD;

    @FXML
    private TableColumn<DoiDiemThanhTienDTO, Integer> colMucTienMax;

    @FXML
    private TableView<DoiDiemThanhTienDTO> diemToTienTable;

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

    @FXML
    private TextField ddttDiemTF;

    @FXML
    private TextField ddttTienTF;

    @FXML
    private TextField ddttDiemApDungTF;

    @FXML
    private TextField ddttTienApDungTF;

    @FXML
    private TextField dttdTienTF;

    @FXML
    private TextField dttdDiemTF;

    @FXML
    private TextField dttdTienApDungTF;

    @FXML
    private TextField dttdDiemApDungTF;

    private ObservableList<ProductDTO> sellItems = FXCollections.observableArrayList();

    private ProductTypeBUS productTypeBUS = new ProductTypeBUS();

    private ProductBUS productBUS = new ProductBUS();

    private KhachHangBUS khachhangBUS = new KhachHangBUS();

    private HoaDonBUS hoaDonBUS = new HoaDonBUS();

    private LichSuDiemBUS lichSuDiemBUS = new LichSuDiemBUS();

    private DoiDiemThanhTienBUS doiDiemThanhTienBUS = new DoiDiemThanhTienBUS();

    private DoiTienThanhDiemBUS doiTienThanhDiemBUS = new DoiTienThanhDiemBUS();

    private TempDataBUS tempDataBUS = new TempDataBUS();

    private QuanLyGiamGiaSpBUS quanLyGiamGiaSpBUS = new QuanLyGiamGiaSpBUS();

    // private NhanVienBUS nhanVienBUS = new NhanVienBUS();

    private NhanVienDTO nv;

    private ObservableList<DoiDiemThanhTienDTO> dataDoiDiemThanhTien; // Khai báo data cho bảng đổi điểm thành tiền

    private ObservableList<DoiTienThanhDiemDTO> dataDoiTienThanhDiem;

    private ObservableList<HoaDonDTO> dataHoaDon;

    private int lastInvoiceId = -1; // -1 để xác định không có hóa đơn

    @FXML
    void handleKMCB(ActionEvent event) {
        showProduct(productBUS.getAllProducts());
    }

    @FXML
    void handlleSearchLSBTN(ActionEvent event) {
        int maHD = -1; // Khởi tạo mã hóa đơn mặc định
        int maKH = -1; // Khởi tạo mã khách hàng mặc định

        // Lấy giá trị từ TextField
        String searchValue = sellSearchLSBar.getText().trim();

        // Kiểm tra lựa chọn tìm kiếm từ ComboBox
        String selectedOption = searchLichSuCB.getValue(); // Lấy giá trị từ ComboBox
        if (selectedOption != null) {
            if (selectedOption.equals("Mã hóa đơn")) {
                try {
                    if (!searchValue.isEmpty()) {
                        maHD = Integer.parseInt(searchValue);
                    }
                } catch (NumberFormatException e) {
                    maHD = -1; // Nếu không phải số hợp lệ, đặt maHD = -1
                }
            } else if (selectedOption.equals("Mã khách hàng")) {
                try {
                    if (!searchValue.isEmpty()) {
                        maKH = Integer.parseInt(searchValue);
                    }
                } catch (NumberFormatException e) {
                    maKH = -1; // Nếu không phải số hợp lệ, đặt maKH = -1
                }
            }
        }

        // Lấy ngày bắt đầu và kết thúc từ DatePicker
        java.sql.Date startDate = null;
        java.sql.Date endDate = null;

        if ((startLSDatePicker.getValue() != null && endLSDatePicker.getValue() == null) ||
                (startLSDatePicker.getValue() == null && endLSDatePicker.getValue() != null)) {
            showAlert(Alert.AlertType.WARNING, "Thiếu thông tin", "Vui lòng nhập cả ngày bắt đầu và ngày kết thúc.");
            return;
        }

        if (startLSDatePicker.getValue() != null && endLSDatePicker.getValue() != null &&
                startLSDatePicker.getValue().isAfter(endLSDatePicker.getValue())) {
            showAlert(Alert.AlertType.WARNING, "Thông tin sai", "Ngày bắt đầu phải nhỏ hơn hoặc bằng ngày kết thúc.");
            return;
        }

        if (startLSDatePicker.getValue() != null && endLSDatePicker.getValue() != null) {
            startDate = java.sql.Date.valueOf(startLSDatePicker.getValue());
            endDate = java.sql.Date.valueOf(endLSDatePicker.getValue());
        }

        // Gọi hàm tìm kiếm với các tham số mã hóa đơn và mã khách hàng
        List<LichSuDiemDTO> results = lichSuDiemBUS.searchLichSuDiem(maKH, maHD, startDate, endDate);

        // Hiển thị danh sách kết quả trong TableView
        loadDataLichSuDiem(results);
    }

    @FXML
    void handlleOpenChiTietHoaDonBTN(ActionEvent event) {
        HoaDonDTO selectedHoaDon = HoaDonTable.getSelectionModel().getSelectedItem();

        if (selectedHoaDon != null) {
            int maHD = selectedHoaDon.getMaHD(); // Lấy Mã Hóa Đơn từ hàng được chọn
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/ChiTietHoaDonGUI.fxml"));
                Parent root = loader.load();

                // Truyền dữ liệu vào controller
                ChiTietHoaDonController controller = loader.getController();
                controller.loadChiTietHoaDon(maHD); // Gọi hàm load chi tiết hóa đơn

                Stage stage = new Stage();
                stage.setTitle("Chi Tiết Hóa Đơn");

                // Tạo scene và áp dụng nền trong suốt
                Scene scene = new Scene(root);
                scene.setFill(Color.TRANSPARENT); // Đặt nền trong suốt cho scene

                stage.setScene(scene);
                stage.initStyle(StageStyle.UNDECORATED);
                stage.initStyle(StageStyle.TRANSPARENT); // Cửa sổ không có viền
                stage.setResizable(false);
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            showAlert(Alert.AlertType.WARNING, "Chưa hóa đơn", "Vui lòng chọn hóa đơn cần xem.");
        }
    }

    @FXML
    void handlleSearchHDBTN(ActionEvent event) {
        String selectedOption = sellSearchHoaDonCB.getValue();
        int maHD = -1;
        int maKH = -1;

        // Lấy giá trị từ TextField
        String searchValue = sellSearchHDBar.getText().trim();

        if (selectedOption != null) {
            if (selectedOption.equals("Mã hóa đơn")) {
                try {
                    if (!searchValue.isEmpty()) {
                        maHD = Integer.parseInt(searchValue);
                    }
                } catch (NumberFormatException e) {
                    maHD = -1; // Nếu không phải số hợp lệ, đặt maHD = -1
                }
            } else if (selectedOption.equals("Mã khách hàng")) {
                try {
                    if (!searchValue.isEmpty()) {
                        maKH = Integer.parseInt(searchValue);
                    }
                } catch (NumberFormatException e) {
                    maKH = -1; // Nếu không phải số hợp lệ, đặt maKH = -1
                }
            }
        }

        // Lấy ngày bắt đầu và kết thúc từ DatePicker
        java.sql.Date startDate = null;
        java.sql.Date endDate = null;

        if ((startHDDatePicker.getValue() != null && endHDDatePicker.getValue() == null) ||
                (startHDDatePicker.getValue() == null && endHDDatePicker.getValue() != null)) {
            showAlert(Alert.AlertType.WARNING, "Thiếu thông tin", "Vui lòng nhập cả ngày bắt đầu và ngày kết thúc.");
            return;
        }

        if (startHDDatePicker.getValue() != null && endHDDatePicker.getValue() != null &&
                startHDDatePicker.getValue().isAfter(endHDDatePicker.getValue())) {
            showAlert(Alert.AlertType.WARNING, "Thông tin sai", "Ngày bắt đầu phải nhỏ hơn hoặc bằng ngày kết thúc.");
            return;
        }

        if (startHDDatePicker.getValue() != null && endHDDatePicker.getValue() != null) {
            startDate = java.sql.Date.valueOf(startHDDatePicker.getValue());
            endDate = java.sql.Date.valueOf(endHDDatePicker.getValue());
        }
        System.out.println(maHD);
        System.out.println(maHD);
        System.out.println(startDate);
        System.out.println(endDate);

        // Gọi hàm tìm kiếm với các tham số đã lấy
        ArrayList<HoaDonDTO> results = hoaDonBUS.searchHoaDon(maKH, maHD, startDate, endDate);
        loadDataHoaDon(results);

    }

    @FXML
    void handleApDungDTBTN(ActionEvent event) {
        DoiTienThanhDiemDTO selectedItem = tienToDiemTable.getSelectionModel().getSelectedItem();

        if (selectedItem == null) {
            showAlert(Alert.AlertType.WARNING, "Chưa chọn mục", "Vui lòng chọn mục cần áp dụng.");
            return;
        }

        dttdTienApDungTF.setText(String.valueOf(selectedItem.getMucTienMin()));
        dttdDiemApDungTF.setText(String.valueOf(selectedItem.getMucDiem()));

        int dttdDiemApDung = Integer.parseInt(dttdDiemApDungTF.getText());
        int dttdTienApDung = Integer.parseInt(dttdTienApDungTF.getText());

        tempDataBUS.updateTienThanhDiem(dttdTienApDung, dttdDiemApDung);
    }

    @FXML
    void handleThemDTBTN(ActionEvent event) {
        try {
            // Lấy dữ liệu từ các trường nhập liệu (TextField)

            if (dttdTienTF.getText().isEmpty() || dttdDiemTF.getText().isEmpty()) {
                showAlert(Alert.AlertType.ERROR, "Lỗi", "Các trường không được để trống.");
                return; // Thoát khỏi hàm nếu có trường bị trống
            }

            int mucTienMin = Integer.parseInt(dttdTienTF.getText());

            int mucDiem = Integer.parseInt(dttdDiemTF.getText());

            if (mucDiem <= 0 || mucTienMin <= 0) {
                showAlert(Alert.AlertType.WARNING, "Dữ liệu không hợp lệ", "Điểm và tiền tối thiểu phải lớn hơn 0.");
                return; // Thoát khỏi hàm nếu dữ liệu không hợp lệ
            }

            // Tạo đối tượng DTO và gọi phương thức add để thêm
            DoiTienThanhDiemDTO newDTTD = new DoiTienThanhDiemDTO();

            newDTTD.setMucTienMin(mucTienMin);
            newDTTD.setMucDiem(mucDiem);

            // Thực hiện thêm dữ liệu vào database thông qua BUS
            boolean success = doiTienThanhDiemBUS.addDoiTienThanhDiem(newDTTD);

            if (success) {
                // Cập nhật bảng dữ liệu sau khi thêm
                tienToDiemTable.getItems().clear();

                loadDataDoiTienThanhDiem();
                showAlert(Alert.AlertType.INFORMATION, "Thành công", "Thêm thành công.");
            } else {
                showAlert(Alert.AlertType.WARNING, "Thất bại", "Thêm không thành công.");
            }
        } catch (NumberFormatException e) {
            // Xử lý khi nhập dữ liệu không phải số
            showAlert(Alert.AlertType.ERROR, "Lỗi nhập liệu", "Vui lòng nhập số hợp lệ.");
        }
    }

    @FXML
    void handleXoaDTBTN(ActionEvent event) {
        // Kiểm tra xem có dòng nào được chọn không
        DoiTienThanhDiemDTO selectedDTTD = tienToDiemTable.getSelectionModel().getSelectedItem();
        if (selectedDTTD == null) {
            showAlert(Alert.AlertType.WARNING, "Cảnh báo", "Vui lòng chọn mục cần xóa.");
            return;
        }

        // Xác nhận trước khi xóa
        Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmAlert.setTitle("Xác nhận");
        confirmAlert.setHeaderText("Bạn có chắc chắn muốn xóa không?");
        confirmAlert.setContentText("Dữ liệu sẽ bị xóa khỏi hệ thống.");

        if (confirmAlert.showAndWait().get() == ButtonType.OK) {
            // Thực hiện xóa dữ liệu thông qua BUS
            boolean success = doiTienThanhDiemBUS.deleteDoiTienThanhDiem(selectedDTTD.getMaDT());

            if (success) {
                // Cập nhật lại dữ liệu trên bảng
                tienToDiemTable.getItems().clear();
                loadDataDoiTienThanhDiem();
                showAlert(Alert.AlertType.INFORMATION, "Thành công", "Xóa thành công.");
            } else {
                showAlert(Alert.AlertType.WARNING, "Thất bại", "Xóa không thành công.");
            }
        }
    }

    @FXML
    void handleSuaDTBTN(ActionEvent event) {
        try {
            // Kiểm tra xem có dòng nào được chọn không
            DoiTienThanhDiemDTO selectedDTTD = tienToDiemTable.getSelectionModel().getSelectedItem();
            if (selectedDTTD == null) {
                showAlert(Alert.AlertType.WARNING, "Cảnh báo", "Vui lòng chọn mục cần sửa.");
                return;
            }

            // Kiểm tra dữ liệu nhập
            if (dttdTienTF.getText().isEmpty() || dttdDiemTF.getText().isEmpty()) {
                showAlert(Alert.AlertType.ERROR, "Lỗi", "Các trường không được để trống.");
                return;
            }

            int mucTienMin = Integer.parseInt(dttdTienTF.getText());
            int mucDiem = Integer.parseInt(dttdDiemTF.getText());

            if (mucDiem <= 0 || mucTienMin <= 0) {
                showAlert(Alert.AlertType.WARNING, "Dữ liệu không hợp lệ", "Điểm và tiền tối thiểu phải lớn hơn 0.");
                return;
            }

            // Cập nhật đối tượng DTO
            selectedDTTD.setMucTienMin(mucTienMin);
            selectedDTTD.setMucDiem(mucDiem);

            // Thực hiện cập nhật dữ liệu vào database thông qua BUS
            boolean success = doiTienThanhDiemBUS.updateDoiTienThanhDiem(selectedDTTD);

            if (success) {
                // Cập nhật lại dữ liệu trên bảng
                tienToDiemTable.getItems().clear();
                loadDataDoiTienThanhDiem();
                showAlert(Alert.AlertType.INFORMATION, "Thành công", "Sửa thành công.");
                dttdTienTF.clear();
                dttdDiemTF.clear();
            } else {
                showAlert(Alert.AlertType.WARNING, "Thất bại", "Sửa không thành công.");
            }
        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "Lỗi nhập liệu", "Vui lòng nhập số hợp lệ.");
        }
    }

    @FXML
    void handleApDungDDBTN(ActionEvent event) {
        // Lấy mục được chọn từ bảng
        DoiDiemThanhTienDTO selectedItem = diemToTienTable.getSelectionModel().getSelectedItem();

        if (selectedItem == null) {
            showAlert(Alert.AlertType.WARNING, "Chưa chọn mục", "Vui lòng chọn mục cần áp dụng.");
            return;
        }

        ddttDiemApDungTF.setText(String.valueOf(selectedItem.getMucDiem()));
        ddttTienApDungTF.setText(String.valueOf(selectedItem.getMucTienMax()));

        int ddttDiemApDung = Integer.parseInt(ddttDiemApDungTF.getText());
        int ddttTienApDung = Integer.parseInt(ddttTienApDungTF.getText());

        tempDataBUS.updateDiemThanhTien(ddttDiemApDung, ddttTienApDung);

    }

    @FXML
    void handleThemDDBTN(ActionEvent event) {
        try {

            if (ddttDiemTF.getText().isEmpty() || ddttTienTF.getText().isEmpty()) {
                showAlert(Alert.AlertType.ERROR, "Lỗi", "Các trường không được để trống.");
                return; // Thoát khỏi hàm nếu có trường bị trống
            }

            // Lấy dữ liệu từ các trường nhập liệu (TextField)
            int mucDiem = Integer.parseInt(ddttDiemTF.getText()); // Kiểm tra trường nhập liệu là TextField chứ không
                                                                  // phải Label
            int mucTienMax = Integer.parseInt(ddttTienTF.getText());

            if (mucDiem <= 0 || mucTienMax <= 0) {
                showAlert(Alert.AlertType.WARNING, "Dữ liệu không hợp lệ", "Điểm và tiền tối đa phải lớn hơn 0.");
                return; // Thoát khỏi hàm nếu dữ liệu không hợp lệ
            }

            // Tạo đối tượng DTO và gọi phương thức add để thêm
            DoiDiemThanhTienDTO newDDTT = new DoiDiemThanhTienDTO();
            newDDTT.setMucDiem(mucDiem);
            newDDTT.setMucTienMax(mucTienMax);

            // Thực hiện thêm dữ liệu vào database thông qua BUS
            boolean success = doiDiemThanhTienBUS.addDoiDiemThanhTien(newDDTT);

            if (success) {
                // Cập nhật bảng dữ liệu sau khi thêm
                diemToTienTable.getItems().clear();

                loadDataDoiDiemThanhTien();
                showAlert(Alert.AlertType.INFORMATION, "Thành công", "Thêm thành công.");
            } else {
                showAlert(Alert.AlertType.WARNING, "Thất bại", "Thêm không thành công.");
            }
        } catch (NumberFormatException e) {
            // Xử lý khi nhập dữ liệu không phải số
            showAlert(Alert.AlertType.ERROR, "Lỗi nhập liệu", "Vui lòng nhập số hợp lệ.");
        }
    }

    @FXML
    void handleXoaDDBTN(ActionEvent event) {
        // Lấy mục được chọn từ bảng
        DoiDiemThanhTienDTO selectedItem = diemToTienTable.getSelectionModel().getSelectedItem();

        if (selectedItem == null) {
            showAlert(Alert.AlertType.WARNING, "Chưa chọn mục", "Vui lòng chọn mục cần xóa.");
            return;
        }

        // Hiển thị thông báo xác nhận
        Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationAlert.setTitle("Xác nhận xóa");
        confirmationAlert.setHeaderText(null);
        confirmationAlert.setContentText("Bạn có chắc chắn muốn xóa mục này?");

        // Nếu người dùng đồng ý xóa
        confirmationAlert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                // Thực hiện xóa dữ liệu qua BUS
                boolean success = doiDiemThanhTienBUS.deleteDoiDiemThanhTien(selectedItem.getMaDD());

                if (success) {
                    diemToTienTable.getItems().remove(selectedItem); // Xóa khỏi TableView
                    showAlert(Alert.AlertType.INFORMATION, "Thành công", "Xóa thành công.");
                } else {
                    showAlert(Alert.AlertType.ERROR, "Thất bại", "Xóa không thành công.");
                }
            }
        });
    }

    @FXML
    void handleSuaDDBTN(ActionEvent event) {
        try {

            DoiDiemThanhTienDTO selectedItem = diemToTienTable.getSelectionModel().getSelectedItem();

            if (selectedItem == null) {
                showAlert(Alert.AlertType.WARNING, "Chưa chọn mục", "Vui lòng chọn mục cần sửa.");
                return;
            }

            // Kiểm tra dữ liệu nhập
            if (ddttTienTF.getText().isEmpty() || ddttDiemTF.getText().isEmpty()) {
                showAlert(Alert.AlertType.ERROR, "Lỗi", "Các trường không được để trống.");
                return;
            }

            int mucDiem = Integer.parseInt(ddttDiemTF.getText());
            int mucTienMax = Integer.parseInt(ddttTienTF.getText());

            if (mucDiem <= 0 || mucTienMax <= 0) {
                showAlert(Alert.AlertType.WARNING, "Dữ liệu không hợp lệ", "Điểm và tiền tối đa phải lớn hơn 0.");
                return;
            }

            // Cập nhật dữ liệu trong đối tượng DTO
            selectedItem.setMucDiem(mucDiem);
            selectedItem.setMucTienMax(mucTienMax);

            // Gọi BUS để cập nhật trong database
            boolean success = doiDiemThanhTienBUS.updateDoiDiemThanhTien(selectedItem);

            if (success) {
                // Cập nhật lại dữ liệu trong TableView
                diemToTienTable.refresh();
                showAlert(Alert.AlertType.INFORMATION, "Thành công", "Sửa thành công.");
            } else {
                showAlert(Alert.AlertType.ERROR, "Thất bại", "Sửa không thành công.");
            }
        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "Lỗi nhập liệu", "Vui lòng nhập số hợp lệ.");
        }
    }

    public void loadDataLichSuDiem(List<LichSuDiemDTO> lichSuDiemList) {
        // Chuyển đổi danh sách thành ObservableList để hiển thị trên TableView
        ObservableList<LichSuDiemDTO> lichSuDiemObservableList = FXCollections.observableArrayList(lichSuDiemList);

        // Thiết lập giá trị cho từng cột trong TableView
        colLSMaDD.setCellValueFactory(data -> new SimpleIntegerProperty(data.getValue().getMaGiaoDich()).asObject());
        colLSMaKH.setCellValueFactory(data -> new SimpleIntegerProperty(data.getValue().getMaKH()).asObject());
        colLSMaHD.setCellValueFactory(data -> new SimpleIntegerProperty(data.getValue().getMaHD()).asObject());
        colLSDiem.setCellValueFactory(data -> new SimpleIntegerProperty(data.getValue().getDiem()).asObject());
        colLSNgayTichLuy.setCellValueFactory(data -> new SimpleObjectProperty<>(data.getValue().getNgayTichLuy()));
        colLSLoaiGiaoDich.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getLoaiGD()));

        // Đưa dữ liệu vào TableView
        LichSuDiemTable.setItems(lichSuDiemObservableList);
    }

    private void loadDataHoaDon(ArrayList<HoaDonDTO> hoaDonList) {
        if (dataHoaDon == null) {
            dataHoaDon = FXCollections.observableArrayList();
        } else {
            dataHoaDon.clear(); // Clear danh sách hiện tại
        }

        // Thêm dữ liệu tìm được vào ObservableList
        dataHoaDon.addAll(hoaDonList);

        // Gán dữ liệu vào TableView
        HoaDonTable.setItems(dataHoaDon);

        // Liên kết các cột với dữ liệu trong HoaDonDTO
        colMaHD.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getMaHD()).asObject());
        colNgayLap.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getNgayLap()));
        colHinhThuc.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getHinhThuc()));
        colTongTien.setCellValueFactory(
                cellData -> new SimpleIntegerProperty(cellData.getValue().getTongTien()).asObject());
        colTienGiam.setCellValueFactory(
                cellData -> new SimpleIntegerProperty(cellData.getValue().getTienGiam()).asObject());
        colThanhTien.setCellValueFactory(
                cellData -> new SimpleIntegerProperty(cellData.getValue().getThanhTien()).asObject());
        colTienKhachDua.setCellValueFactory(
                cellData -> new SimpleIntegerProperty(cellData.getValue().getTienKhachDua()).asObject());
        colTienTraLai.setCellValueFactory(
                cellData -> new SimpleIntegerProperty(cellData.getValue().getTienTraLai()).asObject());
        colMaNV.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getMaNV()).asObject());
        colMaKH.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getMaKH()).asObject());
    }

    private void loadDataDoiTienThanhDiem() {
        // Khởi tạo ObservableList nếu chưa
        if (dataDoiTienThanhDiem == null) {
            dataDoiTienThanhDiem = FXCollections.observableArrayList();
        }

        // Liên kết cột với các thuộc tính trong DoiDiemThanhTienDTO
        colMaDT.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getMaDT()).asObject());
        colMucDiemDT.setCellValueFactory(
                cellData -> new SimpleIntegerProperty(cellData.getValue().getMucDiem()).asObject());
        colMucTienMin.setCellValueFactory(
                cellData -> new SimpleIntegerProperty(cellData.getValue().getMucTienMin()).asObject());

        dataDoiTienThanhDiem.addAll(doiTienThanhDiemBUS.getAllDoiTienThanhDiem()); // Lấy dữ liệu từ DAO
        tienToDiemTable.setItems(dataDoiTienThanhDiem); // Đặt dữ liệu vào TableView
    }

    private void loadDataDoiDiemThanhTien() {
        // Khởi tạo ObservableList nếu chưa
        if (dataDoiDiemThanhTien == null) {
            dataDoiDiemThanhTien = FXCollections.observableArrayList();
        }

        // Liên kết cột với các thuộc tính trong DoiDiemThanhTienDTO
        colMaDD.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getMaDD()).asObject());
        colMucDiemDD.setCellValueFactory(
                cellData -> new SimpleIntegerProperty(cellData.getValue().getMucDiem()).asObject());
        colMucTienMax.setCellValueFactory(
                cellData -> new SimpleIntegerProperty(cellData.getValue().getMucTienMax()).asObject());

        dataDoiDiemThanhTien.addAll(doiDiemThanhTienBUS.getAllDoiDiemThanhTien()); // Lấy dữ liệu từ DAO
        diemToTienTable.setItems(dataDoiDiemThanhTien); // Đặt dữ liệu vào TableView
    }

    public void setNV(NhanVienDTO nv) {
        this.nv = nv;
        sellMaNV.setText(String.valueOf(nv.getMaNV()));
        sellTenNV.setText(nv.getTenNV());
    }

    @SuppressWarnings("static-access")
    public void showProduct(List<ProductDTO> products) {
        sellGridPane.getChildren().clear(); // Xóa hết các sản phẩm hiện tại trong GridPane
        // Lấy giá trị mã khuyến mãi từ ComboBox
        String selectedPromotion = sellKMCB.getSelectionModel().getSelectedItem();
        int promotionId = -1;
        if (selectedPromotion != null) {
            promotionId = quanLyGiamGiaSpBUS.getPromotionIdByName(selectedPromotion);
        }
        int column = 0;
        int row = 1;

        try {
            for (ProductDTO product : products) {
                // Tải FXML của mỗi sản phẩm (Card Product)
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/CardProductGUI.fxml"));
                AnchorPane productCard = loader.load();

                // Lấy controller của Card Product
                CardProductController cardController = loader.getController();

                cardController.setProductInfo(
                    product.getTenSP(),
                    product.getGia(),
                    "D:/Code1/SieuThiMini/Assets/Img/Product/" + product.getHinhAnh(),
                    product.getSoLuong(),
                    product,
                    promotionId 
                );

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
        performSearch();
    }

    @FXML
    void handleSearchCB(ActionEvent event) {
        performSearch();
    }

    @FXML
    void handleThanhToanBTN(ActionEvent event) {
        // Kiểm tra các trường hợp nhập liệu không hợp lệ
        if (Integer.parseInt(dttdDiemApDungTF.getText()) == 0 || Integer.parseInt(dttdTienApDungTF.getText()) == 0) {
            showAlert(AlertType.ERROR, "Lỗi", "Vui lòng chọn tỉ lệ quy đổi điểm");
            return;
        }
        if (sellHinhThucCB.getSelectionModel().isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Chưa chọn hình thức", "Hãy chọn hình thức thanh toán.");
            return;
        }

        java.util.Date today = new java.util.Date();
        Date sqlDate = new Date(today.getTime());

        // Lấy thông tin từ các trường nhập liệu
        String sdt = sellSoDienThoai.getText().trim(); // Số điện thoại khách hàng
        int tongTien = (int) Double.parseDouble(sellTongTien.getText().replace(" VNĐ", "").replace(",", ""));
        int thanhTien = (int) Double.parseDouble(sellThanhTien.getText().replace(" VNĐ", "").replace(",", ""));
        int tienGiam = (int) Double.parseDouble(sellTienGiam.getText().replace(" VNĐ", "").replace(",", ""));
        int tienTraLai = (int) Double.parseDouble(sellTienTraLai.getText().replace(" VNĐ", "").replace(",", ""));
        int maNV = Integer.parseInt(sellMaNV.getText());

        if (tongTien == 0) {
            showAlert(Alert.AlertType.WARNING, "Chưa chọn sản phẩm", "Vui lòng chọn sản phẩm.");
            return;
        }

        if (sellTienKhachTra.getText().isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Chưa trả tiền", "Vui lòng nhập tiền khách trả.");
            return;
        }
        int tienKhachTra = (int) Double.parseDouble(sellTienKhachTra.getText().replace(" VNĐ", "").replace(",", ""));

        if (tienTraLai < 0) {
            showAlert(Alert.AlertType.WARNING, "Chưa trả đủ", "Khách hàng chưa trả đủ tiền.");
            return;
        }

        // Khởi tạo hóa đơn
        HoaDonDTO hoaDon = new HoaDonDTO();
        hoaDon.setNgayLap(sqlDate);
        hoaDon.setHinhThuc(sellHinhThucCB.getSelectionModel().getSelectedItem());
        hoaDon.setTongTien(tongTien);
        hoaDon.setTienGiam(tienGiam);
        hoaDon.setThanhTien(thanhTien);
        hoaDon.setTienKhachDua(tienKhachTra);
        hoaDon.setTienTraLai(tienTraLai);
        hoaDon.setMaNV(maNV);

        int maKH = 0; // Mặc định mã khách hàng là 0 nếu không có số điện thoại
        if (!sdt.isEmpty()) {
            maKH = khachhangBUS.getMaKHBySDT(sdt);
            if (maKH == -1) {
                showAlert(Alert.AlertType.WARNING, "Khách hàng không tồn tại",
                        "Số điện thoại không tồn tại trong hệ thống.");
                return;
            }
        }

        hoaDon.setMaKH(maKH); // Gán mã khách hàng (hoặc 0 nếu không có số điện thoại)

        // Gọi hàm thêm hóa đơn vào database
        hoaDonBUS.addHoaDon(hoaDon); // Lấy mã hóa đơn sau khi thêm
        int invoiceId = hoaDonBUS.getMaHD();
        lastInvoiceId = invoiceId; // Lưu mã hóa đơn vừa tạo

        // Thêm chi tiết hóa đơn vào database
        for (ProductDTO product : sellItems) {
            CTHoaDonDTO ctHoaDon = new CTHoaDonDTO();
            ctHoaDon.setMaHD(invoiceId);
            ctHoaDon.setMaSP(product.getMaSP());
            ctHoaDon.setSoLuong(product.getSoLuong());
            ctHoaDon.setGiaBan(product.getGia());
            hoaDonBUS.addCTHoaDon(ctHoaDon); // Thêm chi tiết hóa đơn vào CSDL
            boolean success = productBUS.updateProductQuantity(product.getMaSP(), product.getSoLuong());
            if (!success) {
                showAlert(Alert.AlertType.ERROR, "Lỗi", "Không thể trừ số lượng sản phẩm trong kho.");
                return; // Dừng lại nếu không trừ được sản phẩm
            }
        }

        // Xử lý điểm tích lũy nếu có mã khách hàng
        if (maKH != 0) {
            System.out.println(maKH);
            int tien = Integer.parseInt(dttdTienApDungTF.getText().trim());
            int diem = Integer.parseInt(dttdDiemApDungTF.getText().trim());

            int diemTichLuy = (thanhTien / tien) * diem; // Quy đổi điểm
            if (diemTichLuy > 0) {
                LichSuDiemDTO lichSuTichLuy = new LichSuDiemDTO();
                lichSuTichLuy.setMaKH(maKH);
                lichSuTichLuy.setMaHD(invoiceId);
                lichSuTichLuy.setDiem(diemTichLuy);
                lichSuTichLuy.setNgayTichLuy(sqlDate);
                lichSuTichLuy.setLoaiGD("Tích lũy");

                lichSuDiemBUS.addLichSuDiem(lichSuTichLuy);
                khachhangBUS.updateDiemTichLuy(maKH, diemTichLuy, sdt);
            }

        }

        // Hiển thị thông báo hoàn thành
        showAlert(Alert.AlertType.INFORMATION, "Thông báo", "Thanh toán thành công!");

        // Reset thông tin sau thanh toán
        sellItems.clear(); // Xóa danh sách sản phẩm trong bảng
        updateTotalPrice(); // Cập nhật lại tổng tiền về 0
        sellTienKhachTra.clear();
        sellTienTraLai.setText("0 VNĐ");
        sellTienGiam.setText("0 VNĐ");
        sellThanhTien.setText("0 VNĐ");
        showProduct(productBUS.getAllProducts());
        loadDataHoaDon(hoaDonBUS.getAllHoaDon());
        loadDataLichSuDiem(lichSuDiemBUS.getLichSuDiemList());
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
        if (lastInvoiceId == -1) {
            showAlert(Alert.AlertType.WARNING, "Thông báo", "Chưa có hóa đơn để in.");
            return;
        }

        // Lấy thông tin hóa đơn từ database
        HoaDonDTO hoaDon = hoaDonBUS.getHoaDonByMaHD(lastInvoiceId);
        List<CTHoaDonDTO> chiTietHoaDonList = hoaDonBUS.getChiTietHoaDonByMaHD(lastInvoiceId);

        if (hoaDon == null || chiTietHoaDonList == null) {
            showAlert(Alert.AlertType.ERROR, "Lỗi", "Không tìm thấy hóa đơn.");
            return;
        }

        // Tạo nội dung hóa đơn để in
        String noiDungHoaDon = createInvoiceContent(hoaDon, chiTietHoaDonList);

        // In hóa đơn (in ra console hoặc tích hợp máy in thực tế)
        inHoaDon(noiDungHoaDon);

        // Reset lastInvoiceId sau khi in để tránh in lại
        lastInvoiceId = -1;
    }

    @FXML
    void handleSoDienThoaiBTN(ActionEvent event) {
        if (Integer.parseInt(ddttDiemApDungTF.getText()) == 0 || Integer.parseInt(ddttTienApDungTF.getText()) == 0) {
            showAlert(AlertType.ERROR, "Lỗi", "Vui lòng chọn tỉ lệ quy đổi điểm");
        }

        String phoneNumber = sellSoDienThoai.getText().trim();

        if (!isValidPhoneNumber(phoneNumber)) {
            sellTienGiam.setText("0 VNĐ");
            showAlert(AlertType.ERROR, "Lỗi", "Số điện thoại không hợp lệ!");
            return; // Nếu số điện thoại không hợp lệ thì thoát khỏi hàm
        }

        if (!khachhangBUS.kiemTraSoDienThoai(phoneNumber)) {
            sellTienGiam.setText("0 VNĐ");
            showAlert(AlertType.ERROR, "Lỗi", "Số điện thoại không tồn tại!");
            return;
        }

        int Points = khachhangBUS.getDiemTichLuyBySoDienThoai(phoneNumber);
        int discountAmount = convertPointsToDiscount(Points);
        if (discountAmount == 0) {
            return;
        }
        Alert confirmAlert = new Alert(AlertType.CONFIRMATION);
        confirmAlert.setTitle("Bạn có muốn giảm giá không");
        confirmAlert.setContentText("Khách hàng có " + Points + " điểm thưởng.\nGiảm giá: " + discountAmount + " VNĐ");
        ButtonType result = confirmAlert.showAndWait().orElse(ButtonType.CANCEL);
        if (result == ButtonType.OK) {
            sellTienGiam.setText(String.format("%,d VNĐ", discountAmount));

        }
        if (result == ButtonType.CANCEL) {
            return;
        }

    }

    private void performSearch() {
        String productName = sellSearchBar.getText().trim(); // Lấy từ khóa tìm kiếm từ thanh nhập liệu
        String productType = sellSearchCB.getValue(); // Lấy loại sản phẩm từ ComboBox

        // Gọi phương thức tìm kiếm trong BUS
        List<ProductDTO> searchedProducts = productBUS.searchProducts(productName, productType);

        // Hiển thị các sản phẩm tìm được lên GridPane
        showProduct(searchedProducts);
    }

    private String createInvoiceContent(HoaDonDTO hoaDon, List<CTHoaDonDTO> chiTietHoaDonList) {
        StringBuilder sb = new StringBuilder();

        sb.append("======= HÓA ĐƠN BÁN HÀNG =======\n");
        sb.append("Mã hóa đơn: ").append(hoaDon.getMaHD()).append("\n");
        sb.append("Tên nhân viên: ").append(nv.getTenNV()).append("\n");
        sb.append("Mã khách hàng: ").append(hoaDon.getMaKH()).append("\n");
        sb.append("Ngày lập: ").append(hoaDon.getNgayLap()).append("\n");
        sb.append("Hình thức thanh toán: ").append(hoaDon.getHinhThuc()).append("\n");
        sb.append("--------------------------------\n");
        sb.append(String.format("%-20s %-10s %-10s\n", "Sản phẩm", "SL", "Giá (VNĐ)"));
        sb.append("--------------------------------\n");

        for (CTHoaDonDTO ctHoaDon : chiTietHoaDonList) {
            // String product = productBUS.getTenSanPhamByMaSP(ctHoaDon.getMaSP());
            sb.append(String.format("%-20s %-10d %,10d\n",
                    ctHoaDon.getTenSP(),
                    ctHoaDon.getSoLuong(),
                    ctHoaDon.getGiaBan() * ctHoaDon.getSoLuong()));
        }

        sb.append("--------------------------------\n");
        sb.append("Tổng tiền: ").append(String.format("%,d VNĐ", hoaDon.getTongTien())).append("\n");
        sb.append("Tiền giảm: ").append(String.format("%,d VNĐ", hoaDon.getTienGiam())).append("\n");
        sb.append("Thành tiền: ").append(String.format("%,d VNĐ", hoaDon.getThanhTien())).append("\n");
        sb.append("Tiền khách đưa: ").append(String.format("%,d VNĐ", hoaDon.getTienKhachDua())).append("\n");
        sb.append("Tiền trả lại: ").append(String.format("%,d VNĐ", hoaDon.getTienTraLai())).append("\n");
        sb.append("================================\n");
        sb.append("Cảm ơn Quý khách đã mua hàng!\n");

        return sb.toString();
    }

    private void inHoaDon(String noiDungHoaDon) {

        System.out.println(noiDungHoaDon);
        // Tạo PrinterJob để bắt đầu in
        PrinterJob printerJob = PrinterJob.createPrinterJob();

        // Kiểm tra nếu PrinterJob không phải là null và hộp thoại in được hiển thị
        if (printerJob != null && printerJob.showPrintDialog(sellForm.getScene().getWindow())) {
            // Tạo Label để chứa nội dung hóa đơn
            Label invoiceLabel = new Label(noiDungHoaDon); // Đảm bảo dùng noiDungHoaDon được truyền vào
            invoiceLabel.setPadding(new Insets(20)); // Đặt khoảng cách giữa các dòng
            invoiceLabel.setStyle("-fx-font-size: 12px; -fx-font-family: Arial;"); // Đặt kiểu font và kích thước chữ

            // In hóa đơn
            boolean success = printerJob.printPage(invoiceLabel);

            // Kiểm tra kết quả in
            if (success) {
                printerJob.endJob(); // Kết thúc công việc in
                showAlert(Alert.AlertType.INFORMATION, "In hóa đơn", "Hóa đơn đã được in thành công!");
            } else {
                showAlert(Alert.AlertType.ERROR, "In hóa đơn", "Không thể in hóa đơn.");
            }
        }
    }

    private boolean isValidPhoneNumber(String phoneNumber) {
        // Kiểm tra số điện thoại phải có 10 chữ số
        return phoneNumber.matches("\\d{10}");
    }

    // Hàm quy đổi điểm thưởng thành tiền giảm giá
    private int convertPointsToDiscount(int points) {

        if (ddttDiemApDungTF.getText().isEmpty() || ddttTienApDungTF.getText().isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Lỗi", "Vui lòng áp dụng tỷ lệ quy đổi điểm thành tiền.");
            return 0;
        }

        int Diem = Integer.parseInt(ddttDiemApDungTF.getText());
        int Tien = Integer.parseInt(ddttTienApDungTF.getText());

        if (points < Diem) {
            String message = String.format(
                    "Khách chỉ có %d điểm.\nKhông đủ điểm để quy đổi.\nTỉ lệ quy đổi là %d điểm = %d VNĐ", points, Diem,
                    Tien);
            showAlert(Alert.AlertType.ERROR, "Cảnh báo", message);
            return 0;
        }
        int conversionRate = Tien / Diem;
        return points * conversionRate;
    }

    private void showAlert(AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void loadProductTypes() {
        List<String> productTypes = productTypeBUS.getAllProductTypesName(); // Gọi đến Service để lấy danh sách loại
                                                                             // sản phẩm

        productTypes.add(0, "Tất cả");

        ObservableList<String> observableProductTypes = FXCollections.observableArrayList(productTypes); // Chuyển đổi
                                                                                                         // thành
                                                                                                         // ObservableList

        sellSearchCB.setItems(observableProductTypes); // Đưa danh sách vào ComboBox

        sellSearchCB.setValue("Tất cả");
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
            int tienKhachTra = (int) Double
                    .parseDouble(sellTienKhachTra.getText().replace(" VNĐ", "").replace(",", ""));

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

    private void loadDiscountNames() {
        List<String> discountNames = quanLyGiamGiaSpBUS.getAllDiscountNames();

        // Đưa danh sách vào ComboBox
        sellKMCB.getItems().clear();
        sellKMCB.getItems().addAll(discountNames);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        List<LichSuDiemDTO> lichSuDiemList = lichSuDiemBUS.getLichSuDiemList();
        loadDataLichSuDiem(lichSuDiemList);
        loadDiscountNames();
        searchLichSuCB.getItems().addAll("Mã hóa đơn", "Mã khách hàng");
        searchLichSuCB.setValue("Mã hóa đơn");

        // Thiết lập Locale cho tiếng Việt
        Locale vietnameseLocale = new Locale("vi", "VN");

        // Cài đặt Locale cho DatePicker và định dạng ngày tháng
        DateTimeFormatter vietnameseFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy", vietnameseLocale);

        // Cài đặt cho startHDDatePicker
        startHDDatePicker.setConverter(new LocalDateStringConverter(vietnameseFormatter, vietnameseFormatter));
        startHDDatePicker.setPromptText("Chọn ngày bắt đầu");

        // Cài đặt cho endHDDatePicker
        endHDDatePicker.setConverter(new LocalDateStringConverter(vietnameseFormatter, vietnameseFormatter));
        endHDDatePicker.setPromptText("Chọn ngày kết thúc");

        // Lấy tất cả dữ liệu hóa đơn từ cơ sở dữ liệu
        ArrayList<HoaDonDTO> hoaDonList = hoaDonBUS.getAllHoaDon();

        // Truyền dữ liệu vào hàm loadDataHoaDon để hiển thị
        loadDataHoaDon(hoaDonList);
        sellSearchHoaDonCB.getItems().addAll("Mã hóa đơn", "Mã khách hàng");
        sellSearchHoaDonCB.setValue("Mã hóa đơn");
        showProduct(productBUS.getAllProducts());

        ArrayList<TempDataDTO> dataList = tempDataBUS.getAllTempData();

        for (TempDataDTO tempData : dataList) {
            ddttDiemApDungTF.setText(String.valueOf(tempData.getDdttDiemApDung()));
            ddttTienApDungTF.setText(String.valueOf(tempData.getDdttTienApDung()));
            dttdTienApDungTF.setText(String.valueOf(tempData.getDttdTienApDung()));
            dttdDiemApDungTF.setText(String.valueOf(tempData.getDttdDiemApDung()));
        }

        sellHinhThucCB.setItems(FXCollections.observableArrayList("Tiền mặt", "Chuyển khoản"));
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

        loadDataDoiDiemThanhTien();
        loadDataDoiTienThanhDiem();

        // Lắng nghe sự kiện chọn hàng trên TableView
        diemToTienTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                ddttDiemTF.setText(String.valueOf(newSelection.getMucDiem())); // Hiển thị điểm
                ddttTienTF.setText(String.valueOf(newSelection.getMucTienMax())); // Hiển thị tiền
            } else {
                ddttDiemTF.clear(); // Xóa nội dung TextField
                ddttTienTF.clear(); // Xóa nội dung TextField
            }
        });

        // Lắng nghe sự kiện chọn hàng trên TableView
        tienToDiemTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                dttdTienTF.setText(String.valueOf(newSelection.getMucTienMin())); // Hiển thị điểm
                dttdDiemTF.setText(String.valueOf(newSelection.getMucDiem())); // Hiển thị tiền
            } else {
                ddttDiemTF.clear(); // Xóa nội dung TextField
                ddttTienTF.clear(); // Xóa nội dung TextField
            }
        });

    }

}
