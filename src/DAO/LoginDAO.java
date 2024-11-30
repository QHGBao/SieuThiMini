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
            e.printStackTrace();
        } finally {
            connectManager.closeConnection();
        }
        return maQuyen;
    }
}
