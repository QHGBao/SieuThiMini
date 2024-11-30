package BUS;

import DAO.LoginDAO;

public class LoginBUS {
    private LoginDAO loginDAO;

    public LoginBUS() {
        loginDAO = new LoginDAO(); // Khởi tạo đối tượng LoginDAO
    }

    public boolean validateUser(String username, String password) {
        // Bạn có thể thêm logic để kiểm tra các điều kiện khác trước khi gọi đến DAO
        return loginDAO.checkLogin(username, password); // Gọi đến phương thức checkLogin trong DAO
    }

    public int getUserRole(String username) {
        return loginDAO.getUserRole(username);
    }
}
