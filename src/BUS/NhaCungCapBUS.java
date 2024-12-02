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
        if (nccDAO.findNCC(ncc.getSdt(),ncc.getMaNCC()))
            return "Có nhà cung cấp đã sài sđt này rồi !!";
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

    public int findMaNCC(String sdt){
        return nccDAO.findMaNCC(sdt);
    }

    public boolean ktSdt(String sdt, int MaNCC){
        return nccDAO.findNCC(sdt, MaNCC);
    }

    public ArrayList<NhaCungCapDTO> searchArrayNCC(String keyword){
        return nccDAO.searchArrayNCC(keyword);
    }
}
