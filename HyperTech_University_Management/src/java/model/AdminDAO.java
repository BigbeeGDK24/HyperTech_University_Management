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
public class AdminDAO {
   /*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */


    public AdminDTO searchByAdminName(String Username) {
         AdminDTO admin = null;
        try {
            Connection con = DbUtil.getConnection();
            String sql = "SELECT * FROM users WHERE Username=?";
            System.out.println(sql);

            PreparedStatement letter = con.prepareStatement(sql);
            letter.setString(1, Username);
            ResultSet rs = letter.executeQuery();

            while (rs.next()) {
                String username = rs.getString("Username");
                String password = rs.getString("password");
                admin = new AdminDTO(username, password);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return admin;
    }

    public AdminDTO login(String Username, String Password) {
        AdminDTO admin = searchByAdminName(Username);
        if (admin != null && BCrypt.checkpw(Password, admin.getPassword())) {
            return admin;
        }
        return null;
    }
    
    
}


