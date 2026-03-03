package controller;

import java.io.IOException;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.AdminDAO;
import model.AdminDTO;
import model.UserDAO;
import model.UserDTO;

public class AdminController extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        String url = "login.jsp";
        HttpSession session = request.getSession();
        String action = request.getParameter("action");

        try {

            /* ===================== ADMIN LOGIN ===================== */
            if ("login".equals(action)) {

                String username = request.getParameter("txtUsername");
                String password = request.getParameter("txtPassword");

                AdminDAO adao = new AdminDAO();
                AdminDTO admin = adao.adLogin(username, password);

                if (admin != null) {
                    session.setAttribute("admin", admin);

                    // load danh sách user khi vào dashboard
                    UserDAO udao = new UserDAO();
                    List<UserDTO> list = udao.getAllUsers();
                    request.setAttribute("listUser", list);

                    url = "adminDashboard.jsp";
                } else {
                    request.setAttribute("error", "Sai tài khoản hoặc mật khẩu Admin!");
                }
            }
            
            /* ===================== ADMIN LOGOUT ===================== */
            else if ("logout".equals(action)) {

                session.removeAttribute("admin");
                response.sendRedirect("login.jsp");
                return;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        RequestDispatcher rd = request.getRequestDispatcher(url);
        rd.forward(request, response);
    }

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
    }
}