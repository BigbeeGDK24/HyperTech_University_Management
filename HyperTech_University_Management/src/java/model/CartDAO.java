package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import util.DbUtil;

public class CartDAO {

    // ===================== SEARCH EXACT =====================
    public ArrayList<CartDTO> searchByColumn(String column, String value) {
        ArrayList<CartDTO> result = new ArrayList<>();

        String sql = "SELECT * FROM cart WHERE " + column + " = ?";

        try (Connection conn = DbUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, value);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                result.add(mapResultSet(rs));
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

        try (Connection conn = DbUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, "%" + value + "%");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                result.add(mapResultSet(rs));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    // ===================== INSERT =====================
    public boolean insertCart(CartDTO cart) {

        String sql = "INSERT INTO cart (username, productId, quality) VALUES (?, ?, ?)";

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

    // ===================== UPDATE =====================
    public boolean updateCart(CartDTO cart) {

        String sql = "UPDATE cart SET productId = ?, quality = ? WHERE cartId = ?";

        try (Connection conn = DbUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, cart.getProductId());
            ps.setInt(2, cart.getQuality());
            ps.setInt(3, cart.getCartId());

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    // ===================== DELETE =====================
    public boolean deleteCart(int cartId) {

        String sql = "DELETE FROM cart WHERE cartId = ?";

        try (Connection conn = DbUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, cartId);
            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    // ===================== GET BY ID =====================
    public CartDTO getById(int cartId) {

        String sql = "SELECT * FROM cart WHERE cartId = ?";

        try (Connection conn = DbUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, cartId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return mapResultSet(rs);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    // ===================== MAP RESULTSET =====================
    private CartDTO mapResultSet(ResultSet rs) throws Exception {

        int cartId = rs.getInt("cartId");
        String username = rs.getString("username");
        int productId = rs.getInt("productId");
        int quality = rs.getInt("quality");

        return new CartDTO(cartId, username, productId, quality);
    }
}