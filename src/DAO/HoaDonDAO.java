package DAO;

import DTO.HoaDonDTO;
import DTO.KhachHangDTO;
import DTO.CTHoaDonDTO;
import java.sql.*;
import java.util.ArrayList;

import BUS.KhachHangBUS;
import BUS.ProductBUS;

public class HoaDonDAO {
    private ConnectManager connectManager;

    public HoaDonDAO() {
        connectManager = new ConnectManager();
    }

    public ArrayList<HoaDonDTO> getAllHoaDon() {
        ArrayList<HoaDonDTO> arr = new ArrayList<HoaDonDTO>();
        String sql = "SELECT * FROM HoaDon WHERE Is_Deleted = 0";
        try {
            connectManager.openConnection();
            Connection connection = connectManager.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                HoaDonDTO hoadon = new HoaDonDTO();
                hoadon.setMaHD(rs.getInt("MaHD"));
                hoadon.setNgayLap(rs.getDate("NgayLap"));
                hoadon.setHinhThuc(rs.getString("HinhThuc"));
                hoadon.setTongTien(rs.getInt("TongTien"));
                hoadon.setTienGiam(rs.getInt("TienGiam"));
                hoadon.setThanhTien(rs.getInt("ThanhTien"));
                hoadon.setTienKhachDua(rs.getInt("TienKhachDua"));
                hoadon.setTienTraLai(rs.getInt("TienTraLai"));
                hoadon.setMaNV(rs.getInt("MaNV"));
                hoadon.setMaKH(rs.getInt("MaKH"));
                arr.add(hoadon);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connectManager.closeConnection();
        }
        return arr;
    }

    public ArrayList<CTHoaDonDTO> getAllCTHoaDon() {
        ArrayList<CTHoaDonDTO> arr = new ArrayList<>();
        String sql = "SELECT MaHD, MaSP, SoLuong, GiaBan FROM CTHoaDon WHERE Is_Deleted = 0"; // Lọc các bản ghi chưa bị
                                                                                              // xóa

        try {
            connectManager.openConnection();
            Connection connection = connectManager.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet rs = preparedStatement.executeQuery();

            // Duyệt qua kết quả trả về và thêm vào ArrayList
            while (rs.next()) {
                CTHoaDonDTO cthd = new CTHoaDonDTO(
                        rs.getInt("MaHD"),
                        rs.getInt("MaSP"),
                        rs.getInt("SoLuong"),
                        rs.getInt("GiaBan"));
                arr.add(cthd);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connectManager.closeConnection();
        }
        return arr;
    }

    public int addHoaDon(HoaDonDTO hoadon) {
        String sql = "INSERT INTO HoaDon (NgayLap, HinhThuc, TongTien, TienGiam, ThanhTien, TienKhachDua, TienTraLai, MaNV, MaKH, Is_Deleted) "
                +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, 0)"; // Is_Deleted mặc định là 0 (không xóa)
        try {
            connectManager.openConnection();
            Connection connection = connectManager.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql); // Không cần
                                                                                    // Statement.RETURN_GENERATED_KEYS
                                                                                    // nữa
            preparedStatement.setDate(1, new java.sql.Date(hoadon.getNgayLap().getTime()));
            preparedStatement.setString(2, hoadon.getHinhThuc());
            preparedStatement.setInt(3, hoadon.getTongTien());
            preparedStatement.setInt(4, hoadon.getTienGiam());
            preparedStatement.setInt(5, hoadon.getThanhTien());
            preparedStatement.setInt(6, hoadon.getTienKhachDua());
            preparedStatement.setInt(7, hoadon.getTienTraLai());
            preparedStatement.setInt(8, hoadon.getMaNV());

            if (hoadon.getMaKH() == 0) {
                preparedStatement.setNull(9, java.sql.Types.INTEGER); // Đặt NULL cho MaKH nếu không có khách hàng
            } else {
                preparedStatement.setInt(9, hoadon.getMaKH()); // Đặt giá trị MaKH nếu có
            }

            int rowsAffected = preparedStatement.executeUpdate();

            // Nếu bạn không cần lấy ID, chỉ cần kiểm tra rowsAffected
            if (rowsAffected > 0) {
                return 1; // Trả về 1 nếu thêm thành công
            } else {
                return 0; // Trả về 0 nếu không có dòng nào bị ảnh hưởng
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return -1; // Trả về -1 nếu có lỗi xảy ra
        } finally {
            connectManager.closeConnection();
        }
    }

    public Integer getMaHD() {
        int mahd = 0;
        String sql = "SELECT TOP 1 MaHD FROM HoaDon ORDER BY MaHD DESC";
        try {
            connectManager.openConnection();
            Connection connection = connectManager.getConnection();

            PreparedStatement pstm = connection.prepareStatement(sql);
            ResultSet rs = pstm.executeQuery();
            if (rs.next()) {
                mahd = rs.getInt("MaHD");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            connectManager.closeConnection();
        }
        return mahd;
    }

    public boolean addCTHoaDon(CTHoaDonDTO cthd) {
        String sql = "INSERT INTO CTHoaDon (MaHD, MaSP, SoLuong, GiaBan) VALUES (?, ?, ?, ?)";
        boolean isSuccess = false;

        try {
            connectManager.openConnection();
            Connection connection = connectManager.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            // Gán giá trị vào các tham số trong câu lệnh SQL
            preparedStatement.setInt(1, cthd.getMaHD());
            preparedStatement.setInt(2, cthd.getMaSP());
            preparedStatement.setInt(3, cthd.getSoLuong());
            preparedStatement.setInt(4, cthd.getGiaBan());

            int rowsAffected = preparedStatement.executeUpdate();

            // Kiểm tra xem có dòng nào được chèn vào không
            if (rowsAffected > 0) {
                isSuccess = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connectManager.closeConnection();
        }

        return isSuccess;
    }

    public ArrayList<HoaDonDTO> searchHoaDon(int maKH, int maHD, java.sql.Date startDate, java.sql.Date endDate) {
        ArrayList<HoaDonDTO> arr = new ArrayList<HoaDonDTO>();

        // Câu lệnh SQL để tìm kiếm theo các điều kiện
        String sql = "SELECT * FROM HoaDon WHERE Is_Deleted = 0";

        // Thêm điều kiện cho mã hóa đơn nếu có
        if (maHD != -1) {
            sql += " AND MaHD = ?";
        }

        // Thêm điều kiện cho mã khách hàng nếu có
        if (maKH != -1) {
            sql += " AND MaKH = ?";
        }

        // Thêm điều kiện cho ngày nếu có
        if (startDate != null && endDate != null) {
            sql += " AND NgayLap BETWEEN ? AND ?";
        }

        try {
            // Mở kết nối
            connectManager.openConnection();
            Connection connection = connectManager.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            int index = 1;

            // Thêm các tham số vào PreparedStatement
            if (maHD != -1) {
                preparedStatement.setInt(index++, maHD);
            }

            if (maKH != -1) {
                preparedStatement.setInt(index++, maKH);
            }

            if (startDate != null && endDate != null) {
                preparedStatement.setDate(index++, startDate);
                preparedStatement.setDate(index++, endDate);
            }

            // Thực hiện truy vấn
            ResultSet rs = preparedStatement.executeQuery();

            // Xử lý kết quả
            while (rs.next()) {
                HoaDonDTO hoadon = new HoaDonDTO();
                hoadon.setMaHD(rs.getInt("MaHD"));
                hoadon.setNgayLap(rs.getDate("NgayLap"));
                hoadon.setHinhThuc(rs.getString("HinhThuc"));
                hoadon.setTongTien(rs.getInt("TongTien"));
                hoadon.setTienGiam(rs.getInt("TienGiam"));
                hoadon.setThanhTien(rs.getInt("ThanhTien"));
                hoadon.setTienKhachDua(rs.getInt("TienKhachDua"));
                hoadon.setTienTraLai(rs.getInt("TienTraLai"));
                hoadon.setMaNV(rs.getInt("MaNV"));
                hoadon.setMaKH(rs.getInt("MaKH"));
                arr.add(hoadon);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connectManager.closeConnection();
        }

        return arr;
    }

    public ArrayList<CTHoaDonDTO> getChiTietHoaDonByMaHD(int maHD) {
        ArrayList<CTHoaDonDTO> chiTietHoaDon = new ArrayList<>();
        String sql = "SELECT C.MaSP, C.SoLuong, C.GiaBan " +
                     "FROM CTHoaDon C " +
                     "WHERE C.MaHD = ?";

        ProductBUS productBUS = new ProductBUS();
    
        try {
            connectManager.openConnection();
            Connection connection = connectManager.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, maHD); // Gán maHD vào câu lệnh SQL
            ResultSet rs = preparedStatement.executeQuery();
    
            // Duyệt qua kết quả trả về và thêm vào ArrayList
            while (rs.next()) {
                int maSP = rs.getInt("MaSP");
                String tenSP = productBUS.getTenSanPhamByMaSP(maSP); 
                int soLuong = rs.getInt("SoLuong");
                int giaBan = rs.getInt("GiaBan");
    
                // Tạo đối tượng DTO và thêm vào list
                CTHoaDonDTO cthd = new CTHoaDonDTO(soLuong, giaBan, tenSP);
                chiTietHoaDon.add(cthd);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connectManager.closeConnection();
        }
        return chiTietHoaDon;
    }

    public HoaDonDTO getHoaDonByMaHD(int maHD) {
        HoaDonDTO hoaDon = null;
        String query = "SELECT * FROM HoaDon WHERE MaHD = ? AND Is_Deleted = 0";
    
        try {
            connectManager.openConnection();
            Connection connection = connectManager.getConnection();
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setInt(1, maHD);
            ResultSet rs = stmt.executeQuery();
    
            if (rs.next()) {
                hoaDon = new HoaDonDTO();
                hoaDon.setMaHD(rs.getInt("MaHD"));
                hoaDon.setNgayLap(rs.getDate("NgayLap"));
                hoaDon.setHinhThuc(rs.getString("HinhThuc"));
                hoaDon.setTongTien(rs.getInt("TongTien"));
                hoaDon.setTienGiam(rs.getInt("TienGiam"));
                hoaDon.setThanhTien(rs.getInt("ThanhTien"));
                hoaDon.setTienKhachDua(rs.getInt("TienKhachDua"));
                hoaDon.setTienTraLai(rs.getInt("TienTraLai"));
                hoaDon.setMaNV(rs.getInt("MaNV"));
                hoaDon.setMaKH(rs.getInt("MaKH")); // Nếu bạn lưu mã khách hàng
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connectManager.closeConnection();
        }
    
        return hoaDon;
    }

}
