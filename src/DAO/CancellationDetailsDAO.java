package DAO;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import DTO.CancellationDetailsDTO;

public class CancellationDetailsDAO {
    private ConnectManager connectManager;

    public CancellationDetailsDAO() {
       connectManager = new ConnectManager();
    }

    public List<CancellationDetailsDTO> getCancellationDetailsByMCancellationID(int cancellationID) {
        List<CancellationDetailsDTO> list = new ArrayList<>();
        try {
            String sql = "SELECT ctp.MaPH, ctp.MaSP, ctp.SoLuong, sp.TenSP, lsp.TenLoai FROM CTPhieuHuy ctp JOIN SanPham sp ON ctp.MaSP = sp.MaSP JOIN LoaiSanPham lsp ON sp.MaLoai = lsp.MaLoai WHERE ctp.MaPH = ?";
            connectManager.openConnection();
            Connection connection = connectManager.getConnection();
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setInt(1, cancellationID);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                list.add(new CancellationDetailsDTO(rs.getInt("MaPH"), rs.getInt("MaSP"), rs.getString("TenSP"), rs.getString("TenLoai"), rs.getInt("SoLuong")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connectManager.closeConnection();
        }
        return list;
    }
}