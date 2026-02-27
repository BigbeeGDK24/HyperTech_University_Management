/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.sql.Date;
import java.time.format.DateTimeFormatter;


/**
 *
 * @author hasot
 */
public class PaymentDTO {
        private int id;
        private int orderId;
        private String userId;
        private String paymentMethod;
        private float amount;
        private String status;
        private String transactionCode;
        private Date paid_at;

    public PaymentDTO() {
    }

    public PaymentDTO(int id, int orderId, String userId, String paymentMethod, float amount, String status, String transactionCode, DateTimeFormatter paid_at) {
        this.id = id;
        this.orderId = orderId;
        this.userId = userId;
        this.paymentMethod = paymentMethod;
        this.amount = amount;
        this.status = status;
        this.transactionCode = transactionCode;
        this.paid_at = paid_at;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTransactionCode() {
        return transactionCode;
    }

    public void setTransactionCode(String transactionCode) {
        this.transactionCode = transactionCode;
    }

    public DateTimeFormatter getPaid_at() {
        return paid_at;
    }

    public void setPaid_at(DateTimeFormatter paid_at) {
        this.paid_at = paid_at;
    }

    
    
}
