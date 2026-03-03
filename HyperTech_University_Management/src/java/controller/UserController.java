/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.UserDAO;
import model.UserDTO;
import org.apache.tomcat.jni.User;

/**
 *
 * @author tungi
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
    protected void doLogin(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        String url = "";
        HttpSession session = request.getSession();
        if (session.getAttribute("User") == null) {
            String txtUsername = request.getParameter("txtUsername");
            String txtPassword = request.getParameter("txtPassword");

            UserDAO udao = new UserDAO();
            UserDTO User = udao.login(txtUsername, txtPassword);
            System.out.println(User);
            if (User != null) {
                if (User.isStatus()) {
                    url = "welcome.jsp";
                    session.setAttribute("User", User);
                } else {
                    url = "welcome.jsp";
                }
            } else {
                url = "login.jsp";
                request.setAttribute("message", "Invalid Username or password!");
            }

        } else {
            url = "welcome.jsp";
        }
        // Chuyen trang
        RequestDispatcher rd = request.getRequestDispatcher(url);
        rd.forward(request, response);
    }

    protected void doLogout(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        HttpSession session = request.getSession();
        if (session.getAttribute("User") != null) {
            // huy bo toan bo noi dung session
            session.invalidate();
        }
        String url = "login.jsp";
        response.sendRedirect(url);
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        String keywords = request.getParameter("keywords");
        String id = request.getParameter("id");
        if (keywords == null) {
            keywords = "";
        }
        if (id == null) {
            id = "";
        }

        System.out.println(keywords);
        UserDAO udao = new UserDAO();
        // Xoa
        if (!id.isEmpty()) {
            boolean check = udao.updateUserStatus(id, false);
            if (check) {
                request.setAttribute("msg", "Deleted!");
            } else {
                request.setAttribute("msg", "Error, can not delete: " + id);
            }
        }

        // Tim kiem
        ArrayList<UserDTO> list = new ArrayList<>();
        if (keywords.trim().length() > 0) {
            list = udao.filterByColum("username", keywords);
        }
        request.setAttribute("list", list);
        request.setAttribute("keywords", keywords);
        String url = "search.jsp";
        RequestDispatcher rd = request.getRequestDispatcher(url);
        rd.forward(request, response);
    }

    protected void doSearch(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        String keywords = request.getParameter("keywords");
        if (keywords == null) {
            keywords = "";
        }

        System.out.println(keywords);
        UserDAO udao = new UserDAO();
        ArrayList<UserDTO> list = new ArrayList<>();
        if (keywords.trim().length() > 0) {
            list = udao.filterByColum("username", keywords);
        }
        request.setAttribute("list", list);
        request.setAttribute("keywords", keywords);
        String url = "search.jsp";
        RequestDispatcher rd = request.getRequestDispatcher(url);
        rd.forward(request, response);
    }

    protected void doUpdate(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String email = request.getParameter("email");
        UserDAO udao = new UserDAO();

        // Tìm kiếm thông tin cũ từ DB
        UserDTO u = udao.searchByEmail(email);

        if (u != null) {
            request.setAttribute("u", u);
            request.setAttribute("mode", "update");
            request.getRequestDispatcher("university-form.jsp").forward(request, response);
        } else {
            request.setAttribute("error", "Không tìm thấy User với ID này!");
            request.getRequestDispatcher("SearchUserController").forward(request, response);
        }
    }

    private UserDTO extractUserFromRequest(HttpServletRequest request) {
        String Username = request.getParameter("Username");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String phone = request.getParameter("phone");
        String address = request.getParameter("address");
        boolean status = true;

        return new UserDTO(Username, email, password, phone, address, status);
    }

    private String validateUser(UserDTO u, boolean isUpdate) {
        StringBuilder error = new StringBuilder();
        if (u.getUsername()== null || u.getUsername().trim().isEmpty()) {
            error.append("Chưa nhập username <br/>");
        }
        if (u.getEmail()== null || u.getEmail().trim().isEmpty()) {
            error.append("Chưa nhập Name <br/>");
        }
        if (u.getEmail()== null || u.getEmail().trim().isEmpty()) {
            error.append("Chưa nhập email <br/>");
        }
        

        // Nếu là thêm mới, cần check trùng ID (Update thì không cần check vì ID là readonly)
        if (!isUpdate) {
            UserDAO udao = new UserDAO();
            if (udao.searchByEmail(u.getUsername()) != null) {
                error.append("email đã tồn tại, vui lòng chọn email khác! <br/>");
            }
        }
        return error.toString();
    }

    protected void doAdd(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        UserDTO u = extractUserFromRequest(request);
        String error = validateUser(u, false);
        String msg = "";

        if (error.isEmpty()) {
            UserDAO udao = new UserDAO();
            if (udao.add(u)) {
                msg = "Đã thêm User thành công!";
            } else {
                error = "Lỗi hệ thống: Không thể thêm vào database!";
            }
        }

        request.setAttribute("u", u);
        request.setAttribute("msg", msg);
        request.setAttribute("error", error);
        response.sendRedirect("UserServlet");
    }

    protected void doSaveUpdate(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        UserDTO u = extractUserFromRequest(request);
        String error = validateUser(u, true);
        String msg = "";

        if (error.isEmpty()) {
            UserDAO udao = new UserDAO();
            if (udao.UpdateU(u)) {
                msg = "Đã cập nhật thành công!";
            } else {
                error = "Lỗi hệ thống: Không thể cập nhật!";
            }
        }

        request.setAttribute("u", u);
        request.setAttribute("mode", "update");
        request.setAttribute("msg", msg);
        request.setAttribute("error", error);
        request.getRequestDispatcher("university-form.jsp").forward(request, response);
    }

    protected void doGetAll(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        UserDAO udao = new UserDAO();
        ArrayList<UserDTO> list = new ArrayList<>(udao.getAllUsers());

        request.setAttribute("list", list);
// tùy lựa chọn của mình mà chỉnh trang
        request.getRequestDispatcher("search.jsp").forward(request, response);
    }

    // Hàm hỗ trợ parse số để tránh lập lại code try-catch
    private int parseOrDefault(String value, int defaultValue) {
        try {
            return Integer.parseInt(value);
        } catch (Exception e) {
            return defaultValue;
        }
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Thiết lập Encoding để tránh lỗi tiếng Việt
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        String action = request.getParameter("action");

        // Phòng trường hợp action bị null (người dùng truy cập trực tiếp URL)
        if (action == null || action.isEmpty()) {
            doSearch(request, response);
            return;
        }

        try {
            switch (action) {
                case "searchUser":
                    doSearch(request, response);
                    break;

                case "addUser":
                    doAdd(request, response);
                    break;

                case "deleteUser":
                    doDelete(request, response);
                    break;

                case "updateUser":
                    // Khi người dùng nhấn nút "Sửa" ở danh sách -> Đổ dữ liệu ra Form
                    doUpdate(request, response);
                    break;

                case "saveUpdateUser":
                    // Khi người dùng nhấn nút "Update" trong Form -> Lưu vào DB
                    doSaveUpdate(request, response);
                    break;
                
                case "getAllUsers":
    doGetAll(request, response);
    break;
                default:
                    // Nếu action không khớp, mặc định quay về trang search hoặc báo lỗi
                    request.setAttribute("error", "Hành động không hợp lệ: " + action);
                    doSearch(request, response);
                    break;
            }
        } catch (Exception e) {
            log("Error at UserController: " + e.toString());
            request.setAttribute("error", "Hệ thống đang gặp sự cố, vui lòng thử lại sau.");
            request.getRequestDispatcher("error.jsp").forward(request, response);
        }
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