package Controller;

import BUS.AccountBUS;
import DTO.AccountDTO;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.util.regex.Pattern;

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
    
    private AccountBUS accBUS = new AccountBUS();
    private String mode = "ADD";
    private AccountDTO currentAccount;
    private Runnable onSuccessCallback;

    // Regex patterns
    private static final Pattern USERNAME_PATTERN = Pattern.compile("^[a-zA-Z0-9_]{6,20}$");
    private static final Pattern PASSWORD_PATTERN = Pattern.compile("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$");

    @FXML
    public void initialize() {
        setupComboBox();
        setupValidation();
    }

    private void setupComboBox() {
        cmbMaQuyen.getItems().addAll(1, 2, 3); // Add your role IDs
    }

    private void setupValidation() {
        // Username validation
        txtTenTK.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!USERNAME_PATTERN.matcher(newValue).matches()) {
                txtTenTK.setStyle("-fx-border-color: red;");
            } else {
                txtTenTK.setStyle("");
            }
        });

        // Password validation
        txtMatKhau.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!PASSWORD_PATTERN.matcher(newValue).matches()) {
                txtMatKhau.setStyle("-fx-border-color: red;");
            } else {
                txtMatKhau.setStyle("");
            }
        });

        // Confirm password validation
        txtNhapLaiMatKhau.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.equals(txtMatKhau.getText())) {
                txtNhapLaiMatKhau.setStyle("-fx-border-color: red;");
            } else {
                txtNhapLaiMatKhau.setStyle("");
            }
        });
    }

    @FXML
    void btnCreate_Clicked(MouseEvent event) {
        if (!validateInputs()) {
            showAlert(Alert.AlertType.ERROR, "Lỗi", "Vui lòng kiểm tra lại thông tin nhập!");
            return;
        }

        AccountDTO account = new AccountDTO(
            mode.equals("ADD") ? 0 : currentAccount.getMaTK(),
            txtTenTK.getText(),
            txtMatKhau.getText(),
            Integer.parseInt(txtMaNV.getText()),
            cmbMaQuyen.getValue(),
            0
        );

        boolean success = mode.equals("ADD") ? 
            accBUS.addAccount(account) : 
            accBUS.updateAccount(account);

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
        if (!USERNAME_PATTERN.matcher(txtTenTK.getText()).matches()) {
            return false;
        }
        if (!PASSWORD_PATTERN.matcher(txtMatKhau.getText()).matches()) {
            return false;
        }
        if (!txtMatKhau.getText().equals(txtNhapLaiMatKhau.getText())) {
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
    }

    public void setAccount(AccountDTO account) {
        this.currentAccount = account;
        txtTenTK.setText(account.getTenTK());
        txtMaNV.setText(String.valueOf(account.getMaNV()));
        cmbMaQuyen.setValue(account.getMaQuyen());
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
