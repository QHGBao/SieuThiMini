package BUS;

import DAO.DoiDiemThanhTienDAO;
import DTO.DoiDiemThanhTienDTO;
import java.util.ArrayList;

public class DoiDiemThanhTienBUS {
    private DoiDiemThanhTienDAO doiDiemThanhTienDAO;

    public DoiDiemThanhTienBUS() {
        doiDiemThanhTienDAO = new DoiDiemThanhTienDAO();
    }

    public ArrayList<DoiDiemThanhTienDTO> getAllDoiDiemThanhTien() {
        return doiDiemThanhTienDAO.getAllDoiDiemThanhTien();
    }

    public boolean addDoiDiemThanhTien(DoiDiemThanhTienDTO ddtt) {
        return doiDiemThanhTienDAO.addDoiDiemThanhTien(ddtt);
    }

    public boolean updateDoiDiemThanhTien(DoiDiemThanhTienDTO ddtt) {
        return doiDiemThanhTienDAO.updateDoiDiemThanhTien(ddtt);
    }

    public boolean deleteDoiDiemThanhTien(int maDD) {
        return doiDiemThanhTienDAO.deleteDoiDiemThanhTien(maDD);
    }

}
