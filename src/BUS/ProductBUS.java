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
}