package DAO;

import java.sql.*;
import java.time.LocalDateTime;
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

    public boolean deleteCancellation (int cancellationID) throws SQLException {
        connectManager.openConnection();
        Connection connection = connectManager.getConnection();
        connection.setAutoCommit(false);
        try {
            // Cập nhật số lượng sản phẩm bị hủy
            String updateProductSQL = "UPDATE SanPham SET SoLuong = SoLuong + (SELECT SoLuong FROM CTPhieuHuy WHERE MaSP = SanPham.MaSP AND MaPH = ?) WHERE MaSP IN (SELECT MaSP FROM CTPhieuHuy WHERE MaPH = ?)";
            PreparedStatement updateStmt = connection.prepareStatement(updateProductSQL);
            updateStmt.setInt(1, cancellationID);
            updateStmt.setInt(2, cancellationID);
            updateStmt.executeUpdate();

            // Xóa chi tiết phiếu hủy
            String deleteDetailSQL = "DELETE FROM CTPhieuHuy WHERE MaPH = ?";
            PreparedStatement deleteDetailStmt = connection.prepareStatement(deleteDetailSQL);
            deleteDetailStmt.setInt(1, cancellationID);
            deleteDetailStmt.executeUpdate();

            // Xóa phiếu hủy
            String deleteSQL = "UPDATE PhieuHuy SET Is_Deleted = 1 WHERE MaPH = ?";
            PreparedStatement deleteStmt = connection.prepareStatement(deleteSQL);
            deleteStmt.setInt(1, cancellationID);
            deleteStmt.executeUpdate();

            /*  Xóa phiếu hủy
            String deleteCSQL = "DELETE FROM PhieuHuy WHERE Is_Deleted = 1";
            PreparedStatement deleteCStmt = connection.prepareStatement(deleteCSQL);
            deleteCStmt.setInt(1, cancellationID);
            deleteCStmt.executeUpdate();*/

            connection.commit();
            return true;
        } catch (SQLException e) {
            connection.rollback();
            throw e;
        } finally {
            connection.setAutoCommit(true);
        }
    }

    private int getCancellationQuantity(int cancellationID) throws SQLException {
        String sql = "SELECT SoLuong FROM CTPhieuHuy WHERE MaPH = ? GROUP BY MaSP";
        connectManager.openConnection();
        Connection connection = connectManager.getConnection();
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, cancellationID);
        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) {
            return resultSet.getInt(1);
        }
        return 0;
    }

    public void createCancellation(CancellationDTO cancellationDTO) {
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
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connectManager.closeConnection();
        }
    }

    public void createCancellationDetails(int cancellationID, CancellationProductDTO productDTO) {
        String insertQuery = "INSERT INTO CTPhieuHuy (MaPH, MaSP, SoLuong) VALUES (?, ?, ?)";
        try {
            connectManager.openConnection();
            Connection connection = connectManager.getConnection();
            PreparedStatement stmt = connection.prepareStatement(insertQuery);
            stmt.setInt(1, cancellationID);
            stmt.setInt(2, productDTO.getProductId());
            stmt.setInt(3, productDTO.getQuantity());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public void updateProductQuantity(int productID, int quantity) {
        String updateQuery = "UPDATE SanPham SET SoLuong = SoLuong - ? WHERE MaSP = ?";
        try {
            connectManager.openConnection();
            Connection connection = connectManager.getConnection(); 
            PreparedStatement stmt = connection.prepareStatement(updateQuery);
            stmt.setInt(1, quantity);
            stmt.setInt(2, productID);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
