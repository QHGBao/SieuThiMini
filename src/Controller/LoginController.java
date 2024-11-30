package Controller;

import BUS.LoginBUS;
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
            boolean isLoginSuccessful = loginBUS.validateUser(username, password);

            if (isLoginSuccessful) {
                try {
                    int userMaQuyen = loginBUS.getUserRole(username); // Lấy MaQuyen từ LoginBUS

                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/AdminPaneGUI.fxml"));
                    AnchorPane adminPane = loader.load();
<<<<<<< HEAD
                    AdminPaneController mainStage = loader.getController();
                    mainStage.setNvLogin(loginBUS.getNvLogin(username, password));
                    System.out.println(loginBUS.getNvLogin(username, password).getMaNV());
                    // Tạo Scene mới và hiển thị trang AdminPane
=======

                    AdminPaneController controller = loader.getController();
                    controller.initialize(userMaQuyen); // Truyền MaQuyen sang AdminPaneController

>>>>>>> f4c9c8c87b45287f920db8b0d68e2ba663d03ea5
                    Stage stage = (Stage) usernameLabel.getScene().getWindow();
                    stage.setScene(new Scene(adminPane));
                } catch (Exception e) {
                    e.printStackTrace();
                    Alert alert = new Alert(AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText("Error loading Admin Pane");
                    alert.setContentText("An error occurred while loading the admin panel.");
                    alert.showAndWait();
                }
            } else {
                Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle("Login Failed");
                alert.setHeaderText("Invalid Credentials");
                alert.setContentText("The username or password is incorrect.");
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
