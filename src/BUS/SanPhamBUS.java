package BUS;

import DAO.SanPhamDAO;
import DTO.SanPhamDTO;

import java.util.ArrayList;

public class SanPhamBUS {
    private SanPhamDAO dao;

    public SanPhamBUS() {
        dao = new SanPhamDAO();
    }

    public ArrayList<SanPhamDTO> getAllSanPham() {
        return dao.getAllSanPham();
    }
}

