package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import DTO.TempDataDTO;

public class TempDataDAO {

    private ConnectManager connectManager = new ConnectManager();

    public void updateDiemThanhTien(int ddttDiemApDung, int ddttTienApDung) {
        String sql = "UPDATE QuyDoiDiemTam SET DiemGiamGia = ?, TienGiamGia = ? WHERE MaBanGhi = 1";
    
        try {
            connectManager.openConnection();
            Connection connection = connectManager.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, ddttDiemApDung);
            preparedStatement.setInt(2, ddttTienApDung);
    
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connectManager.closeConnection();
        }
    }

    public void updateTienThanhDiem(int dttdTienApDung, int dttdDiemApDung) {
        String sql = "UPDATE QuyDoiDiemTam SET TienQuyDoi = ?, DiemQuyDoi = ? WHERE MaBanGhi = 1";
    
        try {
            connectManager.openConnection();
            Connection connection = connectManager.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, dttdTienApDung);
            preparedStatement.setInt(2, dttdDiemApDung);
    
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connectManager.closeConnection();
        }
    }
    

    public ArrayList<TempDataDTO> getTempData() {
        String sql = "SELECT DiemGiamGia, TienGiamGia, TienQuyDoi, DiemQuyDoi FROM QuyDoiDiemTam WHERE MaBanGhi = 1";
        ArrayList<TempDataDTO> dataList = new ArrayList<>();

        try {
            connectManager.openConnection();
            Connection connection = connectManager.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet rs = preparedStatement.executeQuery();

            if (rs.next()) {
                TempDataDTO tempDataDTO = new TempDataDTO(
                    rs.getInt("DiemGiamGia"),
                    rs.getInt("TienGiamGia"),
                    rs.getInt("TienQuyDoi"),
                    rs.getInt("DiemQuyDoi")
                );
                dataList.add(tempDataDTO);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connectManager.closeConnection();
        }

        return dataList;
    }
}
