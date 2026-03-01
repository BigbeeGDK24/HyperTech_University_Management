package model;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import util.DbUtil;

public class PaymentDAO {

    // ===============================
    // GET ALL
    // ===============================
    public ArrayList<PaymentDTO> getAll() {
        ArrayList<PaymentDTO> list = new ArrayList<>();
        String sql = "SELECT * FROM payments";

        try ( Connection conn = DbUtil.getConnection();  PreparedStatement ps = conn.prepareStatement(sql);  ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                PaymentDTO p = new PaymentDTO(
                        rs.getInt("id"),
                        rs.getInt("order_id"),
                        rs.getString("user_id"),
                        rs.getString("payment_method"),
                        rs.getFloat("amount"),
                        rs.getString("status"),
                        rs.getString("transaction_code"),
                        rs.getDate("paid_at") // dùng Date theo DTO
                );
                list.add(p);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    // ===============================
    // GET BY ID
    // ===============================
    public PaymentDTO getById(int id) {
        String sql = "SELECT * FROM payments WHERE id=?";

        try ( Connection conn = DbUtil.getConnection();  PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);

            try ( ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
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

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    // ===============================
    // INSERT
    // ===============================
    public boolean insert(PaymentDTO p) {

        String sql = "INSERT INTO payments (order_id, user_id, payment_method, amount, status, transaction_code, paid_at) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try ( Connection conn = DbUtil.getConnection();  PreparedStatement ps = conn.prepareStatement(sql)) {

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

    // ===============================
    // UPDATE
    // ===============================
    public boolean update(PaymentDTO p) {

        String sql = "UPDATE payments SET order_id=?, user_id=?, payment_method=?, amount=?, status=?, transaction_code=?, paid_at=? WHERE id=?";

        try ( Connection conn = DbUtil.getConnection();  PreparedStatement ps = conn.prepareStatement(sql)) {

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

    // ===============================
    // UPDATE STATUS
    // ===============================
    public boolean updateStatus(int id, String status) {

        String sql = "UPDATE payments SET status=? WHERE id=?";

        try ( Connection conn = DbUtil.getConnection();  PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, status);
            ps.setInt(2, id);

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }
}
