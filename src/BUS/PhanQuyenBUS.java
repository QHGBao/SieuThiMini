package BUS;

import DTO.PhanQuyenDTO;
import DTO.TaiKhoan_DTO;
import DTO.CTPhanQuyenDTO;
import java.util.ArrayList;
import DAO.PhanQuyenDao;

public class PhanQuyenBUS {
    private final PhanQuyenDao phanQuyenDao;

    public PhanQuyenBUS() {
        phanQuyenDao = new PhanQuyenDao();
    }

    public ArrayList<PhanQuyenDTO> getALLQuyen() {
        return phanQuyenDao.getALLQuyen();
    }

    public ArrayList<TaiKhoan_DTO> getTaiKhoanByQuyen(int maQuyen) {
        return phanQuyenDao.getTaiKhoanByQuyen(maQuyen);
    }

    public ArrayList<CTPhanQuyenDTO> getPermissionsByQuyen(int maQuyen) {
        return phanQuyenDao.getPermissionsByQuyen(maQuyen);
    }

    public void savePermissions(ArrayList<CTPhanQuyenDTO> permissions) {
        phanQuyenDao.savePermissions(permissions);
    }
}
