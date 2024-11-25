package DAO;

import DTO.NhaCungCapDTO;
import java.sql.*;
import java.util.ArrayList;

public class NhaCungCapDao {
    private ConnectManager connectManager;

    

    public NhaCungCapDao() {
        connectManager = new ConnectManager();
    }

    public ArrayList<NhaCungCapDTO> getAllNCC(){
        ArrayList<NhaCungCapDTO> dsNCC = new ArrayList<NhaCungCapDTO>();
        String sql = "Select * from NhaCungCap";
        try {
            connectManager.openConnection();
            Connection connection = connectManager.getConnection();
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                if (rs.getInt(6) != 1) {
                    NhaCungCapDTO ncc = new NhaCungCapDTO();
                    ncc.setMaNCC(rs.getInt(1));
                    ncc.setTenNCC(rs.getString(2));
                    ncc.setDiaChi(rs.getString(3));
                    ncc.setSdt(rs.getString(4));
                    ncc.setNguoiLH(rs.getString(5));
                    ncc.setIs_Deleted(rs.getInt(6));
                    dsNCC.add(ncc);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connectManager.closeConnection();
        }
        return dsNCC;
    }

    public boolean themNCC(NhaCungCapDTO ncc){
        boolean check = false;
        try {
            connectManager.openConnection();
            Connection connection = connectManager.getConnection();
            String sql = "Insert into NhaCungCap (TenNCC, DiaChi, Sdt, NguoiLH, Is_Deleted) values(?,?,?,?,?)";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, ncc.getTenNCC());
            stmt.setString(2, ncc.getDiaChi());
            stmt.setString(3, ncc.getSdt());
            stmt.setString(4, ncc.getNguoiLH());
            stmt.setInt(5, ncc.getIs_Deleted()); 
            if (stmt.executeUpdate() >= 1) {
                check = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally{
            connectManager.closeConnection();
        }
        return check;
    }

    public boolean findNCC(String sdt){
        boolean check = false;
        try {
            connectManager.openConnection();
            Connection connection = connectManager.getConnection();
            String sql = "Select * from NhaCungCap Where Sdt="+sdt;
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            check = rs.next();
        } catch (Exception e) {
            e.printStackTrace();
        } finally{
            connectManager.closeConnection();
        }
        return check;
    }

    public boolean updateNCC(NhaCungCapDTO ncc){
        boolean check = false;
        try {
            connectManager.openConnection();
            Connection connection = connectManager.getConnection();
            String sql = "Update NhaCungCap Set TenNCC=?, DiaChi=?, Sdt=?, NguoiLH=? Where MaNCC=?";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, ncc.getTenNCC());
            stmt.setString(2, ncc.getDiaChi());
            stmt.setString(3, ncc.getSdt());
            stmt.setString(4, ncc.getNguoiLH());
            stmt.setInt(5, ncc.getMaNCC());
            if (stmt.executeUpdate() >= 1) {
                check = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            connectManager.closeConnection();
        }
        return check;
    }

    public boolean deleteNCC(NhaCungCapDTO ncc){
        boolean check = false;
        try {
            connectManager.openConnection();
            Connection connection = connectManager.getConnection();
            String sql = "Update NhaCungCap Set Is_Deleted=? Where MaNCC=?";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, 1);
            stmt.setInt(2, ncc.getMaNCC());
            if (stmt.executeUpdate() >= 1) {
                check = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            connectManager.closeConnection();
        }
        return check;
    }

    public int createCodeNCC(){
        int codeCreated = -1;
        try {
            connectManager.openConnection();
            Connection connection = connectManager.getConnection();
            String sql = "SELECT COALESCE(MAX(MaNCC), 0) + 1 AS newCode FROM NhaCungCap";
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            if(rs.next()){
                codeCreated = rs.getInt("newCode");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            connectManager.closeConnection();
        }
        return codeCreated;
    }

}
