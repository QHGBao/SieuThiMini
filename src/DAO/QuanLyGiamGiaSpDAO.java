package DAO;


import DTO.QuanLyGiamGiaSpDTO;
import DTO.SanPhamKmDTO;

import java.sql.*;
import java.util.ArrayList;

public class QuanLyGiamGiaSpDAO {
    private ConnectManager connectManager;

    

    public QuanLyGiamGiaSpDAO() {
        connectManager = new ConnectManager();
    }

  


    public ArrayList<QuanLyGiamGiaSpDTO> getAllGiamGiaSP() {
        ArrayList<QuanLyGiamGiaSpDTO> list = new ArrayList<>();
        String sql = "SELECT MaKM, TenKM, NgayBD, NgayKT, PtGiam " +
                     "FROM GiamGiaSP " +
                     "WHERE Is_Deleted = 0";

        try {
            connectManager.openConnection();
            Connection connection = connectManager.getConnection();
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                int maKM = rs.getInt("MaKM");
                String tenKM = rs.getString("TenKM");
                Date ngayBD = rs.getDate("NgayBD");
                Date ngayKT = rs.getDate("NgayKT");
                int ptGiam = rs.getInt("PtGiam");

                QuanLyGiamGiaSpDTO dto = new QuanLyGiamGiaSpDTO(maKM, tenKM, ngayBD, ngayKT, ptGiam);
                list.add(dto);
            }
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public boolean addGiamGiaSP(QuanLyGiamGiaSpDTO khuyenMai) {
        String query = "SET IDENTITY_INSERT GiamGiaSP ON INSERT INTO GiamGiaSP (MaKM, TenKM, NgayBD, NgayKT, PtGiam, Is_Deleted ) VALUES (?, ?, ?, ?, ?, ?)";
        Connection connection = null;
        PreparedStatement preparedStatement = null;
    
        try {
            // Mở kết nối
            connectManager.openConnection();
            connection = connectManager.getConnection();
    
            // Sử dụng PreparedStatement
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, khuyenMai.getMaKM());
            preparedStatement.setString(2, khuyenMai.getTenKM());
            preparedStatement.setDate(3, khuyenMai.getNgayBD());
            preparedStatement.setDate(4, khuyenMai.getNgayKT());
            preparedStatement.setInt(5, khuyenMai.getPtGiam());
            preparedStatement.setInt(6, 0);
    
            // Thực thi câu lệnh
            int rowsInserted = preparedStatement.executeUpdate();
    
            // Kiểm tra kết quả
            return rowsInserted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                // Đóng tài nguyên
                if (preparedStatement != null) preparedStatement.close();
                if (connection != null) connectManager.closeConnection();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public boolean addGiamGiaSPAndKm(SanPhamKmDTO dto) {
        String query = "INSERT INTO CTGiamGiaSP (MaSP, MaKM) VALUES (?, ?)";
        Connection connection = null;
        PreparedStatement preparedStatement = null;
    
        try {
            // Mở kết nối
            connectManager.openConnection();
            connection = connectManager.getConnection();
    
            // Sử dụng PreparedStatement
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, dto.getMaSP());
            preparedStatement.setInt(2, dto.getMaKM());
    
            // Thực thi câu lệnh
            int rowsInserted = preparedStatement.executeUpdate();
    
            // Kiểm tra kết quả
            return rowsInserted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                // Đóng tài nguyên
                if (preparedStatement != null) preparedStatement.close();
                if (connection != null) connectManager.closeConnection();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    

}

