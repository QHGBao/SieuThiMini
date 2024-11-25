package DAO;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import DTO.CancellationDTO;
import DTO.CancellationProductDTO;

public class CancellationDAO {
    private ConnectManager connectManager ;

    public CancellationDAO() {
       connectManager = new ConnectManager();
    }

    public List<CancellationDTO> getAllCancellation() {
        List<CancellationDTO> list = new ArrayList<>();
        try {
            String sql = "SELECT MaPH, NgayLap, MaNV FROM PhieuHuy WHERE Is_Deleted = 0";
            connectManager.openConnection();
            Connection connection = connectManager.getConnection();
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                Timestamp timestamp = rs.getTimestamp("NgayLap");
                LocalDateTime cancellationDay = timestamp.toLocalDateTime();

                list.add(new CancellationDTO(rs.getInt("MaPH"), cancellationDay, rs.getInt("MaNV")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<CancellationProductDTO> getCancellationDetails(int cancellationID) {
        List<CancellationProductDTO> details = new ArrayList<>();
        try {
            String query = "SELECT sp.MaSP, sp.TenSP, sp.MaLoai, ctp.SoLuong FROM CTPhieuHuy ctp JOIN SanPham sp ON ctp.MaSP = sp.MaSP WHERE ctp.MaPH = ?";
    
            connectManager.openConnection();
            Connection connection = connectManager.getConnection();
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setInt(1, cancellationID);
    
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                CancellationProductDTO product = new CancellationProductDTO(
                    rs.getInt("MaSP"),
                    rs.getString("TenSP"),
                    rs.getInt("MaLoai"),
                    rs.getInt("SoLuong")
                );
                details.add(product);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connectManager.closeConnection();
        }
        return details;
    }

    public boolean deleteCancellation (int cancellationID) throws SQLException {
        connectManager.openConnection();
        Connection connection = connectManager.getConnection();
        connection.setAutoCommit(false);
        try {
            // Cập nhật số lượng sản phẩm bị hủy
            restoreProductQuantities(cancellationID, connection);

            // Xóa chi tiết phiếu hủy
            deleteCancellationDetails(cancellationID, connection);

            // Xóa phiếu hủy
            deleteCancellation(cancellationID, connection);

            connection.commit();
            return true;
        } catch (SQLException e) {
            connection.rollback();
            throw e;
        } finally {
            connection.setAutoCommit(true);
            connectManager.closeConnection();
        }
    }
    
    public void createCancellation(CancellationDTO cancellationDTO, List<CancellationProductDTO> products) {
        String insertQuery = "INSERT INTO PhieuHuy (NgayLap, MaNV, Is_Deleted) VALUES (?, ?, 0)";
        try {
            connectManager.openConnection();
            Connection connection = connectManager.getConnection();
            PreparedStatement stmt = connection.prepareStatement(insertQuery, Statement.RETURN_GENERATED_KEYS);
            stmt.setTimestamp(1, Timestamp.valueOf(cancellationDTO.getCancellationDay()));
            stmt.setInt(2,1);
            //stmt.setInt(2, cancellationDTO.getEmployeeID());
            stmt.executeUpdate();

            ResultSet generatedKeys = stmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                int cancellationID = generatedKeys.getInt(1);
                cancellationDTO.setCancellationID(cancellationID);
            }
            else {
                System.out.println("cant get key");
            }

            for (CancellationProductDTO product : products) {
                createCancellationDetails(cancellationDTO.getCancellationID(), product, connection);
                updateProductQuantities(product.getProductId(), product.getQuantity(), connection);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connectManager.closeConnection();
        }
    }

    public void createCancellationDetails(int cancellationID, CancellationProductDTO productDTO, Connection connection) throws SQLException {
        String insertQuery = "INSERT INTO CTPhieuHuy (MaPH, MaSP, SoLuong) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(insertQuery)) {
            stmt.setInt(1, cancellationID);
            stmt.setInt(2, productDTO.getProductId());
            stmt.setInt(3, productDTO.getQuantity());
            stmt.executeUpdate();
        }
    }
  
    public boolean updateCancellation(int cancellationID, List<CancellationProductDTO> updatedProducts) throws SQLException {
        connectManager.openConnection();
        Connection connection = connectManager.getConnection();
        connection.setAutoCommit(false);

    try {
        // 1. Khôi phục số lượng sản phẩm từ chi tiết phiếu hủy cũ
        restoreProductQuantities(cancellationID, connection);

        // 2. Xóa các chi tiết phiếu hủy cũ
        deleteCancellationDetails(cancellationID, connection);

        // 3. Cập nhật lại các chi tiết phiếu hủy mới
        for (CancellationProductDTO product : updatedProducts) {
            createCancellationDetails(cancellationID, product, connection);
            updateProductQuantities(product.getProductId(), product.getQuantity(), connection);
        }

        // Commit transaction
        connection.commit();
        return true;

        } catch (SQLException e) {
        connection.rollback();
        throw e;
        } finally {
        connection.setAutoCommit(true);
        connectManager.closeConnection();
        }
    }

     public List<CancellationDTO> searchByDate(LocalDate date) {
        List<CancellationDTO> cancellations = new ArrayList<>();
        String query = "SELECT * FROM PhieuHuy WHERE NgayLap >= ? AND NgayLap < ?";
        try  {
            connectManager.openConnection();
            Connection connection = connectManager.getConnection();
            PreparedStatement stmt = connection.prepareStatement(query);
            // Từ đầu ngày
            LocalDateTime startOfDay = date.atStartOfDay();
            stmt.setTimestamp(1, Timestamp.valueOf(startOfDay));

            // Đến cuối ngày
            LocalDateTime endOfDay = date.atTime(LocalTime.MAX);
            stmt.setTimestamp(2, Timestamp.valueOf(endOfDay));

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("MaPH");
                LocalDateTime cancellationDate = rs.getTimestamp("NgayLap").toLocalDateTime();
                int employeeID = rs.getInt("MaNV");
                cancellations.add(new CancellationDTO(id, cancellationDate, employeeID));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cancellations;
    }

    public void restoreProductQuantities ( int cancellationID, Connection connection) throws SQLException{
        String updateProductSQL = "UPDATE SanPham SET SoLuong = SoLuong + (SELECT SoLuong FROM CTPhieuHuy WHERE MaSP = SanPham.MaSP AND MaPH = ?) WHERE MaSP IN (SELECT MaSP FROM CTPhieuHuy WHERE MaPH = ?)";
        try(PreparedStatement updateStmt = connection.prepareStatement(updateProductSQL)){
            updateStmt.setInt(1, cancellationID);
            updateStmt.setInt(2, cancellationID);
            updateStmt.executeUpdate();
        }
    }

    public void updateProductQuantities(int productID, int quantity, Connection connection) throws SQLException{
        String updateQuery = "UPDATE SanPham SET SoLuong = SoLuong - ? WHERE MaSP = ?";
        try (PreparedStatement stmt = connection.prepareStatement(updateQuery)){
            stmt.setInt(1, quantity);
            stmt.setInt(2, productID);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    } 

    public void deleteCancellationDetails (int cancellationID, Connection connection ) throws SQLException {
        String deleteDetailSQL = "DELETE FROM CTPhieuHuy WHERE MaPH = ?";
        try (PreparedStatement deleteDetailStmt = connection.prepareStatement(deleteDetailSQL)){
            deleteDetailStmt.setInt(1, cancellationID);
            deleteDetailStmt.executeUpdate();
        }
    }

    public void deleteCancellation (int cancellationID, Connection connection ) throws SQLException {
        String deleteSQL = "UPDATE PhieuHuy SET Is_Deleted = 1 WHERE MaPH = ?";
        try (PreparedStatement deleteStmt = connection.prepareStatement(deleteSQL)){
            deleteStmt.setInt(1, cancellationID);
            deleteStmt.executeUpdate();
        }
    }
}
