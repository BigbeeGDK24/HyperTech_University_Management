/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import org.mindrot.jbcrypt.BCrypt;
import util.DbUtil;

/**
 *
 * @author truon
 */
public class UserDAO {

    public UserDAO() {

    }

    public UserDTO searchByUsername(String Username) {
        UserDTO user = null;
        try {
            Connection con = DbUtil.getConnection();
            String sql = "SELECT * FROM users WHERE Username=?";
            System.out.println(sql);

            PreparedStatement letter = con.prepareStatement(sql);
            letter.setString(1, Username);
            ResultSet rs = letter.executeQuery();

            while (rs.next()) {
                String username = rs.getString("Username");
                String name = rs.getString("name");
                String email = rs.getString("email");
                String password = rs.getString("password");
                String phone = rs.getString("phone");
                String address = rs.getString("address");
                String created_at = rs.getString("created_at");
                user = new UserDTO(username, name, email, password, phone, address, created_at);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return user;
    }

    public UserDTO login(String Username, String Password) {
        UserDTO user = searchByUsername(Username);
        if (user != null && BCrypt.checkpw(Password, user.getPassword())) {
            return user;
        }
        return null;
    }
    
    
}
