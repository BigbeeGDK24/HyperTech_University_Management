package controller;

import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import model.OrderDAO;
import model.OrderDTO;
import model.OrderItemDAO;

public class OrderController extends HttpServlet {

    // ================= CHECK LOGIN =================
    private boolean isAdmin(HttpServletRequest request) {
        return request.getSession().getAttribute("admin") != null;
    }

    private boolean isUser(HttpServletRequest request) {
        return request.getSession().getAttribute("user") != null;
    }

    private boolean isLoggedIn(HttpServletRequest request) {
        return isAdmin(request) || isUser(request);
    }

    // ================= MAIN PROCESS =================
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        // ===== CHƯA LOGIN =====
        if (!isLoggedIn(request)) {
            response.sendRedirect("index.jsp");
            return;
        }

        String action = request.getParameter("action");
        if (action == null || action.isEmpty()) {
            action = "searchOrder";
        }

        try {
            switch (action) {

                // ===== USER + ADMIN =====
                case "searchOrder":
                    handleSearch(request, response);
                    break;

                // ===== ADMIN ONLY =====
                case "deleteOrder":
                    if (isAdmin(request)) {
                        handleDelete(request, response);
                    } else {
                        deny(response);
                    }
                    break;

                case "updateOrder":
                    if (isAdmin(request)) {
                        handleUpdate(request, response);
                    } else {
                        deny(response);
                    }
                    break;

                case "saveUpdateOrder":
                    if (isAdmin(request)) {
                        handleSaveUpdate(request, response);
                    } else {
                        deny(response);
                    }
                    break;

                case "statisticsOrder":
                    if (isAdmin(request)) {
                        handleStatistics(request, response);
                    } else {
                        deny(response);
                    }
                    break;

                default:
                    handleSearch(request, response);
                    break;
            }

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "System error!");
            request.getRequestDispatcher("error.jsp").forward(request, response);
        }
    }

    // ================= SEARCH =================
    private void handleSearch(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        OrderDAO dao = new OrderDAO();
        ArrayList<OrderDTO> list = dao.getAll();

        request.setAttribute("list", list);
        request.getRequestDispatcher("order-list.jsp").forward(request, response);
    }

    // ================= DELETE =================
    private void handleDelete(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        int id = parseInt(request.getParameter("id"));
        OrderDAO dao = new OrderDAO();

        if (dao.delete(id)) {
            response.sendRedirect("MainController?action=searchOrder");
        } else {
            response.sendRedirect("MainController?action=searchOrder&error=deleteFail");
        }
    }

    // ================= LOAD UPDATE =================
    private void handleUpdate(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int id = parseInt(request.getParameter("id"));
        OrderDAO dao = new OrderDAO();

        ArrayList<OrderDTO> list = dao.searchByColumn("id", String.valueOf(id));

        if (!list.isEmpty()) {
            request.setAttribute("order", list.get(0));
            request.setAttribute("mode", "update");
            request.getRequestDispatcher("order-form.jsp").forward(request, response);
        } else {
            response.sendRedirect("MainController?action=searchOrder");
        }
    }

    // ================= SAVE UPDATE =================
    private void handleSaveUpdate(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        int id = parseInt(request.getParameter("id"));
        String userID = request.getParameter("userID");
        double totalPrice = parseDouble(request.getParameter("totalPrice"));
        String status = request.getParameter("status");

        OrderDTO order = new OrderDTO(id, userID, totalPrice, status, null);
        OrderDAO dao = new OrderDAO();

        if (dao.update(order)) {
            response.sendRedirect("MainController?action=searchOrder");
        } else {
            response.sendRedirect("MainController?action=searchOrder&error=updateFail");
        }
    }

    // ================= STATISTICS =================
    private void handleStatistics(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        OrderDAO orderDAO = new OrderDAO();
        OrderItemDAO itemDAO = new OrderItemDAO();

        double totalRevenue = orderDAO.getTotalRevenue();
        int totalOrders = orderDAO.getTotalOrders();
        int totalSold = itemDAO.getTotalSoldQuantity();

        request.setAttribute("totalRevenue", totalRevenue);
        request.setAttribute("totalOrders", totalOrders);
        request.setAttribute("totalSold", totalSold);

        request.getRequestDispatcher("statistics-order.jsp").forward(request, response);
    }

    // ================= SAFE PARSE =================
    private int parseInt(String value) {
        try {
            return Integer.parseInt(value);
        } catch (Exception e) {
            return 0;
        }
    }

    private double parseDouble(String value) {
        try {
            return Double.parseDouble(value);
        } catch (Exception e) {
            return 0;
        }
    }

    // ================= DENY ACCESS =================
    private void deny(HttpServletResponse response) throws IOException {
        response.sendError(HttpServletResponse.SC_FORBIDDEN, "Admin only!");
    }

    // ================= GET / POST =================
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
}