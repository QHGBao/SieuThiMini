package BUS;

//import DTO.NhaCungCapDTO;
import DTO.PhanQuyenDTO;

import java.util.ArrayList;

import DAO.PhanQuyenDao;

public class PhanQuyenBUS {
    PhanQuyenDao pqDAO = new PhanQuyenDao();
    public ArrayList<PhanQuyenDTO> getALLQuyen() {
        return pqDAO.getALLQuyen();
    }
}
