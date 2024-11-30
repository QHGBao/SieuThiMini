package BUS;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import DAO.CancellationDAO;
import DTO.CancellationDTO;
import DTO.CancellationProductDTO;

public class CancellationBUS {
    private CancellationDAO dao = new CancellationDAO();

    public List<CancellationDTO> getAllCancellation() {
        return dao.getAllCancellation();
    }

    public List<CancellationProductDTO> getCancellationDetails (int cancellationID){
        return dao.getCancellationDetails(cancellationID);
    }
    
    public boolean deleteCancellation(int cancellationID) throws SQLException {
        return dao.deleteCancellation(cancellationID);
    }

    public void createCancellation(CancellationDTO cancellationDTO, List<CancellationProductDTO> products) throws SQLException {
        try{
            createCancellation(cancellationDTO, products);} catch (Exception e){System.out.println("error" + e);}
    }

    public boolean updateCancellation(int cancellationID, List<CancellationProductDTO> updatedProducts) {
        try {
            return dao.updateCancellation(cancellationID, updatedProducts);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<CancellationDTO> getCancellationsByDate(LocalDate date) {
        return getAllCancellation().stream()
                .filter(cancellation -> cancellation.getCancellationDay().toLocalDate().equals(date))
                .collect(Collectors.toList());
    }
}
