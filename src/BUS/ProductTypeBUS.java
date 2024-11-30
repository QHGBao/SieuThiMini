package BUS;


import DAO.ProductTypeDAO;
import java.util.List;

public class ProductTypeBUS {

    private ProductTypeDAO productTypeDAO;

    public ProductTypeBUS() {
        productTypeDAO = new ProductTypeDAO();
    }
    
    public List<String> getAllProductTypesName() {
        return productTypeDAO.getAllProductTypesName();
    }
}

