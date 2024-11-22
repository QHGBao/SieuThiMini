package DTO;

public class CancellationProductDTO {
    private int productId;
    private String productName;
    private int productTypeID;
    private int quantity;

    public CancellationProductDTO(int productId, String productName, int productTypeID, int quantity) {
        this.productId = productId;
        this.productName = productName;
        this.productTypeID = productTypeID;
        this.quantity = quantity;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getProductTypeID() {
        return productTypeID;
    }

    public void setProductTypeID(int productTypeID ) {
        this.productTypeID = productTypeID;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity ) {
        this.quantity = quantity;
    }
}
