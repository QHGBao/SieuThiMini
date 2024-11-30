package BUS;
import java.util.List;

import DAO.CancellationDetailsDAO;
import DTO.CancellationDetailsDTO;

public class CancellationDetailsBUS {
    private CancellationDetailsDAO dao = new CancellationDetailsDAO();

    public List<CancellationDetailsDTO> getCancellationDetailsByCancellationID(int cancellationID) {
        return dao.getCancellationDetailsByMCancellationID(cancellationID);
    }
}
