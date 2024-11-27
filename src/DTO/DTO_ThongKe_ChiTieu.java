package DTO;

public class DTO_ThongKe_ChiTieu {
	private String ngayLap;
    private String maPhieu;
    private String tenSanPham;
    private String maSanPham;
    private int soLuong;
    private double giaNhap;
    private double chiTieu;

    public DTO_ThongKe_ChiTieu(String ngayLap, String maPhieu, String tenSanPham, String maSanPham, int soLuong, double giaNhap, double chiTieu) {
        this.ngayLap = ngayLap;
        this.maPhieu = maPhieu;
        this.tenSanPham = tenSanPham;
        this.maSanPham = maSanPham;
        this.soLuong = soLuong;
        this.giaNhap = giaNhap;
        this.chiTieu = chiTieu;
    }

    // Getters and Setters
    public String getNgayLap() { return ngayLap; }
    public void setNgayLap(String ngayLap) { this.ngayLap = ngayLap; }
    public String getMaPhieu() { return maPhieu; }
    public void setMaPhieu(String maPhieu) { this.maPhieu = maPhieu; }
    public String getTenSanPham() { return tenSanPham; }
    public void setTenSanPham(String tenSanPham) { this.tenSanPham = tenSanPham; }
    public String getMaSanPham() { return maSanPham; }
    public void setMaSanPham(String maSanPham) { this.maSanPham = maSanPham; }
    public int getSoLuong() { return soLuong; }
    public void setSoLuong(int soLuong) { this.soLuong = soLuong; }
    public double getGiaNhap() { return giaNhap; }
    public void setGiaNhap(double giaNhap) { this.giaNhap = giaNhap; }
    public double getChiTieu() { return chiTieu; }
    public void setChiTieu(double chiTieu) { this.chiTieu = chiTieu; }
}
