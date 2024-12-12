package BUS;

import DAO.ProductDAO;
import DTO.ProductDTO;
import java.util.ArrayList;

public class ProductBUS {

    ProductDAO prDAO = new ProductDAO();

    public ArrayList<ProductDTO> getAllProducts() {
        return new ArrayList<>(prDAO.getAllProducts()); 
    }

    public ArrayList<ProductDTO> searchProductsByName(String keyword) {
        return new ArrayList<>(prDAO.searchProductsByName(keyword));
    }

    public ProductDTO getProductById(int id) {
        return prDAO.getProductbyId(id);
    }

    public boolean addProduct(ProductDTO product) {
        return prDAO.addProduct(product);
    }

    public boolean updateProduct(ProductDTO product) {
        return prDAO.updateProduct(product);
    }

    public boolean deleteProduct(int id) {
        return prDAO.deleteProduct(id);
    }

    public ArrayList<ProductDTO> searchProducts(String productName, String productType) {
        return prDAO.searchProductsByNameAndType(productName, productType);
    }

    public String getTenSanPhamByMaSP(int maSP) {
        return prDAO.getTenSanPhamByMaSP(maSP);
    }

    public boolean updateProductQuantity(int maSP, int soLuongBan) {
        return prDAO.updateProductQuantity(maSP, soLuongBan);
    }

    public int getMaLoaiByTenLoai(String tenLoai) {
        return prDAO.getMaLoaiByTenLoai(tenLoai);
    }

    public String getTenLoaiByMaLoai(int maLoai) {
        return prDAO.getTenLoaiByMaLoai(maLoai);
    }

    public int getProductQuantityById(int maSP) {
        return prDAO.getProductQuantityById(maSP);
    }

}
