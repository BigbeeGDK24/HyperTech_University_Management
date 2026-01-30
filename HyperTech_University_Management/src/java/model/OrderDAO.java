package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import utils.DbUtils;

public class OrderDAO {

    public OrderDAO() {
    }

    // ===============================
    // SEARCH BY COLUMN (EQUAL)
    // ===============================
    public ArrayList<OrderDTO> searchByColum(String column, String value) {
        ArrayList<OrderDTO> result = new ArrayList<>();
        try {
            Connection conn = DbUtils.getConnection();
            String sql = "SELECT * FROM orders WHERE " + column + " = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, value);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                String userID = rs.getString("user_id");
                double totalPrice = rs.getDouble("total_price");
                String status = rs.getString("status");
                java.sql.Timestamp createdAt = rs.getTimestamp("created_at");

                OrderDTO o = new OrderDTO(id, userID, totalPrice, status, createdAt);
                result.add(o);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    // ===============================
    // FILTER BY COLUMN (LIKE)
    // ===============================
    public ArrayList<OrderDTO> filterByColum(String column, String value) {
        ArrayList<OrderDTO> result = new ArrayList<>();
        try {
            Connection conn = DbUtils.getConnection();
            String sql = "SELECT * FROM orders WHERE " + column + " LIKE ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, "%" + value + "%");
            System.out.println(ps.toString());

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String userID = rs.getString("user_id");
                double totalPrice = rs.getDouble("total_price");
                String status = rs.getString("status");
                java.sql.Timestamp createdAt = rs.getTimestamp("created_at");

                OrderDTO o = new OrderDTO(id, userID, totalPrice, status, createdAt);
                result.add(o);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    // ===============================
    // WRAPPER METHODS (GIỐNG BÀI MẪU)
    // ===============================
    public ArrayList<OrderDTO> searchByID(String id) {
        return searchByColum("id", id);
    }

    public ArrayList<OrderDTO> searchByUserID(String userID) {
        return searchByColum("user_id", userID);
    }

    public ArrayList<OrderDTO> filterByStatus(String status) {
        return filterByColum("status", status);
    }

    // ===============================
    // UPDATE STATUS (THAY softDelete)
    // ===============================
    public boolean updateStatus(int id, String status) {
        try {
            Connection conn = DbUtils.getConnection();
            String sql = "UPDATE orders SET status=? WHERE id=?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, status);
            ps.setInt(2, id);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
        return false;
    }
}
