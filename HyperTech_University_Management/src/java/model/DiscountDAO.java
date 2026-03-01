/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
                int id = rs.getInt("id");
                String name = rs.getString("name");
                int discount_percent = rs.getInt("discount_percent");
                Date start_date = rs.getDate("start_date");
                Date end_date = rs.getDate("end_date");

                DiscountDTO u = new DiscountDTO(id, name, discount_percent, start_date, end_date);
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
                int id = rs.getInt("id");
                String name = rs.getString("name");
                int discount_percent = rs.getInt("discount_percent");
                Date start_date = rs.getDate("paid_at");
                Date end_date = rs.getDate("paid_at");

                DiscountDTO u = new DiscountDTO(id, name, discount_percent, start_date, end_date);
                result.add(u);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
    }
}
