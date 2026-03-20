package controller;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import model.LaptopDAO;
import model.LaptopDTO;
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

        String action = request.getParameter("action");
        if (action == null) {
            action = "searchProduct";
        }

        switch (action) {

            // ================= USER + ADMIN =================
            case "searchProduct":
                doSearch(request, response);
                break;

            case "viewProduct":
                doView(request, response);
                break;

            // ================= ADMIN ONLY =================
            case "addProduct":
                if (isAdmin(request)) {
                    doAdd(request, response);
                } else {
                    deny(response);
                }
                break;

            case "updateProduct":
                if (isAdmin(request)) {
                    doUpdate(request, response);
                } else {
                    deny(response);
                }
                break;

            case "deleteProduct":
                if (isAdmin(request)) {
                    doSoftDelete(request, response);
                } else {
                    deny(response);
                }
                break;

            case "ProductStatistic":
                if (isAdmin(request)) {
                    doStatistic(request, response);
                } else {
                    deny(response);
                }
                break;

            case "list":
                doShowLap(request, response);
                break;
            case "keyboard":
                doShowKeyBoard(request, response);
                break;
            case "monitor":
                doShowMonitor(request, response);
                break;
            default:
                doSearch(request, response);
        }
    }

    // ================= SEARCH =================
    private void doSearch(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String keywords = request.getParameter("keywords");
        String category = request.getParameter("category");

        if (keywords == null) {
            keywords = "";
        }

        ProductDAO dao = new ProductDAO();

        ArrayList<ProductDTO> list;
        ArrayList<ProductDTO> dealMouse = dao.getDealMouse();
        ArrayList<ProductDTO> gamingMouse = dao.getGamingMouse();
        ArrayList<ProductDTO> officeMouse = dao.getOfficeMouse();

        if (category != null) {
            int category_id = Integer.parseInt(category);
            list = dao.getByCategory(category_id);
        } else if (!keywords.trim().isEmpty()) {
            list = dao.searchByName(keywords);
        } else {
            list = dao.getAll();
        }

        request.setAttribute("productList", list);

        // ===== thêm 2 list chuột =====
        request.setAttribute("dealMouse", dealMouse);
        request.setAttribute("gamingMouse", gamingMouse);
        request.setAttribute("officeMouse", officeMouse);

        request.setAttribute("keywords", keywords);

        request.getRequestDispatcher("BestSeller2.jsp").forward(request, response);
    }

    // ================= VIEW DETAIL =================
    private void doView(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int id = parseInt(request.getParameter("id"));
        ProductDAO dao = new ProductDAO();
        ProductDTO product = dao.getById(id);

        request.setAttribute("product", product);
        request.getRequestDispatcher("product-detail.jsp").forward(request, response);
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

        if (dao.update(p)) {
            response.sendRedirect("ProductController?action=searchProduct");
        } else {
            response.sendRedirect("ProductController?action=searchProduct&error=updateFail");
        }
    }

    // ================= SOFT DELETE =================
    private void doSoftDelete(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        int id = parseInt(request.getParameter("id"));
        ProductDAO dao = new ProductDAO();

        if (dao.delete(id)) {
            response.sendRedirect("ProductController?action=searchProduct");
        } else {
            response.sendRedirect("ProductController?action=searchProduct&error=deleteFail");
        }
    }

    // ================= COUNT PRODUCT =================
    private void doStatistic(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        ProductDAO dao = new ProductDAO();
        int total = dao.countProducts();

        request.setAttribute("totalProduct", total);
        request.getRequestDispatcher("admin-dashboard.jsp").forward(request, response);
    }

    // ================= EXTRACT =================
    private ProductDTO extractProduct(HttpServletRequest request) {

        int id = parseInt(request.getParameter("id"));
        int category_id = parseInt(request.getParameter("category_id"));

        String name = request.getParameter("name");
        String cpu = request.getParameter("cpu");
        String gpu = request.getParameter("gpu");
        String ram = request.getParameter("ram");
        String ssd = request.getParameter("ssd");
        String screen = request.getParameter("screen");
        String refresh = request.getParameter("refresh_rate");

        float old_price = parseFloat(request.getParameter("old_price"));
        float new_price = parseFloat(request.getParameter("new_price"));

        int stock = parseInt(request.getParameter("stock"));
        String description = request.getParameter("description");
        String image = request.getParameter("image");

        return new ProductDTO(
                id,
                category_id,
                name,
                cpu,
                gpu,
                ram,
                ssd,
                screen,
                refresh,
                old_price,
                new_price,
                stock,
                description,
                image,
                true
        );
    }

    private int parseInt(String value) {
        try {
            return Integer.parseInt(value);
        } catch (Exception e) {
            return 0;
        }
    }

    private float parseFloat(String value) {
        try {
            return Float.parseFloat(value);
        } catch (Exception e) {
            return 0;
        }
    }

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

    protected void doShowLap(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");

        if (action == null) {
            action = "list";
        }

        if (action.equals("list")) {

            ProductDAO dao = new ProductDAO();

            ArrayList<ProductDTO> list = dao.getAllLaptop();
            ArrayList<ProductDTO> listUnder25 = dao.getLaptopUnder25();
            ArrayList<ProductDTO> listUnder30 = dao.getLaptopUnder30();
            ArrayList<ProductDTO> listTop30 = dao.getLaptopTop30();

            request.setAttribute("list", list);
            request.setAttribute("listUnder25", listUnder25);
            request.setAttribute("listUnder30", listUnder30);
            request.setAttribute("listTop30", listTop30);

            request.getRequestDispatcher("BestSeller.jsp").forward(request, response);
        }
    }

    protected void doShowKeyBoard(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        ProductDAO dao = new ProductDAO();

        ArrayList<ProductDTO> keyboardList = dao.getKeyboard();

        request.setAttribute("keyboardList", keyboardList);

        request.getRequestDispatcher("BestSeller3.jsp").forward(request, response);
    }

    protected void doShowMonitor(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        ProductDAO dao = new ProductDAO();

        ArrayList<ProductDTO> monitorList = dao.getMonitor();
        ArrayList<ProductDTO> list24inch = dao.getMonitor24Inch();
        ArrayList<ProductDTO> list27inch = dao.getMonitor27Inch();
        ArrayList<ProductDTO> listOLED = dao.getMonitorOLED();


        // set tất cả trước
        request.setAttribute("listProduct", monitorList);
        request.setAttribute("list24inch", list24inch);
        request.setAttribute("list27inch", list27inch);
        request.setAttribute("listOLED", listOLED);
        // 👉 forward 1 lần duy nhất
        request.getRequestDispatcher("BestSeller4.jsp").forward(request, response);
    }

}
