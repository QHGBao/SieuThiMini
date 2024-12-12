package DAO;

import DTO.AccountDTO;
import DTO.ProductDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AccountDAO {

    private ConnectManager connectManager;

    public AccountDAO() {
        connectManager = new ConnectManager();
    }

    public boolean addAccount(AccountDTO account) {
        String query = "INSERT INTO TaiKhoan (TenTK, MatKhau, MaNV, MaQuyen, Is_Deleted) VALUES (?, ?, ?, ?, ?)";
        try {
            connectManager.openConnection();
            Connection connection = connectManager.getConnection();
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setString(1, account.getTenTK());
            stmt.setString(2, account.getMatKhau());
            stmt.setInt(3, account.getMaNV());
            stmt.setInt(4, account.getMaQuyen());
            stmt.setInt(5, account.getIsDeleted());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateAccount(AccountDTO account) {
        String query = "UPDATE TaiKhoan SET TenTK = ?, MatKhau = ?, MaNV = ?, MaQuyen = ?, Is_Deleted = ? WHERE MaTK = ?";
        try {
            connectManager.openConnection();
            Connection connection = connectManager.getConnection();
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setString(1, account.getTenTK());
            stmt.setString(2, account.getMatKhau());
            stmt.setInt(3, account.getMaNV());
            stmt.setInt(4, account.getMaQuyen());
            stmt.setInt(5, account.getIsDeleted());
            stmt.setInt(6, account.getMaTK());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteAccount(int maTK) {
        String query = "UPDATE TaiKhoan SET Is_Deleted = 1 WHERE MaTK = ?";
        try {
            connectManager.openConnection();
            Connection connection = connectManager.getConnection();
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setInt(1, maTK);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean ktTaiKhoanTonTai(int maNV) {
        String query = "SELECT COUNT(*) AS count FROM TaiKhoan WHERE MaNV = ? AND Is_Deleted = 0";
        try {
            connectManager.openConnection();
            Connection connection = connectManager.getConnection();
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setInt(1, maNV);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("count") > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean ktNvTonTai(int maNV) {
        String query = "SELECT 1 FROM NhanVien WHERE MaNV = ? AND Is_Deleted = 0";
        try {
            connectManager.openConnection();
            Connection connection = connectManager.getConnection();
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setInt(1, maNV);
            ResultSet rs = stmt.executeQuery();
            return rs.next(); // Nếu có dòng dữ liệu trả về thì nhân viên tồn tại
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<AccountDTO> getAllAccounts() {
        List<AccountDTO> accounts = new ArrayList<>();
        String query = "SELECT * FROM TaiKhoan WHERE Is_Deleted = 0";
        try {
            connectManager.openConnection();
            Connection connection = connectManager.getConnection();
            PreparedStatement stmt = connection.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                accounts.add(new AccountDTO(
                        rs.getInt("MaTK"),
                        rs.getString("TenTK"),
                        rs.getString("MatKhau"),
                        rs.getInt("MaNV"),
                        rs.getInt("MaQuyen"),
                        rs.getInt("Is_Deleted")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return accounts;
    }

    public List<AccountDTO> searchTaiKhoanbyName(String keyword) {
        List<AccountDTO> accounts = new ArrayList<>();
        String query = "SELECT * FROM TaiKhoan WHERE TenTK LIKE ? AND Is_Deleted = 0";
        try {
            connectManager.openConnection();
            Connection connection = connectManager.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, "%" + keyword + "%");
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                accounts.add(new AccountDTO(
                        rs.getInt("MaTK"),
                        rs.getString("TenTK"),
                        rs.getString("MatKhau"),
                        rs.getInt("MaNV"),
                        rs.getInt("MaQuyen"),
                        rs.getInt("Is_Deleted")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connectManager.closeConnection();
        }
        return accounts;
    }
}
