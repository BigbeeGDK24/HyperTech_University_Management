package controller;

import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import model.ProductDAO;
import model.ProductDTO;

public class ProductController extends HttpServlet {

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
            response.sendRedirect("login.jsp");
            return;
        }

        String action = request.getParameter("action");
        if (action == null) action = "searchProduct";

        switch (action) {

            // ===== USER + ADMIN =====
            case "searchProduct":
                doSearch(request, response);
                break;

            case "viewProduct":
                doView(request, response);
                break;

            case "filterProductByCategory":
                filterByCategory(request, response);
                break;

            // ===== ADMIN =====
            case "showAddProductForm":
                request.getRequestDispatcher("product-add.jsp").forward(request, response);
                break;

            case "showUpdateProductForm":
                showUpdateForm(request, response);
                break;

            case "addProduct":
                if (isAdmin(request)) doAdd(request, response);
                else deny(response);
                break;

            case "updateProduct":
                if (isAdmin(request)) doUpdate(request, response);
                else deny(response);
                break;

            case "deleteProduct":
                if (isAdmin(request)) doSoftDelete(request, response);
                else deny(response);
                break;

            case "productStatistic":
                if (isAdmin(request)) doStatistic(request, response);
                else deny(response);
                break;

            default:
                doSearch(request, response);
        }
    }

    // ================= SEARCH =================
    private void doSearch(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String keywords = request.getParameter("keywords");
        if (keywords == null) keywords = "";

        ProductDAO dao = new ProductDAO();
        ArrayList<ProductDTO> list;

        if (!keywords.trim().isEmpty()) {
            list = dao.searchByName(keywords);
        } else {
            list = dao.getAll();
        }

        request.setAttribute("list", list);
        request.setAttribute("keywords", keywords);

        request.getRequestDispatcher("product-search.jsp").forward(request, response);
    }

    // ================= VIEW DETAIL =================
    private void doView(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int id = parseInt(request.getParameter("id"));

        ProductDAO dao = new ProductDAO();
        ProductDTO product = dao.getById(id);

        if (product == null) {
            response.sendRedirect("ProductController?action=searchProduct");
            return;
        }

        request.setAttribute("product", product);

        request.getRequestDispatcher("product-detail.jsp").forward(request, response);
    }

    // ================= FILTER CATEGORY =================
    private void filterByCategory(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int categoryId = parseInt(request.getParameter("category_id"));

        ProductDAO dao = new ProductDAO();
        ArrayList<ProductDTO> list = dao.getByCategory(categoryId);

        request.setAttribute("list", list);

        request.getRequestDispatcher("product-search.jsp").forward(request, response);
    }

    // ================= SHOW UPDATE FORM =================
    private void showUpdateForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int id = parseInt(request.getParameter("id"));

        ProductDAO dao = new ProductDAO();
        ProductDTO product = dao.getById(id);

        request.setAttribute("product", product);

        request.getRequestDispatcher("product-update.jsp").forward(request, response);
    }

    // ================= ADD =================
    private void doAdd(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        ProductDTO p = extractProduct(request);
        ProductDAO dao = new ProductDAO();

        if (dao.add(p)) {
            response.sendRedirect("ProductController?action=searchProduct");
        } else {
            response.sendRedirect("ProductController?action=searchProduct&error=addFail");
        }
    }

    // ================= UPDATE =================
    private void doUpdate(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        ProductDTO p = extractProduct(request);
        ProductDAO dao = new ProductDAO();

        if (!dao.exists(p.getId())) {
            response.sendRedirect("ProductController?action=searchProduct&error=notFound");
            return;
        }

        if (dao.update(p)) {
            response.sendRedirect("ProductController?action=searchProduct");
        } else {
            response.sendRedirect("ProductController?action=searchProduct&error=updateFail");
        }
    }

    // ================= DELETE =================
    private void doSoftDelete(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        int id = parseInt(request.getParameter("id"));
        ProductDAO dao = new ProductDAO();

        if (!dao.exists(id)) {
            response.sendRedirect("ProductController?action=searchProduct");
            return;
        }

        if (dao.delete(id)) {
            response.sendRedirect("ProductController?action=searchProduct");
        } else {
            response.sendRedirect("ProductController?action=searchProduct&error=deleteFail");
        }
    }

    // ================= STATISTIC =================
    private void doStatistic(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        ProductDAO dao = new ProductDAO();
        int total = dao.countProducts();

        request.setAttribute("totalProduct", total);

        request.getRequestDispatcher("admin-dashboard.jsp").forward(request, response);
    }

    // ================= EXTRACT PRODUCT =================
    private ProductDTO extractProduct(HttpServletRequest request) {

        int id = parseInt(request.getParameter("id"));
        int category_id = parseInt(request.getParameter("category_id"));
        String name = request.getParameter("name");
        double price = parseDouble(request.getParameter("price"));
        int stock = parseInt(request.getParameter("stock"));
        String description = request.getParameter("description");
        String image = request.getParameter("image");

        return new ProductDTO(id, category_id, name, price, stock, description, image, true);
    }

    // ================= PARSE INT =================
    private int parseInt(String value) {
        try { return Integer.parseInt(value); }
        catch (Exception e) { return 0; }
    }

    // ================= PARSE DOUBLE =================
    private double parseDouble(String value) {
        try { return Double.parseDouble(value); }
        catch (Exception e) { return 0; }
    }

    // ================= ACCESS DENY =================
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