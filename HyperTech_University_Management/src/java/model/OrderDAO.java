package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import util.DbUtil;

public class OrderDAO {

    // ===============================
    // CREATE - INSERT
    // ===============================
    public boolean insert(OrderDTO order) {
        String sql = "INSERT INTO orders (user_id, total_price, status) VALUES (?, ?, ?)";

        try ( Connection conn = DbUtil.getConnection();  PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, order.getUserID());
            ps.setDouble(2, order.getTotalPrice());
            ps.setString(3, order.getStatus());

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // ===============================
    // READ - GET ALL
    // ===============================
    public ArrayList<OrderDTO> getAll() {
        ArrayList<OrderDTO> list = new ArrayList<>();
        String sql = "SELECT * FROM orders";

        try ( Connection conn = DbUtil.getConnection();  PreparedStatement ps = conn.prepareStatement(sql);  ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                OrderDTO o = new OrderDTO(
                        rs.getInt("id"),
                        rs.getString("user_id"),
                        rs.getDouble("total_price"),
                        rs.getString("status"),
                        rs.getTimestamp("created_at")
                );
                list.add(o);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    // ===============================
    // READ - SEARCH EQUAL
    // ===============================
    public ArrayList<OrderDTO> searchByColumn(String column, String value) {
        ArrayList<OrderDTO> result = new ArrayList<>();

        // Chỉ cho phép search các cột hợp lệ (tránh SQL Injection)
        if (!column.equals("id")
                && !column.equals("user_id")
                && !column.equals("status")) {
            return result;
        }

        String sql = "SELECT * FROM orders WHERE " + column + " = ?";

        try ( Connection conn = DbUtil.getConnection();  PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, value);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                result.add(new OrderDTO(
                        rs.getInt("id"),
                        rs.getString("user_id"),
                        rs.getDouble("total_price"),
                        rs.getString("status"),
                        rs.getTimestamp("created_at")
                ));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    // ===============================
    // READ - FILTER LIKE
    // ===============================
    public ArrayList<OrderDTO> filterByColumn(String column, String value) {
        ArrayList<OrderDTO> result = new ArrayList<>();

        if (!column.equals("user_id")
                && !column.equals("status")) {
            return result;
        }

        String sql = "SELECT * FROM orders WHERE " + column + " LIKE ?";

        try ( Connection conn = DbUtil.getConnection();  PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, "%" + value + "%");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                result.add(new OrderDTO(
                        rs.getInt("id"),
                        rs.getString("user_id"),
                        rs.getDouble("total_price"),
                        rs.getString("status"),
                        rs.getTimestamp("created_at")
                ));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    // ===============================
    // UPDATE - FULL UPDATE
    // ===============================
    public boolean update(OrderDTO order) {
        String sql = "UPDATE orders SET user_id=?, total_price=?, status=? WHERE id=?";

        try ( Connection conn = DbUtil.getConnection();  PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, order.getUserID());
            ps.setDouble(2, order.getTotalPrice());
            ps.setString(3, order.getStatus());
            ps.setInt(4, order.getId());

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // ===============================
    // UPDATE - ONLY STATUS
    // ===============================
    public boolean updateStatus(int id, String status) {
        String sql = "UPDATE orders SET status=? WHERE id=?";

        try ( Connection conn = DbUtil.getConnection();  PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, status);
            ps.setInt(2, id);

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // ===============================
    // DELETE
    // ===============================
    public boolean delete(int id) {
        String sql = "DELETE FROM orders WHERE id=?";

        try ( Connection conn = DbUtil.getConnection();  PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    // ===============================
// STATISTICS - TOTAL REVENUE
// ===============================

    public double getTotalRevenue() {
        double total = 0;
        String sql = "SELECT SUM(total_price) AS total FROM orders";

        try ( Connection conn = DbUtil.getConnection();  PreparedStatement ps = conn.prepareStatement(sql);  ResultSet rs = ps.executeQuery()) {

            if (rs.next()) {
                total = rs.getDouble("total");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return total;
    }

// ===============================
// STATISTICS - TOTAL ORDERS
// ===============================
    public int getTotalOrders() {
        int count = 0;
        String sql = "SELECT COUNT(*) AS total FROM orders";

        try ( Connection conn = DbUtil.getConnection();  PreparedStatement ps = conn.prepareStatement(sql);  ResultSet rs = ps.executeQuery()) {

            if (rs.next()) {
                count = rs.getInt("total");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return count;
    }
}
