package BUS;
import java.util.List;

import DAO.QuanLyGiamGiaSpDAO;
import DAO.SanPhamDAO;
import DTO.QuanLyGiamGiaSpDTO;
import DTO.SanPhamDTO;
import DTO.SanPhamKmDTO;
import java.util.ArrayList;

public class QuanLyGiamGiaSpBUS {
    private QuanLyGiamGiaSpDAO dao;

    public QuanLyGiamGiaSpBUS() {
        dao = new QuanLyGiamGiaSpDAO();
    }

    public ArrayList<QuanLyGiamGiaSpDTO> getAllGiamGiaSP() {
        return dao.getAllGiamGiaSP();
    }
    
    public boolean addGiamGiaSP(QuanLyGiamGiaSpDTO khuyenMai, List<SanPhamKmDTO> smkmDtos) {
    if (khuyenMai.getTenKM() == null || khuyenMai.getTenKM().isEmpty()) {
        System.out.println("Tên khuyến mãi không được để trống!");
        return false;
    }
    if (khuyenMai.getNgayBD() == null || khuyenMai.getNgayKT() == null) {
        System.out.println("Ngày bắt đầu và ngày kết thúc không được để trống!");
        return false;
    }
    if (khuyenMai.getNgayBD().after(khuyenMai.getNgayKT())) {
        
        
        return false;
    }
    if (khuyenMai.getPtGiam() < 0 || khuyenMai.getPtGiam() > 100) {
        System.out.println("Phần trăm giảm giá phải nằm trong khoảng 0 đến 100!");
        return false;
    }
    
    try {
        boolean res = dao.addGiamGiaSP(khuyenMai);
        smkmDtos.forEach(t -> {
            dao.addGiamGiaSPAndKm(t);
        });
        return res;
    } catch (NumberFormatException e) {
        System.out.println("Lỗi khi thêm khuyến mãi: " + e.getMessage());
        e.printStackTrace();
        return false;
    }
    
    }

    public ArrayList<SanPhamKmDTO> getSanPhamKmByMaKM(int maKM) {
        return dao.getSanPhamKmByMaKM(maKM);
    }

    public SanPhamDTO getSanPhamByMaSP(int maSP) {
        return SanPhamDAO.getSanPhamByMaSP(maSP); // Gọi hàm từ DAO
    }

    public int getLastMaKM() {
        try {
            return dao.getLastMaKM(); // Gọi phương thức từ DAO
        } catch (Exception e) {
            e.printStackTrace();
            return -1; // Trả về -1 nếu có lỗi
        }
    }

    public List<String> getAllDiscountNames() {
        return dao.getAllDiscountNames();
    }

    public int getDiscountedPrice(int promotionId, int productId) {
        return dao.getDiscountedPrice(promotionId, productId);
    }

    public int getPromotionIdByName(String promotionName) {
        return dao.getPromotionIdByName(promotionName);
    }
    
    
}
