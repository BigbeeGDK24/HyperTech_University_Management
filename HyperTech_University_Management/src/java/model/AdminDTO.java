/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author truon
 */
public class AdminDTO {

    public AdminDTO() {
        
    }

    public AdminDTO(String Username, String Password) {
        this.Username = Username;
        this.Password = Password;
    }
    

    public String getUsername() {
        return Username;
    }

    public String getAdPass() {
        return adPass;
    }

    public void setAdminID(String adminID) {
        this.adminID = adminID;
    }

    public void setAdPass(String adPass) {
        this.adPass = adPass;
    }

}
