/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import util.DbUtil;

/**
 *
 * @author hasot
 */
public class CartDAO {

    public ArrayList<CartDTO> searchByColum(String column, String value) {
        ArrayList<CartDTO> result = new ArrayList<>();
        try {
            Connection conn = DbUtil.getConnection();
            String sql = "SELECT * FROM Cart WHERE " + column + "=?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, value);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String id = rs.getString("id");
                String userId = rs.getString("userId");
                CartDTO u = new CartDTO(id, id);
                result.add(u);
            }
        } catch (Exception e) {
        }
        return result;
    }

    public ArrayList<CartDTO> filterByColum(String column, String value) {
        ArrayList<CartDTO> result = new ArrayList<>();
        try {
            Connection conn = DbUtil.getConnection();
            String sql = "SELECT * FROM cart WHERE " + column + " LIKE ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, "%" + value + "%");
            System.out.println(ps.toString());
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String id = rs.getString("id");
                String userId = rs.getString("userId");
                CartDTO u = new CartDTO("id", id);
                result.add(u);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}
