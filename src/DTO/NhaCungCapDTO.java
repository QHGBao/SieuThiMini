package DTO;

public class NhaCungCapDTO {
    private String maNCC;
    private String tenNCC;
    private String diaChi;
    private int sdt;
    private String nguoiLH;
    private int is_Deleted;
    
    public NhaCungCapDTO() {
    }

    public String getMaNCC() {
        return maNCC;
    }

    public void setMaNCC(String maNCC) {
        this.maNCC = maNCC;
    }

    public String getTenNCC() {
        return tenNCC;
    }

    public void setTenNCC(String tenNCC) {
        this.tenNCC = tenNCC;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }

    public int getSdt() {
        return sdt;
    }

    public void setSdt(int sdt) {
        this.sdt = sdt;
    }

    public String getNguoiLH() {
        return nguoiLH;
    }

    public void setNguoiLH(String nguoiLH) {
        this.nguoiLH = nguoiLH;
    }

    public int getIs_Deleted() {
        return is_Deleted;
    }

    public void setIs_Deleted(int is_Deleted) {
        this.is_Deleted = is_Deleted;
    }


}
