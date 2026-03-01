package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import org.mindrot.jbcrypt.BCrypt;
import util.DbUtil;

public class UserDAO {

    // ================= SEARCH =================
    public UserDTO searchByUsername(String Username) {
        UserDTO user = null;
        String sql = "SELECT * FROM users WHERE Username=?";

        try (Connection con = DbUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, Username);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                String name = rs.getString("name");
                String email = rs.getString("email");
                String password = rs.getString("password");
                String phone = rs.getString("phone");
                String address = rs.getString("address");

                user = new UserDTO(Username, name, email, password, phone, address);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return user;
    }

    // ================= LOGIN =================
    public UserDTO login(String Username, String Password) {
        UserDTO user = searchByUsername(Username);

        if (user != null && BCrypt.checkpw(Password, user.getPassword())) {
            return user;
        }
        return null;
    }

    // ================= ADD (REGISTER) =================
    public boolean add(UserDTO u) {
        String sql = "INSERT INTO users (Username, name, email, password, phone, address) "
                   + "VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = DbUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            // Hash mật khẩu trước khi lưu
            String hashedPassword = BCrypt.hashpw(u.getPassword(), BCrypt.gensalt());

            ps.setString(1, u.getUsername());
            ps.setString(2, u.getName());
            ps.setString(3, u.getEmail());
            ps.setString(4, hashedPassword);
            ps.setString(5, u.getPhone());
            ps.setString(6, u.getAddress());

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }
}