package BUS;

import DAO.LoginDAO;
import DTO.NhanVienDTO;

public class LoginBUS {
    private LoginDAO loginDAO;

    public LoginBUS() {
        loginDAO = new LoginDAO(); // Khởi tạo đối tượng LoginDAO
    }

    public int validateUser(String username, String password) {
        return loginDAO.checkLogin(username, password); // Gọi đến phương thức checkLogin trong DAO
    }

    public NhanVienDTO getNvLogin(String username, String password){
        return loginDAO.nvLogin(username, password);
    }
        
    public int getUserRole(String username) {
        return loginDAO.getUserRole(username);
    }
}
