package BUS;

import DTO.CTPhieuNhapDTO;
import DTO.PhieuNhapDTO;
import DTO.ProductDTO;

import java.sql.Timestamp;
import java.util.ArrayList;

import DAO.PhieuNhapDao;

public class PhieuNhapBUS {
    PhieuNhapDao pnDAO = new PhieuNhapDao();

    public ArrayList<PhieuNhapDTO.tablePNDTO> getAllRow(){
        return pnDAO.getAllRow();
    }

    public ArrayList<PhieuNhapDTO.tableCtPNDTO> getAllRowCT(int maPN){
        return pnDAO.getAllRowCT(maPN);
    }

    public ArrayList<PhieuNhapDTO.tableSPchon> getAllRowSPChon(int maSP){
        return pnDAO.getAllRowSPChon(maSP);
    }

    public ArrayList<PhieuNhapDTO> getAllPN(){
        return pnDAO.getAllPN();
    }

    public ArrayList<CTPhieuNhapDTO> getAllCTPN(int maPn){
        return pnDAO.getAllCTPN(maPn);
    }

    public ArrayList<ProductDTO> getAllSP(){
        return pnDAO.getAllSP();
    }

    public ArrayList<PhieuNhapDTO.tablePNDTO> searchPnArray(String keyword){
        return pnDAO.searchPnArray(keyword);
    }

    public ArrayList<PhieuNhapDTO.tablePNDTO> searchPnArray(String keyword, Timestamp ngayBD, Timestamp ngayKT){
        return pnDAO.searchPnArray(keyword, ngayBD, ngayKT);
    }

    public ArrayList<ProductDTO> searchSpArray(String keyword){
        return pnDAO.searchSpArray(keyword);
    }

    public PhieuNhapDTO findPn(int maPN){
        return pnDAO.findPn(maPN);
    }

    public String xoaPN(int maPN){
        if(pnDAO.xoaPN(maPN))
            return "Xóa phiếu nhập thành công !!";
        return "Xóa phiếu nhập thất bại !!";
    }

    public String duyetPN(int maPN){
        if(pnDAO.capNhatSL(maPN) && pnDAO.duyetPN(maPN))
            return "Duyệt phiếu nhập thành công !!";
        return "Duyệt phiếu nhập thất bại !!";
    }

    public String themPN(PhieuNhapDTO pn){
        if(pnDAO.themPN(pn))
            return "Thêm phiếu nhập thành công !!";
        return "Thêm phiếu nhập thất bại !!";
    }

    public void themCTPN(CTPhieuNhapDTO ctpn){
        pnDAO.themCTPN(ctpn);
    }

    public int createNewCode(){
        return pnDAO.createCodeNCC();
    }

    public String capNhatPN(PhieuNhapDTO pn){
        if(pnDAO.capNhatPN(pn))
            return "Cập nhật phiếu nhập thành công !!";
        return "Cập nhật phiếu nhập thất bại !!";
    }

    public boolean deleteAllCTPN(int maPn){
        return pnDAO.deletAllCTPN(maPn);
    }
}
