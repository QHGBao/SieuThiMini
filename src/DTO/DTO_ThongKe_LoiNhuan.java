package DTO;

public class DTO_ThongKe_LoiNhuan {
	private String ngayLap;
    private String maHoaDon;
    private String tenSanPham;
    private int soLuong;
    private double giaNhap;
    private double giaBan;
    private double loiNhuan;
    
	public DTO_ThongKe_LoiNhuan() {
	}

	public DTO_ThongKe_LoiNhuan(String ngayLap, String maHoaDon, String tenSanPham, int soLuong, double giaNhap,
			double giaBan, double loiNhuan) {
		this.ngayLap = ngayLap;
		this.maHoaDon = maHoaDon;
		this.tenSanPham = tenSanPham;
		this.soLuong = soLuong;
		this.giaNhap = giaNhap;
		this.giaBan = giaBan;
		this.loiNhuan = loiNhuan;
	}

	public String getNgayLap() {
		return ngayLap;
	}

	public void setNgayLap(String ngayLap) {
		this.ngayLap = ngayLap;
	}

	public String getMaHoaDon() {
		return maHoaDon;
	}

	public void setMaHoaDon(String maHoaDon) {
		this.maHoaDon = maHoaDon;
	}

	public String getTenSanPham() {
		return tenSanPham;
	}

	public void setTenSanPham(String tenSanPham) {
		this.tenSanPham = tenSanPham;
	}

	public int getSoLuong() {
		return soLuong;
	}

	public void setSoLuong(int soLuong) {
		this.soLuong = soLuong;
	}

	public double getGiaNhap() {
		return giaNhap;
	}

	public void setGiaNhap(double giaNhap) {
		this.giaNhap = giaNhap;
	}

	public double getGiaBan() {
		return giaBan;
	}

	public void setGiaBan(double giaBan) {
		this.giaBan = giaBan;
	}

	public double getLoiNhuan() {
		return loiNhuan;
	}

	public void setLoiNhuan(double loiNhuan) {
		this.loiNhuan = loiNhuan;
	}
    
}
