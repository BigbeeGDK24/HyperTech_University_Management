/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.CartDAO;
import model.UserDAO;
import model.UserDTO;

/**
 *
 * @author hasot
 */
public class UserController extends HttpServlet {

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
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        String url = "login.jsp"; // NOTE: luôn gán default để tránh url rỗng
        String error = "";
        String msg = "";
        
        HttpSession session = request.getSession();
        String action = request.getParameter("action"); 
        
        try {

            /* ===================== LOGIN ===================== */
            if ("login".equals(action)) {

                String username = request.getParameter("txtUsername");
                String password = request.getParameter("txtPassword");

                UserDAO udao = new UserDAO();
                UserDTO user = udao.login(username, password);

                if (user != null) {
                    session.setAttribute("user", user);
                    // NOTE: trước đó bạn quên set session
                    url = "welcome.jsp";
                } else {
                    request.setAttribute("error", "Sai tai khoan hoac mat khau");
                    url = "login.jsp";
                }
            } /* ===================== REGISTER ===================== */ 
            else if ("register".equals(action)) {

                String username = request.getParameter("Username");
                String name = request.getParameter("name");
                String email = request.getParameter("email");
                String password = request.getParameter("password");
                String phone = request.getParameter("phone");
                String address = request.getParameter("address");

                // NOTE: tránh NullPointer khi trim()
                if (username == null || username.trim().isEmpty()) {
                    error += "Chua nhap Username <br/>";
                } else {
                    username = username.trim();
                }

                if (name == null || name.trim().isEmpty()) {
                    error += "Chua nhap Name <br/>";
                } else {
                    name = name.trim();
                }

                UserDAO udao = new UserDAO();

                // Kiểm tra username tồn tại
                if (username != null && udao.searchByUsername(username) != null) {
                    error += "Username da ton tai <br/>";
                }

                UserDTO u = new UserDTO(username, name, email, password, phone, address);

                if (error.isEmpty()) {
                    if (udao.add(u)) {
                        CartDAO cartDAO = new CartDAO();
                        cartDAO.createCart(username); 
                        msg = "Tao user thanh cong, moi ban dang nhap";
                        url = "login.jsp";
                    } else {
                        error += "Khong the tao user!";
                        url = "register.jsp";
                    }
                } else {
                    url = "register.jsp";
                }

                request.setAttribute("error", error);
                request.setAttribute("msg", msg);
                request.setAttribute("u", u);
            }// logout 
            else if ("logout".equals(action)) {
                session.invalidate();   // hủy session
                response.sendRedirect("login.jsp");
                return; // QUAN TRỌNG: dừng luôn, không chạy tiếp
            }
        } catch (Exception e) {
            e.printStackTrace();

        }

        RequestDispatcher rd = request.getRequestDispatcher(url);
        rd.forward(request, response);
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
