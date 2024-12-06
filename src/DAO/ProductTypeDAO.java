package DAO;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductTypeDAO {

    private ConnectManager connectManager;

    public ProductTypeDAO() {
        connectManager = new ConnectManager();
    }

    public List<String> getAllProductTypesName() {
        List<String> productTypes = new ArrayList<>();
        String query = "SELECT TenLoai FROM LoaiSanPham";  // Truy vấn lấy tên loại sản phẩm
        try {
            connectManager.openConnection();
            Connection connection = connectManager.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet rs = preparedStatement.executeQuery();

            // Duyệt qua kết quả và thêm vào danh sách
            while (rs.next()) {
                productTypes.add(rs.getString("TenLoai"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return productTypes;
    }
}
