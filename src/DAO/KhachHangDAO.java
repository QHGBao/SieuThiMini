package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import DTO.KhachHangDTO;

public class KhachHangDAO {
    private ConnectManager connectManager;

    public KhachHangDAO() {
        connectManager = new ConnectManager();
    }

    public ArrayList<KhachHangDTO> getAllKhachHangDTO() {
        ArrayList<KhachHangDTO> arr = new ArrayList<KhachHangDTO>();
        String sql = "SELECT MaKH,TenKH,SoDienThoai,DiemTichLuy FROM KhachHang WHERE Is_Deleted = 0";
        try {
            connectManager.openConnection();
            Connection connection = connectManager.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                KhachHangDTO kh = new KhachHangDTO();
                kh.setMaKH(rs.getInt("MaKH"));
                kh.setTenKH(rs.getString("TenKH"));
                kh.setSoDienThoai(rs.getString("SoDienThoai"));
                kh.setDiemTichLuy(rs.getInt("DiemTichLuy"));
                arr.add(kh);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connectManager.closeConnection();
        }

        return arr;
    }
    public boolean addKhachHangDTO(KhachHangDTO kh) {
        boolean result = false;
        String sql = "Insert into KhachHang(TenKH,SoDienThoai,DiemTichLuy,Is_Deleted)"
                    +"values(?,?,?,0)";
        try {
            connectManager.openConnection();
            Connection connection = connectManager.getConnection();
            PreparedStatement pstm = connection.prepareStatement(sql);
            pstm.setString(1, kh.getTenKH());
            pstm.setString(2, kh.getSoDienThoai());
            pstm.setInt(3, kh.getDiemTichLuy());
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

    public Integer getMaKH() {
        int MaKH = 0;
        String sql = "SELECT TOP 1 MaKH FROM KhachHang ORDER BY MaKH DESC";
        try {
            connectManager.openConnection();
            Connection connection = connectManager.getConnection();
            
            PreparedStatement pstm = connection.prepareStatement(sql);
            ResultSet rs = pstm.executeQuery();
            if (rs.next()) {
                MaKH = rs.getInt("MaKH");
                MaKH += 1;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally{
            connectManager.closeConnection();
        }
        return MaKH;
    }
    public boolean deleteKhachHangDTO(int MaKH) {
        boolean result = false;
        String sql = "Update KhachHang Set Is_Deleted = ? Where MaKH = ?";
        try {
            connectManager.openConnection();
            Connection connection = connectManager.getConnection();
            PreparedStatement pstm = connection.prepareStatement(sql);
            pstm.setBoolean(1, true);
            pstm.setInt(2, MaKH);
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
    public KhachHangDTO getKhachHangByMaKH(int MaKH){
        KhachHangDTO kh = new KhachHangDTO();
        String sql = "SELECT MaKH,TenKH,SoDienThoai,DiemTichLuy FROM KhachHang WHERE MaKH = ?";
        try {
            connectManager.openConnection();
            Connection connection = connectManager.getConnection();
            PreparedStatement pstm = connection.prepareStatement(sql);
            pstm.setInt(1, MaKH);
            ResultSet rs = pstm.executeQuery();
            if (rs.next()) {
                kh.setMaKH(rs.getInt("MaKH"));
                kh.setTenKH(rs.getString("TenKH"));
                kh.setSoDienThoai(rs.getString("SoDienThoai"));
                kh.setDiemTichLuy(rs.getInt("DiemTichLuy"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally{
            connectManager.closeConnection();
        }
        return kh;
    }
    public boolean updateKhachHangDTO(KhachHangDTO kh){
        boolean result = false;
        String sql = "Update KhachHang Set TenKH = ?,SoDienThoai = ?,DiemTichLuy = ? Where MaKH = ?";
        try {
            connectManager.openConnection();
            Connection connection = connectManager.getConnection();
            PreparedStatement pstm = connection.prepareStatement(sql);
            pstm.setString(1, kh.getTenKH());
            pstm.setString(2, kh.getSoDienThoai());
            pstm.setInt(3, kh.getDiemTichLuy());
            pstm.setInt(4, kh.getMaKH());
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
        String sql = "Select COUNT(*) From KhachHang  Where SoDienThoai = ? and Is_Deleted = 0";
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
}
