package controller;

import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.*;

import model.CartDTO;
import model.ProductDTO;
import model.UserDTO;
import model.OrderDAO;
import model.OrderDTO;
import model.OrderItemDAO;
import model.OrderItemDTO;

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

        if (!isLoggedIn(request)) {
            response.sendRedirect("index.jsp");
            return;
        }

        String action = request.getParameter("action");

        if (action == null) {
            action = "searchOrder";
        }

        try {

            switch (action) {

                // ===== USER CHECKOUT =====
                case "createOrder":
                    handleCreateOrder(request, response);
                    break;

                // ===== VIEW ORDER =====
                case "searchOrder":
                    handleSearch(request, response);
                    break;

                // ===== ADMIN =====
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

    // ================= CREATE ORDER FROM CART =================
    private void handleCreateOrder(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        HttpSession session = request.getSession();

        CartDTO cart = (CartDTO) session.getAttribute("CART");

        if (cart == null || cart.getCart().isEmpty()) {
            response.sendRedirect("cart.jsp");
            return;
        }

        UserDTO user = (UserDTO) session.getAttribute("user");

        double total = 0;

        for (ProductDTO p : cart.getCart().values()) {
            total += p.getPrice() * p.getQuantity();
        }

        OrderDAO orderDAO = new OrderDAO();

        OrderDTO order = new OrderDTO(
                0,
                user.getEmail(),
                total,
                "pending",
                null
        );

        int orderId = orderDAO.insert(order);

        if (orderId > 0) {

            OrderItemDAO itemDAO = new OrderItemDAO();

            for (ProductDTO p : cart.getCart().values()) {

                OrderItemDTO item = new OrderItemDTO(
                        0,
                        orderId,
                        p.getId(),
                        p.getPrice(),
                        p.getQuantity()
                );

                itemDAO.insert(item);

            }

            session.removeAttribute("CART");

            response.sendRedirect("payment.jsp");

        } else {

            response.sendRedirect("cart.jsp?error=orderFail");

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

        OrderDTO order = dao.getById(id);

        if (order != null) {

            request.setAttribute("order", order);
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
        String email = request.getParameter("email");
        double totalPrice = parseDouble(request.getParameter("totalPrice"));
        String status = request.getParameter("status");

        OrderDTO order = new OrderDTO(id, email, totalPrice, status, null);

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
