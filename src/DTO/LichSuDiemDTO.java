package DTO;

import java.sql.Date;

public class LichSuDiemDTO {
    private int maGiaoDich;
    private int maKH;
    private int maHD;
    private int diem;
    private Date ngayTichLuy;
    private String loaiGD;
    
    public LichSuDiemDTO(int maGiaoDich, int maKH, int maHD, int diem, Date ngayTichLuy, String loaiGD) {
        this.maGiaoDich = maGiaoDich;
        this.maKH = maKH;
        this.maHD = maHD;
        this.diem = diem;
        this.ngayTichLuy = ngayTichLuy;
        this.loaiGD = loaiGD;
    }

    public LichSuDiemDTO() {
    }

    public int getMaGiaoDich() {
        return maGiaoDich;
    }
    public void setMaGiaoDich(int maGiaoDich) {
        this.maGiaoDich = maGiaoDich;
    }
    public int getMaKH() {
        return maKH;
    }
    public void setMaKH(int maKH) {
        this.maKH = maKH;
    }
    public int getMaHD() {
        return maHD;
    }
    public void setMaHD(int maHD) {
        this.maHD = maHD;
    }
    public int getDiem() {
        return diem;
    }
    public void setDiem(int diem) {
        this.diem = diem;
    }
    public Date getNgayTichLuy() {
        return ngayTichLuy;
    }
    public void setNgayTichLuy(Date ngayTichLuy) {
        this.ngayTichLuy = ngayTichLuy;
    }
    public String getLoaiGD() {
        return loaiGD;
    }
    public void setLoaiGD(String loaiGD) {
        this.loaiGD = loaiGD;
    }

    
}
