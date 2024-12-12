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
        String query = "INSERT INTO SanPham (TenSP, MaLoai, MoTa, GiaBan, SoLuong, HinhAnh, Is_Deleted) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try {
            connectManager.openConnection();
            Connection connection = connectManager.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, product.getTenSP());
            preparedStatement.setInt(2, product.getMaLoai());
            preparedStatement.setString(3, product.getMoTa());
            preparedStatement.setDouble(4, product.getGia());
            preparedStatement.setInt(5, product.getSoLuong());
            preparedStatement.setString(6, product.getHinhAnh());
            preparedStatement.setInt(7, product.getIsDeleted());
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            connectManager.closeConnection();
        }
    }

    public boolean updateProduct(ProductDTO product) {
        String query = "UPDATE SanPham SET TenSP = ?, MaLoai = ?, MoTa = ?, GiaBan = ?, SoLuong = ?, HinhAnh = ?, Is_Deleted = ? WHERE MaSP = ?";
        try {
            connectManager.openConnection();
            Connection connection = connectManager.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, product.getTenSP());
            preparedStatement.setInt(2, product.getMaLoai());
            preparedStatement.setString(3, product.getMoTa());
            preparedStatement.setDouble(4, product.getGia());
            preparedStatement.setInt(5, product.getSoLuong());
            preparedStatement.setString(6, product.getHinhAnh());
            preparedStatement.setInt(7, product.getIsDeleted());
            preparedStatement.setInt(8, product.getMaSP());
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            connectManager.closeConnection();
        }
    }

    public boolean deleteProduct(int maSP) {
        String query = "UPDATE SanPham SET Is_Deleted = ? WHERE MaSP = ?";
        try {
            connectManager.openConnection();
            Connection connection = connectManager.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setBoolean(1, true);
            preparedStatement.setInt(2, maSP);
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            connectManager.closeConnection();
        }
    }

    public ProductDTO getProductbyId(int maSP) {
        String query = "SELECT * FROM SanPham WHERE MaSP = ? AND Is_Deleted = false";
        try {
            connectManager.openConnection();
            Connection connection = connectManager.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, maSP);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                return new ProductDTO(
                        rs.getInt("MaSP"),
                        rs.getString("TenSP"),
                        rs.getInt("MaLoai"),
                        rs.getString("MoTa"),
                        rs.getInt("Gia"),
                        rs.getInt("SoLuong"),
                        rs.getString("HinhAnh"),
                        rs.getInt("Is_Deleted"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connectManager.closeConnection();
        }
        return null;
    }

    public ArrayList<ProductDTO> searchProductsByNameAndType(String productName, String productType) {
        ArrayList<ProductDTO> products = new ArrayList<>();
        String sql = "SELECT * FROM SanPham sp " +
                     "JOIN LoaiSanPham lsp ON sp.MaLoai = lsp.MaLoai " +
                     "WHERE (sp.TenSP LIKE ? OR ? = '') " +
                     (productType.equals("Tất cả") ? "" : "AND lsp.TenLoai = ? ") +  // Bỏ điều kiện nếu là "Tất cả"
                     "AND sp.Is_Deleted = 0";
    
        try {
            connectManager.openConnection();
            Connection connection = connectManager.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, "%" + productName + "%");  // Điều kiện tìm theo tên
            preparedStatement.setString(2, productName);              // Điều kiện nếu tên trống
    
            if (!productType.equals("Tất cả")) {
                preparedStatement.setString(3, productType);  // Điều kiện tìm theo loại khi không phải "Tất cả"
            }
    
            ResultSet rs = preparedStatement.executeQuery();
    
            while (rs.next()) {
                products.add(new ProductDTO(
                    rs.getInt("MaSP"),
                    rs.getString("TenSP"),
                    rs.getInt("MaLoai"),
                    rs.getString("MoTa"),
                    rs.getInt("GiaBan"),
                    rs.getInt("SoLuong"),
                    rs.getString("HinhAnh"),
                    rs.getInt("Is_Deleted")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connectManager.closeConnection();
        }
    
        return products;
    }
    
    
    public List<ProductDTO> searchProductsByName(String keyword) {
        List<ProductDTO> products = new ArrayList<>();
        String query = "SELECT * FROM SanPham WHERE TenSP LIKE ? AND Is_Deleted = 0";
        try {
            connectManager.openConnection();
            Connection connection = connectManager.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, "%" + keyword + "%");
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                products.add(new ProductDTO(
                        rs.getInt("MaSP"),
                        rs.getString("TenSP"),
                        rs.getInt("MaLoai"),
                        rs.getString("MoTa"),
                        rs.getInt("GiaBan"),
                        rs.getInt("SoLuong"),
                        rs.getString("HinhAnh"),
                        rs.getInt("Is_Deleted")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connectManager.closeConnection();
        }
        return products;
    }

    public List<ProductDTO> searchProductsByType(String keyword) {
        List<ProductDTO> products = new ArrayList<>();
        String query = "SELECT sp.* FROM SanPham sp, LoaiSanPham lsp WHERE TenLoai LIKE ? AND sp.Is_Deleted = 0 AND sp.MaLoai = lsp.MaLoai";
        try {
            connectManager.openConnection();
            Connection connection = connectManager.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, "%" + keyword + "%");
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                products.add(new ProductDTO(
                        rs.getInt("MaSP"),
                        rs.getString("TenSP"),
                        rs.getInt("MaLoai"),
                        rs.getString("MoTa"),
                        rs.getInt("GiaBan"),
                        rs.getInt("SoLuong"),
                        rs.getString("HinhAnh"),
                        rs.getInt("Is_Deleted")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connectManager.closeConnection();
        }
        return products;
    }

    public List<ProductDTO> getAllProducts() {
        List<ProductDTO> products = new ArrayList<>();
        String query = "SELECT * FROM SanPham WHERE Is_Deleted = 0";
        try {
            connectManager.openConnection();
            Connection connection = connectManager.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                products.add(new ProductDTO(
                        rs.getInt("MaSP"),
                        rs.getString("TenSP"),
                        rs.getInt("MaLoai"),
                        rs.getString("MoTa"),
                        rs.getInt("GiaBan"),
                        rs.getInt("SoLuong"),
                        rs.getString("HinhAnh"),
                        rs.getInt("Is_Deleted")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connectManager.closeConnection();
        }
        return products;
    }

    public int getProductQuantityById(int maSP) {
        int soLuong = -1; // Trả về -1 nếu không tìm thấy sản phẩm
        String query = "SELECT SoLuong FROM SanPham WHERE MaSP = ? AND Is_Deleted = 0";
        try {
            connectManager.openConnection();
            Connection connection = connectManager.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, maSP); // Gán giá trị MaSP vào câu lệnh SQL
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                soLuong = rs.getInt("SoLuong"); // Lấy số lượng sản phẩm
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connectManager.closeConnection();
        }
        return soLuong;
    }    

    public String getTenSanPhamByMaSP(int maSP) {
        String tenSP = "";
        String sql = "SELECT TenSP FROM SanPham WHERE MaSP = ?";
    
        try {
            connectManager.openConnection();
            Connection connection = connectManager.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, maSP); // Gán maSP vào câu lệnh SQL
            ResultSet rs = preparedStatement.executeQuery();
    
            if (rs.next()) {
                tenSP = rs.getString("TenSP");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connectManager.closeConnection();
        }
        return tenSP;
    }

    public boolean updateProductQuantity(int maSP, int soLuongBan) {
        // Cập nhật số lượng sản phẩm trong cơ sở dữ liệu
        String query = "UPDATE SanPham SET SoLuong = SoLuong - ? WHERE MaSP = ?";
        try {
            connectManager.openConnection();
            Connection connection = connectManager.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, soLuongBan);
            preparedStatement.setInt(2, maSP);
            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0;  // Trả về true nếu số lượng sản phẩm được cập nhật thành công
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            connectManager.closeConnection();
        }
    }

    public int getMaLoaiByTenLoai(String tenLoai) {
        String query = "SELECT MaLoai FROM LoaiSanPham WHERE TenLoai = ?";
        int maLoai = -1; // Mặc định trả về -1 nếu không tìm thấy
    
        try {
            connectManager.openConnection();
            Connection connection = connectManager.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, tenLoai);
            ResultSet rs = preparedStatement.executeQuery();
    
            if (rs.next()) {
                maLoai = rs.getInt("MaLoai");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connectManager.closeConnection();
        }
    
        return maLoai;
    }
    
    public String getTenLoaiByMaLoai(int maLoai) {
        String query = "SELECT TenLoai FROM LoaiSanPham WHERE MaLoai = ?";
        String tenLoai = null; // Mặc định trả về null nếu không tìm thấy
    
        try {
            connectManager.openConnection();
            Connection connection = connectManager.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            
            preparedStatement.setInt(1, maLoai); // Đặt mã loại vào câu lệnh SQL
            try (ResultSet rs = preparedStatement.executeQuery()) {
                if (rs.next()) {
                    tenLoai = rs.getString("TenLoai");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connectManager.closeConnection();
        }
    
        return tenLoai;
    }
    
}
