package DTO;

public class DoiTienThanhDiemDTO {
    private int maDT;
    private int mucTienMin;
    private int mucDiem;
 
    public DoiTienThanhDiemDTO(int maDT, int mucTienMin, int mucDiem) {
        this.maDT = maDT;
        this.mucTienMin = mucTienMin;
        this.mucDiem = mucDiem;
    }

    public DoiTienThanhDiemDTO() {
    }

    public int getMaDT() {
        return maDT;
    }
    public void setMaDT(int maDT) {
        this.maDT = maDT;
    }
    public int getMucTienMin() {
        return mucTienMin;
    }
    public void setMucTienMin(int mucTienMin) {
        this.mucTienMin = mucTienMin;
    }
    public int getMucDiem() {
        return mucDiem;
    }
    public void setMucDiem(int mucDiem) {
        this.mucDiem = mucDiem;
    }
}
