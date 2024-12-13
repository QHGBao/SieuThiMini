package DAO;
import DTO.SanPhamDTO;

import java.sql.*;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class SanPhamDAO {
    private static ConnectManager connectManager;

    public SanPhamDAO(){
        connectManager = new ConnectManager();
    }
    
    

    public ArrayList<SanPhamDTO> getAllSanPham(){
        ArrayList<SanPhamDTO> dsSP = new ArrayList<SanPhamDTO>();
        //String sql = "SELECT * FROM SANPHAM";
        String sql = "SELECT MaSP, TenSP, MoTa, SoLuong, HinhAnh, GiaBan, MaLoai " + 
                     "FROM SanPham " + 
                     "WHERE Is_Deleted = 0";

        try {
            connectManager.openConnection();
            Connection connection = connectManager.getConnection();
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                int MaSP = rs.getInt("MaSP");
                String TenSP = rs.getString("TenSP");
                String MoTa = rs.getString("MoTa");
                int SoLuong = rs.getInt("SoLuong");
                String HinhAnh = rs.getString("HinhAnh");
                Double GiaBan = rs.getDouble("GiaBan");
                int MaLoai = rs.getInt("MaLoai");

                SanPhamDTO SPdto = new SanPhamDTO(MaSP, TenSP, MoTa, SoLuong, HinhAnh, GiaBan, MaLoai);
                dsSP.add(SPdto);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connectManager.closeConnection();
        }
        return dsSP;
    }

    public ArrayList<SanPhamDTO> getSanPhamByMaKM(int maKM) throws SQLException {
        ArrayList<SanPhamDTO> danhSach = new ArrayList<>();
        String sql = "SELECT sp.MaSP, sp.TenSP, sp.MoTa, sp.SoLuong, sp.HinhAnh, sp.GiaBan, sp.MaLoai " +
                     "FROM SanPham sp " +
                     "INNER JOIN CTGiamGiaSP g ON sp.MaSP = g.MaSP " +
                     "WHERE g.MaKM = ?";
        try {
            connectManager.openConnection();
            Connection connection = connectManager.getConnection();
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, maKM);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                SanPhamDTO sp = new SanPhamDTO();
                sp.setMaSP(rs.getInt("MaSP"));
                sp.setTenSP(rs.getString("TenSP"));
                sp.setMoTa(rs.getString("MoTa"));
                sp.setSoLuong(rs.getInt("SoLuong"));
                sp.setHinhAnh(rs.getString("HinhAnh"));
                sp.setGiaBan(rs.getDouble("GiaBan"));
                sp.setMaLoai(rs.getInt("MaLoai"));
                danhSach.add(sp);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connectManager.closeConnection();
        }
        return danhSach;
    }

    public void XoaSP(int maSP) throws SQLException {
        String query = "DELETE FROM SanPham WHERE MaSP = ?";
        Connection connection = null;
    
        try {
            // Mở kết nối
            connectManager.openConnection();
            connection = connectManager.getConnection();
    
            if (connection == null) {
                throw new SQLException("Không thể kết nối đến cơ sở dữ liệu.");
            }
    
            // Tắt auto-commit để quản lý giao dịch thủ công
            connection.setAutoCommit(false);
    
            // Chuẩn bị câu lệnh SQL
            try (PreparedStatement psSPchon = connection.prepareStatement(query)) {
                psSPchon.setInt(1, maSP);
                psSPchon.executeUpdate();
            }
    
            // Commit giao dịch
            connection.commit();
    
        } catch (SQLException e) {
            // Rollback nếu có lỗi
            if (connection != null) {
                try {
                    connection.rollback();
                } catch (SQLException rollbackEx) {
                    rollbackEx.printStackTrace();
                }
            }
            throw e;  // Ném lại lỗi để xử lý ở nơi gọi
        } finally {
            // Đảm bảo kết nối được đóng
            connectManager.closeConnection();
        }
    }
    
    public ArrayList<SanPhamDTO> getSanPhamByListMaSP(ArrayList<Integer> danhSachMaSP) {
    ArrayList<SanPhamDTO> danhSachSanPham = new ArrayList<>();
    if (danhSachMaSP.isEmpty()) {
        return danhSachSanPham;
    }

    try {
        // Tạo câu truy vấn với danh sách mã sản phẩm
       
        String sql = "SELECT * FROM SanPham WHERE MaSP IN (" + 
                     danhSachMaSP.stream().map(String::valueOf).collect(Collectors.joining(",")) + ")";
        connectManager.openConnection();
        Connection connection = connectManager.getConnection();
        Statement stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery(sql);

        while (rs.next()) {
            SanPhamDTO sp = new SanPhamDTO(
                
                rs.getInt("MaSP"),
                rs.getString("TenSP"),
                rs.getString("MoTa"),
                rs.getInt("SoLuong"),
                rs.getString("HinhAnh"),
                rs.getDouble("GiaBan"),
                rs.getInt("MaLoai")
            );
            danhSachSanPham.add(sp);
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return danhSachSanPham;
    }

    public static SanPhamDTO getSanPhamByMaSP(int maSP) {
        String query = "SELECT * FROM SanPham WHERE maSP = ?";
        try {
            connectManager.openConnection();
            Connection connection = connectManager.getConnection();
            Statement stmt = connection.createStatement();
            
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setInt(1, maSP);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new SanPhamDTO(
                    rs.getInt("maSP"),
                    rs.getString("tenSP"),
                    rs.getString("moTa"),
                    rs.getInt("soLuong"),
                    rs.getString("hinhAnh"),
                    rs.getDouble("giaBan"),
                    rs.getInt("maLoai")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
