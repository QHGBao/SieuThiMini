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
            return "Tạo nhà cung cấp thành công !!";
        return "Tạo nhà cung cấp thất bại !!";
    }

    public String capNhatNCC(NhaCungCapDTO ncc){
        if (nccDAO.updateNCC(ncc))
            return "Cập nhật nhà cung cấp thành công !!";
        return "Cập nhật nhà cung cấp thất bại !!";
    }

    public String xoaNCC(NhaCungCapDTO ncc){
        if (nccDAO.deleteNCC(ncc))
            return "Xóa nhà cung cấp thành công !!";
        return "Xóa nhà cung cấp thất bại !!";
    }

    public int taoMaNCC(){
        return nccDAO.createCodeNCC();
    }
}
