package DAO;

import DTO.PhieuNhapDTO;
import DTO.ProductDTO;
import DTO.CTPhieuNhapDTO;

import java.sql.*;
import java.util.ArrayList;

public class PhieuNhapDao {
    private ConnectManager connectManager;

    public PhieuNhapDao() {
        connectManager = new ConnectManager();
    }

    public ArrayList<ProductDTO> getAllSP(){
        ArrayList<ProductDTO> lssp = new ArrayList<ProductDTO>();
        String sql = "Select MaSP, TenSP, SoLuong, Is_Deleted from SanPham";
        try {
            connectManager.openConnection();
            Connection connection = connectManager.getConnection();
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                if (rs.getInt(4) != 1) {
                    ProductDTO sp = new ProductDTO();
                    sp.setMaSP(rs.getInt(1));
                    sp.setTenSP(rs.getString(2));
                    sp.setSoLuong(rs.getInt(3));
                    lssp.add(sp);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            connectManager.closeConnection();
        }
        return lssp;
    }

    public ArrayList<PhieuNhapDTO.tablePNDTO> getAllRow(){
        ArrayList<PhieuNhapDTO.tablePNDTO> dsTable = new ArrayList<PhieuNhapDTO.tablePNDTO>();
        String sql = "Select p.MaPN, p.NgayLap, nv.TenNV, ncc.TenNCC, TrangThai, p.Is_Deleted " + 
                    "From PhieuNhap p join NhaCungCap ncc on p.MaNCC = ncc.MaNCC join NhanVien nv on p.MaNV = nv.MaNV";
        try {
            connectManager.openConnection();
            Connection connection = connectManager.getConnection();
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                if (rs.getInt(6) != 1) {
                    PhieuNhapDTO pn = new PhieuNhapDTO();
                    PhieuNhapDTO.tablePNDTO row = pn.new tablePNDTO();
                    row.setMaPN(rs.getInt(1));
                    row.setNgayLap(rs.getTimestamp(2));
                    row.setTenNV(rs.getString(3));
                    row.setTenNCC(rs.getString(4));
                    if(rs.getInt(5) == 0){
                        row.setTrangThai("Chờ Xử Lý");
                    } else {
                        row.setTrangThai("Hoàn Thành");
                    }
                    row.setIs_deleted(rs.getInt(6));
                    dsTable.add(row);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            connectManager.closeConnection();
        }
        return dsTable;
    }

    public ArrayList<PhieuNhapDTO.tableCtPNDTO> getAllRowCT(int maPN){
        ArrayList<PhieuNhapDTO.tableCtPNDTO> dsCTPn = new ArrayList<PhieuNhapDTO.tableCtPNDTO>();
        String sql = "Select sp.TenSP, ctpn.SoLuong, ctpn.GiaNhap, (ctpn.SoLuong * ctpn.GiaNhap) AS ThanhTien " + 
                        "from CTPhieuNhap ctpn join SanPham sp on ctpn.MaSP=sp.MaSP " +
                        "where ctpn.MaPN =? ";
        try {
            connectManager.openConnection();
            Connection connection = connectManager.getConnection();
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, maPN);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                PhieuNhapDTO pn = new PhieuNhapDTO();
                PhieuNhapDTO.tableCtPNDTO row = pn.new tableCtPNDTO();
                row.setTenSP(rs.getString(1));
                row.setSoLuong(rs.getInt(2));
                row.setGiaNhap(rs.getInt(3));
                row.setThanhTien(rs.getInt(4));
                dsCTPn.add(row);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            connectManager.closeConnection();
        }
        return dsCTPn;
    }

    public ArrayList<PhieuNhapDTO.tableSPchon> getAllRowSPChon(int maPN){
        ArrayList<PhieuNhapDTO.tableSPchon> dsCTPn = new ArrayList<PhieuNhapDTO.tableSPchon>();
        String sql = "Select sp.MaSP, sp.TenSP, ctpn.SoLuong, ctpn.GiaNhap " + 
                        "from CTPhieuNhap ctpn join SanPham sp on ctpn.MaSP=sp.MaSP " +
                        "where ctpn.MaPN =? ";
        try {
            connectManager.openConnection();
            Connection connection = connectManager.getConnection();
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, maPN);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                PhieuNhapDTO pn = new PhieuNhapDTO();
                PhieuNhapDTO.tableSPchon row = pn.new tableSPchon();
                row.setMaSP(rs.getInt(1));
                row.setTenSP(rs.getString(2));
                row.setSoLuong(rs.getInt(3));
                row.setGiaNhap(rs.getInt(4));
                dsCTPn.add(row);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            connectManager.closeConnection();
        }
        return dsCTPn;
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
                    pn.setMaPN(rs.getInt(1));
                    pn.setNgayLap(rs.getTimestamp(2));
                    pn.setMaNV(rs.getInt(3));
                    pn.setMaNCC(rs.getInt(4));
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

    public ArrayList<CTPhieuNhapDTO> getAllCTPN(int mapn){
        ArrayList<CTPhieuNhapDTO> lsctpn = new ArrayList<CTPhieuNhapDTO>();
        String sql = "Select * from CTPhieuNhap Where MaPN = ?";
        try {
            connectManager.openConnection();
            Connection connection = connectManager.getConnection();
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, mapn);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                CTPhieuNhapDTO ctpn = new CTPhieuNhapDTO();
                ctpn.setMaPN(rs.getInt(1));
                ctpn.setMaSP(rs.getInt(2));
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

    public ArrayList<PhieuNhapDTO.tablePNDTO> searchArrayNCC(String keyword){
        ArrayList<PhieuNhapDTO.tablePNDTO> dsSearch = new ArrayList<PhieuNhapDTO.tablePNDTO>();
        try {
            connectManager.openConnection();
            Connection connection = connectManager.getConnection();
            String sql = "Select p.MaPN, p.NgayLap, nv.TenNV, ncc.TenNCC, TrangThai, p.Is_Deleted " +
                        "From PhieuNhap p join NhaCungCap ncc on p.MaNCC = ncc.MaNCC join NhanVien nv on p.MaNV = nv.MaNV " +
                        "where ncc.TenNCC like ?";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, "%" + keyword + "%");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                if(rs.getInt(6) != 1){
                    PhieuNhapDTO pn = new PhieuNhapDTO();
                    PhieuNhapDTO.tablePNDTO row = pn.new tablePNDTO();
                    row.setMaPN(rs.getInt(1));
                    row.setNgayLap(rs.getTimestamp(2));
                    row.setTenNV(rs.getString(3));
                    row.setTenNCC(rs.getString(4));
                    if(rs.getInt(5) == 0){
                        row.setTrangThai("Chờ Xử Lý");
                    } else {
                        row.setTrangThai("Hoàn Thành");
                    }
                    row.setIs_deleted(rs.getInt(6));
                    dsSearch.add(row);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            connectManager.closeConnection();
        }
        return dsSearch;
    }
    
    public boolean themPN(PhieuNhapDTO pn){
        boolean check = false;
        try {
            connectManager.openConnection();
            Connection connection = connectManager.getConnection();
            String sql = "Insert into PhieuNhap values(?,?,?,?,?,?)";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, pn.getMaPN());
            stmt.setTimestamp(2, pn.getNgayLap());
            stmt.setInt(3, pn.getMaNV());
            stmt.setInt(4, pn.getMaNCC());
            stmt.setInt(5, 0);
            stmt.setInt(6, pn.getIs_Deleted());
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
            stmt.setInt(1, ctpn.getMaPN());
            stmt.setInt(2, ctpn.getMaSP());
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

    public PhieuNhapDTO findPn(int maPn){
        PhieuNhapDTO pn = new PhieuNhapDTO();
        try {
            connectManager.openConnection();
            Connection connection = connectManager.getConnection();
            String sql = "Select * from PhieuNhap Where MaPN = ?";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, maPn);
            ResultSet rs = stmt.executeQuery();
            if(rs.next()){
                pn.setMaPN(rs.getInt(1));
                pn.setNgayLap(rs.getTimestamp(2));
                pn.setMaNV(rs.getInt(3));
                pn.setMaNCC(rs.getInt(4));
                if(rs.getInt(5) == 0){
                    pn.setTrangThai("Chờ Xử Lý");
                } else {
                    pn.setTrangThai("Hoàn Thành");
                }
                pn.setIs_Deleted(rs.getInt(6));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            connectManager.closeConnection();
        }
        return pn;
    }

    public boolean xoaPN(int maPN){
        boolean check = false;
        if(xoaCTPN(maPN)){
            try {
                connectManager.openConnection();
                Connection connection = connectManager.getConnection();
                String sql = "Delete from PhieuNhap Where MaPN=?";
                PreparedStatement stmt = connection.prepareStatement(sql);
                stmt.setInt(1, maPN);
                if (stmt.executeUpdate() >= 1) {
                    check = true;
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                connectManager.closeConnection();
            }
        }
        return check;
    }

    public boolean xoaCTPN(int maPN){
        boolean check = false;
        try {
            connectManager.openConnection();
            Connection connection = connectManager.getConnection();
            String sql = "Delete from CTPhieuNhap Where MaPN=?";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, maPN);
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

    public boolean capNhatSL(int maPN){
        boolean check = false;
        try {
            connectManager.openConnection();
            Connection connection = connectManager.getConnection();
            String sql = "UPDATE SanPham " +
            "SET SoLuong = SoLuong + (" +
            "    SELECT SoLuong " +
            "    FROM CTPhieuNhap " +
            "    WHERE CTPhieuNhap.MaSP = SanPham.MaSP AND CTPhieuNhap.MaPN = ?" +
            ") " +
            "WHERE EXISTS (" +
            "    SELECT 1 " +
            "    FROM CTPhieuNhap " +
            "    WHERE CTPhieuNhap.MaSP = SanPham.MaSP AND CTPhieuNhap.MaPN = ?" +
            ")";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, maPN);
            stmt.setInt(2, maPN);
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

    public boolean duyetPN(int maPN){
        boolean check = false;
        try {
            connectManager.openConnection();
            Connection connection = connectManager.getConnection();
            String sql = "UPDATE PhieuNhap SET TrangThai = 1 WHERE MaPN = ?";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, maPN);
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
            String sql = "SELECT COALESCE(MAX(MaPN), 0) + 1 AS newCode FROM PhieuNhap";
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
