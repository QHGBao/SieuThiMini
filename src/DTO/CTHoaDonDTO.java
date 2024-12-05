package DTO;

public class CTHoaDonDTO {
    private int maHD;
    private int maSP;
    private int soLuong;
    private int giaBan;
    private String tenSP;
    private int thanhTien;

    public CTHoaDonDTO(int soLuong, int giaBan, String tenSP) {
        this.soLuong = soLuong;
        this.giaBan = giaBan;
        this.tenSP = tenSP;
        this.thanhTien = soLuong * giaBan;
    }

    public CTHoaDonDTO(int maHD, int maSP, int soLuong, int giaBan) {
        this.maHD = maHD;
        this.maSP = maSP;
        this.soLuong = soLuong;
        this.giaBan = giaBan;
    }

    public CTHoaDonDTO() {
        
    }

    public int getThanhTien() {
        return thanhTien;
    }

    public void setThanhTien(int thanhTien) {
        this.thanhTien = thanhTien;
    }

    public String getTenSP() {
        return tenSP;
    }

    public void setTenSP(String tenSP) {
        this.tenSP = tenSP;
    }

    public int getMaHD() {
        return maHD;
    }
    public void setMaHD(int maHD) {
        this.maHD = maHD;
    }
    public int getMaSP() {
        return maSP;
    }
    public void setMaSP(int maSP) {
        this.maSP = maSP;
    }
    public int getSoLuong() {
        return soLuong;
    }
    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }
    public int getGiaBan() {
        return giaBan;
    }
    public void setGiaBan(int giaBan) {
        this.giaBan = giaBan;
    }
}
