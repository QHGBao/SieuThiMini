package BUS;

import DAO.ProductDAO;
import DTO.ProductDTO;
import java.util.ArrayList;

public class ProductBUS {

    ProductDAO prDAO = new ProductDAO();

    public ArrayList<ProductDTO> getAllProducts() {
        return new ArrayList<>(prDAO.getAllProducts()); 
    }

    public ArrayList<ProductDTO> searchProductsByName(String keyword) {
        return new ArrayList<>(prDAO.searchProductsByName(keyword));
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

    public boolean updateProductStatus(int maSP, int status) {
        try {
            // Assuming you have a ProductDAO with this method
            return productDAO.updateProductStatus(maSP, status);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
