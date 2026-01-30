/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import util.DbUtil;

/**
 *
 * @author hasot
 */
public class PaymentDAO {

    public class CartItemDAO {

        public ArrayList<PaymentDTO> searchByColum(String column, String value) {
            ArrayList<PaymentDTO> result = new ArrayList<>();
            try {
                Connection conn = DbUtil.getConnection();
                String sql = "SELECT * FROM payment WHERE " + column + "=?";
                PreparedStatement ps = conn.prepareStatement(sql);
                ps.setString(1, value);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    String id = rs.getString("id");
                    String orderId = rs.getString("orderId");
                    String userId = rs.getString("userId");
                    String paymentMethod = rs.getString("paymentMethod");
                    String amount = rs.getString("amount");
                    String status = rs.getString("status");
                    String transactionCode = rs.getString("transactionCode");
                    LocalDateTime paidAt = rs.getTimestamp("paid_at").toLocalDateTime();
                    PaymentDTO u = new PaymentDTO(id, orderId, userId, paymentMethod, amount, status, transactionCode, DateTimeFormatter.ISO_DATE, DateTimeFormatter.ISO_DATE);
                    result.add(u);
                }
            } catch (Exception e) {
            }
            return result;
        }

        public ArrayList<PaymentDTO> filterByColum(String column, String value) {
            ArrayList<PaymentDTO> result = new ArrayList<>();
            try {
                Connection conn = DbUtil.getConnection();
                String sql = "SELECT * FROM payment WHERE " + column + " LIKE ?";
                PreparedStatement ps = conn.prepareStatement(sql);
                ps.setString(1, "%" + value + "%");
                System.out.println(ps.toString());
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    String id = rs.getString("id");
                    String orderId = rs.getString("orderId");
                    String userId = rs.getString("userId");
                    String paymentMethod = rs.getString("paymentMethod");
                    String amount = rs.getString("amount");
                    String status = rs.getString("status");
                    String transactionCode = rs.getString("transactionCode");
                    LocalDateTime paidAt = rs.getTimestamp("paid_at").toLocalDateTime();
                    PaymentDTO u = new PaymentDTO(id, orderId, userId, paymentMethod, amount, status, transactionCode, DateTimeFormatter.ISO_DATE, DateTimeFormatter.ISO_DATE);
                    result.add(u);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return result;
            
            
        }
    }
}
