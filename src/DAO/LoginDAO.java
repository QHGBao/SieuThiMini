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

    public Integer checkLogin(String username, String password) {
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

    public NhanVienDTO nvLogin(String username, String password) {
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
