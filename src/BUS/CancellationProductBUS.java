package BUS;

import DAO.CancellationProductDAO;
import DAO.CancellationProductTypeDAO;
import DTO.CancellationProductDTO;

import java.util.List;

public class CancellationProductBUS {
    private CancellationProductDAO cancellationProductDAO;
    private CancellationProductTypeDAO cancellationProductTypeDAO;

    public CancellationProductBUS() {
        this.cancellationProductDAO = new CancellationProductDAO();
        this.cancellationProductTypeDAO = new CancellationProductTypeDAO();
    }

    public List<CancellationProductDTO> getAllProducts() {
        return cancellationProductDAO.getAllProducts();
    }

    public CancellationProductDTO getProductByName(String productName) {
        return cancellationProductDAO.getProductByName(productName);
    }

    public String getProductType(int productTypeID) {
        return cancellationProductTypeDAO.getProductTypeById(productTypeID);
    }
}