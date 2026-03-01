package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import util.DbUtil;

public class ComplaintDAO {

    // ===============================
    // GET ALL
    // ===============================
   public ArrayList<ComplaintDTO> getAll() {
    ArrayList<ComplaintDTO> list = new ArrayList<>();
    String sql = "SELECT * FROM complaints";

    try (Connection conn = DbUtil.getConnection();
         PreparedStatement ps = conn.prepareStatement(sql);
         ResultSet rs = ps.executeQuery()) {

        while (rs.next()) {
            list.add(new ComplaintDTO(
                    rs.getInt("id"),
                    rs.getString("user_id"),
                    rs.getInt("order_id"),
                    rs.getInt("product_id"),
                    rs.getString("title"),
                    rs.getString("content"),
                    rs.getString("status")
            ));
        }

    } catch (Exception e) {
        e.printStackTrace();
    }

    return list;
}

    // ===============================
    // SEARCH BY ID
    // ===============================
    public ComplaintDTO searchById(int id) {
        try {
            Connection conn = DbUtil.getConnection();
            String sql = "SELECT * FROM complaints WHERE id = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return new ComplaintDTO(
                        rs.getInt("id"),
                        rs.getString("user_id"),
                        rs.getInt("order_id"),
                        rs.getInt("product_id"),
                        rs.getString("title"),
                        rs.getString("content"),
                        rs.getString("status")
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // ===============================
    // INSERT
    // ===============================
    public boolean insert(ComplaintDTO c) {
        try {
            Connection conn = DbUtil.getConnection();
            String sql = "INSERT INTO complaints (user_id, order_id, product_id, title, content, status) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setString(1, c.getUserId());
            ps.setInt(2, c.getOrderId());
            ps.setInt(3, c.getProductId());
            ps.setString(4, c.getTitle());
            ps.setString(5, c.getContent());
            ps.setString(6, c.getStatus()); // hoặc truyền "pending"

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // ===============================
    // UPDATE (Sửa toàn bộ)
    // ===============================
    public boolean update(ComplaintDTO c) {
        try {
            Connection conn = DbUtil.getConnection();
            String sql = "UPDATE complaints SET user_id=?, order_id=?, product_id=?, title=?, content=?, status=? WHERE id=?";
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setString(1, c.getUserId());
            ps.setInt(2, c.getOrderId());
            ps.setInt(3, c.getProductId());
            ps.setString(4, c.getTitle());
            ps.setString(5, c.getContent());
            ps.setString(6, c.getStatus());
            ps.setInt(7, c.getId());

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // ===============================
    // UPDATE STATUS (Admin xử lý)
    // ===============================
    public boolean updateStatus(int id, String status) {
        try {
            Connection conn = DbUtil.getConnection();
            String sql = "UPDATE complaints SET status=? WHERE id=?";
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setString(1, status);
            ps.setInt(2, id);

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // ===============================
    // DELETE
    // ===============================
    public boolean delete(int id) {
        try {
            Connection conn = DbUtil.getConnection();
            String sql = "DELETE FROM complaints WHERE id=?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}