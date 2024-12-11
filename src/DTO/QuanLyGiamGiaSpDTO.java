package DTO;



import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class QuanLyGiamGiaSpDTO {

    private int maKM;
    private String tenKM;
    private Date ngayBD;
    private Date ngayKT;
    private int ptGiam;
    

    public QuanLyGiamGiaSpDTO(int maKM, String tenKM, Date ngayBD, Date ngayKT, int ptGiam) {
        this.maKM = maKM;
        this.tenKM = tenKM;
        this.ngayBD = ngayBD;
        this.ngayKT = ngayKT;
        this.ptGiam = ptGiam;
        
    }

    public int getMaKM() {
        return maKM;
    }

    public void setMaKM(int maKM) {
        this.maKM = maKM;
    }

    public String getTenKM() {
        return tenKM;
    }

    public void setTenKM(String tenKM) {
        this.tenKM = tenKM;
    }

    public Date getNgayBD() {
        return ngayBD;
    }

    public void setNgayBD(Date ngayBD) {
        this.ngayBD = ngayBD;
    }

    public Date getNgayKT() {
        return ngayKT;
    }

    public void setNgayKT(Date ngayKT) {
        this.ngayKT = ngayKT;
    }

    public int getPtGiam() {
        return ptGiam;
    }

    public void setPtGiam(int ptGiam) {
        this.ptGiam = ptGiam;
    }

    
    
}
