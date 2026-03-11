package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import util.DbUtil;

public class CartDAO {

    // ==========================================
    // 1. LẤY GIỎ HÀNG THEO USER
    // ==========================================
    public ArrayList<CartDTO> getByUserId(String userId) {
        ArrayList<CartDTO> list = new ArrayList<>();
        String sql = "SELECT * FROM cart WHERE user_id = ?";

        try (Connection conn = DbUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, userId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                list.add(new CartDTO(
                        rs.getString("user_id"),
                        rs.getInt("product_id"),
                        rs.getInt("quantity")
                ));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    // ==========================================
    // 2. KIỂM TRA ITEM ĐÃ TỒN TẠI CHƯA
    // ==========================================
    public CartDTO getItem(String userId, int productId) {
        String sql = "SELECT * FROM cart WHERE user_id = ? AND product_id = ?";

        try (Connection conn = DbUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, userId);
            ps.setInt(2, productId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return new CartDTO(
                        rs.getString("user_id"),
                        rs.getInt("product_id"),
                        rs.getInt("quantity")
                );
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    // ==========================================
    // 3. THÊM VÀO CART
    // ==========================================
    public boolean insert(CartDTO cart) {
        String sql = "INSERT INTO cart (user_id, product_id, quantity) VALUES (?, ?, ?)";

        try (Connection conn = DbUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, cart.getUsername());
            ps.setInt(2, cart.getProductId());
            ps.setInt(3, cart.getQuality());

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    // ==========================================
    // 4. CẬP NHẬT SỐ LƯỢNG
    // ==========================================
    public boolean updateQuantity(String userId, int productId, int quantity) {
        String sql = "UPDATE cart SET quantity = ? WHERE user_id = ? AND product_id = ?";

        try (Connection conn = DbUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, quantity);
            ps.setString(2, userId);
            ps.setInt(3, productId);

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    // ==========================================
    // 5. XÓA 1 ITEM
    // ==========================================
    public boolean deleteItem(String userId, int productId) {
        String sql = "DELETE FROM cart WHERE user_id = ? AND product_id = ?";

        try (Connection conn = DbUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, userId);
            ps.setInt(2, productId);

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    // ==========================================
    // 6. XÓA TOÀN BỘ CART (Sau khi checkout)
    // ==========================================
    public boolean clearCart(String userId) {
        String sql = "DELETE FROM cart WHERE user_id = ?";

        try (Connection conn = DbUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, userId);
            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }
}