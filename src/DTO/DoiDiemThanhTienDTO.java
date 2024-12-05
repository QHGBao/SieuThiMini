package DTO;

public class DoiDiemThanhTienDTO {
    private int maDD;
    private int mucDiem;
    private int mucTienMax;
    
    public DoiDiemThanhTienDTO(int maDD, int mucDiem, int mucTienMax) {
        this.maDD = maDD;
        this.mucDiem = mucDiem;
        this.mucTienMax = mucTienMax;
    }

    public DoiDiemThanhTienDTO() {
    }

    public int getMaDD() {
        return maDD;
    }
    public void setMaDD(int maDD) {
        this.maDD = maDD;
    }
    public int getMucDiem() {
        return mucDiem;
    }
    public void setMucDiem(int mucDiem) {
        this.mucDiem = mucDiem;
    }
    public int getMucTienMax() {
        return mucTienMax;
    }
    public void setMucTienMax(int mucTienMax) {
        this.mucTienMax = mucTienMax;
    }

}
