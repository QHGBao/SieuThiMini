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
            e.printStackTrace();
        } finally {
            connectManager.closeConnection();
        }
        return nvLogin;
    }
}
