package DAO;

import DTO.QuanLyGiamGiaSpDTO;
import DTO.SanPhamKmDTO;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class QuanLyGiamGiaSpDAO {
    private ConnectManager connectManager;

    public QuanLyGiamGiaSpDAO() {
        connectManager = new ConnectManager();
    }

    public List<String> getAllDiscountNames() {
        List<String> discountNames = new ArrayList<>();
        String query = "SELECT TenKM FROM GiamGiaSP WHERE Is_Deleted = 0"; // Chỉ lấy các chương trình khuyến mãi còn
                                                                           // hiệu lực

        try {
            connectManager.openConnection();
            Connection connection = connectManager.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                discountNames.add(resultSet.getString("TenKM"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return discountNames;
    }

    public ArrayList<QuanLyGiamGiaSpDTO> getAllGiamGiaSP() {
        ArrayList<QuanLyGiamGiaSpDTO> list = new ArrayList<>();
        String sql = "SELECT MaKM, TenKM, NgayBD, NgayKT, PtGiam " +
                "FROM GiamGiaSP " +
                "WHERE Is_Deleted = 0";

        try {
            connectManager.openConnection();
            Connection connection = connectManager.getConnection();
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                int maKM = rs.getInt("MaKM");
                String tenKM = rs.getString("TenKM");
                Date ngayBD = rs.getDate("NgayBD");
                Date ngayKT = rs.getDate("NgayKT");
                int ptGiam = rs.getInt("PtGiam");

                QuanLyGiamGiaSpDTO dto = new QuanLyGiamGiaSpDTO(maKM, tenKM, ngayBD, ngayKT, ptGiam);
                list.add(dto);
            }
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public boolean addGiamGiaSP(QuanLyGiamGiaSpDTO khuyenMai) {
        String query = "SET IDENTITY_INSERT GiamGiaSP ON INSERT INTO GiamGiaSP (MaKM, TenKM, NgayBD, NgayKT, PtGiam, Is_Deleted ) VALUES (?, ?, ?, ?, ?, 0)";
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            // Mở kết nối
            connectManager.openConnection();
            connection = connectManager.getConnection();

            // Sử dụng PreparedStatement
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, khuyenMai.getMaKM());
            preparedStatement.setString(2, khuyenMai.getTenKM());
            preparedStatement.setDate(3, khuyenMai.getNgayBD());
            preparedStatement.setDate(4, khuyenMai.getNgayKT());
            preparedStatement.setInt(5, khuyenMai.getPtGiam());

            // Thực thi câu lệnh
            int rowsInserted = preparedStatement.executeUpdate();

            // Kiểm tra kết quả
            return rowsInserted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                // Đóng tài nguyên
                if (preparedStatement != null)
                    preparedStatement.close();
                if (connection != null)
                    connectManager.closeConnection();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public boolean addGiamGiaSPAndKm(SanPhamKmDTO dto) {
        String query = "INSERT INTO CTGiamGiaSP (MaSP, MaKM) VALUES (?, ?)";
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            // Mở kết nối
            connectManager.openConnection();
            connection = connectManager.getConnection();

            if (connection == null || connection.isClosed()) {
                System.out.println("Kết nối thất bại!");
                return false;
            }

            System.out.println("Kết nối thành công!");
            System.out.println("Thêm CTGiamGiaSP với MaSP: " + dto.getMaSP() + ", MaKM: " + dto.getMaKM());

            // Sử dụng PreparedStatement
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, dto.getMaSP());
            preparedStatement.setInt(2, dto.getMaKM());

            // Thực thi câu lệnh
            int rowsInserted = preparedStatement.executeUpdate();

            // Kiểm tra kết quả
            System.out.println("Số dòng chèn vào: " + rowsInserted);
            return rowsInserted > 0;
        } catch (SQLException e) {
            System.out.println("Lỗi SQL khi thêm CTGiamGiaSP: " + e.getMessage());
            e.printStackTrace();
            return false;
        } finally {
            try {
                // Đóng tài nguyên
                if (preparedStatement != null)
                    preparedStatement.close();
                if (connection != null)
                    connectManager.closeConnection();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void XoaCT(int maKM) throws SQLException {
        String deleteSPQuery = "DELETE FROM CTGiamGiaSP WHERE MaKM = ?";
        String deleteKMQuery = "DELETE FROM GiamGiaSP WHERE MaKM = ?";

        try {
            connectManager.openConnection();
            Connection connection = connectManager.getConnection();
            connection.setAutoCommit(false);

            PreparedStatement psSP = connection.prepareStatement(deleteSPQuery);
            psSP.setInt(1, maKM);
            psSP.executeUpdate();

            PreparedStatement psKM = connection.prepareStatement(deleteKMQuery);
            psKM.setInt(1, maKM);
            psKM.executeUpdate();

            connection.commit(); // Commit transaction
        } catch (SQLException e) {
            connectManager.getConnection().rollback(); // Rollback nếu có lỗi
            throw e; // Ném lại lỗi để xử lý ở nơi khác
        } finally {
            connectManager.closeConnection();
        }
    }

    public ArrayList<SanPhamKmDTO> getSanPhamKmByMaKM(int maKM) {
        ArrayList<SanPhamKmDTO> danhSach = new ArrayList<>();
        String query = "SELECT * FROM CTGiamGiaSP WHERE maKM = ?";
        try {
            connectManager.openConnection();
            Connection connection = connectManager.getConnection();
            Statement stmt = connection.createStatement();
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setInt(1, maKM);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                danhSach.add(new SanPhamKmDTO(rs.getInt("maSP"), rs.getInt("maKM")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return danhSach;

    }

    public int getLastMaKM() throws SQLException {
        String sql = "SELECT MAX(MaKM) AS LastMaKM FROM GiamGiaSP";
        try {
            connectManager.openConnection();
            Connection connection = connectManager.getConnection();
            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("LastMaKM"); // Lấy giá trị lớn nhất
            }
            return 0; // Nếu không có bản ghi nào, trả về 0
        } catch (SQLException e) {
            e.printStackTrace();
            return -1; // Trả về -1 nếu có lỗi
        } finally {
            connectManager.closeConnection();
        }
    }

    public int getDiscountedPrice(int promotionId, int productId) {
        int discountedPrice = 0;
        String sql = "SELECT sp.GiaBan - (sp.GiaBan * gg.PtGiam / 100) AS GiaSauGiam " +
                "FROM SanPham sp " +
                "JOIN CTGiamGiaSP ctgg ON sp.MaSP = ctgg.MaSP " +
                "JOIN GiamGiaSP gg ON ctgg.MaKM = gg.MaKM " +
                "WHERE sp.MaSP = ? AND gg.MaKM = ? " +
                "AND gg.NgayBD <= GETDATE() AND gg.NgayKT >= GETDATE() " +
                "AND sp.Is_Deleted = 0 AND gg.Is_Deleted = 0";
        try {
            connectManager.openConnection();
            Connection connection = connectManager.getConnection();
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, productId);
            ps.setInt(2, promotionId); // Set product ID
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    discountedPrice = rs.getInt("GiaSauGiam");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connectManager.closeConnection();
        }

        return discountedPrice;
    }

    public int getPromotionIdByName(String promotionName) {
        int promotionId = -1; // Giá trị mặc định nếu không tìm thấy
        String sql = "SELECT MaKM FROM GiamGiaSP WHERE TenKM = ? AND NgayBD <= GETDATE() AND NgayKT >= GETDATE() AND Is_Deleted = 0";

        try {
            connectManager.openConnection();
            Connection connection = connectManager.getConnection();
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, promotionName); // Set tên chương trình khuyến mãi

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    promotionId = rs.getInt("MaKM");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return promotionId;
    }

}
