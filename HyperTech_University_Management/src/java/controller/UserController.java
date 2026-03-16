package controller;

import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import model.UserDAO;
import model.UserDTO;
import org.mindrot.jbcrypt.BCrypt;

public class UserController extends HttpServlet {

    // ================= LOGIN =================
    protected void doLogin(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        String email = request.getParameter("txtUsername");
        String password = request.getParameter("txtPassword");

        UserDAO dao = new UserDAO();
        UserDTO user = dao.login(email, password);
        HttpSession session = request.getSession();

        if (user != null) {

            session.setAttribute("user", user);
            response.sendRedirect("header.jsp");

        } else {

            request.setAttribute("message", "Sai email hoặc mật khẩu");
            request.setAttribute("showLoginModal", true);

            request.getRequestDispatcher("index.jsp").forward(request, response);
        }
    }

    // ================= LOGOUT =================
    protected void doLogout(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();

        if (session != null) {
            session.invalidate();
        }

        response.sendRedirect("login.jsp");
    }

    // ================= DELETE USER =================
    protected void doDelete(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String keywords = request.getParameter("keywords");
        String email = request.getParameter("email");

        if (keywords == null) {
            keywords = "";
        }

        UserDAO dao = new UserDAO();

        if (email != null && !email.isEmpty()) {

            boolean check = dao.updateUserStatus(email, false);

            if (check) {
                request.setAttribute("msg", "Deleted!");
            } else {
                request.setAttribute("msg", "Cannot delete user: " + email);
            }
        }

        ArrayList<UserDTO> list = dao.filterByColumn("username", keywords);

        request.setAttribute("list", list);
        request.setAttribute("keywords", keywords);

        request.getRequestDispatcher("search.jsp").forward(request, response);
    }

    // ================= SEARCH USER =================
    protected void doSearch(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String keywords = request.getParameter("keywords");

        if (keywords == null) {
            keywords = "";
        }

        UserDAO dao = new UserDAO();

        ArrayList<UserDTO> list = dao.filterByColumn("username", keywords);

        request.setAttribute("list", list);
        request.setAttribute("keywords", keywords);

        request.getRequestDispatcher("search.jsp").forward(request, response);
    }

    // ================= LOAD USER TO UPDATE =================
    protected void doUpdate(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String email = request.getParameter("email");

        UserDAO dao = new UserDAO();

        UserDTO u = dao.searchByEmail(email);

        if (u != null) {

            request.setAttribute("u", u);
            request.setAttribute("mode", "update");

            request.getRequestDispatcher("university-form.jsp").forward(request, response);

        } else {

            request.setAttribute("error", "Không tìm thấy user!");
            request.getRequestDispatcher("search.jsp").forward(request, response);
        }
    }

    // ================= EXTRACT USER =================
    private UserDTO extractUserFromRequest(HttpServletRequest request) {

        String username = request.getParameter("username");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String phone = request.getParameter("phone");
        String address = request.getParameter("address");

        boolean status = true;

        return new UserDTO(email, username, password, phone, address, status);
    }

    private boolean checkpass(HttpServletRequest request) {
        String password = request.getParameter("password");
        String passcom = request.getParameter("password");

        return password.equalsIgnoreCase(passcom);
    }

    // ================= VALIDATE =================
    private String validateUser(UserDTO u, boolean isUpdate) {

        StringBuilder error = new StringBuilder();

        if (u.getUsername() == null || u.getUsername().trim().isEmpty()) {
            error.append("Chưa nhập username <br>");
        }

        if (u.getEmail() == null || u.getEmail().trim().isEmpty()) {
            error.append("Chưa nhập email <br>");
        }

        if (!isUpdate) {

            UserDAO dao = new UserDAO();

            if (dao.searchByEmail(u.getEmail()) != null) {
                error.append("Email đã tồn tại <br>");
            }
        }

        return error.toString();
    }

    // ================= ADD USER =================
    protected void doAdd(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        UserDTO u = extractUserFromRequest(request);
        String error = validateUser(u, false);
        String msg = "";
        
        if (checkpass(request)) {

            System.out.println(u);
            if (error.isEmpty()) {

                UserDAO dao = new UserDAO();

                if (dao.add(u)) {
                    msg = "Thêm user thành công!";
                } else {
                    error = "Không thể thêm user!";
                }
            }

        } else {
            error = "Password is incorrect!";
        }

        System.out.println(msg);
        System.out.println(u.getUsername());
        System.out.println(u.getEmail());
        System.out.println(u.getPassword());

        System.out.println();

        request.setAttribute("u", u);
        request.setAttribute("msg", msg);
        request.setAttribute("error", error);

        request.getRequestDispatcher("index.jsp").forward(request, response);
    }

    // ================= SAVE UPDATE =================
    protected void doSaveUpdate(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        UserDTO u = extractUserFromRequest(request);

        String error = validateUser(u, true);
        String msg = "";

        if (error.isEmpty()) {

            UserDAO dao = new UserDAO();

            if (dao.updateUser(u)) {
                msg = "Cập nhật thành công!";
            } else {
                error = "Không thể cập nhật!";
            }
        }

        request.setAttribute("u", u);
        request.setAttribute("mode", "update");
        request.setAttribute("msg", msg);
        request.setAttribute("error", error);

        request.getRequestDispatcher("university-form.jsp").forward(request, response);
    }

    // ================= GET ALL USERS =================
    protected void doGetAll(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        UserDAO dao = new UserDAO();

        ArrayList<UserDTO> list = new ArrayList<>(dao.getAllUsers());

        request.setAttribute("list", list);

        request.getRequestDispatcher("search.jsp").forward(request, response);
    }

    // ================= MAIN CONTROLLER =================
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        String action = request.getParameter("action");

        if (action == null) {
            doSearch(request, response);
            return;
        }

        switch (action) {

            case "login":
                doLogin(request, response);
                break;

            case "logout":
                doLogout(request, response);
                break;

            case "addUser":
                doAdd(request, response);
                break;

            case "deleteUser":
                doDelete(request, response);
                break;

            case "updateUser":
                doUpdate(request, response);
                break;

            case "saveUpdateUser":
                doSaveUpdate(request, response);
                break;

            case "getAllUsers":
                doGetAll(request, response);
                break;

            default:
                doSearch(request, response);
                break;
        }
    }

    // ================= GET =================
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        processRequest(request, response);
    }

    // ================= POST =================
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "User Controller";
    }
}
