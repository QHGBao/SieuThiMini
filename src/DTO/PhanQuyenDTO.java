package DTO;

public class PhanQuyenDTO {
    private int maQuyen;
    private String tenQuyen;
    private String moTa;

    public PhanQuyenDTO(int maQuyen, String tenQuyen, String moTa) {
        this.maQuyen = maQuyen;
        this.tenQuyen = tenQuyen;
        this.moTa = moTa;
    }

    public PhanQuyenDTO(){

    }
    
    public int getMaQuyen() {
        return maQuyen;
    }
    public String getTenQuyen() {
    
        return tenQuyen;
    }
    public String getMoTa() {
        return moTa;
    }

    public void setMaQuyen(int maQuyen) {
        this.maQuyen = maQuyen;
    }

    public void setTenQuyen(String tenQuyen) {
        this.tenQuyen = tenQuyen;
    }

    public void setMoTa(String moTa) {
        this.moTa = moTa;
    }

    
}
