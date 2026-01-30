package model;

import java.sql.Timestamp;

public class OrderDTO {

    private int id;
    private String userID;
    private double totalPrice;
    private String status;
    private Timestamp createdAt;

    public OrderDTO() {
    }

    public OrderDTO(int id, String userID, double totalPrice, String status, Timestamp createdAt) {
        this.id = id;
        this.userID = userID;
        this.totalPrice = totalPrice;
        this.status = status;
        this.createdAt = createdAt;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }
}
