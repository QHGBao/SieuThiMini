package DAO;

import DTO.PhieuNhapDTO;
import DTO.CTPhieuNhapDTO;
import java.sql.*;
import java.util.ArrayList;

public class PhieuNhapDao {
    private ConnectManager connectManager;

    public PhieuNhapDao() {
        connectManager = new ConnectManager();
    }

    public ArrayList<PhieuNhapDTO> getAllPN(){
        ArrayList<PhieuNhapDTO> lspn = new ArrayList<PhieuNhapDTO>();
        String sql = "Select * from PhieuNhap";
        try {
            connectManager.openConnection();
            Connection connection = connectManager.getConnection();
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                if (rs.getInt(5) != 1) {
                    PhieuNhapDTO pn = new PhieuNhapDTO();
                    pn.setMaPN(rs.getString(1));
                    pn.setNgayLap(rs.getString(2));
                    pn.setMaNV(rs.getString(3));
                    pn.setMaNCC(rs.getString(4));
                    pn.setIs_Deleted(rs.getInt(5));
                    lspn.add(pn);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            connectManager.closeConnection();
        }
        return lspn;
    }

    public ArrayList<CTPhieuNhapDTO> getAllCTPN(String mapn){
        ArrayList<CTPhieuNhapDTO> lsctpn = new ArrayList<CTPhieuNhapDTO>();
        String sql = "Select * from CTPhieuNhap Where MaPN="+mapn;
        try {
            connectManager.openConnection();
            Connection connection = connectManager.getConnection();
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                CTPhieuNhapDTO ctpn = new CTPhieuNhapDTO();
                ctpn.setMaPN(rs.getString(1));
                ctpn.setMaSP(rs.getString(2));
                ctpn.setSoLuong(rs.getInt(3));
                ctpn.setGiaNhap(rs.getInt(4));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            connectManager.closeConnection();
        }
        return lsctpn;
    }
    
    public boolean themPN(PhieuNhapDTO pn){
        boolean check = false;
        try {
            connectManager.openConnection();
            Connection connection = connectManager.getConnection();
            String sql = "Insert into PhieuNhap values(?,?,?,?,?)";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, pn.getMaPN());
            stmt.setString(2, pn.getNgayLap());
            stmt.setString(3, pn.getMaNV());
            stmt.setString(4, pn.getMaNCC());
            stmt.setInt(5, pn.getIs_Deleted());
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

    public boolean themCTPN(CTPhieuNhapDTO ctpn){
        boolean check = false;
        try {
            connectManager.openConnection();
            Connection connection = connectManager.getConnection();
            String sql = "Insert into CTPhieuNhap values(?,?,?,?)";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, ctpn.getMaPN());
            stmt.setString(2, ctpn.getMaSP());
            stmt.setInt(3, ctpn.getSoLuong());
            stmt.setInt(4, ctpn.getGiaNhap());
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

}
