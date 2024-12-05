package BUS;

import java.util.List;

import DAO.LichSuDiemDAO;
import DTO.LichSuDiemDTO;

public class LichSuDiemBUS {

    private LichSuDiemDAO lichSuDiemDAO = new LichSuDiemDAO();

    public boolean addLichSuDiem(LichSuDiemDTO lichSuDiem) {
        return lichSuDiemDAO.insertLichSuDiem(lichSuDiem);
    }
    
    public List<LichSuDiemDTO> getLichSuDiemList() {
        return lichSuDiemDAO.getLichSuDiemList();
    }

    public List<LichSuDiemDTO> searchLichSuDiem(int maKH, int maHD, java.sql.Date startDate, java.sql.Date endDate) {
        return lichSuDiemDAO.searchLichSuDiem(maKH, maHD, startDate, endDate);
    }
}
