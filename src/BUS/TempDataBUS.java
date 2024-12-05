package BUS;

import java.util.ArrayList;

import DAO.TempDataDAO;
import DTO.TempDataDTO;

public class TempDataBUS {

    private TempDataDAO tempDataDAO;

    public TempDataBUS() {
        tempDataDAO = new TempDataDAO();
    }

    // Đổi điểm thành tiền
    public void updateDiemThanhTien(int ddttDiemApDung, int ddttTienApDung) {
        tempDataDAO.updateDiemThanhTien(ddttDiemApDung, ddttTienApDung);
    }

    // Đổi tiền thành điểm
    public void updateTienThanhDiem(int ddttTienApDung, int dttdDiemApDung) {
        tempDataDAO.updateTienThanhDiem(ddttTienApDung, dttdDiemApDung);
    }

    public ArrayList<TempDataDTO> getAllTempData() {
        return tempDataDAO.getTempData();  // Trả về ArrayList<TempDataDTO>
    }
}
