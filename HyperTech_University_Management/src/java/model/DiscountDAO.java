package model;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import util.DbUtil;

public class DiscountDAO {

    // ================= SEARCH EXACT =================
    public ArrayList<CategoryDTO> searchByColumn(String column, String value) {
    ArrayList<CategoryDTO> result = new ArrayList<>();

    // Whitelist tránh SQL Injection
    if (!column.equals("id") &&
        !column.equals("name") &&
        !column.equals("description") &&
        !column.equals("created_at")) {
        return result;
    }

    try {
        Connection conn = DbUtil.getConnection();
        String sql = "SELECT * FROM categories WHERE " + column + " = ?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, value);

        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            int id = rs.getInt("id");
            String name = rs.getString("name");
            String description = rs.getString("description");
            java.sql.Timestamp createdAt = rs.getTimestamp("created_at");

            CategoryDTO c = new CategoryDTO(id, name, description, createdAt);
            result.add(c);
        }

        rs.close();
        ps.close();
        conn.close();

    } catch (Exception e) {
        e.printStackTrace();
    }

    return result;
}


// ===============================
// FILTER BY COLUMN (LIKE)
// ===============================
public ArrayList<CategoryDTO> filterByColumn(String column, String value) {
    ArrayList<CategoryDTO> result = new ArrayList<>();

    if (!column.equals("id") &&
        !column.equals("name") &&
        !column.equals("description") &&
        !column.equals("created_at")) {
        return result;
    }

    try {
        Connection conn = DbUtil.getConnection();
        String sql = "SELECT * FROM categories WHERE " + column + " LIKE ?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, "%" + value + "%");

        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            int id = rs.getInt("id");
            String name = rs.getString("name");
            String description = rs.getString("description");
            java.sql.Timestamp createdAt = rs.getTimestamp("created_at");

            CategoryDTO c = new CategoryDTO(id, name, description, createdAt);
            result.add(c);
        }

        rs.close();
        ps.close();
        conn.close();

    } catch (Exception e) {
        e.printStackTrace();
    }

    return result;
}
    // ================= INSERT =================
    public boolean insert(DiscountDTO discount) {

        String sql = "INSERT INTO discounts (name, discount_percent, start_date, end_date) VALUES (?, ?, ?, ?)";

        try (Connection conn = DbUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, discount.getName());
            ps.setInt(2, discount.getDiscount_percent());
            ps.setDate(3, discount.getStart_date());
            ps.setDate(4, discount.getEnd_date());

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    // ================= UPDATE =================
    public boolean update(DiscountDTO discount) {

        String sql = "UPDATE discounts SET name = ?, discount_percent = ?, start_date = ?, end_date = ? WHERE id = ?";

        try (Connection conn = DbUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, discount.getName());
            ps.setInt(2, discount.getDiscount_percent());
            ps.setDate(3, discount.getStart_date());
            ps.setDate(4, discount.getEnd_date());
            ps.setInt(5, discount.getId());

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    // ================= DELETE =================
    public boolean delete(int id) {

        String sql = "DELETE FROM discounts WHERE id = ?";

        try (Connection conn = DbUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }
}