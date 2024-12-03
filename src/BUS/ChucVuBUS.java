package BUS;

import DAO.ChucVuDAO;

public class ChucVuBUS {
    ChucVuDAO cvDAO = new ChucVuDAO();

    public String getTenChucVuByMaChucVu(int maChucVu){
        return cvDAO.getTenChucVuByMaChucVu(maChucVu);
    }
    public Integer getMaChucVuByTenChucVu(String tenChucVu){
        return cvDAO.getMaChucVuByTenChucVu(tenChucVu);
    }

    public String getAllTenChucVu(){
        return cvDAO.getAllTenChucVu();
    }
}
