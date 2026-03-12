package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import util.DbUtil;

public class ProductDAO {

    // =====================================================
    // 1. LẤY TẤT CẢ SẢN PHẨM ĐANG HOẠT ĐỘNG
    // =====================================================
    public ArrayList<ProductDTO> getAll() {
        ArrayList<ProductDTO> list = new ArrayList<>();
        String sql = "SELECT * FROM products WHERE status = 1";

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

    // =====================================================
    // 2. LẤY THEO ID
    // =====================================================
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

    // =====================================================
    // 3. SEARCH THEO TÊN (CHỈ LẤY SẢN PHẨM ACTIVE)
    // =====================================================
    public ArrayList<ProductDTO> searchByName(String name) {
        ArrayList<ProductDTO> list = new ArrayList<>();
        String sql = "SELECT * FROM products WHERE name LIKE ? AND status = 1";

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

    // =====================================================
    // 4. THÊM SẢN PHẨM
    // =====================================================
    public boolean add(ProductDTO p) {
        String sql = "INSERT INTO products "
                + "(category_id, name, price, stock, description, image, status) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection con = DbUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, p.getCategory_id());
            ps.setString(2, p.getName());
            ps.setFloat(3, p.getPrice());
            ps.setInt(4, p.getStock());
            ps.setString(5, p.getDescription());
            ps.setString(6, p.getImage());
            ps.setBoolean(7, true); // mặc định active

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    // =====================================================
    // 5. UPDATE
    // =====================================================
    public boolean update(ProductDTO p) {
        String sql = "UPDATE products SET "
                + "category_id=?, name=?, price=?, stock=?, "
                + "description=?, image=?, status=? "
                + "WHERE id=?";

        try (Connection con = DbUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, p.getCategory_id());
            ps.setString(2, p.getName());
            ps.setFloat(3, p.getPrice());
            ps.setInt(4, p.getStock());
            ps.setString(5, p.getDescription());
            ps.setString(6, p.getImage());
            ps.setBoolean(7, p.getStatus());
            ps.setInt(8, p.getId());

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    // =====================================================
    // 6. DELETE MỀM (SOFT DELETE)
    // =====================================================
    public boolean delete(int id) {
        String sql = "UPDATE products SET status = 0 WHERE id = ?";

        try (Connection con = DbUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    // =====================================================
    // 7. ĐẾM SỐ SẢN PHẨM ACTIVE
    // =====================================================
    public int countProducts() {
        String sql = "SELECT COUNT(*) FROM products WHERE status = 1";

        try (Connection con = DbUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            if (rs.next()) {
                return rs.getInt(1);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0;
    }

    // =====================================================
    // HÀM HỖ TRỢ
    // =====================================================
    private ProductDTO extractProduct(ResultSet rs) throws Exception {
        return new ProductDTO(
                rs.getInt("id"),
                rs.getInt("category_id"),
                rs.getString("name"),
                rs.getFloat("price"),
                rs.getInt("stock"),
                rs.getString("description"),
                rs.getString("image"),
                rs.getBoolean("status")
        );
    }
    
    public ArrayList<ProductDTO> getByCategory(int category_id){

    ArrayList<ProductDTO> list = new ArrayList<>();

    try {
        Connection con = DbUtil.getConnection();
        String sql = "SELECT * FROM Product WHERE category_id = ?";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setInt(1, category_id);

        ResultSet rs = ps.executeQuery();

        while(rs.next()){

            ProductDTO p = new ProductDTO(
                rs.getInt("id"),
                rs.getInt("category_id"),
                rs.getString("name"),
                rs.getFloat("price"),
                rs.getInt("stock"),
                rs.getString("description"),
                rs.getString("image"),
                rs.getBoolean("status")
            );

            list.add(p);
        }

    } catch(Exception e){
        e.printStackTrace();
    }

    return list;
}
}

