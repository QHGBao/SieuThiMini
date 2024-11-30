package BUS;

import DAO.LoginDAO;
import DTO.NhanVienDTO;

public class LoginBUS {
    private LoginDAO loginDAO;

    public LoginBUS() {
        loginDAO = new LoginDAO(); // Khởi tạo đối tượng LoginDAO
    }

    public boolean validateUser(String username, String password) {
        // Bạn có thể thêm logic để kiểm tra các điều kiện khác trước khi gọi đến DAO
        return loginDAO.checkLogin(username, password); // Gọi đến phương thức checkLogin trong DAO
    }

<<<<<<< HEAD
    public NhanVienDTO getNvLogin(String username, String password){
        return loginDAO.nvLogin(username, password);
=======
    public int getUserRole(String username) {
        return loginDAO.getUserRole(username);
>>>>>>> f4c9c8c87b45287f920db8b0d68e2ba663d03ea5
    }
}
