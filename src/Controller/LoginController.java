package Controller;

import BUS.LoginBUS;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

public class LoginController {

    @FXML
    private Pane loginButton;

    @FXML
    private TextField passwordLabel;

    @FXML
    private TextField usernameLabel;

    private LoginBUS loginBUS;

    public LoginController() {
        loginBUS = new LoginBUS(); // Tạo đối tượng LoginBUS
    }

    @FXML
    void handleLoginClick(MouseEvent event) {
        String username = usernameLabel.getText();
        String password = passwordLabel.getText();

        if (username.isEmpty() || password.isEmpty()) {
            System.out.println("Please enter username and password.");
        } else {
            boolean isLoginSuccessful = loginBUS.validateUser(username, password); // Sử dụng LoginBUS để xác thực

            if (isLoginSuccessful) {
                System.out.println("Login successful!");
                // Thực hiện chuyển tiếp đến màn hình chính hoặc làm gì đó sau khi đăng nhập thành công
            } else {
                System.out.println("Invalid username or password.");
            }
        }
    }
}
