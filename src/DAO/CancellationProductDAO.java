package DAO;

import DTO.CancellationProductDTO;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CancellationProductDAO {
    private ConnectManager connectManager;

    public CancellationProductDAO() {
        connectManager = new ConnectManager();
    }

    public List<CancellationProductDTO> getAllProducts() {
        List<CancellationProductDTO> products = new ArrayList<>();
        String query = "SELECT * FROM SanPham";
        try {
            connectManager.openConnection();
            Connection connection = connectManager.getConnection();
            PreparedStatement ps = connection.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                products.add(new CancellationProductDTO(
                    rs.getInt("MaSP"),
                    rs.getString("TenSP"),
                    rs.getInt("MaLoai"),
                    rs.getInt("SoLuong")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return products;
    }

    public CancellationProductDTO getProductByName(String productName) {
        String query = "SELECT * FROM SanPham WHERE TenSP = ?";
        try {
            connectManager.openConnection();
            Connection connection = connectManager.getConnection();
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, productName);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new CancellationProductDTO(
                    rs.getInt("MaSP"),
                    rs.getString("TenSP"),
                    rs.getInt("MaLoai"),
                    rs.getInt("SoLuong")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}