package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginDAO {
    private ConnectManager connectManager;

    public LoginDAO() {
        connectManager = new ConnectManager();
    }

    public Integer  checkLogin(String username, String password) {
        Integer maNV = null; // Biến để lưu mã nhân viên nếu đăng nhập thành công
        String query = "SELECT MaNV FROM TaiKhoan WHERE TenTK = ? AND MatKhau = ?";

        try {
            connectManager.openConnection();
            Connection connection = connectManager.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                maNV = resultSet.getInt("MaNV"); // Lấy mã nhân viên nếu đăng nhập thành công
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connectManager.closeConnection();
        }

        return maNV; // Trả về mã nhân viên hoặc null nếu đăng nhập thất bại
    }
}
