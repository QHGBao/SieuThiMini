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

    public NhanVienDTO checkLogin(String username, String password) {
        NhanVienDTO nv = null;
        String query = "SELECT tk.MaNV, nv.TenNV FROM TaiKhoan tk, NhanVien nv WHERE TenTK = ? AND MatKhau = ? AND tk.MaNV = nv.MaNV";

        try {
            connectManager.openConnection();
            Connection connection = connectManager.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                nv = new NhanVienDTO();
                nv.setMaNV(resultSet.getInt(1));
                nv.setTenNV(resultSet.getString(2));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connectManager.closeConnection();
        }

        return nv; // Trả về mã nhân viên hoặc null nếu đăng nhập thất bại
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
