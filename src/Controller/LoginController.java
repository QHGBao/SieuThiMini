package Controller;

import BUS.LoginBUS;
import DTO.NhanVienDTO;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class LoginController {

    @FXML
    private TextField passwordLabel; // Mã người dùng nhập mật khẩu

    @FXML
    private TextField usernameLabel; // Mã người dùng nhập tên đăng nhập

    private LoginBUS loginBUS; // Đối tượng LoginBUS để kiểm tra đăng nhập

    public LoginController() {
        loginBUS = new LoginBUS(); // Tạo đối tượng LoginBUS
    }

    @FXML
    void handleLoginClick(MouseEvent event) {
        String username = usernameLabel.getText();
        String password = passwordLabel.getText();

        if (username.isEmpty() || password.isEmpty()) {
            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("Input Error");
            alert.setHeaderText("Username or Password Missing");
            alert.setContentText("Please enter both username and password.");
            alert.showAndWait();
        } else {
            // Kiểm tra thông tin đăng nhập từ LoginBUS và lấy mã nhân viên
            NhanVienDTO nv = loginBUS.checkLogin(username, password);

            if (nv != null) {
                try {
                    int userMaQuyen = loginBUS.getUserRole(username); // Lấy MaQuyen từ LoginBUS

                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/AdminPaneGUI.fxml"));
                    AnchorPane adminPane = loader.load();

                    // Lấy controller của AdminPane
                    AdminPaneController controller = loader.getController();

                    // Truyền mã nhân viên cho AdminPaneController
                    AdminPaneController mainStage = loader.getController();
                    mainStage.setNV(nv);
                    // Tạo Scene mới và hiển thị trang AdminPane
                    controller.initialize(userMaQuyen); // Truyền MaQuyen sang AdminPaneController
                    Stage stage = (Stage) usernameLabel.getScene().getWindow();
                    stage.setScene(new Scene(adminPane));
                } catch (Exception e) {
                    e.printStackTrace();
                    Alert alert = new Alert(AlertType.ERROR);
                    alert.setTitle("Lỗi");
                    alert.setHeaderText("Lỗi tải Admin Pane");
                    alert.setContentText("Đã xảy ra lỗi khi tải admin panel.");
                    alert.showAndWait();
                }
            } else {
                Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle("Lỗi đăng nhập");
                alert.setHeaderText("Thông tin xác thực không hợp lệ");
                alert.setContentText("Tên người dùng hoặc mật khẩu không chính xác.");
                alert.showAndWait();
            }
        }
    }

    @FXML
    void handleExitClick(MouseEvent event) {
        Platform.exit();
        System.exit(0);
    }

    @FXML
    public void initialize() {
        // Xử lý khi nhấn Enter trong usernameLabel
        usernameLabel.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                passwordLabel.requestFocus(); // Chuyển focus sang passwordLabel
            }
        });

        // Xử lý khi nhấn Enter trong passwordLabel
        passwordLabel.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                handleLoginClick(null); // Gọi trực tiếp handleLoginClick để đăng nhập
            }
        });
    }
}