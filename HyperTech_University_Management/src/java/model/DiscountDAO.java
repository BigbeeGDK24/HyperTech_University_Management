/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import util.DbUtil;

/**
 *
 * @author hasot
 */
        

public class DiscountDAO {
    public class CartItemDAO {
    public ArrayList<DiscountDTO> searchByColum(String column, String value) {
        ArrayList<DiscountDTO> result = new ArrayList<>();
        try {
            Connection conn = DbUtil.getConnection();
            String sql = "SELECT * FROM discounts WHERE " + column + "=?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, value);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String id = rs.getString("id");
                String name = rs.getString("name");
                double discount_percent = rs.getDouble("discount_percent");
                Timestamp start_date = rs.getTimestamp("paid_at");
                Timestamp end_date = rs.getTimestamp("paid_at");
                String created_at = rs.getString("created_at");

                DiscountDTO u = new DiscountDTO(id, name, discount_percent, DateTimeFormatter.ISO_DATE, DateTimeFormatter.ISO_DATE, created_at);
                result.add(u);
            }
        } catch (Exception e) {
        }
        return result;
    }

    public ArrayList<DiscountDTO> filterByColum(String column, String value) {
        ArrayList<DiscountDTO> result = new ArrayList<>();
        try {
            Connection conn = DbUtil.getConnection();
            String sql = "SELECT * FROM discounts WHERE " + column + " LIKE ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, "%" + value + "%");
            System.out.println(ps.toString());
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String id = rs.getString("id");
                String name = rs.getString("name");
                double discount_percent = rs.getDouble("discount_percent");
                Timestamp start_date = rs.getTimestamp("paid_at");
                Timestamp end_date = rs.getTimestamp("paid_at");
                String created_at = rs.getString("created_at");

                DiscountDTO u = new DiscountDTO(id, name, discount_percent, DateTimeFormatter.ISO_DATE, DateTimeFormatter.ISO_DATE, created_at);
                result.add(u);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}
}
