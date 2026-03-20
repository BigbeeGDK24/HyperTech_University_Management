package controller;

import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.*;

import model.*;

public class PaymentController extends HttpServlet {

    private boolean isAdmin(HttpServletRequest request) {
        return request.getSession().getAttribute("admin") != null;
    }

    private boolean isUser(HttpServletRequest request) {
        return request.getSession().getAttribute("user") != null;
    }

    private boolean isLoggedIn(HttpServletRequest request) {
        return isAdmin(request) || isUser(request);
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        if (!isLoggedIn(request)) {
            response.sendRedirect("index.jsp");
            return;
        }

        String action = request.getParameter("action");
        if (action == null) {
            action = "searchPayment";
        }

        switch (action) {

            case "checkout":
                request.getRequestDispatcher("payment.jsp").forward(request, response);
                break;

            case "searchPayment":
                doSearch(request, response);
                break;

            case "viewPayment":
                doView(request, response);
                break;

            case "viewUserPayments":
                doViewUserPayments(request, response);
                break;

            case "addPayment":
                if (isUser(request)) {
                    doAdd(request, response);
                } else {
                    deny(response);
                }
                break;

            default:
                doSearch(request, response);
        }
    }

    // ================= SEARCH =================
    private void doSearch(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        ArrayList<PaymentDTO> list = new PaymentDAO().getAll();
        request.setAttribute("list", list);
        request.getRequestDispatcher("payment-search.jsp").forward(request, response);
    }

    // ================= VIEW =================
    private void doView(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int id = parseInt(request.getParameter("id"));
        PaymentDTO payment = new PaymentDAO().getById(id);

        if (payment == null) {
            response.sendRedirect("PaymentController?action=searchPayment");
            return;
        }

        request.setAttribute("payment", payment);
        request.getRequestDispatcher("payment-detail.jsp").forward(request, response);
    }

    // ================= USER PAYMENT =================
    private void doViewUserPayments(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        UserDTO user = (UserDTO) request.getSession().getAttribute("user");

        ArrayList<PaymentDTO> list = new PaymentDAO().getByUserId(user.getEmail());

        request.setAttribute("list", list);
        request.getRequestDispatcher("payment-user.jsp").forward(request, response);
    }

    // ================= ADD PAYMENT =================
    private void doAdd(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        HttpSession session = request.getSession();
        CartDTO cart = (CartDTO) session.getAttribute("CART");
        UserDTO user = (UserDTO) session.getAttribute("user");

        if (user == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        if (cart == null || cart.getCart() == null || cart.getCart().isEmpty()) {
            response.sendRedirect("cart.jsp?error=empty");
            return;
        }

        float total = 0;
        for (ProductDTO p : cart.getCart().values()) {
            total += p.getFinalPrice() * p.getQuantity();
        }

        String method = request.getParameter("payment_method");
        if (method == null) {
            method = "COD";
        }

        OrderDAO orderDAO = new OrderDAO();
        OrderDTO order = new OrderDTO();

        order.setEmail(user.getEmail());
        order.setTotalPrice(total);
        order.setStatus("pending");

        int orderId = orderDAO.insert(order);

        if (orderId <= 0) {
            response.sendRedirect("payment.jsp?error=orderFail");
            return;
        }

        OrderItemDAO itemDAO = new OrderItemDAO();

        for (ProductDTO p : cart.getCart().values()) {
            OrderItemDTO item = new OrderItemDTO();
            item.setOrderID(orderId);
            item.setProductID(p.getId());
            item.setPrice(p.getFinalPrice());
            item.setQuantity(p.getQuantity());
            itemDAO.insert(item);
        }

        PaymentDTO payment = new PaymentDTO();

        payment.setOrderId(orderId);
        payment.setUserId(user.getEmail());
        payment.setPaymentMethod(method);
        payment.setAmount(total);

        if ("COD".equals(method)) {
            payment.setStatus("pending");
            payment.setPaid_at(null);
        } else {
            payment.setStatus("paid");
            payment.setPaid_at(new java.sql.Date(System.currentTimeMillis()));
        }

        payment.setTransactionCode("TXN" + System.currentTimeMillis());

        boolean success = new PaymentDAO().insert(payment);

        if (success) {

            session.setAttribute("ORDER_ID", orderId);
            session.setAttribute("PAYMENT_METHOD", method);
            session.setAttribute("LAST_CART", cart);

            // 🔥 GỬI MAIL Ở ĐÂY
            String content = "Đơn hàng #" + orderId
                    + "\nTổng tiền: " + total + " VND"
                    + "\nPhương thức: " + method
                    + "\nCảm ơn bạn đã mua hàng!";

            util.MailUtil.sendEmail(user.getEmail(), "Xác nhận đơn hàng", content);

            session.removeAttribute("CART");

            response.sendRedirect("success.jsp");

        } else {
            response.sendRedirect("payment.jsp?error=fail");
        }
    }

    // ================= HELPER =================
    private int parseInt(String val) {
        try {
            return Integer.parseInt(val);
        } catch (Exception e) {
            return 0;
        }
    }

    private void deny(HttpServletResponse response) throws IOException {
        response.sendError(HttpServletResponse.SC_FORBIDDEN, "Access Denied!");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        processRequest(req, res);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        processRequest(req, res);
    }
}
