package controller;

import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import model.CartDAO;
import model.CartDTO;

public class CartController extends HttpServlet {

    // ================= CHECK LOGIN =================
    private boolean isUser(HttpServletRequest request) {
        return request.getSession().getAttribute("user") != null;
    }

    // ================= MAIN PROCESS =================
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        if (!isUser(request)) {
            response.sendRedirect("login.jsp");
            return;
        }

        String action = request.getParameter("action");
        if (action == null) action = "viewCart";

        switch (action) {

            case "viewCart":
                doViewCart(request, response);
                break;

            case "addToCart":
                doAdd(request, response);
                break;

            case "updateCart":
                doUpdate(request, response);
                break;

            case "deleteCartItem":
                doDelete(request, response);
                break;

            case "clearCart":
                doClear(request, response);
                break;

            default:
                doViewCart(request, response);
        }
    }

    // ================= VIEW CART =================
    private void doViewCart(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        String userId = (String) session.getAttribute("user");

        CartDAO dao = new CartDAO();
        ArrayList<CartDTO> list = dao.getByUserId(userId);

        request.setAttribute("cartList", list);

        request.getRequestDispatcher("cart.jsp").forward(request, response);
    }

    // ================= ADD TO CART =================
    private void doAdd(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        HttpSession session = request.getSession();
        String userId = (String) session.getAttribute("user");

        int productId = parseInt(request.getParameter("product_id"));
        int quantity = parseInt(request.getParameter("quantity"));

        if (quantity <= 0) quantity = 1;

        CartDAO dao = new CartDAO();

        CartDTO existing = dao.getItem(userId, productId);

        if (existing == null) {

            CartDTO cart = new CartDTO(userId, productId, quantity);
            dao.insert(cart);

        } else {

            int newQuantity = existing.getQuality() + quantity;
            dao.updateQuantity(userId, productId, newQuantity);

        }

        response.sendRedirect("CartController?action=viewCart");
    }

    // ================= UPDATE CART =================
    private void doUpdate(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        HttpSession session = request.getSession();
        String userId = (String) session.getAttribute("user");

        int productId = parseInt(request.getParameter("product_id"));
        int quantity = parseInt(request.getParameter("quantity"));

        CartDAO dao = new CartDAO();

        if (quantity <= 0) {
            dao.deleteItem(userId, productId);
        } else {
            dao.updateQuantity(userId, productId, quantity);
        }

        response.sendRedirect("CartController?action=viewCart");
    }

    // ================= DELETE ITEM =================
    protected void doDelete(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        HttpSession session = request.getSession();
        String userId = (String) session.getAttribute("user");

        int productId = parseInt(request.getParameter("product_id"));

        CartDAO dao = new CartDAO();
        dao.deleteItem(userId, productId);

        response.sendRedirect("CartController?action=viewCart");
    }

    // ================= CLEAR CART =================
    private void doClear(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        HttpSession session = request.getSession();
        String userId = (String) session.getAttribute("user");

        CartDAO dao = new CartDAO();
        dao.clearCart(userId);

        response.sendRedirect("CartController?action=viewCart");
    }

    // ================= PARSE INT =================
    private int parseInt(String value) {
        try { return Integer.parseInt(value); }
        catch (Exception e) { return 0; }
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