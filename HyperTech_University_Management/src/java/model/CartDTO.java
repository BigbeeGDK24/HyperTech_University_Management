/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author hasot
 */
public class CartDTO {

    private int cartId;
    private String username;
    private int productId;
    private int quality;

    public CartDTO() {
    }

    public CartDTO(int cartId, String username, int productId, int quality) {
        this.cartId = cartId;
        this.username = username;
        this.productId = productId;
        this.quality = quality;
    }

    

    public int getCartId() {
        return cartId;
    }

    public void setCartItemId(int cartId) {
        this.cartId = cartId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }


    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getQuality() {
        return quality;
    }

    public void setQuality(int quality) {
        this.quality = quality;
    }
}
