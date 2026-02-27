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

    private String adminID;
    private String adPass;

    public AdminDTO(String adminID, String adPass) {
        this.adminID = adminID;
        this.adPass = adPass;
    }



    public String getAdminID() {
        return adminID;
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
