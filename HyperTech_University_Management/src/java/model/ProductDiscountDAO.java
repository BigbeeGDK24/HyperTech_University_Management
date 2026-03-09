package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import utils.DbUtils;

public class ProductDiscountDAO {

    public ProductDiscountDAO() {
    }

    // 1. GET ALL
    public ArrayList<ProductDiscountDTO> getAll() {

        ArrayList<ProductDiscountDTO> list = new ArrayList<>();

        try {

            Connection conn = DbUtils.getConnection();

            String sql = "SELECT * FROM product_discounts";

            PreparedStatement ps = conn.prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                int productID = rs.getInt("product_id");
                int discountID = rs.getInt("discount_id");

                ProductDiscountDTO pd = new ProductDiscountDTO(productID, discountID);

                list.add(pd);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    // 2. SEARCH BY PRODUCT ID
    public ArrayList<ProductDiscountDTO> searchByProductID(int productID) {

        ArrayList<ProductDiscountDTO> list = new ArrayList<>();

        try {

            Connection conn = DbUtils.getConnection();

            String sql = "SELECT * FROM product_discounts WHERE product_id = ?";

            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setInt(1, productID);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                int discountID = rs.getInt("discount_id");

                ProductDiscountDTO pd = new ProductDiscountDTO(productID, discountID);

                list.add(pd);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    // 3. SEARCH BY DISCOUNT ID
    public ArrayList<ProductDiscountDTO> searchByDiscountID(int discountID) {

        ArrayList<ProductDiscountDTO> list = new ArrayList<>();

        try {

            Connection conn = DbUtils.getConnection();

            String sql = "SELECT * FROM product_discounts WHERE discount_id = ?";

            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setInt(1, discountID);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                int productID = rs.getInt("product_id");

                ProductDiscountDTO pd = new ProductDiscountDTO(productID, discountID);

                list.add(pd);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    // 4. ADD
    public boolean add(ProductDiscountDTO pd) {

        int result = 0;

        try {

            Connection conn = DbUtils.getConnection();

            String sql = "INSERT INTO product_discounts(product_id, discount_id) VALUES (?, ?)";

            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setInt(1, pd.getProductID());
            ps.setInt(2, pd.getDiscountID());

            result = ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return result > 0;
    }

    // 5. DELETE (xóa mapping)
    public boolean delete(int productID, int discountID) {

        int result = 0;

        try {

            Connection conn = DbUtils.getConnection();

            String sql = "DELETE FROM product_discounts WHERE product_id = ? AND discount_id = ?";

            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setInt(1, productID);
            ps.setInt(2, discountID);

            result = ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return result > 0;
    }

}