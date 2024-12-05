package DTO;

public class SanPhamKmDTO {
    private int maSP;
    private int maKM;

    public SanPhamKmDTO(int maSP, int maKM) {
        this.maSP = maSP;
        this.maKM = maKM;
    }

    public int getMaKM() {
        return maKM;
    }

    public void setMaKM(int maKM) {
        this.maKM = maKM;
    }

    public int getMaSP() {
        return maSP;
    }

    public void setMaSP(int maSP) {
        this.maSP = maSP;
    }
}
