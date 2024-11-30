package Controller;

import BUS.PhanQuyenBUS;
import DTO.CTPhanQuyenDTO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AdminPaneController {

    private NhanVienDTO nvLogin;
    
    public void setNvLogin(NhanVienDTO nvLogin){
        this.nvLogin = nvLogin;
    }

    @FXML
    private Button btnBanHang;

    @FXML
    private Button btnGiamGia;

    @FXML
    private Button btnKhachHang;

    @FXML
    private Button btnNCC;

    @FXML
    private Button btnNhanVien;

    @FXML
    private Button btnPhanQuyen;

    @FXML
    private Button btnPhieuHuy;

    @FXML
    private Button btnPhieuNhap;

    @FXML
    private Button btnQLSP;

    @FXML
    private Button btnQLTaiKhoan;

    @FXML
    private Button btnQLThongKe;
    @FXML
    private StackPane contentPane;

    private PhanQuyenBUS phanQuyenBUS;
    private Map<Integer, Button> buttonMap;
    private int userMaQuyen;

    public AdminPaneController() {
        phanQuyenBUS = new PhanQuyenBUS();
    }

    @FXML
    public void initialize(int userMaQuyen) {
        this.userMaQuyen = userMaQuyen; // Lưu mã quyền của người dùng
        setupButtonMap(); // Tạo ánh xạ mã chức năng với các nút
        applyPermissions(); // Áp dụng quyền cho các nút
    }

    private void setupButtonMap() {
        buttonMap = new HashMap<>();
        buttonMap.put(1, btnQLSP);
        buttonMap.put(2, btnNCC);
        buttonMap.put(3, btnPhieuNhap);
        buttonMap.put(4, btnPhieuHuy);
        buttonMap.put(5, btnKhachHang);
        buttonMap.put(6, btnNhanVien);
        buttonMap.put(7, btnBanHang);
        buttonMap.put(8, btnGiamGia);
        buttonMap.put(9, btnQLTaiKhoan);
        buttonMap.put(10, btnQLThongKe);
        buttonMap.put(11, btnPhanQuyen);
    }

    private void applyPermissions() {
        ArrayList<CTPhanQuyenDTO> permissions = phanQuyenBUS.getPermissionsByQuyen(userMaQuyen);

        // Khóa tất cả các nút trước
        buttonMap.values().forEach(button -> button.setDisable(true));

        // Áp dụng quyền
        for (CTPhanQuyenDTO permission : permissions) {
            int maChucNang = permission.getMaChucNang();
            int maHanhDong = permission.getMaHanhDong();

            Button button = buttonMap.get(maChucNang);
            if (button != null) {
                button.setDisable(false); // Mở nút
                if (maHanhDong == 1) {
                    button.setOnAction(event -> loadContent(getFXMLFile(maChucNang), true));
                } else if (maHanhDong == 2) {
                    button.setOnAction(event -> loadContent(getFXMLFile(maChucNang), false));
                }
            }
        }
    }

    private void loadContent(String fxmlFile, boolean isReadOnly) {
        try {
            Pane newContent = FXMLLoader.load(getClass().getResource("/GUI/" + fxmlFile));
            contentPane.getChildren().setAll(newContent);

            if (isReadOnly) {
                disableAllControls(newContent); // Khóa trang nếu chỉ được xem
            }
        } catch (IOException e) {
            showErrorAlert("Unable to load content for: " + fxmlFile);
            e.printStackTrace();
        }
    }

    private void disableAllControls(Pane pane) {
        pane.setDisable(true); // Khóa toàn bộ nội dung
    }

    private String getFXMLFile(int maChucNang) {
        switch (maChucNang) {
            case 1:
                return "QLSPGUI.fxml";
            case 2:
                return "NhaCungCapGUI.fxml";
            case 3:
                return "PhieuNhapGUI.fxml";
            case 4:
                return "Review+DeleteCancellationGUI.fxml";
            case 5:
                return "QLKHGUI.fxml";
            case 6:
                return "QLNVGUI.fxml";
            case 7:
                return "BanHangGUI.fxml";
            case 8:
                return "GiamGiaSPGUI.fxml";
            case 9:
                return "TaiKhoanGUI.fxml";
            case 10:
                return "ThongKeGUI.fxml";
            case 11:
                return "PhanQuyenGUI.fxml";
            default:
                return null;
        }
    }

    @FXML
    public void handleLogoutAction(ActionEvent event) throws IOException {
        // Đóng cửa sổ hiện tại
        Stage currentStage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        currentStage.close();

        // Mở cửa sổ đăng nhập
        Stage loginStage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/LoginGUI.fxml"));
        Scene scene = new Scene(loader.load());
        loginStage.initStyle(StageStyle.UNDECORATED);
        loginStage.setScene(scene);
        loginStage.show();
    }

    private void showErrorAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("Error loading GUI");
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    void HandleKhachHangAction(ActionEvent event) {

    }

    @FXML
    void HandleNhanVienAction(ActionEvent event) {

    }

    @FXML
    void HandlePhieuHuyAction(ActionEvent event) {

    }

    @FXML
    void handleBanHangAction(ActionEvent event) {

    }

    @FXML
    void handleGiamGiaction(ActionEvent event) {

    }

    @FXML
    void handleNCCAction(ActionEvent event) {

    }

    @FXML
    void handlePNAction(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/PhieuNhapGUI.fxml"));
            Pane newContent = loader.load();
            PhieuNhapController pnController = loader.getController();
            pnController.setNvLogin(nvLogin);
            contentPane.getChildren().setAll(newContent);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void handlePhanQuyenAction(ActionEvent event) {

    }

    @FXML
    void handlePhieuNhapAction(ActionEvent event) {

    }

    @FXML
    void handleQLSPAction(ActionEvent event) {

    }

    @FXML
    void handleQLTaiKhoanction(ActionEvent event) {

    }

    @FXML
    void handleQLThongKeAction(ActionEvent event) {

    }
}
