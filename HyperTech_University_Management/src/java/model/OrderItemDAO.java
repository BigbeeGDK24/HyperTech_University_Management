package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import utils.DbUtils;

public class OrderItemDAO {

    public OrderItemDAO() {
    }

    // ===============================
    // SEARCH BY COLUMN (EQUAL)
    // ===============================
    public ArrayList<OrderItemDTO> searchByColum(String column, String value) {
        ArrayList<OrderItemDTO> result = new ArrayList<>();
        try {
            Connection conn = DbUtils.getConnection();
            String sql = "SELECT * FROM order_items WHERE " + column + " = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, value);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                int orderID = rs.getInt("order_id");
                int productID = rs.getInt("product_id");
                double price = rs.getDouble("price");
                int quantity = rs.getInt("quantity");

                OrderItemDTO item = new OrderItemDTO(
                        id, orderID, productID, price, quantity
                );
                result.add(item);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    // ===============================
    // FILTER BY COLUMN (LIKE)
    // ===============================
    public ArrayList<OrderItemDTO> filterByColum(String column, String value) {
        ArrayList<OrderItemDTO> result = new ArrayList<>();
        try {
            Connection conn = DbUtils.getConnection();
            String sql = "SELECT * FROM order_items WHERE " + column + " LIKE ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, "%" + value + "%");
            System.out.println(ps.toString());

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                int orderID = rs.getInt("order_id");
                int productID = rs.getInt("product_id");
                double price = rs.getDouble("price");
                int quantity = rs.getInt("quantity");

                OrderItemDTO item = new OrderItemDTO(
                        id, orderID, productID, price, quantity
                );
                result.add(item);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    // ===============================
    // WRAPPER METHODS (GIỐNG BÀI MẪU)
    // ===============================
    public ArrayList<OrderItemDTO> searchByID(String id) {
        return searchByColum("id", id);
    }

    public ArrayList<OrderItemDTO> searchByOrderID(String orderID) {
        return searchByColum("order_id", orderID);
    }

    public ArrayList<OrderItemDTO> searchByProductID(String productID) {
        return searchByColum("product_id", productID);
    }

    // ===============================
    // INSERT ORDER ITEM
    // ===============================
    public boolean insert(OrderItemDTO item) {
        try {
            Connection conn = DbUtils.getConnection();
            String sql = "INSERT INTO order_items(order_id, product_id, price, quantity) "
                    + "VALUES(?,?,?,?)";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, item.getOrderID());
            ps.setInt(2, item.getProductID());
            ps.setDouble(3, item.getPrice());
            ps.setInt(4, item.getQuantity());

            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
        return false;
    }
}
