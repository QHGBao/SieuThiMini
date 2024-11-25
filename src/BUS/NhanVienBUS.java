package BUS;

import java.util.ArrayList;

import DAO.NhanVienDAO;
import DTO.NhanVienDTO;

public class NhanVienBUS {
    NhanVienDAO nvDAO = new NhanVienDAO();
    public ArrayList<NhanVienDTO> getAllNhanVienDTO(){
        return nvDAO.getAllNhanVienDTO();
    }
    public boolean addNhanVienDTO(NhanVienDTO nv){
        return nvDAO.addNhanVienDTO(nv);
    }
    public Integer getMaNV(){
        return nvDAO.getMaNV();
    }
    public boolean deleteNhanVienDTO(int manv){
        return nvDAO.deleteNhanVienDTO(manv);
    }
    public NhanVienDTO getNhanVienByMaNV(int maNV){
        return nvDAO.getNhanVienByMaNV(maNV);
    }
    public boolean updateNhanVienDTO(NhanVienDTO nv){
        return nvDAO.updateNhanVienDTO(nv);
    }
    public boolean kiemTraSoDienThoai(String soDienThoai){
        return nvDAO.kiemTraSoDienThoai(soDienThoai);
    }
}
