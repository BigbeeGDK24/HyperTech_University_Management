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
            String sql = "SELECT * FROM admin WHERE admin=?";
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

    public AdminDTO adLogin(String Username, String Password) {
        AdminDTO admin = searchByAdminName(Username);
        if (admin != null && BCrypt.checkpw(Password, admin.getPassword())) {
            return admin;
        }
        return null;
    }

    public boolean addAd(AdminDTO ad) {
        int result = 0;
        try {

            Connection conn = DbUtil.getConnection();
            String sql = "INSERT INTO users (Username, password) VALUES (?,?)";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, ad.getUsername());
            ps.setString(2, ad.getPassword());
            result = ps.executeUpdate();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return result > 0;
    }

    public boolean UpdateAd(AdminDTO ad) {
        int result = 0;
        try {

            Connection conn = DbUtil.getConnection();
            String sql = "UPDATE admin"
                    + "   SET adPass = ?"
                    + " WHERE admin = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, ad.getPassword());
            ps.setString(2, ad.getUsername());
            result = ps.executeUpdate();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return result > 0;
    }
}
