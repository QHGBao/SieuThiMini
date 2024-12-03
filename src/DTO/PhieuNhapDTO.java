package DTO;

import java.sql.Timestamp;

public class PhieuNhapDTO {
    private int maPN;
    private Timestamp ngayLap;
    private int maNV;
    private int maNCC;
    private String trangThai;
    private int is_Deleted;
    
    public PhieuNhapDTO() {
    }

    public int getMaPN() {
        return maPN;
    }

    public void setMaPN(int maPN) {
        this.maPN = maPN;
    }

    public Timestamp getNgayLap() {
        return ngayLap;
    }

    public void setNgayLap(Timestamp ngayLap) {
        this.ngayLap = ngayLap;
    }

    public int getMaNV() {
        return maNV;
    }

    public void setMaNV(int maNV) {
        this.maNV = maNV;
    }

    public int getMaNCC() {
        return maNCC;
    }

    public void setMaNCC(int maNCC) {
        this.maNCC = maNCC;
    }

    public String getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }

    public int getIs_Deleted() {
        return is_Deleted;
    }

    public void setIs_Deleted(int is_Deleted) {
        this.is_Deleted = is_Deleted;
    }

    public class tablePNDTO {
        private int maPN;
        private Timestamp ngayLap;
        private String tenNV;
        private String tenNCC;
        private String trangThai;
        private int is_deleted;

        public tablePNDTO() {
        }

        public int getMaPN() {
            return maPN;
        }

        public void setMaPN(int maPN) {
            this.maPN = maPN;
        }

        public Timestamp getNgayLap() {
            return ngayLap;
        }

        public void setNgayLap(Timestamp ngayLap) {
            this.ngayLap = ngayLap;
        }

        public String getTenNV() {
            return tenNV;
        }

        public void setTenNV(String tenNV) {
            this.tenNV = tenNV;
        }

        public String getTenNCC() {
            return tenNCC;
        }

        public void setTenNCC(String tenNCC) {
            this.tenNCC = tenNCC;
        }

        public String getTrangThai() {
            return trangThai;
        }

        public void setTrangThai(String trangThai) {
            this.trangThai = trangThai;
        }

        public int getIs_deleted() {
            return is_deleted;
        }

        public void setIs_deleted(int is_deleted) {
            this.is_deleted = is_deleted;
        }
        
    }

    public class tableCtPNDTO {
        private String tenSP;
        private int soLuong;
        private int giaNhap;
        private int thanhTien;
        
        public tableCtPNDTO() {
        }

        public String getTenSP() {
            return tenSP;
        }

        public void setTenSP(String tenSP) {
            this.tenSP = tenSP;
        }

        public int getSoLuong() {
            return soLuong;
        }

        public void setSoLuong(int soLuong) {
            this.soLuong = soLuong;
        }

        public int getGiaNhap() {
            return giaNhap;
        }

        public void setGiaNhap(int giaNhap) {
            this.giaNhap = giaNhap;
        }

        public int getThanhTien() {
            return thanhTien;
        }

        public void setThanhTien(int thanhTien) {
            this.thanhTien = thanhTien;
        }
        
    }

    public class tableSPchon {
        private int maSP;
        private String tenSP;
        private int soLuong;
        private int giaNhap;
        
        public tableSPchon() {
        }

        public int getMaSP() {
            return maSP;
        }

        public void setMaSP(int maSP) {
            this.maSP = maSP;
        }

        public String getTenSP() {
            return tenSP;
        }

        public void setTenSP(String tenSP) {
            this.tenSP = tenSP;
        }

        public int getSoLuong() {
            return soLuong;
        }

        public void setSoLuong(int soLuong) {
            this.soLuong = soLuong;
        }

        public int getGiaNhap() {
            return giaNhap;
        }

        public void setGiaNhap(int giaNhap) {
            this.giaNhap = giaNhap;
        }

        
    }


}
