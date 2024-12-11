package BUS;

import DAO.SanPhamDAO;
import DTO.SanPhamDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public class SanPhamBUS {
    private SanPhamDAO dao;

    public SanPhamBUS() {
        dao = new SanPhamDAO();
    }

    public ArrayList<SanPhamDTO> getAllSanPham() {
        return dao.getAllSanPham();
    }

    public ArrayList<SanPhamDTO> getSanPhamByMaKM(int maKM) throws SQLException {
        return dao.getSanPhamByMaKM(maKM); // Gọi DAO
    }
    
    
}

