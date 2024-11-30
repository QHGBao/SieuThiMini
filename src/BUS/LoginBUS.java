package BUS;

import DAO.LoginDAO;

public class LoginBUS {
    private LoginDAO loginDAO;

    public LoginBUS() {
        loginDAO = new LoginDAO(); // Khởi tạo đối tượng LoginDAO
    }

    public int validateUser(String username, String password) {
        return loginDAO.checkLogin(username, password); // Gọi đến phương thức checkLogin trong DAO
    }
}
