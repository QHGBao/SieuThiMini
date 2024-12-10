package DTO;

public class SanPhamDTO {
    private int MaSP;
    private String TenSP;
    private String MoTa;
    private int SoLuong;
    private String HinhAnh;
    private double GiaBan;
    private int MaLoai;

    public SanPhamDTO(){
        
    }

    public SanPhamDTO(int MaSP, String TenSP, String MoTa, int SoLuong, String HinhAnh, double GiaBan, int MaLoai) {
        this.MaSP = MaSP;
        this.TenSP = TenSP;
        this.MoTa = MoTa;
        this.SoLuong = SoLuong;
        this.HinhAnh = HinhAnh;
        this.GiaBan = GiaBan;
        this.MaLoai = MaLoai;
    }

    // Getters and Setters
    public int getMaSP() { 
        return MaSP; 
    }
    public void setMaSP(int MaSP) { 
        this.MaSP = MaSP; 
    }

    public String getTenSP() { 
        return TenSP; 
    }
    public void setTenSP(String TenSP) { 
        this.TenSP = TenSP; 
    }

    public String getMoTa() {
        return MoTa; 
    }
    public void setMoTa(String MoTa) { 
        this.MoTa = MoTa; 
    }

    public int getSoLuong() { 
        return SoLuong; 
    }
    public void setSoLuong(int SoLuong) { 
        this.SoLuong = SoLuong; 
    }

    public String getHinhAnh() { 
        return HinhAnh; 
    }
    public void setHinhAnh(String HinhAnh) { 
        this.HinhAnh = HinhAnh; 
    }

    public double getGiaBan() { 
        return GiaBan; 
    }
    public void setGiaBan(double GiaBan) { 
        this.GiaBan = GiaBan; 
    }

    public int getMaLoai() { 
        return MaLoai; 
    }
    public void setMaLoai(int MaLoai) { 
        this.MaLoai = MaLoai; 
    }
}
