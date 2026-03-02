package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
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
                boolean status = rs.getBoolean("status");
                        
                user = new UserDTO(Username, name, email, password, phone, address, status);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return user;
    }
public List<UserDTO> getAllUsers() {

    List<UserDTO> list = new ArrayList<>();
    String sql = "SELECT * FROM users";

    try (Connection con = DbUtil.getConnection();
         PreparedStatement ps = con.prepareStatement(sql);
         ResultSet rs = ps.executeQuery()) {

        while (rs.next()) {

            UserDTO user = new UserDTO(
                    rs.getString("Username"),
                    rs.getString("name"),
                    rs.getString("email"),
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
    public UserDTO login(String Username, String Password) {
        UserDTO user = searchByUsername(Username);

        if (user != null && BCrypt.checkpw(Password, user.getPassword())) {
            if (user.isStatus() == true) {
                return user;
            } else {
                return null;
            }
        }
        return null;
    }

    // ================= ADD (REGISTER) =================
    public boolean add(UserDTO u) {
        String sql = "INSERT INTO users (Username, name, email, password, phone, address) "
                   + "VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = DbUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
//private String Username;
//    private String name;
//    private String email;
//    private String password;
//    private String phone;
//    private String address;
//    private boolean status;

            // Hash mật khẩu trước khi lưu
            String hashedPassword = BCrypt.hashpw(u.getPassword(), BCrypt.gensalt());

            ps.setString(1, u.getUsername());
            ps.setString(2, u.getName());
            ps.setString(3, u.getEmail());
            ps.setString(4, hashedPassword);
            ps.setString(5, u.getPhone());
            ps.setString(6, u.getAddress());
            ps.setBoolean(7, true);
            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }
    public boolean updateUserStatus(String username, int status) {
    boolean check = false;
    String sql = "UPDATE users SET status = ? WHERE Username = ?";
    try (Connection con = DbUtil.getConnection();
         PreparedStatement ps = con.prepareStatement(sql)) {

        ps.setInt(1, status);
        ps.setString(2, username);

        check = ps.executeUpdate() > 0;
    } catch (Exception e) {
        e.printStackTrace();
    }
    return check;
}

}