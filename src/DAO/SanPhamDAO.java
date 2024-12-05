package DAO;


import DTO.SanPhamDTO;

import java.sql.*;
import java.util.ArrayList;

public class SanPhamDAO {
    private ConnectManager connectManager;

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
}

