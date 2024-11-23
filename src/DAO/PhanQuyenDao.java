package DAO;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement; 
import java.util.ArrayList;

import DTO.PhanQuyenDTO;

public class PhanQuyenDao {
    private ConnectManager connectManager;

    public PhanQuyenDao() {
        connectManager = new ConnectManager();
    }

    public ArrayList<PhanQuyenDTO> getALLQuyen() {
        ArrayList<PhanQuyenDTO> dsQuyen = new ArrayList<PhanQuyenDTO>();
        String sql = "SELECT * FROM PhanQuyen";
        try (Connection connection = connectManager.getConnection();
             Statement stmt = connection.createStatement(); // Sử dụng java.sql.Statement
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                if (rs.getInt(3) != 1) {
                    PhanQuyenDTO pq = new PhanQuyenDTO();
                    pq.setMaQuyen(rs.getInt(1));
                    pq.setTenQuyen(rs.getString(2));
                    pq.setMoTa(rs.getString(3));
                    dsQuyen.add(pq);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connectManager.closeConnection();
        }
        return dsQuyen;
    }
}
