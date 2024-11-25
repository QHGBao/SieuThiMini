package BUS;

import DTO.PhanQuyenDTO;
import java.util.ArrayList;
import DAO.PhanQuyenDao;
import DTO.TaiKhoan_DTO;

public class PhanQuyenBUS {
    PhanQuyenDao pqDAO = new PhanQuyenDao();

    public ArrayList<PhanQuyenDTO> getALLQuyen() {
        return pqDAO.getALLQuyen();
    }

    public ArrayList<TaiKhoan_DTO> getTaiKhoanByQuyen(int maQuyen){
        return pqDAO.getTaiKhoanByQuyen(maQuyen);
    }
}
