package DAO;

import DTO.DoiTienThanhDiemDTO;
import java.sql.*;
import java.util.ArrayList;

public class DoiTienThanhDiemDAO {
    private ConnectManager connectManager;

    public DoiTienThanhDiemDAO() {
        connectManager = new ConnectManager();
    }

    public ArrayList<DoiTienThanhDiemDTO> getAllDoiTienThanhDiem() {
        ArrayList<DoiTienThanhDiemDTO> arr = new ArrayList<DoiTienThanhDiemDTO>();
        String sql = "SELECT * FROM DoiTienThanhDiem WHERE Is_Deleted = 0";
        try {
            connectManager.openConnection();
            Connection connection = connectManager.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                DoiTienThanhDiemDTO dttd = new DoiTienThanhDiemDTO();
                dttd.setMaDT(rs.getInt("MaDT"));
                dttd.setMucTienMin(rs.getInt("MucTienMin"));
                dttd.setMucDiem(rs.getInt("MucDiem"));
                arr.add(dttd);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connectManager.closeConnection();
        }
        return arr;
    }

    public boolean addDoiTienThanhDiem(DoiTienThanhDiemDTO dttd) {
        String sql = "INSERT INTO DoiTienThanhDiem (MucTienMin, MucDiem, Is_Deleted) VALUES (?, ?, 0)";
        try {
            connectManager.openConnection();
            Connection connection = connectManager.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, dttd.getMucTienMin());
            preparedStatement.setInt(2, dttd.getMucDiem());
            int result = preparedStatement.executeUpdate();
            return result > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            connectManager.closeConnection();
        }
    }

    public boolean updateDoiTienThanhDiem(DoiTienThanhDiemDTO dttd) {
        String sql = "UPDATE DoiTienThanhDiem SET MucTienMin = ?, MucDiem = ? WHERE MaDT = ? AND Is_Deleted = 0";
        try {
            connectManager.openConnection();
            Connection connection = connectManager.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, dttd.getMucTienMin());
            preparedStatement.setInt(2, dttd.getMucDiem());
            preparedStatement.setInt(3, dttd.getMaDT()); 
            int result = preparedStatement.executeUpdate();
            return result > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            connectManager.closeConnection();
        }
    }

    public boolean deleteDoiTienThanhDiem(int maDT) {
        String sql = "UPDATE DoiTienThanhDiem SET Is_Deleted = 1 WHERE MaDT = ?";
        try {
            connectManager.openConnection();
            Connection connection = connectManager.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, maDT);
            int result = preparedStatement.executeUpdate();
            return result > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            connectManager.closeConnection();
        }
    }
    

}
