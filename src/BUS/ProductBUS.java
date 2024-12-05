package BUS;

import DAO.ProductDAO;
import DTO.ProductDTO;
import java.util.List;

public class ProductBUS {

    ProductDAO prDAO = new ProductDAO();

    public List<ProductDTO> getAllProducts() {
        return prDAO.getAllProducts();
    }

    public List<ProductDTO> searchProductsByName(String keyword) {
        return prDAO.searchProductsByName(keyword);
    }

    public ProductDTO getProductById(int id) {
        return prDAO.getProductbyId(id);
    }

    public boolean addProduct(ProductDTO product) {
        return prDAO.addProduct(product);
    }

    public boolean updateProduct(ProductDTO product) {
        return prDAO.updateProduct(product);
    }

    public boolean deleteProduct(int id) {
        return prDAO.deleteProduct(id);
    }

    public List<ProductDTO> searchProducts(String productName, String productType) {
        return prDAO.searchProductsByNameAndType(productName, productType);
    }

    public String getTenSanPhamByMaSP(int maSP) {
        return prDAO.getTenSanPhamByMaSP(maSP);
    }

    public boolean updateProductQuantity(int maSP, int soLuongBan) {
        return prDAO.updateProductQuantity(maSP, soLuongBan);
    }

}