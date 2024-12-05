package DAO;

import DTO.DoiDiemThanhTienDTO;
import java.sql.*;
import java.util.ArrayList;

public class DoiDiemThanhTienDAO {
    private ConnectManager connectManager;

    public DoiDiemThanhTienDAO() {
        connectManager = new ConnectManager();
    }

    // Lấy tất cả các quy định đổi điểm thành tiền chưa bị xóa
    public ArrayList<DoiDiemThanhTienDTO> getAllDoiDiemThanhTien() {
        ArrayList<DoiDiemThanhTienDTO> arr = new ArrayList<DoiDiemThanhTienDTO>();
        String sql = "SELECT * FROM DoiDiemThanhTien WHERE Is_Deleted = 0";
        try {
            connectManager.openConnection();
            Connection connection = connectManager.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                DoiDiemThanhTienDTO ddtt = new DoiDiemThanhTienDTO();
                ddtt.setMaDD(rs.getInt("MaDD"));
                ddtt.setMucDiem(rs.getInt("MucDiem"));
                ddtt.setMucTienMax(rs.getInt("MucTienMax"));
                arr.add(ddtt);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connectManager.closeConnection();
        }
        return arr;
    }

    // Thêm quy định đổi điểm thành tiền mới
    public boolean addDoiDiemThanhTien(DoiDiemThanhTienDTO ddtt) {
        String sql = "INSERT INTO DoiDiemThanhTien (MucDiem, MucTienMax) VALUES (?, ?)";
        try {
            connectManager.openConnection();
            Connection connection = connectManager.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, ddtt.getMucDiem());
            preparedStatement.setInt(2, ddtt.getMucTienMax());
            int result = preparedStatement.executeUpdate();
            return result > 0;  // Nếu có ít nhất một dòng bị ảnh hưởng, trả về true
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            connectManager.closeConnection();
        }
    }

    // Sửa quy định đổi điểm thành tiền
    public boolean updateDoiDiemThanhTien(DoiDiemThanhTienDTO ddtt) {
        String sql = "UPDATE DoiDiemThanhTien SET MucDiem = ?, MucTienMax = ? WHERE MaDD = ?";
        try {
            connectManager.openConnection();
            Connection connection = connectManager.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, ddtt.getMucDiem());
            preparedStatement.setInt(2, ddtt.getMucTienMax());
            preparedStatement.setInt(3, ddtt.getMaDD());
            int result = preparedStatement.executeUpdate();
            return result > 0;  // Nếu có ít nhất một dòng bị ảnh hưởng, trả về true
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            connectManager.closeConnection();
        }
    }

    // Xóa mềm quy định đổi điểm thành tiền (đánh dấu là đã xóa)
    public boolean deleteDoiDiemThanhTien(int maDD) {
        String sql = "UPDATE DoiDiemThanhTien SET Is_Deleted = 1 WHERE MaDD = ?";
        try {
            connectManager.openConnection();
            Connection connection = connectManager.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, maDD);
            int result = preparedStatement.executeUpdate();
            return result > 0;  // Nếu có ít nhất một dòng bị ảnh hưởng, trả về true
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            connectManager.closeConnection();
        }
    }
}
