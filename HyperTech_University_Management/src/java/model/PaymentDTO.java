/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.time.format.DateTimeFormatter;

/**
 *
 * @author hasot
 */
public class PaymentDTO {
    private String id;
    private String orderId;
    private String userId;
    private String paymentMethod;
    private String amount;
    private String status;
    private String transactionCode;
    private DateTimeFormatter paid_at;
    private DateTimeFormatter created_at;

    public PaymentDTO() {
    }

    public PaymentDTO(String id, String orderId, String userId, String paymentMethod, String amount, String status, String transactionCode, DateTimeFormatter paid_at, DateTimeFormatter created_at) {
        this.id = id;
        this.orderId = orderId;
        this.userId = userId;
        this.paymentMethod = paymentMethod;
        this.amount = amount;
        this.status = status;
        this.transactionCode = transactionCode;
        this.paid_at = paid_at;
        this.created_at = created_at;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
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

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
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

    public DateTimeFormatter getCreated_at() {
        return created_at;
    }

    public void setCreated_at(DateTimeFormatter created_at) {
        this.created_at = created_at;
    }
    
}
