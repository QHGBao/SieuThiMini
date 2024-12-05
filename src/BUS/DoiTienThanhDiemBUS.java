package BUS;

import java.util.ArrayList;

import DAO.DoiTienThanhDiemDAO;
import DTO.DoiTienThanhDiemDTO;

public class DoiTienThanhDiemBUS {
    private DoiTienThanhDiemDAO doiTienThanhDiemDAO;

    public DoiTienThanhDiemBUS() {
        doiTienThanhDiemDAO = new DoiTienThanhDiemDAO();
    }

    // Lấy toàn bộ danh sách
    public ArrayList<DoiTienThanhDiemDTO> getAllDoiTienThanhDiem() {
        return doiTienThanhDiemDAO.getAllDoiTienThanhDiem();
    }

    // Thêm mới
    public boolean addDoiTienThanhDiem(DoiTienThanhDiemDTO dttd) {
        return doiTienThanhDiemDAO.addDoiTienThanhDiem(dttd);
    }

    // Sửa đổi
    public boolean updateDoiTienThanhDiem(DoiTienThanhDiemDTO dttd) {
            return doiTienThanhDiemDAO.updateDoiTienThanhDiem(dttd);
    }

    // Xóa mềm
    public boolean deleteDoiTienThanhDiem(int maDT) {
        return doiTienThanhDiemDAO.deleteDoiTienThanhDiem(maDT);
    }

}
