package DAO;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;

public class DAO_ThongKe {
    private Connection con;
    private String url = "jdbc:sqlserver://localhost:1433;DatabaseName=SieuThiMini;encrypt=true;trustServerCertificate=true";
    private String username = "sa";
    private String pass = "1234";
    
    public DAO_ThongKe() {
        try {
            con = DriverManager.getConnection(url, username, pass);
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public ResultSet thongkeChiTieu(Timestamp startDate, Timestamp endDate) {
    	ResultSet rs = null;
        try {
            String sql ="SELECT pn.NgayLap, pn.MaPN, sp.TenSP, ctpn.MaSP, ctpn.SoLuong, ctpn.GiaNhap, " +
                    	"(ctpn.SoLuong * ctpn.GiaNhap) AS TongChiTieu " +
                    	"FROM PhieuNhap pn " +
                    	"JOIN CTPhieuNhap ctpn ON pn.MaPN = ctpn.MaPN " +
                    	"JOIN SanPham sp ON ctpn.MaSP = sp.MaSP " +
                    	"WHERE pn.NgayLap BETWEEN ? AND ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setTimestamp(1, startDate);
            ps.setTimestamp(2, endDate);
            rs = ps.executeQuery();
        } catch (Exception e) {
            System.out.println(e);
        }
        return rs;
    }
    
    // lấy dữ liệu thống kê chi tiêu
    public ResultSet thongkeChiTieuAll() {
        ResultSet rs = null;
        try {
            String sql = "SELECT pn.NgayLap, pn.MaPN, sp.TenSP, ctpn.MaSP, ctpn.SoLuong, ctpn.GiaNhap, " +
                         "(ctpn.SoLuong * ctpn.GiaNhap) AS TongChiTieu " +
                         "FROM PhieuNhap pn " +
                         "JOIN CTPhieuNhap ctpn ON pn.MaPN = ctpn.MaPN " +
                         "JOIN SanPham sp ON ctpn.MaSP = sp.MaSP";
            PreparedStatement ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
        } catch (Exception e) {
            System.out.println(e);
        }
        return rs;
    }

    // Thống kê doanh thu theo khoảng thời gian
    public ResultSet thongkeDoanhThu(Timestamp startDate, Timestamp endDate) {
    	ResultSet rs = null;
        try {
            String sql = "SELECT hd.NgayLap, hd.MaHD, sp.TenSP, cthd.SoLuong, cthd.GiaBan, " +
                    	"(cthd.SoLuong * cthd.GiaBan) AS TongTienHang, " +
                    	"SUM(cthd.SoLuong * cthd.GiaBan) OVER() AS TongDoanhThu " +
                    	"FROM HoaDon hd " +
                    	"JOIN CTHoaDon cthd ON hd.MaHD = cthd.MaHD " +
                    	"JOIN SanPham sp ON cthd.MaSP = sp.MaSP " +
                    	"WHERE hd.NgayLap BETWEEN ? AND ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setTimestamp(1, startDate);
            ps.setTimestamp(2, endDate);
            rs = ps.executeQuery();
        } catch (Exception e) {
            System.out.println(e);
        }
        return rs;
    }
    // lấy dữ liệu thống kê doanh thu
    public ResultSet thongkeDoanhThuAll() {
        ResultSet rs = null;
        try {
        	String sql = "SELECT hd.NgayLap, hd.MaHD, sp.TenSP, cthd.SoLuong, cthd.GiaBan, " +
                	"(cthd.SoLuong * cthd.GiaBan) AS TongTienHang, " +
                	"SUM(cthd.SoLuong * cthd.GiaBan) OVER() AS TongDoanhThu " +
                	"FROM HoaDon hd " +
                	"JOIN CTHoaDon cthd ON hd.MaHD = cthd.MaHD " +
                	"JOIN SanPham sp ON cthd.MaSP = sp.MaSP ";
            PreparedStatement ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
        } catch (Exception e) {
            System.out.println(e);
        }
        return rs;
    }
    
    // Thống kê lợi nhuận theo khoảng thời gian
    public ResultSet thongkeLoiNhuan(Timestamp startDate, Timestamp endDate) {
        ResultSet rs = null;
        try {
			String sql ="SELECT hd.NgayLap, hd.MaHD, sp.TenSP, cthd.SoLuong, ctpn.GiaNhap, cthd.GiaBan, " +
						"((cthd.SoLuong * cthd.GiaBan) - (cthd.SoLuong * ctpn.GiaNhap)) AS LoiNhuan " +
						"FROM HoaDon hd " +
						"JOIN CTHoaDon cthd ON hd.MaHD = cthd.MaHD " +
						"JOIN SanPham sp ON cthd.MaSP = sp.MaSP " +
						"JOIN CTPhieuNhap ctpn ON ctpn.MaSP = sp.MaSP " +
	                    "WHERE hd.NgayLap BETWEEN ? AND ?";
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setTimestamp(1, startDate);
			ps.setTimestamp(2, endDate);
			rs = ps.executeQuery();
		} catch (Exception e) {
			System.out.println(e);
		}
        return rs;
    }
    //lấy dữ liệu thống kê lợi nhuận
    public ResultSet thongkeLoiNhuanAll() {
        ResultSet rs = null;
        try {
        	String sql ="SELECT hd.NgayLap, hd.MaHD, sp.TenSP, cthd.SoLuong, ctpn.GiaNhap, cthd.GiaBan, " +
					"((cthd.SoLuong * cthd.GiaBan) - (cthd.SoLuong * ctpn.GiaNhap)) AS LoiNhuan " +
					"FROM HoaDon hd " +
					"JOIN CTHoaDon cthd ON hd.MaHD = cthd.MaHD " +
					"JOIN SanPham sp ON cthd.MaSP = sp.MaSP " +
					"JOIN CTPhieuNhap ctpn ON ctpn.MaSP = sp.MaSP ";
            PreparedStatement ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
        } catch (Exception e) {
            System.out.println(e);
        }
        return rs;
    }
}
