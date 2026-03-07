package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import util.DbUtil;

public class OrderDAO {

    // =====================================================
    // 1. INSERT ORDER (Tạo đơn hàng mới)
    // =====================================================
    public boolean insert(OrderDTO order) {
        String sql = "INSERT INTO orders (user_id, total_price, status) VALUES (?, ?, ?)";

        try (Connection conn = DbUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, order.getUserID());
            ps.setDouble(2, order.getTotalPrice());
            ps.setString(3, order.getStatus());

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // =====================================================
    // 2. LẤY TẤT CẢ ORDER (Admin xem toàn bộ)
    // =====================================================
    public ArrayList<OrderDTO> getAll() {
        ArrayList<OrderDTO> list = new ArrayList<>();
        String sql = "SELECT * FROM orders ORDER BY created_at DESC";

        try (Connection conn = DbUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                list.add(extractOrder(rs));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    // =====================================================
    // 3. LẤY ORDER THEO ID (Xem chi tiết)
    // =====================================================
    public OrderDTO getById(int id) {
        String sql = "SELECT * FROM orders WHERE id = ?";

        try (Connection conn = DbUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return extractOrder(rs);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    // =====================================================
    // 4. LẤY ORDER THEO USER (User xem lịch sử mua hàng)
    // =====================================================
    public ArrayList<OrderDTO> getByUserId(String userId) {
        ArrayList<OrderDTO> list = new ArrayList<>();
        String sql = "SELECT * FROM orders WHERE user_id = ? ORDER BY created_at DESC";

        try (Connection conn = DbUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, userId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                list.add(extractOrder(rs));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    // =====================================================
    // 5. TÌM THEO CỘT (EQUAL - an toàn)
    // =====================================================
    public ArrayList<OrderDTO> searchByColumn(String column, String value) {
        ArrayList<OrderDTO> result = new ArrayList<>();

        // Whitelist tránh SQL Injection
        if (!column.equals("id") &&
            !column.equals("user_id") &&
            !column.equals("status")) {
            return result;
        }

        String sql = "SELECT * FROM orders WHERE " + column + " = ?";

        try (Connection conn = DbUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, value);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                result.add(extractOrder(rs));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    // =====================================================
    // 6. UPDATE TOÀN BỘ ORDER
    // =====================================================
    public boolean update(OrderDTO order) {
        String sql = "UPDATE orders SET user_id=?, total_price=?, status=? WHERE id=?";

        try (Connection conn = DbUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

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

    // =====================================================
    // 7. UPDATE CHỈ STATUS (Admin xử lý đơn)
    // =====================================================
    public boolean updateStatus(int id, String status) {
        String sql = "UPDATE orders SET status=? WHERE id=?";

        try (Connection conn = DbUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, status);
            ps.setInt(2, id);

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // =====================================================
    // 8. DELETE ORDER
    // =====================================================
    public boolean delete(int id) {
        String sql = "DELETE FROM orders WHERE id=?";

        try (Connection conn = DbUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // =====================================================
    // 9. TỔNG DOANH THU (Chỉ tính đơn hoàn thành)
    // =====================================================
    public double getTotalRevenue() {
        double total = 0;
        String sql = "SELECT SUM(total_price) AS total FROM orders WHERE status = 'completed'";

        try (Connection conn = DbUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            if (rs.next()) {
                total = rs.getDouble("total");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return total;
    }

    // =====================================================
    // 10. TỔNG SỐ ORDER
    // =====================================================
    public int getTotalOrders() {
        int count = 0;
        String sql = "SELECT COUNT(*) AS total FROM orders";

        try (Connection conn = DbUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            if (rs.next()) {
                count = rs.getInt("total");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return count;
    }

    // =====================================================
    // 11. ĐẾM ORDER THEO STATUS (Dashboard nâng cao)
    // =====================================================
    public int countByStatus(String status) {
        int count = 0;
        String sql = "SELECT COUNT(*) FROM orders WHERE status = ?";

        try (Connection conn = DbUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, status);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                count = rs.getInt(1);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return count;
    }

    // =====================================================
    // HÀM HỖ TRỢ: CHUYỂN ResultSet → OrderDTO
    // =====================================================
    private OrderDTO extractOrder(ResultSet rs) throws Exception {
        return new OrderDTO(
                rs.getInt("id"),
                rs.getString("user_id"),
                rs.getDouble("total_price"),
                rs.getString("status"),
                rs.getTimestamp("created_at")
        );
    }
}