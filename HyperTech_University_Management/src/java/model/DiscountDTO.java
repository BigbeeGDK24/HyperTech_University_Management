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
public class DiscountDTO {
    private String id;
    private String name;
    private double discount_percent;
    private DateTimeFormatter start_date;
    private DateTimeFormatter end_date;
    private String created_at;

    public DiscountDTO() {
    }

    public DiscountDTO(String id, String name, double discount_percent, DateTimeFormatter start_date, DateTimeFormatter end_date, String created_at) {
        this.id = id;
        this.name = name;
        this.discount_percent = discount_percent;
        this.start_date = start_date;
        this.end_date = end_date;
        this.created_at = created_at;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getDiscount_percent() {
        return discount_percent;
    }

    public void setDiscount_percent(double discount_percent) {
        this.discount_percent = discount_percent;
    }

    public DateTimeFormatter getStart_date() {
        return start_date;
    }

    public void setStart_date(DateTimeFormatter start_date) {
        this.start_date = start_date;
    }

    public DateTimeFormatter getEnd_date() {
        return end_date;
    }

    public void setEnd_date(DateTimeFormatter end_date) {
        this.end_date = end_date;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    
}
