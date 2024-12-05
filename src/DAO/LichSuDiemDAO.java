package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import DTO.LichSuDiemDTO;

public class LichSuDiemDAO {
    private ConnectManager connectManager;

    public LichSuDiemDAO() {
        connectManager = new ConnectManager();
    }

    public boolean insertLichSuDiem(LichSuDiemDTO lichSuDiem) {
        String sql = "INSERT INTO LichSuDiem (MaKH, MaHD, Diem, NgayTichLuy, LoaiGD) VALUES (?, ?, ?, ?, ?)";
        try {
            connectManager.openConnection();
            Connection connection = connectManager.getConnection();
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, lichSuDiem.getMaKH());
            ps.setInt(2, lichSuDiem.getMaHD());
            ps.setInt(3, lichSuDiem.getDiem());
            ps.setDate(4, lichSuDiem.getNgayTichLuy());
            ps.setString(5, lichSuDiem.getLoaiGD());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            connectManager.closeConnection();
        }
    }

    public List<LichSuDiemDTO> getLichSuDiemList() {
        List<LichSuDiemDTO> lichSuDiemList = new ArrayList<>();
        String sql = "SELECT * FROM LichSuDiem";

        try {
            connectManager.openConnection();
            Connection connection = connectManager.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                LichSuDiemDTO lichSuDiem = new LichSuDiemDTO(
                        rs.getInt("MaGiaoDich"),
                        rs.getInt("MaKH"),
                        rs.getInt("MaHD"),
                        rs.getInt("Diem"),
                        rs.getDate("NgayTichLuy"),
                        rs.getString("LoaiGD"));
                lichSuDiemList.add(lichSuDiem);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connectManager.closeConnection();
        }

        return lichSuDiemList;
    }

    public List<LichSuDiemDTO> searchLichSuDiem(int maKH, int maHD, java.sql.Date startDate, java.sql.Date endDate) {
        List<LichSuDiemDTO> result = new ArrayList<>();
        StringBuilder sql = new StringBuilder("SELECT * FROM LichSuDiem WHERE 1=1");
    
        // Thêm điều kiện tìm kiếm theo mã hóa đơn hoặc mã khách hàng
        if (maHD != -1) {
            sql.append(" AND MaHD = ").append(maHD);
        }
        if (maKH != -1) {
            sql.append(" AND MaKH = ").append(maKH);
        }
    
        // Thêm điều kiện lọc theo ngày
        if (startDate != null && endDate != null) {
            sql.append(" AND NgayTichLuy BETWEEN '").append(startDate).append("' AND '").append(endDate).append("'");
        }
    
        try {
            connectManager.openConnection();
            Connection connection = connectManager.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql.toString());
            ResultSet rs = preparedStatement.executeQuery();
    
            while (rs.next()) {
                result.add(new LichSuDiemDTO(
                        rs.getInt("MaGiaoDich"),
                        rs.getInt("MaKH"),
                        rs.getInt("MaHD"),
                        rs.getInt("Diem"),
                        rs.getDate("NgayTichLuy"),
                        rs.getString("LoaiGD")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connectManager.closeConnection();
        }
    
        return result;
    }

}
