/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author truon
 */
public class ProductDTO {
    private String id;
    private String category_id;
    private String name;
    private String price;
    private String stock;
    private String description;
    private String image;
    private String created_ad;

    public ProductDTO(String id, String category_id, String name, String price, String stock, String description, String image, String created_ad) {
        this.id = id;
        this.category_id = category_id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.description = description;
        this.image = image;
        this.created_ad = created_ad;
    }

    public String getId() {
        return id;
    }

    public String getCategory_id() {
        return category_id;
    }

    public String getName() {
        return name;
    }

    public String getPrice() {
        return price;
    }

    public String getStock() {
        return stock;
    }

    public String getDescription() {
        return description;
    }

    public String getImage() {
        return image;
    }

    public String getCreated_ad() {
        return created_ad;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setCategory_id(String category_id) {
        this.category_id = category_id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public void setStock(String stock) {
        this.stock = stock;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setCreated_ad(String created_ad) {
        this.created_ad = created_ad;
    }
 
}
