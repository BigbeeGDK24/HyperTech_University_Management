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

            // duyệt từng dòng dữ liệu
            while (rs.next()) {
                list.add(extractProduct(rs));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    // =====================================================
    // 2. LẤY SẢN PHẨM THEO ID
    // =====================================================
    public ProductDTO getById(int id) {

        String sql = "SELECT * FROM products WHERE id = ?";

        try (Connection con = DbUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {
                    return extractProduct(rs);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    // =====================================================
    // 3. TÌM SẢN PHẨM THEO TÊN
    // =====================================================
    public ArrayList<ProductDTO> searchByName(String name) {

        ArrayList<ProductDTO> list = new ArrayList<>();

        String sql = "SELECT * FROM products WHERE name LIKE ? AND status = 1";

        try (Connection con = DbUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, "%" + name + "%");

            try (ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {
                    list.add(extractProduct(rs));
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    // =====================================================
    // 4. LẤY SẢN PHẨM THEO CATEGORY
    // =====================================================
    public ArrayList<ProductDTO> getByCategory(int categoryId) {

        ArrayList<ProductDTO> list = new ArrayList<>();

        String sql = "SELECT * FROM products WHERE category_id = ? AND status = 1";

        try (Connection con = DbUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, categoryId);

            try (ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {
                    list.add(extractProduct(rs));
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    // =====================================================
    // 5. THÊM SẢN PHẨM
    // =====================================================
    public boolean add(ProductDTO p) {

        String sql = "INSERT INTO products "
                + "(category_id, name, price, stock, description, image, status) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection con = DbUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, p.getCategory_id());
            ps.setString(2, p.getName());
            ps.setDouble(3, p.getPrice());
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
    // 6. UPDATE THÔNG TIN SẢN PHẨM
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
            ps.setDouble(3, p.getPrice());
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
    // 7. CẬP NHẬT STOCK (DÙNG CHO ORDER)
    // =====================================================
    public boolean updateStock(int productId, int newStock) {

        String sql = "UPDATE products SET stock = ? WHERE id = ?";

        try (Connection con = DbUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, newStock);
            ps.setInt(2, productId);

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    // =====================================================
    // 8. DELETE MỀM (SOFT DELETE)
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
    // 9. KIỂM TRA SẢN PHẨM TỒN TẠI
    // =====================================================
    public boolean exists(int id) {

        String sql = "SELECT 1 FROM products WHERE id = ?";

        try (Connection con = DbUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    // =====================================================
    // 10. ĐẾM SỐ SẢN PHẨM ACTIVE
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
    // HÀM HỖ TRỢ: CHUYỂN RESULTSET → DTO
    // =====================================================
    private ProductDTO extractProduct(ResultSet rs) throws Exception {

        return new ProductDTO(
                rs.getInt("id"),
                rs.getInt("category_id"),
                rs.getString("name"),
                rs.getDouble("price"),
                rs.getInt("stock"),
                rs.getString("description"),
                rs.getString("image"),
                rs.getBoolean("status")
        );
    }
}