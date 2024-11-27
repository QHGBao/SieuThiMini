package BUS;

import java.sql.ResultSet;
import java.sql.Timestamp;

import DAO.DAO_ThongKe;

public class BUS_ThongKe {
	private DAO_ThongKe daoThongKe;
	
	public BUS_ThongKe() {
		daoThongKe = new DAO_ThongKe();
	}
	public ResultSet thongkeChiTieu(Timestamp startDate, Timestamp endDate) {
        return daoThongKe.thongkeChiTieu(startDate, endDate);
    }

    public ResultSet thongkeDoanhThu(Timestamp startDate, Timestamp endDate) {
        return daoThongKe.thongkeDoanhThu(startDate, endDate);
    }

    public ResultSet thongkeLoiNhuan(Timestamp startDate, Timestamp endDate) {
        return daoThongKe.thongkeLoiNhuan(startDate, endDate);
    }
}
