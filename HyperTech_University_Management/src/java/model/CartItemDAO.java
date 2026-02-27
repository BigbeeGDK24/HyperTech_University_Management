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
public class CartItemDAO {
    public ArrayList<CartItemDTO> searchByColum(String column, String value) {
        ArrayList<CartItemDTO> result = new ArrayList<>();
        try {
            Connection conn = DbUtil.getConnection();
            String sql = "SELECT * FROM CartItem WHERE " + column + "=?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, value);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String CartItemId = rs.getString("CartItemId");
                String cartId = rs.getString("cartId");
                String productId = rs.getString("productId");
                int quality = rs.getInt("quality");

                CartItemDTO u = new CartItemDTO(CartItemId, cartId, productId, quality);
                result.add(u);
            }
        } catch (Exception e) {
        }
        return result;
    }

    public ArrayList<CartItemDTO> filterByColum(String column, String value) {
        ArrayList<CartItemDTO> result = new ArrayList<>();
        try {
            Connection conn = DbUtil.getConnection();
            String sql = "SELECT * FROM cartItem WHERE " + column + " LIKE ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, "%" + value + "%");
            System.out.println(ps.toString());
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String CartItemId = rs.getString("CartItemId");
                String cartId = rs.getString("cartId");
                String productId = rs.getString("productId");
                int quality = rs.getInt("quality");

                CartItemDTO u = new CartItemDTO(CartItemId, cartId, productId, quality);
                result.add(u);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}
