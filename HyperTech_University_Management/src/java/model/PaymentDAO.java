package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import util.DbUtil;

public class PaymentDAO {

    // =====================================================
    // 1. LẤY TẤT CẢ PAYMENT (Admin)
    // =====================================================
    public ArrayList<PaymentDTO> getAll() {
        ArrayList<PaymentDTO> list = new ArrayList<>();
        String sql = "SELECT * FROM payments ORDER BY paid_at DESC";

        try (Connection conn = DbUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                list.add(extractPayment(rs));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    // =====================================================
    // 2. LẤY THEO ID
    // =====================================================
    public PaymentDTO getById(int id) {
        String sql = "SELECT * FROM payments WHERE id=?";

        try (Connection conn = DbUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return extractPayment(rs);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    // =====================================================
    // 3. LẤY PAYMENT THEO ORDER (RẤT QUAN TRỌNG)
    // =====================================================
    public PaymentDTO getByOrderId(int orderId) {
        String sql = "SELECT * FROM payments WHERE order_id=?";

        try (Connection conn = DbUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, orderId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return extractPayment(rs);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    // =====================================================
    // 4. LẤY PAYMENT THEO USER
    // =====================================================
    public ArrayList<PaymentDTO> getByUserId(String userId) {
        ArrayList<PaymentDTO> list = new ArrayList<>();
        String sql = "SELECT * FROM payments WHERE user_id=? ORDER BY paid_at DESC";

        try (Connection conn = DbUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, userId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                list.add(extractPayment(rs));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    // =====================================================
    // 5. INSERT (User thanh toán)
    // =====================================================
    public boolean insert(PaymentDTO p) {
        String sql = "INSERT INTO payments "
                   + "(order_id, user_id, payment_method, amount, status, transaction_code, paid_at) "
                   + "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DbUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, p.getOrderId());
            ps.setString(2, p.getUserId());
            ps.setString(3, p.getPaymentMethod());
            ps.setFloat(4, p.getAmount());
            ps.setString(5, p.getStatus());
            ps.setString(6, p.getTransactionCode());
            ps.setDate(7, p.getPaid_at());

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    // =====================================================
    // 6. UPDATE TOÀN BỘ
    // =====================================================
    public boolean update(PaymentDTO p) {
        String sql = "UPDATE payments SET order_id=?, user_id=?, payment_method=?, "
                   + "amount=?, status=?, transaction_code=?, paid_at=? WHERE id=?";

        try (Connection conn = DbUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, p.getOrderId());
            ps.setString(2, p.getUserId());
            ps.setString(3, p.getPaymentMethod());
            ps.setFloat(4, p.getAmount());
            ps.setString(5, p.getStatus());
            ps.setString(6, p.getTransactionCode());
            ps.setDate(7, p.getPaid_at());
            ps.setInt(8, p.getId());

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    // =====================================================
    // 7. UPDATE STATUS (Admin xác nhận)
    // =====================================================
    public boolean updateStatus(int id, String status) {
        String sql = "UPDATE payments SET status=? WHERE id=?";

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
    // 8. DELETE (Admin)
    // =====================================================
    public boolean delete(int id) {
        String sql = "DELETE FROM payments WHERE id=?";

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
    // 9. ĐẾM SỐ PAYMENT (Dashboard)
    // =====================================================
    public int countPayments() {
        String sql = "SELECT COUNT(*) FROM payments";

        try (Connection conn = DbUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            if (rs.next()) {
                return rs.getInt(1);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0;
    }

    // =====================================================
    // 10. TỔNG TIỀN ĐÃ THANH TOÁN (Chỉ tính paid)
    // =====================================================
    public float getTotalPaidAmount() {
        float total = 0;
        String sql = "SELECT SUM(amount) FROM payments WHERE status='paid'";

        try (Connection conn = DbUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            if (rs.next()) {
                total = rs.getFloat(1);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return total;
    }

    // =====================================================
    // HÀM HỖ TRỢ
    // =====================================================
    private PaymentDTO extractPayment(ResultSet rs) throws Exception {
        return new PaymentDTO(
                rs.getInt("id"),
                rs.getInt("order_id"),
                rs.getString("user_id"),
                rs.getString("payment_method"),
                rs.getFloat("amount"),
                rs.getString("status"),
                rs.getString("transaction_code"),
                rs.getDate("paid_at")
        );
    }
}