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

    public boolean updateDiemTichLuy(int maKH, int diemMoi, String phoneNumber) {
        try {
            // Lấy điểm tích lũy hiện tại của khách hàng
            int diemHienTai = khDAO.getDiemTichLuyBySoDienThoai(phoneNumber);
            int tongDiem = diemHienTai + diemMoi;  // Cộng thêm điểm mới
    
            // Cập nhật lại điểm tích lũy trong cơ sở dữ liệu
            return khDAO.updateDiemTichLuy(maKH, tongDiem);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean subtractPoints(int maKH, int pointsToSubtract) {
        return khDAO.subtractPoints(maKH, pointsToSubtract);
    }
    
}
