package BUS;

import DAO.LoginDAO;
import DTO.NhanVienDTO;

public class LoginBUS {
    private LoginDAO loginDAO;

    public LoginBUS() {
        loginDAO = new LoginDAO(); // Khởi tạo đối tượng LoginDAO
    }

    public NhanVienDTO checkLogin(String username, String password) {
        return loginDAO.checkLogin(username, password);
    }

    public int getUserRole(String username) {
        return loginDAO.getUserRole(username);
    }
}
