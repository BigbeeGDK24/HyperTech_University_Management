package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import util.DbUtil; // Đảm bảo đúng tên package tiện ích của bạn (DbUtil hoặc DbUtils)

public class OrderItemDAO {

    public OrderItemDAO() {
    }

    // ===============================
    // READ: SEARCH BY COLUMN (EQUAL)
    // ===============================
    public ArrayList<OrderItemDTO> searchByColum(String column, String value) {
        ArrayList<OrderItemDTO> result = new ArrayList<>();
        try {
            Connection conn = DbUtil.getConnection();
            String sql = "SELECT * FROM order_items WHERE " + column + " = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, value);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                int orderID = rs.getInt("order_id");
                int productID = rs.getInt("product_id");
                float price = rs.getFloat("price");
                int quantity = rs.getInt("quantity");

                OrderItemDTO item = new OrderItemDTO(id, orderID, productID, price, quantity);
                result.add(item);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    // ===============================
    // READ: FILTER BY COLUMN (LIKE)
    // ===============================
    public ArrayList<OrderItemDTO> filterByColum(String column, String value) {
        ArrayList<OrderItemDTO> result = new ArrayList<>();
        try {
            Connection conn = DbUtil.getConnection();
            String sql = "SELECT * FROM order_items WHERE " + column + " LIKE ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, "%" + value + "%");
            System.out.println(ps.toString());

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                int orderID = rs.getInt("order_id");
                int productID = rs.getInt("product_id");
                float price = rs.getFloat("price");
                int quantity = rs.getInt("quantity");

                OrderItemDTO item = new OrderItemDTO(id, orderID, productID, price, quantity);
                result.add(item);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    // ===============================
    // READ: WRAPPER METHODS
    // ===============================
    public OrderItemDTO searchByID(int id) {
        // Chuyển int sang String để dùng chung hàm searchByColum
        ArrayList<OrderItemDTO> list = searchByColum("id", String.valueOf(id));
        if (!list.isEmpty()) {
            return list.get(0);
        }
        return null;
    }

    public ArrayList<OrderItemDTO> searchByOrderID(int orderID) {
        return searchByColum("order_id", String.valueOf(orderID));
    }

    public ArrayList<OrderItemDTO> searchByProductID(int productID) {
        return searchByColum("product_id", String.valueOf(productID));
    }

    // ===============================
    // CREATE: ADD ORDER ITEM
    // ===============================
    public boolean add(OrderItemDTO item) {
        int result = 0;
        try {
            Connection conn = DbUtil.getConnection();
            // Giả sử id là tự tăng (Auto Increment) nên không insert id
            String sql = "INSERT INTO order_items(order_id, product_id, price, quantity) VALUES (?, ?, ?, ?)";
            
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, item.getOrderID());
            ps.setInt(2, item.getProductID());
            ps.setFloat(3,(float) item.getPrice());
            ps.setInt(4, item.getQuantity());

            result = ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
        return result > 0;
    }

    // ===============================
    // UPDATE: UPDATE ORDER ITEM
    // ===============================
    public boolean update(OrderItemDTO item) {
        int result = 0;
        try {
            Connection conn = DbUtil.getConnection();
            String sql = "UPDATE order_items "
                       + "SET order_id = ?, "
                       + "    product_id = ?, "
                       + "    price = ?, "
                       + "    quantity = ? "
                       + "WHERE id = ?";

            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, item.getOrderID());
            ps.setInt(2, item.getProductID());
            ps.setFloat(3, (float) item.getPrice());
            ps.setInt(4, item.getQuantity());
            ps.setInt(5, item.getId()); // Cập nhật dựa trên ID

            result = ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
        return result > 0;
    }

    // ===============================
    // DELETE: HARD DELETE
    // ===============================
    public boolean delete(int id) {
        int result = 0;
        try {
            Connection conn = DbUtil.getConnection();
            // Xóa cứng khỏi database. Nếu bạn muốn xóa mềm thì đổi thành UPDATE status
            String sql = "DELETE FROM order_items WHERE id = ?";
            
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            
            result = ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
        return result > 0;
    }
}