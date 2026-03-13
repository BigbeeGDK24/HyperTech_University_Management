package model;

public class ProductDTO {

    private int id;
    private int category_id;
    private String name;
    private float price;
    private int stock;
    private String description;
    private String image;
    private Boolean status;

    // dùng cho cart
    private int quantity = 1;

    public ProductDTO() {
    }

    public ProductDTO(int id, int category_id, String name, float price,
            int stock, String description, String image, Boolean status) {

        this.id = id;
        this.category_id = category_id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.description = description;
        this.image = image;
        this.status = status;
    }

    // ===== ID =====
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    // alias cho cart.jsp
    public int getProductID() {
        return id;
    }

    // ===== CATEGORY =====
    public int getCategory_id() {
        return category_id;
    }

    public void setCategory_id(int category_id) {
        this.category_id = category_id;
    }

    // ===== NAME =====
    public String getName() {
        return name;
    }

    public String getProductName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    // ===== PRICE =====
    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    // ===== STOCK =====
    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    // ===== DESCRIPTION =====
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    // ===== IMAGE =====
    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    // ===== STATUS =====
    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    // ===== CART QUANTITY =====
    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

}
