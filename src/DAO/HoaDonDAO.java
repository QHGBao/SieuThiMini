package DAO;

import DTO.HoaDonDTO;
import DTO.KhachHangDTO;
import DTO.CTHoaDonDTO;
import java.sql.*;
import java.util.ArrayList;

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
        String sql = "SELECT MaHD, MaSP, SoLuong, GiaBan FROM CTHoaDon WHERE Is_Deleted = 0";  // Lọc các bản ghi chưa bị xóa
        
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
                    rs.getInt("GiaBan")
                );
                arr.add(cthd);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connectManager.closeConnection();
        }
        return arr;
    }
    
    // public boolean addHoaDon(HoaDonDTO hoadon) {
    //     String sql = "INSERT INTO HoaDon (NgayLap, HinhThuc, TongTien, TienGiam, ThanhTien, TienKhachDua, TienTraLai, MaNV, MaKH, Is_Deleted) " +
    //                  "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, 0)";  // Is_Deleted mặc định là 0 (không xóa)
    //     boolean isSuccess = false;
        
    //     try {
    //         connectManager.openConnection();
    //         Connection connection = connectManager.getConnection();
    //         PreparedStatement preparedStatement = connection.prepareStatement(sql);
            
    //         // Gán giá trị vào các tham số của câu lệnh SQL
    //         preparedStatement.setDate(1, new java.sql.Date(hoadon.getNgayLap().getTime()));
    //         preparedStatement.setString(2, hoadon.getHinhThuc());
    //         preparedStatement.setInt(3, hoadon.getTongTien());
    //         preparedStatement.setInt(4, hoadon.getTienGiam());
    //         preparedStatement.setInt(5, hoadon.getThanhTien());
    //         preparedStatement.setInt(6, hoadon.getTienKhachDua());
    //         preparedStatement.setInt(7, hoadon.getTienTraLai());
    //         preparedStatement.setInt(8, hoadon.getMaNV());
    //         preparedStatement.setInt(9, hoadon.getMaKH());
            
    //         int rowsAffected = preparedStatement.executeUpdate();
            
    //         // Kiểm tra xem có dòng nào được chèn vào không
    //         if (rowsAffected > 0) {
    //             isSuccess = true;
    //         }
    //     } catch (SQLException e) {
    //         e.printStackTrace();
    //     } finally {
    //         connectManager.closeConnection();
    //     }
        
    //     return isSuccess;
    // }

    public int addHoaDon(HoaDonDTO hoadon) {
        String sql = "INSERT INTO HoaDon (NgayLap, HinhThuc, TongTien, TienGiam, ThanhTien, TienKhachDua, TienTraLai, MaNV, MaKH, Is_Deleted) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, 0)";  // Is_Deleted mặc định là 0 (không xóa)
        int generatedId = -1;
        
        try {
            connectManager.openConnection();
            Connection connection = connectManager.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS); // Lấy key tự động
            preparedStatement.setDate(1, new java.sql.Date(hoadon.getNgayLap().getTime()));
            preparedStatement.setString(2, hoadon.getHinhThuc());
            preparedStatement.setInt(3, hoadon.getTongTien());
            preparedStatement.setInt(4, hoadon.getTienGiam());
            preparedStatement.setInt(5, hoadon.getThanhTien());
            preparedStatement.setInt(6, hoadon.getTienKhachDua());
            preparedStatement.setInt(7, hoadon.getTienTraLai());
            preparedStatement.setInt(8, hoadon.getMaNV());
            preparedStatement.setInt(9, hoadon.getMaKH());
    
            int rowsAffected = preparedStatement.executeUpdate();
            
            // Kiểm tra và lấy ID của hóa đơn vừa được chèn vào
            if (rowsAffected > 0) {
                ResultSet rs = preparedStatement.getGeneratedKeys();
                if (rs.next()) {
                    generatedId = rs.getInt(1); // ID của hóa đơn mới
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connectManager.closeConnection();
        }
        
        return generatedId;
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
    

}
