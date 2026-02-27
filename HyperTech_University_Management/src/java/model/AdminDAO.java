/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import org.mindrot.jbcrypt.BCrypt;
import utils.DbUtils;

/**
 *
 * @author truon
 */
public class AdminDAO {

    public AdminDAO() {

    }

    public AdminDTO searchByID(String adminID) {
        AdminDTO ad = null;
        try {
            Connection con = DbUtils.getConnection();
            String sql = "SELECT * FROM admin WHERE adminID=?";
            System.out.println(sql);

            PreparedStatement letter = con.prepareStatement(sql);
            letter.setString(1, adminID);
            ResultSet rs = letter.executeQuery();

            while (rs.next()) {
                String username = rs.getString("adminID");
                String password = rs.getString("adPass");
                ad = new AdminDTO(username , password);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
return ad;
    }
    
    public AdminDTO login(String adminID , String adPass){
        AdminDTO ad = searchByID(adminID);
        if(ad != null && BCrypt.checkpw(adPass, ad.getAdPass())){
            return ad;
        }
        return null;
    }
}
