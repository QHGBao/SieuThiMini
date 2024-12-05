package BUS;

import DAO.HoaDonDAO;
import DTO.HoaDonDTO;
import DTO.CTHoaDonDTO;
import java.util.ArrayList;

public class HoaDonBUS {

    private HoaDonDAO hoaDonDAO;

    public HoaDonBUS() {
        hoaDonDAO = new HoaDonDAO();
    }

    public ArrayList<HoaDonDTO> getAllHoaDon() {
        return hoaDonDAO.getAllHoaDon();
    }

    public ArrayList<CTHoaDonDTO> getAllCTHoaDon() {
        return hoaDonDAO.getAllCTHoaDon();
    }

    public int addHoaDon(HoaDonDTO hoadon) {
        return hoaDonDAO.addHoaDon(hoadon);
    }

    public boolean addCTHoaDon(CTHoaDonDTO cthd) {
        return hoaDonDAO.addCTHoaDon(cthd);
    }

    public Integer getMaHD() {
        return hoaDonDAO.getMaHD();
    }

    public ArrayList<HoaDonDTO> searchHoaDon(int maKH, int maHD, java.sql.Date startDate, java.sql.Date endDate) {
        return hoaDonDAO.searchHoaDon(maKH, maHD, startDate, endDate);
    }

    public ArrayList<CTHoaDonDTO> getChiTietHoaDonByMaHD(int maHD) {
        return hoaDonDAO.getChiTietHoaDonByMaHD(maHD);
    }

    public HoaDonDTO getHoaDonByMaHD(int maHD) {
        return hoaDonDAO.getHoaDonByMaHD(maHD);
    }
}