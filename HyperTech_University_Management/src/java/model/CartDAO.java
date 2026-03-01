package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import util.DbUtil;

public class CartDAO {

    // ===================== SEARCH EXACT =====================
    public ArrayList<CartDTO> searchByColum(String column, String value) {
        ArrayList<CartDTO> result = new ArrayList<>();
        try {
            Connection conn = DbUtil.getConnection();
            String sql = "SELECT * FROM cart"
                    + " WHERE " + column + "=?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, value);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int cartId = rs.getInt("cartId");
                String username = rs.getString("username");
                int productId = rs.getInt("productId");
                int quality = rs.getInt("quality");
                CartDTO u = new CartDTO(username, productId, quality);
                result.add(u);
            }
        } catch (Exception e) {
        }
        return result;
    }

    public ArrayList<CartDTO> filterByColum(String column, String value) {
        ArrayList<CartDTO> result = new ArrayList<>();
        try {
            Connection conn = DbUtil.getConnection();
            String sql = "SELECT * FROM cart "
                    + "WHERE status = 1 AND " + column + " LIKE ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, "%" + value + "%");
            System.out.println(ps.toString());
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String username = rs.getString("username");
                int productId = rs.getInt("productId");
                int quality = rs.getInt("quality");
                CartDTO u = new CartDTO(username, productId, quality);
                result.add(u);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    // ===================== SEARCH LIKE =====================
    public ArrayList<CartDTO> filterByColumn(String column, String value) {
        ArrayList<CartDTO> result = new ArrayList<>();

        String sql = "SELECT * FROM cart WHERE " + column + " LIKE ?";

        try ( Connection conn = DbUtil.getConnection();  PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, "%" + value + "%");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                String userId = rs.getString("user_id");
                int productId = rs.getInt("product_id");
                int quantity = rs.getInt("quantity");

                result.add(new CartDTO(userId, productId, quantity));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    // ===================== INSERT =====================
    public boolean insertCart(CartDTO cart) {

        String sql = "INSERT INTO cart (username, productId, quality) VALUES (?, ?, ?)";

        try ( Connection conn = DbUtil.getConnection();  PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, cart.getUsername());
            ps.setInt(2, cart.getProductId());
            ps.setInt(3, cart.getQuality());

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    // ===================== UPDATE =====================
    public boolean updateQuantity(String userId, int productId, int quantity) {

        String sql = "UPDATE cart SET quantity = ? WHERE user_id = ? AND product_id = ?";

        try ( Connection conn = DbUtil.getConnection();  PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, quantity);
            ps.setString(2, userId);
            ps.setInt(3, productId);

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // ===================== DELETE =====================
    public boolean deleteCart(int cartId) {

        String sql = "DELETE FROM cart WHERE cartId = ?";

        try ( Connection conn = DbUtil.getConnection();  PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, cartId);
            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

}
