package DTO;

import java.sql.Date;

public class HoaDonDTO {
    private int maHD;
    private int maNV;
    private int maKH;
    private Date ngayLap;
    private String hinhThuc;
    private int tongTien;
    private int tienGiam;
    private int thanhTien;
    private int tienKhachDua;
    private int tienTraLai;
    private int is_Deleted;

    public HoaDonDTO(int maHD, int maNV, int maKH, Date ngayLap, String hinhThuc, int tongTien, int tienGiam,
            int thanhTien, int tienKhachDua, int tienTraLai, int is_Deleted) {
        this.maHD = maHD;
        this.maNV = maNV;
        this.maKH = maKH;
        this.ngayLap = ngayLap;
        this.hinhThuc = hinhThuc;
        this.tongTien = tongTien;
        this.tienGiam = tienGiam;
        this.thanhTien = thanhTien;
        this.tienKhachDua = tienKhachDua;
        this.tienTraLai = tienTraLai;
        this.is_Deleted = is_Deleted;
    }

    public HoaDonDTO() {
        
    }

    public int getMaHD() {
        return maHD;
    }

    public void setMaHD(int maHD) {
        this.maHD = maHD;
    }

    public int getMaNV() {
        return maNV;
    }

    public void setMaNV(int maNV) {
        this.maNV = maNV;
    }

    public int getMaKH() {
        return maKH;
    }

    public void setMaKH(int maKH) {
        this.maKH = maKH;
    }

    public Date getNgayLap() {
        return ngayLap;
    }

    public void setNgayLap(Date ngayLap) {
        this.ngayLap = ngayLap;
    }

    public String getHinhThuc() {
        return hinhThuc;
    }

    public void setHinhThuc(String hinhThuc) {
        this.hinhThuc = hinhThuc;
    }

    public int getTongTien() {
        return tongTien;
    }

    public void setTongTien(int tongTien) {
        this.tongTien = tongTien;
    }

    public int getTienGiam() {
        return tienGiam;
    }

    public void setTienGiam(int tienGiam) {
        this.tienGiam = tienGiam;
    }

    public int getThanhTien() {
        return thanhTien;
    }

    public void setThanhTien(int thanhTien) {
        this.thanhTien = thanhTien;
    }

    public int getTienKhachDua() {
        return tienKhachDua;
    }

    public void setTienKhachDua(int tienKhachDua) {
        this.tienKhachDua = tienKhachDua;
    }

    public int getTienTraLai() {
        return tienTraLai;
    }

    public void setTienTraLai(int tienTraLai) {
        this.tienTraLai = tienTraLai;
    }

    public int getIs_Deleted() {
        return is_Deleted;
    }

    public void setIs_Deleted(int is_Deleted) {
        this.is_Deleted = is_Deleted;
    }

}
