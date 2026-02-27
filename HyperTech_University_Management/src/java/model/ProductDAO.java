package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import util.DbUtil;

public class ProductDAO {

    // ================= GET ALL =================
    public ArrayList<ProductDTO> getAll() {
        ArrayList<ProductDTO> list = new ArrayList<>();
        String sql = "SELECT * FROM products";

        try (Connection con = DbUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                list.add(extractProduct(rs));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    // ================= GET BY ID =================
    public ProductDTO getById(int id) {
        String sql = "SELECT * FROM products WHERE id = ?";

        try (Connection con = DbUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return extractProduct(rs);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    // ================= SEARCH BY NAME =================
    public ArrayList<ProductDTO> searchByName(String name) {
        ArrayList<ProductDTO> list = new ArrayList<>();
        String sql = "SELECT * FROM products WHERE name LIKE ?";

        try (Connection con = DbUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, "%" + name + "%");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                list.add(extractProduct(rs));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    // ================= ADD =================
    public boolean add(ProductDTO p) {
        String sql = "INSERT INTO products (id, category_id, name, price, stock, description, image) "
                   + "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection con = DbUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, p.getId());
            ps.setInt(2, p.getCategory_id());
            ps.setString(3, p.getName());
            ps.setFloat(4, p.getPrice());
            ps.setInt(5, p.getStock());
            ps.setString(6, p.getDescription());
            ps.setString(7, p.getImage());

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    // ================= UPDATE =================
    public boolean update(ProductDTO p) {
        String sql = "UPDATE products SET "
                   + "category_id = ?, "
                   + "name = ?, "
                   + "price = ?, "
                   + "stock = ?, "
                   + "description = ?, "
                   + "image = ? "
                   + "WHERE id = ?";

        try (Connection con = DbUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, p.getCategory_id());
            ps.setString(2, p.getName());
            ps.setFloat(3, p.getPrice());
            ps.setInt(4, p.getStock());
            ps.setString(5, p.getDescription());
            ps.setString(6, p.getImage());
            ps.setInt(7, p.getId());

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    // ================= DELETE =================
    public boolean delete(int id) {
        String sql = "DELETE FROM products WHERE id = ?";

        try (Connection con = DbUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    // ================= EXTRACT =================
    private ProductDTO extractProduct(ResultSet rs) throws Exception {
        int id = rs.getInt("id");
        int category_id = rs.getInt("category_id");
        String name = rs.getString("name");
        float price = rs.getFloat("price");
        int stock = rs.getInt("stock");
        String description = rs.getString("description");
        String image = rs.getString("image");

        return new ProductDTO(id, category_id, name, price, stock, description, image);
    }
}