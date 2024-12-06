package DAO;

import DTO.AccountDTO;
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
        String query = "INSERT INTO TaiKhoan (MaTK, TenTK, MatKhau, MaNV, MaQuyen, Is_Deleted) VALUES (?, ?, ?, ?, ?, ?)";
        try {
            connectManager.openConnection();
            Connection connection = connectManager.getConnection();
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setInt(1, account.getMaTK());
            stmt.setString(2, account.getTenTK());
            stmt.setString(3, account.getMatKhau());
            stmt.setInt(4, account.getMaNV());
            stmt.setInt(5, account.getMaQuyen());
            stmt.setInt(6, account.getIsDeleted());
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

    public AccountDTO getAccountbyId(int maTK) {
        String query = "SELECT * FROM TaiKhoan WHERE MaTK = ? AND Is_Deleted = 0";
        try {
            connectManager.openConnection();
            Connection connection = connectManager.getConnection();
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setInt(1, maTK);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new AccountDTO(
                        rs.getInt("MaTK"),
                        rs.getString("TenTK"),
                        rs.getString("MatKhau"),
                        rs.getInt("MaNV"),
                        rs.getInt("MaQuyen"),
                        rs.getInt("Is_Deleted"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
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
}
