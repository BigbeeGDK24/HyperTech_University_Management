package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import utils.DbUtil;

public class CategoryDAO {

    public CategoryDAO() {
    }

    // ===============================
    // SEARCH BY COLUMN (EQUAL)
    // ===============================
    public ArrayList<CategoryDTO> searchByColum(String column, String value) {
        ArrayList<CategoryDTO> result = new ArrayList<>();
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
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    // ===============================
    // FILTER BY COLUMN (LIKE)
    // ===============================
    public ArrayList<CategoryDTO> filterByColum(String column, String value) {
        ArrayList<CategoryDTO> result = new ArrayList<>();
        try {
            Connection conn = DbUtil.getConnection();
            String sql = "SELECT * FROM categories WHERE " + column + " LIKE ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, "%" + value + "%");
            System.out.println(ps.toString());

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String description = rs.getString("description");
                java.sql.Timestamp createdAt = rs.getTimestamp("created_at");

                CategoryDTO c = new CategoryDTO(id, name, description, createdAt);
                result.add(c);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    // ===============================
    // WRAPPER METHODS (GIỐNG BÀI MẪU)
    // ===============================
    public ArrayList<CategoryDTO> searchByID(String id) {
        return searchByColum("id", id);
    }

    public ArrayList<CategoryDTO> searchByName(String name) {
        return searchByColum("name", name);
    }

    public ArrayList<CategoryDTO> filterByName(String name) {
        return filterByColum("name", name);
    }

    // ===============================
    // INSERT CATEGORY
    // ===============================
    public boolean insert(CategoryDTO c) {
        try {
            Connection conn = DbUtil.getConnection();
            String sql = "INSERT INTO categories(name, description) VALUES(?,?)";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, c.getName());
            ps.setString(2, c.getDescription());

            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
        return false;
    }// insert

    // ===============================
    // UPDATE CATEGORY
    // ===============================
    public boolean update(CategoryDTO c) {
        try {
            Connection conn = DbUtil.getConnection();
            String sql = "UPDATE categories SET name=?, description=? WHERE id=?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, c.getName());
            ps.setString(2, c.getDescription());
            ps.setInt(3, c.getId());

            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
        return false;
    }

    // ===============================
    // DELETE CATEGORY (HARD DELETE)
    // ===============================
    public boolean delete(String id) {
        try {
            Connection conn = DbUtil.getConnection();
            String sql = "DELETE FROM categories WHERE id=?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, id);

            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
        return false;
    }
}
