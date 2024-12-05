package DTO;

public class TempDataDTO {
    private int ddttDiemApDung;
    private int ddttTienApDung;
    private int dttdTienApDung ;
    private int dttdDiemApDung;

    public TempDataDTO(int ddttDiemApDung, int ddttTienApDung, int dttdTienApDung, int dttdDiemApDung) {
        this.ddttDiemApDung = ddttDiemApDung;
        this.ddttTienApDung = ddttTienApDung;
        this.dttdTienApDung = dttdTienApDung;
        this.dttdDiemApDung = dttdDiemApDung;
    }
    public TempDataDTO() {
    }
    public int getDdttDiemApDung() {
        return ddttDiemApDung;
    }
    public void setDdttDiemApDung(int ddttDiemApDung) {
        this.ddttDiemApDung = ddttDiemApDung;
    }
    public int getDdttTienApDung() {
        return ddttTienApDung;
    }
    public void setDdttTienApDung(int ddttTienApDung) {
        this.ddttTienApDung = ddttTienApDung;
    }
    public int getDttdTienApDung() {
        return dttdTienApDung;
    }
    public void setDttdTienApDung(int dttdTienApDung) {
        this.dttdTienApDung = dttdTienApDung;
    }
    public int getDttdDiemApDung() {
        return dttdDiemApDung;
    }
    public void setDttdDiemApDung(int dttdDiemApDung) {
        this.dttdDiemApDung = dttdDiemApDung;
    }

}
