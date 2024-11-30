package DTO;

public class CTPhanQuyenDTO {
    private int maQuyen;
    private int maChucNang;
    private int maHanhDong;
    private String hanhDong;
    private int is_Deleted;

    public CTPhanQuyenDTO(){

    }

    public int getMaQuyen() {
        return maQuyen;
    }

    public void setMaQuyen(int maQuyen) {
        this.maQuyen = maQuyen;
    }

    public int getMaChucNang() {
        return maChucNang;
    }

    public void setMaChucNang(int maChucNang) {
        this.maChucNang = maChucNang;
    }

    public int getMaHanhDong() {
        return maHanhDong;
    }

    public void setMaHanhDong(int maHanhDong) {
        this.maHanhDong = maHanhDong;
    }

    public String getHanhDong() {
        return hanhDong;
    }

    public void setHanhDong(String hanhDong) {
        this.hanhDong = hanhDong;
    }

    public int getIs_Deleted() {
        return is_Deleted;
    }

    public void setIs_Deleted(int is_Deleted) {
        this.is_Deleted = is_Deleted;
    }
   
    
}
