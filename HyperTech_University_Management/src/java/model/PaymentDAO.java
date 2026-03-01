package model;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import util.DbUtil;

/**
 *
 * @author hasot
 */
public class PaymentDAO {

    public PaymentDAO() {
    }

    // ================== READ (SEARCH & FILTER) ==================

    public ArrayList<PaymentDTO> searchByColum(String column, String value) {
        ArrayList<PaymentDTO> result = new ArrayList<>();
        try {
            Connection conn = DbUtil.getConnection();
            String sql = "SELECT * FROM payment WHERE " + column + "=?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, value);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                int orderId = rs.getInt("orderId");
                String userId = rs.getString("userId");
                String paymentMethod = rs.getString("paymentMethod");
                float amount = rs.getFloat("amount");
                String status = rs.getString("status");
                String transactionCode = rs.getString("transactionCode");
                Date paid_at = rs.getDate("paid_at");
                
                PaymentDTO p = new PaymentDTO(id, orderId, userId, paymentMethod, amount, status, transactionCode, paid_at);
                result.add(p);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public ArrayList<PaymentDTO> filterByColum(String column, String value) {
        ArrayList<PaymentDTO> result = new ArrayList<>();
        try {
            Connection conn = DbUtil.getConnection();
            String sql = "SELECT * FROM payment WHERE " + column + " LIKE ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, "%" + value + "%");
            System.out.println(ps.toString());
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                int orderId = rs.getInt("orderId");
                String userId = rs.getString("userId");
                String paymentMethod = rs.getString("paymentMethod");
                float amount = rs.getFloat("amount");
                String status = rs.getString("status");
                String transactionCode = rs.getString("transactionCode");
                Date paid_at = rs.getDate("paid_at");
                
                PaymentDTO p = new PaymentDTO(id, orderId, userId, paymentMethod, amount, status, transactionCode, paid_at);
                result.add(p);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public PaymentDTO searchByID(int id) {
        ArrayList<PaymentDTO> list = searchByColum("id", String.valueOf(id));
        if (list.size() > 0) {
            return list.get(0);
        }
        return null;
    }

    public ArrayList<PaymentDTO> searchByUserId(String userId) {
        return searchByColum("userId", userId);
    }
    
    public ArrayList<PaymentDTO> searchByOrderId(int orderId) {
        return searchByColum("orderId", String.valueOf(orderId));
    }

    // ================== CREATE ==================

    public boolean add(PaymentDTO p) {
        int result = 0;
        try {
            Connection conn = DbUtil.getConnection();
            // Giả sử cột `id` là tự động tăng (AUTO_INCREMENT) nên không truyền vào câu INSERT
            String sql = "INSERT INTO payment (orderId, userId, paymentMethod, amount, status, transactionCode, paid_at) VALUES (?, ?, ?, ?, ?, ?, ?)";

            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, p.getOrderId());
            ps.setString(2, p.getUserId());
            ps.setString(3, p.getPaymentMethod());
            ps.setFloat(4, p.getAmount());
            ps.setString(5, p.getStatus());
            ps.setString(6, p.getTransactionCode());
            ps.setDate(7, p.getPaid_at());

            result = ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Add Payment Error: " + e.getMessage());
        }
        return result > 0;
    }

    // ================== UPDATE ==================

    public boolean update(PaymentDTO p) {
        int result = 0;
        try {
            Connection conn = DbUtil.getConnection();
            String sql = "UPDATE payment "
                    + "   SET orderId = ?"
                    + "      ,userId = ?"
                    + "      ,paymentMethod = ?"
                    + "      ,amount = ?"
                    + "      ,status = ?"
                    + "      ,transactionCode = ?"
                    + "      ,paid_at = ?"
                    + " WHERE id = ?";

            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, p.getOrderId());
            ps.setString(2, p.getUserId());
            ps.setString(3, p.getPaymentMethod());
            ps.setFloat(4, p.getAmount());
            ps.setString(5, p.getStatus());
            ps.setString(6, p.getTransactionCode());
            ps.setDate(7, p.getPaid_at());
            ps.setInt(8, p.getId()); // Cập nhật dựa trên ID

            result = ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Update Payment Error: " + e.getMessage());
        }
        return result > 0;
    }

    // ================== DELETE ==================

    /**
     * Tùy thuộc vào thiết kế DB, cột status của bạn có kiểu String.
     * Hàm softDelete này sẽ chuyển status thành 'Cancelled' hoặc 'Deleted'.
     */
    public boolean softDelete(int id) {
        try {
            Connection conn = DbUtil.getConnection();
            String sql = "UPDATE payment SET status = 'Deleted' WHERE id = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Soft Delete Payment Error: " + e.getMessage());
        }
        return false;
    }
    
    /**
     * Hàm xóa vĩnh viễn (Hard Delete) khỏi database
     */
    public boolean delete(int id) {
        try {
            Connection conn = DbUtil.getConnection();
            String sql = "DELETE FROM payment WHERE id = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Delete Payment Error: " + e.getMessage());
        }
        return false;
    }
}