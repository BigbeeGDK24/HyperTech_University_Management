package controller;

import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import model.PaymentDAO;
import model.PaymentDTO;

public class PaymentController extends HttpServlet {

    // ================= CHECK ROLE =================
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

        if (!isLoggedIn(request)) {
            response.sendRedirect("index.jsp");
            return;
        }

        String action = request.getParameter("action");
        if (action == null) action = "searchPayment";

        switch (action) {

            // ===== USER + ADMIN =====
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
                if (isUser(request)) doAdd(request, response);
                else deny(response);
                break;

            // ===== ADMIN =====
            case "showUpdatePaymentForm":
                showUpdateForm(request, response);
                break;

            case "updatePayment":
                if (isAdmin(request)) doUpdate(request, response);
                else deny(response);
                break;

            case "updatePaymentStatus":
                if (isAdmin(request)) doUpdateStatus(request, response);
                else deny(response);
                break;

            case "deletePayment":
                if (isAdmin(request)) doDelete(request, response);
                else deny(response);
                break;

            case "paymentStatistic":
                if (isAdmin(request)) doStatistic(request, response);
                else deny(response);
                break;

            default:
                doSearch(request, response);
        }
    }

    // ================= SEARCH (ADMIN VIEW ALL) =================
    private void doSearch(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        PaymentDAO dao = new PaymentDAO();
        ArrayList<PaymentDTO> list = dao.getAll();

        request.setAttribute("list", list);

        request.getRequestDispatcher("payment-search.jsp").forward(request, response);
    }

    // ================= VIEW DETAIL =================
    private void doView(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int id = parseInt(request.getParameter("id"));

        PaymentDAO dao = new PaymentDAO();
        PaymentDTO payment = dao.getById(id);

        if (payment == null) {
            response.sendRedirect("PaymentController?action=searchPayment");
            return;
        }

        request.setAttribute("payment", payment);

        request.getRequestDispatcher("payment-detail.jsp").forward(request, response);
    }

    // ================= USER VIEW THEIR PAYMENTS =================
    private void doViewUserPayments(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String userId = (String) request.getSession().getAttribute("user");

        PaymentDAO dao = new PaymentDAO();
        ArrayList<PaymentDTO> list = dao.getByUserId(userId);

        request.setAttribute("list", list);

        request.getRequestDispatcher("payment-user.jsp").forward(request, response);
    }

    // ================= ADD PAYMENT =================
    private void doAdd(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        PaymentDTO p = extractPayment(request);

        PaymentDAO dao = new PaymentDAO();

        if (dao.insert(p)) {
            response.sendRedirect("PaymentController?action=viewUserPayments");
        } else {
            response.sendRedirect("PaymentController?action=viewUserPayments&error=addFail");
        }
    }

    // ================= SHOW UPDATE FORM =================
    private void showUpdateForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int id = parseInt(request.getParameter("id"));

        PaymentDAO dao = new PaymentDAO();
        PaymentDTO payment = dao.getById(id);

        request.setAttribute("payment", payment);

        request.getRequestDispatcher("payment-update.jsp").forward(request, response);
    }

    // ================= UPDATE =================
    private void doUpdate(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        PaymentDTO p = extractPayment(request);
        PaymentDAO dao = new PaymentDAO();

        if (dao.update(p)) {
            response.sendRedirect("PaymentController?action=searchPayment");
        } else {
            response.sendRedirect("PaymentController?action=searchPayment&error=updateFail");
        }
    }

    // ================= UPDATE STATUS =================
    private void doUpdateStatus(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        int id = parseInt(request.getParameter("id"));
        String status = request.getParameter("status");

        PaymentDAO dao = new PaymentDAO();

        if (dao.updateStatus(id, status)) {
            response.sendRedirect("PaymentController?action=searchPayment");
        } else {
            response.sendRedirect("PaymentController?action=searchPayment&error=statusFail");
        }
    }

    // ================= DELETE =================
    protected void doDelete(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        int id = parseInt(request.getParameter("id"));

        PaymentDAO dao = new PaymentDAO();

        if (dao.delete(id)) {
            response.sendRedirect("PaymentController?action=searchPayment");
        } else {
            response.sendRedirect("PaymentController?action=searchPayment&error=deleteFail");
        }
    }

    // ================= STATISTIC =================
    private void doStatistic(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        PaymentDAO dao = new PaymentDAO();

        int totalPayments = dao.countPayments();
        float totalAmount = dao.getTotalPaidAmount();

        request.setAttribute("totalPayments", totalPayments);
        request.setAttribute("totalRevenue", totalAmount);

        request.getRequestDispatcher("admin-dashboard.jsp").forward(request, response);
    }

    // ================= EXTRACT PAYMENT =================
    private PaymentDTO extractPayment(HttpServletRequest request) {

        int id = parseInt(request.getParameter("id"));
        int orderId = parseInt(request.getParameter("order_id"));
        String userId = request.getParameter("user_id");
        String method = request.getParameter("payment_method");
        float amount = parseFloat(request.getParameter("amount"));
        String status = request.getParameter("status");
        String code = request.getParameter("transaction_code");

        Date paidAt = Date.valueOf(request.getParameter("paid_at"));

        return new PaymentDTO(id, orderId, userId, method, amount, status, code, paidAt);
    }

    // ================= PARSE =================
    private int parseInt(String value) {
        try { return Integer.parseInt(value); }
        catch (Exception e) { return 0; }
    }

    private float parseFloat(String value) {
        try { return Float.parseFloat(value); }
        catch (Exception e) { return 0; }
    }

    // ================= ACCESS DENY =================
    private void deny(HttpServletResponse response) throws IOException {
        response.sendError(HttpServletResponse.SC_FORBIDDEN, "Access Denied!");
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