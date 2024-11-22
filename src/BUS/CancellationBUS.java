package BUS;
import java.sql.SQLException;
import java.util.List;

import DAO.CancellationDAO;
import DTO.CancellationDTO;
import DTO.CancellationProductDTO;

public class CancellationBUS {
    private CancellationDAO dao = new CancellationDAO();

    public List<CancellationDTO> getAllCancellation() {
        return dao.getAllCancellation();
    }

    public boolean deleteCancellation(int cancellationID) throws SQLException {
        return dao.deleteCancellation(cancellationID);
    }

    public void createCancellation(CancellationDTO cancellationDTO, List<CancellationProductDTO> products) {
        try{
            dao.createCancellation(cancellationDTO);} catch (Exception e){System.out.println("error" + e);}

        for (CancellationProductDTO product : products) {
            dao.createCancellationDetails(cancellationDTO.getCancellationID(), product);
            dao.updateProductQuantity(product.getProductId(), product.getQuantity());
        }
    }
}
