package BUS;

import DTO.CTPhieuNhapDTO;
import DTO.PhieuNhapDTO;

import java.util.ArrayList;

import DAO.PhieuNhapDao;

public class PhieuNhapBUS {
    PhieuNhapDao pnDAO = new PhieuNhapDao();

    public ArrayList<PhieuNhapDTO> getAllPN(){
        return pnDAO.getAllPN();
    }

    public ArrayList<CTPhieuNhapDTO> getAllCTPN(String maPn){
        return pnDAO.getAllCTPN(maPn);
    }
}
