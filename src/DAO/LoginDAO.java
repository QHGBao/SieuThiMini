package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import DTO.NhanVienDTO;

public class LoginDAO {
    private ConnectManager connectManager;

    public LoginDAO() {
        connectManager = new ConnectManager();
    }

    public boolean checkLogin(String username, String password) {
        boolean isValid = false;
        String query = "SELECT * FROM TaiKhoan WHERE TenTK = ? AND MatKhau = ?"; // Giả sử bạn có bảng Users

        try {
            connectManager.openConnection();
            Connection connection = connectManager.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                isValid = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connectManager.closeConnection();
        }

        return isValid;
    }

<<<<<<< HEAD
    public NhanVienDTO nvLogin(String username, String password){
        NhanVienDTO nvLogin = new NhanVienDTO();
        String sql = "select nv.MaNV, nv.TenNV from TaiKhoan tk, NhanVien nv where tk.MaNV = nv.MaNV AND TenTK = ? AND MatKhau = ? ";
        try {
            connectManager.openConnection();
            Connection connection = connectManager.getConnection();
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, username);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                nvLogin.setMaNV(rs.getInt(1));
                nvLogin.setTenNV(rs.getString(2));
            }
        } catch (Exception e) {
=======
    public int getUserRole(String username) {
        int maQuyen = -1; // Giá trị mặc định nếu không tìm thấy
        String query = "SELECT MaQuyen FROM TaiKhoan WHERE TenTK = ?";
        try {
            connectManager.openConnection();
            Connection connection = connectManager.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, username);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                maQuyen = resultSet.getInt("MaQuyen");
            }
        } catch (SQLException e) {
>>>>>>> f4c9c8c87b45287f920db8b0d68e2ba663d03ea5
            e.printStackTrace();
        } finally {
            connectManager.closeConnection();
        }
<<<<<<< HEAD
        return nvLogin;
=======
        return maQuyen;
>>>>>>> f4c9c8c87b45287f920db8b0d68e2ba663d03ea5
    }
}
