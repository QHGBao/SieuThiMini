package DTO;

public class CancellationDetailsDTO {
    private int cancellationID;
    private int productID;
    private String productName;
    private String productType;
    private int cancellationQuantity;

    public CancellationDetailsDTO(int cancellationID, int productID, String productName, String productType, int cancellationQuantity) {
        this.cancellationID = cancellationID;
        this.productID = productID;
        this.productName = productName;
        this.productType= productType;
        this.cancellationQuantity = cancellationQuantity;
    }

    public int getCancellationID() {
        return cancellationID;
    }

    public void setCancellationID(int cancellationID) {
        this.cancellationID = cancellationID;
    }

    public int getProductID() {
        return productID;
    }

    public void setProductID(int productID) {
        this.productID = productID;
    }

    public String getProductName(){
        return productName;
    }

    public void setProductName(String productName){
        this.productName = productName;
    }

    public String getProductType(){
       return productType;
    }

    public void setProductType(String productType){
        this.productType = productType;
    }

    public int getCancellationQuantity() {
        return cancellationQuantity;
    }

    public void setCancellationQuantity(int cancellationQuantity) {
        this.cancellationQuantity = cancellationQuantity;
    }
}
