package DTO;

public class DTO_ThongKe_DoanhThu {
	private String ngayLap;
    private String maHoaDon;
    private String tenSanPham;
    private int soLuong;
    private double giaThuTe;
    private double tongTienHang;
    private double doanhThu;
    
	public DTO_ThongKe_DoanhThu(String ngayLap, String maHoaDon, String tenSanPham, int soLuong, double giaThuTe,double tongTienHang, double doanhThu) {
		this.ngayLap = ngayLap;
		this.maHoaDon = maHoaDon;
		this.tenSanPham = tenSanPham;
		this.soLuong = soLuong;
		this.giaThuTe = giaThuTe;
		this.tongTienHang = tongTienHang;
		this.doanhThu = doanhThu;
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

	public double getGiaThuTe() {
		return giaThuTe;
	}

	public void setGiaThuTe(double giaThuTe) {
		this.giaThuTe = giaThuTe;
	}

	public double getTongTienHang() {
		return tongTienHang;
	}

	public void setTongTienHang(double tongTienHang) {
		this.tongTienHang = tongTienHang;
	}

	public double getDoanhThu() {
		return doanhThu;
	}

	public void setDoanhThu(double doanhThu) {
		this.doanhThu = doanhThu;
	}
	
    
}
