package BUS;

import DTO.NhaCungCapDTO;
import DAO.NhaCungCapDao;
import java.util.ArrayList;

public class NhaCungCapBUS {
    NhaCungCapDao nccDAO = new NhaCungCapDao();


    public ArrayList<NhaCungCapDTO> getAllNCC(){
        return nccDAO.getAllNCC();
    }

    public String themNCC(NhaCungCapDTO ncc){
        if (nccDAO.findNCC(ncc.getSdt()))
            return "Nhà cung cấp đã sài sđt này rồi !!";
        if (nccDAO.themNCC(ncc))
            return "Thêm nhà cung cấp thành công !!";
        return "Thêm nhà cung cấp thất bại !!";
    }

}
