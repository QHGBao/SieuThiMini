package DTO;

import java.util.ArrayList;

public class TaiKhoan_DTO {
    private int maTK;
    private String tenTK;
    private String matKhau;
    private int maNV;
    private int maQuyen;
    private int is_Deleted;

    public TaiKhoan_DTO(){

    }
    
    public int getMaTK() {
        return maTK;
    }
    public void setMaTK(int maTK) {
        this.maTK = maTK;
    }
    public String getTenTK() {
        return tenTK;
    }
    public void setTenTK(String tenTK) {
        this.tenTK = tenTK;
    }
    public String getMatKhau() {
        return matKhau;
    }
    public void setMatKhau(String matKhau) {
        this.matKhau = matKhau;
    }
    public int getMaNV() {
        return maNV;
    }
    public void setMaNV(int maNV) {
        this.maNV = maNV;
    }
    public int getMaQuyen() {
        return maQuyen;
    }
    public void setMaQuyen(int maQuyen) {
        this.maQuyen = maQuyen;
    }
    public int getIs_Deleted() {
        return is_Deleted;
    }
    public void setIs_Deleted(int is_Deleted) {
        this.is_Deleted = is_Deleted;
    }

    public void setMaTaiKhoan(int int1) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setMaTaiKhoan'");
    }

    public int getMaTaiKhoan() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getMaTaiKhoan'");
    }

    public void setPermissions(ArrayList<CTPhanQuyenDTO> permissions) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setPermissions'");
    }

    
}
