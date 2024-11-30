package BUS;

import java.util.ArrayList;

import DAO.KhachHangDAO;
import DTO.KhachHangDTO;

public class KhachHangBUS {
    KhachHangDAO khDAO = new KhachHangDAO();
    public ArrayList<KhachHangDTO> getAllKhachHangDTO(){
        return khDAO.getAllKhachHangDTO();
    }
    public boolean addKhachHangDTO(KhachHangDTO nv){
        return khDAO.addKhachHangDTO(nv);
    }
    public Integer getMaKH(){
        return khDAO.getMaKH();
    }
    public boolean deleteKhachHangDTO(int MaKH){
        return khDAO.deleteKhachHangDTO(MaKH);
    }
    public KhachHangDTO getKhachHangByMaKH(int makh){
        return khDAO.getKhachHangByMaKH(makh);
    }
    public boolean updateKhachHangDTO(KhachHangDTO nv){
        return khDAO.updateKhachHangDTO(nv);
    }
    public boolean kiemTraSoDienThoai(String soDienThoai){
        return khDAO.kiemTraSoDienThoai(soDienThoai);
    }
    public int getDiemTichLuyBySoDienThoai(String phoneNumber) {
        return khDAO.getDiemTichLuyBySoDienThoai(phoneNumber);
    }

    public int getMaKHBySDT(String sdt) {
        return khDAO.getMaKHBySDT(sdt);
    }
}
