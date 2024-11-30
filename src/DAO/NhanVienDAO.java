package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import DTO.NhanVienDTO;

public class NhanVienDAO {
    private ConnectManager connectManager;

    public NhanVienDAO() {
        connectManager = new ConnectManager();
    }

    public ArrayList<NhanVienDTO> getAllNhanVienDTO() {
        ArrayList<NhanVienDTO> arr = new ArrayList<NhanVienDTO>();
        String sql = "SELECT MaNV,TenNV,GioiTinh,NgaySinh,SoDienThoai,Email,DiaChi,MaChucVu FROM NhanVien WHERE "
                       + "Is_Deleted = 0";
        try {
            connectManager.openConnection();
            Connection connection = connectManager.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                NhanVienDTO nv = new NhanVienDTO();
                nv.setMaNV(rs.getInt("MaNV"));
                nv.setTenNV(rs.getString("TenNV"));
                nv.setGioiTinh(rs.getBoolean("GioiTinh"));;
                nv.setNgaySinh(rs.getDate("NgaySinh"));
                nv.setSoDienThoai(rs.getString("SoDienThoai"));
                nv.setEmail(rs.getString("Email"));
                nv.setDiaChi(rs.getString("DiaChi"));
                nv.setMaChucVu(rs.getInt("MaChucVu"));
                arr.add(nv);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connectManager.closeConnection();
        }

        return arr;
    }
    public boolean addNhanVienDTO(NhanVienDTO nv) {
        boolean result = false;
        String sql = "Insert into NhanVien(TenNV, GioiTinh,NgaySinh,SoDienThoai,Email,DiaChi,MaChucVu,Is_Deleted)"
                    +"values(?,?,?,?,?,?,?,0)";
        try {
            connectManager.openConnection();
            Connection connection = connectManager.getConnection();
            PreparedStatement pstm = connection.prepareStatement(sql);
            pstm.setString(1, nv.getTenNV());
            pstm.setBoolean(2, nv.isGioiTinh());
            pstm.setDate(3, nv.getNgaySinh());
            pstm.setString(4, nv.getSoDienThoai());
            pstm.setString(5, nv.getEmail());
            pstm.setString(6, nv.getDiaChi());
            pstm.setInt(7, nv.getMaChucVu());
            int r = pstm.executeUpdate();
            if (r > 0) {
                result = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally{
            connectManager.closeConnection();
        }
        return result;
    }

    public Integer getMaNV() {
        int manv = 0;
        String sql = "SELECT TOP 1 MaNV FROM NhanVien ORDER BY MaNV DESC";
        try {
            connectManager.openConnection();
            Connection connection = connectManager.getConnection();
            
            PreparedStatement pstm = connection.prepareStatement(sql);
            ResultSet rs = pstm.executeQuery();
            if (rs.next()) {
                manv = rs.getInt("MaNV");
                manv += 1;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally{
            connectManager.closeConnection();
        }
        return manv;
    }
    public boolean deleteNhanVienDTO(int manv) {
        boolean result = false;
        String sql = "Update NhanVien Set Is_Deleted = ? Where MaNV = ?";
        try {
            connectManager.openConnection();
            Connection connection = connectManager.getConnection();
            PreparedStatement pstm = connection.prepareStatement(sql);
            pstm.setBoolean(1, true);
            pstm.setInt(2, manv);
            int r = pstm.executeUpdate();
            if (r > 0) {
                result = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally{
            connectManager.closeConnection();
        }
        return result;
    }
    public NhanVienDTO getNhanVienByMaNV(int maNV){
        NhanVienDTO nv = new NhanVienDTO();
        String sql = "SELECT MaNV,TenNV,GioiTinh,NgaySinh,SoDienThoai,Email,DiaChi,MaChucVu FROM NhanVien WHERE "
                       + "MaNV = ?";
        try {
            connectManager.openConnection();
            Connection connection = connectManager.getConnection();
            PreparedStatement pstm = connection.prepareStatement(sql);
            pstm.setInt(1, maNV);
            ResultSet rs = pstm.executeQuery();
            if (rs.next()) {
                nv.setMaNV(rs.getInt("MaNV"));
                nv.setTenNV(rs.getString("TenNV"));
                nv.setGioiTinh(rs.getBoolean("GioiTinh"));;
                nv.setNgaySinh(rs.getDate("NgaySinh"));
                nv.setSoDienThoai(rs.getString("SoDienThoai"));
                nv.setEmail(rs.getString("Email"));
                nv.setDiaChi(rs.getString("DiaChi"));
                nv.setMaChucVu(rs.getInt("MaChucVu"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally{
            connectManager.closeConnection();
        }
        return nv;
    }
    public boolean updateNhanVienDTO(NhanVienDTO nv){
        boolean result = false;
        String sql = "Update NhanVien Set TenNV = ?, GioiTinh = ?,NgaySinh = ?,SoDienThoai = ?,Email = ?,"+
        "DiaChi = ?,MaChucVu = ? Where MaNV = ?";
        try {
            connectManager.openConnection();
            Connection connection = connectManager.getConnection();
            PreparedStatement pstm = connection.prepareStatement(sql);
            pstm.setString(1, nv.getTenNV());
            pstm.setBoolean(2, nv.isGioiTinh());
            pstm.setDate(3, nv.getNgaySinh());
            pstm.setString(4, nv.getSoDienThoai());
            pstm.setString(5, nv.getEmail());
            pstm.setString(6, nv.getDiaChi());
            pstm.setInt(7, nv.getMaChucVu());
            pstm.setInt(8, nv.getMaNV());
            int r = pstm.executeUpdate();
            if (r > 0) {
                result = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally{
            connectManager.closeConnection();
        }
        return result;
    }
    public boolean kiemTraSoDienThoai(String soDienThoai){
        boolean result = false;
        String sql = "Select COUNT(*) From NhanVien  Where SoDienThoai = ? and Is_Deleted = 0";
        try {
            connectManager.openConnection();
            Connection connection = connectManager.getConnection();
            PreparedStatement pstm = connection.prepareStatement(sql);
            pstm.setString(1, soDienThoai);
            
            ResultSet rs = pstm.executeQuery();
            if (rs.next()) {
                int count = rs.getInt(1);
                if(count > 0)
                    return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally{
            connectManager.closeConnection();
        }
        return result;
    }

    public String getTenNVByMaNV(int maNV) {
        String tenNhanVien = null;
        String sql = "SELECT TenNV FROM NhanVien WHERE MaNV = ?";

        try {
            connectManager.openConnection();
            Connection connection = connectManager.getConnection();
            PreparedStatement pstm = connection.prepareStatement(sql);
            pstm.setInt(1, maNV);
            ResultSet rs = pstm.executeQuery();

            if (rs.next()) {
                tenNhanVien = rs.getString("TenNV");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return tenNhanVien;
    }
}

