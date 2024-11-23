package DTO;

public class CancellationProductTypeDTO {
    private int productTypeID;
    private String productType;

    public CancellationProductTypeDTO(int productTypeID, String productType) {
        this.productTypeID = productTypeID;
        this.productType = productType;
    }

    public int getProductTypeID() {
        return productTypeID;
    }

    public void setProductTypeID(int productTypeID) {
        this.productTypeID = productTypeID;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }
}