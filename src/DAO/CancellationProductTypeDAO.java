package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CancellationProductTypeDAO {
    private ConnectManager connectManager;
    public CancellationProductTypeDAO(){
        connectManager = new ConnectManager();
    }
    
    public String getProductTypeById(int productTypeID) {
        String query = "SELECT TenLoai FROM LoaiSanPham WHERE MaLoai = ?";
        try {
            connectManager.openConnection();
            Connection connection = connectManager.getConnection();
            PreparedStatement ps = connection.prepareStatement(query); 
            ps.setInt(1, productTypeID);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getString("TenLoai");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
