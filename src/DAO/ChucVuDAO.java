package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ChucVuDAO {
    private ConnectManager connectManager;

    public ChucVuDAO() {
        connectManager = new ConnectManager();
    }
    public String getTenChucVuByMaChucVu(int maChucVu){
        String TenCV = "";
        String sql = "SELECT TenChucVu FROM ChucVu WHERE MaChucVu = ?";
        try {
            connectManager.openConnection();
            Connection connection = connectManager.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, maChucVu);
            ResultSet rs = preparedStatement.executeQuery();

            if (rs.next()) {
                TenCV = rs.getString("TenChucVu");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connectManager.closeConnection();
        }
        return TenCV;
    }

    public Integer getMaChucVuByTenChucVu(String tenChucVu){
        int maCV = 0;
        String sql = "SELECT MaChucVu FROM ChucVu WHERE TenChucVu = ?";
        try {
            connectManager.openConnection();
            Connection connection = connectManager.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, tenChucVu);
            ResultSet rs = preparedStatement.executeQuery();

            if (rs.next()) {
                maCV = rs.getInt("MaChucVu");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connectManager.closeConnection();
        }
        return maCV;
    }

    public String getAllTenChucVu() {
        String s = "";
        String sql = "SELECT TenChucVu FROM ChucVu";
        try {
            connectManager.openConnection();
            Connection connection = connectManager.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                s+=rs.getString("TenChucVu");
                s+=", ";
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connectManager.closeConnection();
        }

        return s;
    }
}
