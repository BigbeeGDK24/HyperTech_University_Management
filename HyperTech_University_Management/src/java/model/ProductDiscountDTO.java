package model;

public class ProductDiscountDTO {

    private int productID;
    private int discountID;

    public ProductDiscountDTO() {
    }

    public ProductDiscountDTO(int productID, int discountID) {
        this.productID = productID;
        this.discountID = discountID;
    }

    public int getProductID() {
        return productID;
    }

    public void setProductID(int productID) {
        this.productID = productID;
    }

    public int getDiscountID() {
        return discountID;
    }

    public void setDiscountID(int discountID) {
        this.discountID = discountID;
    }

}