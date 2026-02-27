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
 * @author truon
 */
public class ProductDAO {

    public ArrayList<ProductDTO> searchByColumn(String column, String value) {
        ArrayList<ProductDTO> products = new ArrayList<>();

        try {
            Connection con = DbUtil.getConnection();
            String sql = "SELECT * FROM products WHERE " + column + "=?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, value);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                String id = rs.getString("id");
                String name = rs.getString("name");
                String description = rs.getString("description");
                double price = rs.getDouble("price");
                int quantity = rs.getInt("quantity");
                String category = rs.getString("category");
                boolean status = rs.getBoolean("status");
                
                ProductDTO product = new ProductDTO(id, category, name, name, sql, description, name, category);
                products.add(product);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return products;
    }
    
    private ProductDTO extractProduct(ResultSet rs) throws Exception {
    String id = rs.getString("id");
    String name = rs.getString("name");
    String description = rs.getString("description");
    double price = rs.getDouble("price");
    int quantity = rs.getInt("quantity");
    String category = rs.getString("category");
    boolean status = rs.getBoolean("status");

    return new ProductDTO(id, category, name, description, price, quantity, status);
}
    
    public ArrayList<ProductDTO> filterByColumn(String column, String value) {
        ArrayList<ProductDTO> products = new ArrayList<>();

        try {
            Connection con = DbUtil.getConnection();
            String sql = "SELECT * FROM products WHERE status = 1 AND " + column + " LIKE ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, "%" + value + "%");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                ProductDTO product = extractProduct(rs);
                products.add(product);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return products;
    }

    public ArrayList<ProductDTO> searchByID(String id) {
        return searchByColumn("id", id);
    }

    public ArrayList<ProductDTO> searchByName(String name) {
        return searchByColumn("name", name);
    }

    public ArrayList<ProductDTO> filterByName(String name) {
        return filterByColumn("name", name);
    }

    public boolean softDelete(String id) {
        try {
            Connection con = DbUtil.getConnection();
            String sql = "UPDATE products SET status = 0 WHERE id=?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, id);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
