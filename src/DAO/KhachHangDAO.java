package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import DTO.KhachHangDTO;

public class KhachHangDAO {
    private ConnectManager connectManager;

    public KhachHangDAO() {
        connectManager = new ConnectManager();
    }

    public ArrayList<KhachHangDTO> getAllKhachHangDTO() {
        ArrayList<KhachHangDTO> arr = new ArrayList<KhachHangDTO>();
        String sql = "SELECT MaKH,TenKH,SoDienThoai,DiemTichLuy FROM KhachHang WHERE Is_Deleted = 0";
        try {
            connectManager.openConnection();
            Connection connection = connectManager.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                KhachHangDTO kh = new KhachHangDTO();
                kh.setMaKH(rs.getInt("MaKH"));
                kh.setTenKH(rs.getString("TenKH"));
                kh.setSoDienThoai(rs.getString("SoDienThoai"));
                kh.setDiemTichLuy(rs.getInt("DiemTichLuy"));
                arr.add(kh);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connectManager.closeConnection();
        }

        return arr;
    }
    public boolean addKhachHangDTO(KhachHangDTO kh) {
        boolean result = false;
        String sql = "Insert into KhachHang(TenKH,SoDienThoai,DiemTichLuy,Is_Deleted)"
                    +"values(?,?,?,0)";
        try {
            connectManager.openConnection();
            Connection connection = connectManager.getConnection();
            PreparedStatement pstm = connection.prepareStatement(sql);
            pstm.setString(1, kh.getTenKH());
            pstm.setString(2, kh.getSoDienThoai());
            pstm.setInt(3, kh.getDiemTichLuy());
            int r = pstm.executeUpdate();
            if (r > 0) {
                result = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally{
            connectManager.closeConnection();
        }
        return result;
    }

    public Integer getMaKH() {
        int MaKH = 0;
        String sql = "SELECT TOP 1 MaKH FROM KhachHang ORDER BY MaKH DESC";
        try {
            connectManager.openConnection();
            Connection connection = connectManager.getConnection();
            
            PreparedStatement pstm = connection.prepareStatement(sql);
            ResultSet rs = pstm.executeQuery();
            if (rs.next()) {
                MaKH = rs.getInt("MaKH");
                MaKH += 1;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally{
            connectManager.closeConnection();
        }
        return MaKH;
    }
    public boolean deleteKhachHangDTO(int MaKH) {
        boolean result = false;
        String sql = "Update KhachHang Set Is_Deleted = ? Where MaKH = ?";
        try {
            connectManager.openConnection();
            Connection connection = connectManager.getConnection();
            PreparedStatement pstm = connection.prepareStatement(sql);
            pstm.setBoolean(1, true);
            pstm.setInt(2, MaKH);
            int r = pstm.executeUpdate();
            if (r > 0) {
                result = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally{
            connectManager.closeConnection();
        }
        return result;
    }
    public KhachHangDTO getKhachHangByMaKH(int MaKH){
        KhachHangDTO kh = new KhachHangDTO();
        String sql = "SELECT MaKH,TenKH,SoDienThoai,DiemTichLuy FROM KhachHang WHERE MaKH = ?";
        try {
            connectManager.openConnection();
            Connection connection = connectManager.getConnection();
            PreparedStatement pstm = connection.prepareStatement(sql);
            pstm.setInt(1, MaKH);
            ResultSet rs = pstm.executeQuery();
            if (rs.next()) {
                kh.setMaKH(rs.getInt("MaKH"));
                kh.setTenKH(rs.getString("TenKH"));
                kh.setSoDienThoai(rs.getString("SoDienThoai"));
                kh.setDiemTichLuy(rs.getInt("DiemTichLuy"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally{
            connectManager.closeConnection();
        }
        return kh;
    }
    public boolean updateKhachHangDTO(KhachHangDTO kh){
        boolean result = false;
        String sql = "Update KhachHang Set TenKH = ?,SoDienThoai = ?,DiemTichLuy = ? Where MaKH = ?";
        try {
            connectManager.openConnection();
            Connection connection = connectManager.getConnection();
            PreparedStatement pstm = connection.prepareStatement(sql);
            pstm.setString(1, kh.getTenKH());
            pstm.setString(2, kh.getSoDienThoai());
            pstm.setInt(3, kh.getDiemTichLuy());
            pstm.setInt(4, kh.getMaKH());
            int r = pstm.executeUpdate();
            if (r > 0) {
                result = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally{
            connectManager.closeConnection();
        }
        return result;
    }
    
    
    public boolean kiemTraSoDienThoai(String soDienThoai){
        boolean result = false;
        String sql = "Select COUNT(*) From KhachHang  Where SoDienThoai = ? and Is_Deleted = 0";
        try {
            connectManager.openConnection();
            Connection connection = connectManager.getConnection();
            PreparedStatement pstm = connection.prepareStatement(sql);
            pstm.setString(1, soDienThoai);
            
            ResultSet rs = pstm.executeQuery();
            if (rs.next()) {
                int count = rs.getInt(1);
                if(count > 0)
                    return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally{
            connectManager.closeConnection();
        }
        return result;
    }

    public int getDiemTichLuyBySoDienThoai(String phoneNumber) {
        int Points = -1;
        String query = "SELECT DiemTichLuy FROM KhachHang WHERE SoDienThoai = ? AND Is_Deleted = 0";
        try {
            connectManager.openConnection();
            Connection connection = connectManager.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, phoneNumber);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                Points = resultSet.getInt("DiemTichLuy");
            } 
        } catch (SQLException e) {
            e.printStackTrace();
        } finally{
            connectManager.closeConnection();
        }
        return Points;
    }

    public int getMaKHBySDT(String sdt) {
        int maKH = -1;  // Giá trị mặc định nếu không tìm thấy mã khách hàng
        String sql = "SELECT MaKH FROM KhachHang WHERE SoDienThoai = ? AND Is_Deleted = 0";  // Lọc theo số điện thoại và đảm bảo khách hàng chưa bị xóa
        
        try {
            connectManager.openConnection();  // Mở kết nối đến cơ sở dữ liệu
            Connection connection = connectManager.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, sdt);  // Thiết lập giá trị cho tham số ? trong câu lệnh SQL
            
            ResultSet rs = preparedStatement.executeQuery();  // Thực thi câu lệnh SQL và nhận kết quả
            
            // Kiểm tra nếu có kết quả trả về
            if (rs.next()) {
                maKH = rs.getInt("MaKH");  // Lấy mã khách hàng từ ResultSet
            }
        } catch (SQLException e) {
            e.printStackTrace();  // Xử lý ngoại lệ SQL
        } finally {
            connectManager.closeConnection();  // Đóng kết nối sau khi thực hiện xong
        }
        return maKH;  // Trả về mã khách hàng (nếu tìm thấy) hoặc -1 nếu không tìm thấy
    }

    public boolean updateDiemTichLuy(int maKH, int diemMoi) {
        String sql = "UPDATE KhachHang SET DiemTichLuy = ? WHERE MaKH = ?";
    
        try {
            connectManager.openConnection();
            Connection connection = connectManager.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, diemMoi);
            preparedStatement.setInt(2, maKH);
            int rowsUpdated = preparedStatement.executeUpdate();
    
            return rowsUpdated > 0;  // Trả về true nếu cập nhật thành công
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            connectManager.closeConnection();
        }
    }

    public boolean subtractPoints(int maKH, int pointsToSubtract) {
        String sql = "UPDATE KhachHang SET DiemTichLuy = DiemTichLuy - ? WHERE MaKH = ? AND DiemTichLuy >= ?";
        try {
            connectManager.openConnection();
            Connection connection = connectManager.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, pointsToSubtract);  // Số điểm cần trừ
            preparedStatement.setInt(2, maKH);  // Mã khách hàng
            preparedStatement.setInt(3, pointsToSubtract);  // Đảm bảo khách hàng có đủ điểm để trừ
    
            int rowsAffected = preparedStatement.executeUpdate();
    
            // Nếu có ít nhất một dòng bị ảnh hưởng, có nghĩa là điểm đã được trừ thành công
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;  // Trả về false nếu có lỗi xảy ra
        } finally {
            connectManager.closeConnection();
        }
    }
    

}
