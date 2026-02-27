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
import model.AdminDAO;
import model.AdminDTO;

/**
 *
 * @author
 */
public class AdminController extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        // Mặc định trả về trang đăng nhập 
        String url = "login.jsp";

        HttpSession session = request.getSession();
        String action = request.getParameter("action");

        try {
            /* ===================== ADMIN LOGIN ===================== */
            if ("login".equals(action)) {
                // Nhận tham số từ form đăng nhập của admin
                String username = request.getParameter("txtUsername");
                String password = request.getParameter("txtPassword");

                AdminDAO adao = new AdminDAO();
                AdminDTO admin = adao.login(username, password);

                if (admin != null) {
                    // Lưu session với tên là "admin" để phân biệt với "user"
                    session.setAttribute("admin", admin);
                    // Chuyển hướng đến trang quản trị
                    url = "adminDashboard.jsp";
                } else {
                    request.setAttribute("error", "Sai tài khoản hoặc mật khẩu Admin!");
                    url = "login.jsp";
                }
            } /* ===================== ADMIN LOGOUT ===================== */ else if ("logout".equals(action)) {
                // Xóa session của admin thay vì invalidate toàn bộ 
                // (tránh ảnh hưởng nếu admin đang test tài khoản user ở tab khác)
                session.removeAttribute("admin");
                // Hoặc nếu muốn hủy toàn bộ: session.invalidate();

                response.sendRedirect("login.jsp");
                return; // Dừng thực thi để không bị lỗi forward
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        // Forward request đến trang tương ứng
        RequestDispatcher rd = request.getRequestDispatcher(url);
        rd.forward(request, response);
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Admin Controller";
    }// </editor-fold>

}
