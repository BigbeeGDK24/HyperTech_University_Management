/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author ASUS
 */
public class MainController extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        String action = request.getParameter("action");
        String url = "index.jsp";


        if (action == null) {
            url = "index.jsp";
        } else if (action.equals("login") || action.equals("Adminlogout")) {
            url = "AdminController";
        } else if (action.equals("Userlogout")) {
            url = "UserController";  
        } else if (action.equals("addUser")) {
            url = "UserController";
            //======== PRODUCT  ===================
            } else if (action.equals("searchLaptop")
                || action.equals("searchVGA")
                || action.equals("searchCase")
                || action.equals("searchRAM")
                || action.equals("searchBanPhim")
                || action.equals("searchProduct")
                || action.equals("searchManHinh")) {

            url = "ProductController";
            
            // ================= CART =================
        } else if (action.equals("viewCart")
                || action.equals("AddCart")
                || action.equals("UpdateCart")
                || action.equals("RemoveCart")
                || action.equals("clearCart")) {

            url = "CartController";

            // ================= ORDER =================
        } else if (action.equals("createOrder")
                || action.equals("searchOrder")
                || action.equals("deleteOrder")
                || action.equals("updateOrder")
                || action.equals("saveUpdateOrder")
                || action.equals("statisticsOrder")) {

            url = "OrderController";

            // ================= PAYMENT =================
        } else if (action.equals("checkout")
                || action.equals("searchPayment")
                || action.equals("viewPayment")
                || action.equals("viewUserPayments")
                || action.equals("addPayment")
                || action.equals("updatePayment")
                || action.equals("deletePayment")
                || action.equals("paymentStatistic")) {

            url = "PaymentController";

        }
        request.getRequestDispatcher(url).forward(request, response);
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
