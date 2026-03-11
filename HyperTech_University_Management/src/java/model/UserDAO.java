package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import org.mindrot.jbcrypt.BCrypt;
import util.DbUtil;
import utils.DbUtils;

public class UserDAO {

    // ================= SEARCH =================
    public UserDTO searchByEmail(String email) {
        UserDTO user = null;
        String sql = "SELECT * FROM users WHERE email =?";
        System.out.println("EMAIL=" + email + "|");
        System.out.println(sql);
        try ( Connection con = DbUtil.getConnection();  PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                String Username = rs.getString("username");               
                String password = rs.getString("password");
                String phone = rs.getString("phone");
                String address = rs.getString("address");
                boolean status = rs.getBoolean("status");
                user = new UserDTO(email, Username, password, phone, address, status);
                }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return user;
    }

    public ArrayList<UserDTO> filterByColum(String column, String value) {
        ArrayList<UserDTO> result = new ArrayList<>();
        try {
            Connection conn = DbUtils.getConnection();
            String sql = "SELECT * FROM users WHERE status = 1 AND " + column + " LIKE ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, "%" + value + "%");
            System.out.println(ps.toString());
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String email = rs.getString("email");
                String Username = rs.getString("username");               
                String password = rs.getString("password");
                String phone = rs.getString("phone");
                String address = rs.getString("address");
                boolean status = rs.getBoolean("status");
                UserDTO u = new UserDTO(email, Username, password, phone, address, status);
                result.add(u);
                System.out.println(u);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public List<UserDTO> getAllUsers() {

        List<UserDTO> list = new ArrayList<>();
        String sql = "SELECT * FROM users";

        try ( Connection con = DbUtil.getConnection();  PreparedStatement ps = con.prepareStatement(sql);  ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {

                UserDTO user = new UserDTO(
                        rs.getString("email"),
                        rs.getString("username"),                        
                        rs.getString("password"),
                        rs.getString("phone"),
                        rs.getString("address"),
                        rs.getBoolean("status")
                );

                list.add(user);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    // ================= LOGIN =================
    public UserDTO login(String email, String Password) {
        UserDTO user = searchByEmail(email);

        if (user != null && Password.equals(user.getPassword())) {
            if (user.isStatus()) {
                return user;
            } else {
                return null;
            }
        }
        return null;
    }

    // ================= ADD (REGISTER) =================
    public boolean add(UserDTO u) {
        String sql = "INSERT INTO users (Username, email, password, phone, address, status) "
                + "VALUES (?, ?, ?, ?, ?, ?)";

        try ( Connection conn = DbUtil.getConnection();  PreparedStatement ps = conn.prepareStatement(sql)) {

            // Hash mật khẩu trước khi lưu
            String hashedPassword = BCrypt.hashpw(u.getPassword(), BCrypt.gensalt());

            ps.setString(1, u.getUsername());
            ps.setString(2, u.getEmail());
            ps.setString(3, hashedPassword);
            ps.setString(4, u.getPhone());
            ps.setString(5, u.getAddress());
            ps.setBoolean(6, true);
            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }
//===============================================
//                  cẬP NHẬT TRẠNG THÁI NG DÙNG
//===============================================
    public boolean updateUserStatus(String email, boolean status) {
        boolean check = false;
        String sql = "UPDATE users SET status = ? WHERE email = ?";
        try ( Connection con = DbUtil.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setBoolean(1, status);
            ps.setString(2, email);

            check = ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return check;
    }

    public boolean UpdateU(UserDTO u) {
        int result = 0;
        try {
            Connection conn = DbUtils.getConnection();
            String sql = "UPDATE users"
                    + "   SET username = ?"
                    + "      ,password = ?"
                    + "      ,phone = ?"
                    + "      ,address = ?"
                    + " WHERE email = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, u.getUsername());
            ps.setString(2, u.getPassword());
            ps.setString(3, u.getPhone());
            ps.setString(4, u.getAddress());
            ps.setString(5, u.getEmail());

            result = ps.executeUpdate();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return result > 0;
    }
//===============================================
//                  ĐẾM SỐ NG DÙNG
//===============================================
    public int countUsers() {
        int count = 0;
        String sql = "SELECT COUNT(*) FROM users";

        try ( Connection con = DbUtil.getConnection();
                PreparedStatement ps = con.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()) {

            if (rs.next()) {
                count = rs.getInt(1);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return count;
    }

}
