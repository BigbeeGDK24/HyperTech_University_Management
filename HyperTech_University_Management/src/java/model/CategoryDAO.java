package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import util.DbUtil;

public class CategoryDAO {

    // ===============================
    // GET ALL CATEGORIES
    // ===============================
    public ArrayList<CategoryDTO> getAll() {
        ArrayList<CategoryDTO> list = new ArrayList<>();
        String sql = "SELECT * FROM categories";

        try ( Connection conn = DbUtil.getConnection();  PreparedStatement ps = conn.prepareStatement(sql);  ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                list.add(extractCategory(rs));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    // ===============================
    // GET CATEGORY BY ID
    // ===============================
    public CategoryDTO getById(int id) {
        String sql = "SELECT * FROM categories WHERE id = ?";

        try ( Connection conn = DbUtil.getConnection();  PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return extractCategory(rs);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    // ===============================
    // SEARCH BY NAME (LIKE)
    // ===============================
    public ArrayList<CategoryDTO> searchByName(String name) {
        ArrayList<CategoryDTO> list = new ArrayList<>();
        String sql = "SELECT * FROM categories WHERE name LIKE ?";

        try ( Connection conn = DbUtil.getConnection();  PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, "%" + name + "%");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                list.add(extractCategory(rs));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    // ===============================
    // INSERT CATEGORY
    // ===============================
    public boolean insert(CategoryDTO c) {
        String sql = "INSERT INTO categories (name, description) VALUES (?, ?)";

        try ( Connection conn = DbUtil.getConnection();  PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, c.getName());
            ps.setString(2, c.getDescription());

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    // ===============================
    // UPDATE CATEGORY
    // ===============================
    public boolean update(CategoryDTO c) {
        String sql = "UPDATE categories SET name = ?, description = ? WHERE id = ?";

        try ( Connection conn = DbUtil.getConnection();  PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, c.getName());
            ps.setString(2, c.getDescription());
            ps.setInt(3, c.getId());

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    // ===============================
    // DELETE CATEGORY
    // ===============================
    public boolean delete(int id) {
        String sql = "DELETE FROM categories WHERE id = ?";

        try ( Connection conn = DbUtil.getConnection();  PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    // ===============================
    // EXTRACT CATEGORY (PRIVATE)
    // ===============================
    private CategoryDTO extractCategory(ResultSet rs) throws Exception {
        int id = rs.getInt("id");
        String name = rs.getString("name");
        String description = rs.getString("description");
        java.sql.Timestamp createdAt = rs.getTimestamp("created_at");

        return new CategoryDTO(id, name, description, createdAt);
    }
}
