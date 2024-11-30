package DAO;
import DTO.ProductDTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductDAO {

    private ConnectManager connectManager;

    public ProductDAO() {
        connectManager = new ConnectManager();
    }

    public boolean addProduct(ProductDTO product) {
        String query = "INSERT INTO Product (MaSP, TenSP, MaLoai, MoTa, Gia, SoLuong, HinhAnh, Is_Deleted) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection connection = connectManager.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, product.getMaSP());
            stmt.setString(2, product.getTenSP());
            stmt.setInt(3, product.getMaLoai());
            stmt.setString(4, product.getMoTa());
            stmt.setDouble(5, product.getGia());
            stmt.setInt(6, product.getSoLuong());
            stmt.setString(7, product.getHinhAnh());
            stmt.setInt(8, product.getIsDeleted());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateProduct(ProductDTO product) {
        String query = "UPDATE Product SET TenSP = ?, MaLoai = ?, MoTa = ?, Gia = ?, SoLuong = ?, HinhAnh = ?, Is_Deleted = ? WHERE MaSP = ?";
        try (Connection connection = connectManager.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, product.getTenSP());
            stmt.setInt(2, product.getMaLoai());
            stmt.setString(3, product.getMoTa());
            stmt.setDouble(4, product.getGia());
            stmt.setInt(5, product.getSoLuong());
            stmt.setString(6, product.getHinhAnh());
            stmt.setInt(7, product.getIsDeleted());
            stmt.setInt(8, product.getMaSP());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteProduct(int maSP) {
        String query = "UPDATE Product SET Is_Deleted = ? WHERE MaSP = ?";
        try (Connection connection = connectManager.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setBoolean(1, true);
            stmt.setInt(2, maSP);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public ProductDTO getProductbyId(int maSP) {
        String query = "SELECT * FROM Product WHERE MaSP = ? AND Is_Deleted = false";
        try (Connection connection = connectManager.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, maSP);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new ProductDTO(
                    rs.getInt("MaSP"),
                    rs.getString("TenSP"),
                    rs.getInt("MaLoai"),
                    rs.getString("MoTa"),
                    rs.getDouble("Gia"),
                    rs.getInt("SoLuong"),
                    rs.getString("HinhAnh"),
                    rs.getInt("Is_Deleted")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<ProductDTO> getAllProducts() {
        List<ProductDTO> products = new ArrayList<>();
        String query = "SELECT * FROM Product WHERE Is_Deleted = false";
        try (Connection connection = connectManager.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                products.add(new ProductDTO(
                    rs.getInt("MaSP"),
                    rs.getString("TenSP"),
                    rs.getInt("MaLoai"),
                    rs.getString("MoTa"),
                    rs.getDouble("Gia"),
                    rs.getInt("SoLuong"),
                    rs.getString("HinhAnh"),
                    rs.getInt("Is_Deleted") 
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return products;
    }
}
