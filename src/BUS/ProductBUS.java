package BUS;

import DAO.ProductDAO;
import DTO.ProductDTO;
import java.util.ArrayList;

public class ProductBUS {

    ProductDAO prDAO = new ProductDAO();

    public ArrayList<ProductDTO> getAllProducts() {
        return new ArrayList<>(prDAO.getAllProducts()); 
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
