package Controller;

import BUS.AccountBUS;
import DTO.AccountDTO;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class AccountFormController {
    @FXML
    private TextField txtTenTK;
    @FXML
    private TextField txtMatKhau;
    @FXML
    private TextField txtNhapLaiMatKhau;
    @FXML
    private TextField txtMaNV;
    @FXML
    private ComboBox<Integer> cmbMaQuyen;
    @FXML
    private ImageView btnClose;
    @FXML
    private Button btnCreate;

    private AccountBUS accBUS = new AccountBUS();
    private String mode = "ADD";
    private AccountDTO currentAccount;
    private Runnable onSuccessCallback;

    @FXML
    public void initialize() {
        setupComboBox();

    }

    private void setupComboBox() {
        cmbMaQuyen.getItems().addAll(1, 2, 3, 4, 5); // Add your role IDs
    }

    @FXML
    void btnCreate_Clicked(MouseEvent event) {
        if (!validateInputs()) {
            return;
        }

        int maNV = Integer.parseInt(txtMaNV.getText());

        // Kiểm tra mã nhân viên có tồn tại không
        if (!accBUS.ktNvTonTai(maNV)) {
            showAlert(Alert.AlertType.ERROR, "Lỗi", "Mã nhân viên không tồn tại. Vui lòng thêm nhân viên trước.");
            return;
        }

        // Nếu là chế độ ADD, kiểm tra xem nhân viên đã có tài khoản chưa
        if (mode.equals("ADD") && accBUS.ktTaiKhoanTonTai(maNV)) {
            showAlert(Alert.AlertType.ERROR, "Lỗi", "Nhân viên này đã có tài khoản, không thể thêm tài khoản mới!");
            return;
        }

        // Tạo đối tượng AccountDTO
        AccountDTO account = new AccountDTO(
                mode.equals("ADD") ? 0 : currentAccount.getMaTK(),
                txtTenTK.getText(),
                txtMatKhau.getText(),
                maNV,
                cmbMaQuyen.getValue(),
                0);

        // Thêm hoặc cập nhật tài khoản
        boolean success = mode.equals("ADD") ? accBUS.addAccount(account) : accBUS.updateAccount(account);

        // Kiểm tra kết quả thêm hoặc cập nhật
        if (success) {
            if (onSuccessCallback != null) {
                onSuccessCallback.run();
            }
            closeWindow();
        } else {
            showAlert(Alert.AlertType.ERROR, "Lỗi",
                    mode.equals("ADD") ? "Không thể thêm tài khoản!" : "Không thể cập nhật tài khoản!");
        }
    }

    private boolean validateInputs() {
        if (txtTenTK.getText().isEmpty()) {
            return false;
        }
        if (txtMatKhau.getText().isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Lỗi", "Mật khẩu không được để trống!");
            return false;
        }
        if (!txtMatKhau.getText().equals(txtNhapLaiMatKhau.getText())) {
            showAlert(Alert.AlertType.ERROR, "Lỗi", "Mật khẩu không khớp!");
            return false;
        }
        if (txtMaNV.getText().isEmpty() || !txtMaNV.getText().matches("\\d+")) {
            return false;
        }
        if (cmbMaQuyen.getValue() == null) {
            return false;
        }
        return true;
    }

    @FXML
    void btnClose_Clicked(MouseEvent event) {
        closeWindow();
    }

    private void closeWindow() {
        Stage stage = (Stage) btnClose.getScene().getWindow();
        stage.close();
    }

    public void setMode(String mode) {
        this.mode = mode;
        if (mode.equals("EDIT")) {
            txtTenTK.setDisable(true);
            txtMaNV.setDisable(true);
            btnCreate.setText("Lưu thay đổi");
        } else if (mode.equals("ADD")) {
            txtTenTK.setDisable(false);
            txtMaNV.setDisable(false);
            btnCreate.setText("Thêm");
        }
    }

    public void setAccount(AccountDTO account) {
        this.currentAccount = account;
        txtTenTK.setText(account.getTenTK());
        txtMaNV.setText(String.valueOf(account.getMaNV()));
        cmbMaQuyen.setValue(account.getMaQuyen());
        if (mode.equals("EDIT")) {
            txtTenTK.setDisable(true);
            txtMaNV.setDisable(true);
        }
    }

    public void setOnSuccessCallback(Runnable callback) {
        this.onSuccessCallback = callback;
    }

    private void showAlert(Alert.AlertType type, String title, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
