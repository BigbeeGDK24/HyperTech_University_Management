package controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.UserDTO;

public class MainController extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        String action = request.getParameter("action");
        String url = "index.jsp";

        if (action == null) {
            url = "index.jsp";

            // ================= LOGIN =================
        } else if (action.equals("login") || action.equals("Adminlogout")) {
            url = "AdminController";

        } else if (action.equals("Userlogout")) {
            url = "UserController";

        } else if (action.equals("addUser")) {
            url = "UserController";

            // ================= PRODUCT =================
        } else if (action.equals("searchLaptop")
                || action.equals("searchVGA")
                || action.equals("searchCase")
                || action.equals("searchRAM")
                || action.equals("searchBanPhim")
                || action.equals("searchProduct")
                || action.equals("searchManHinh")) {

            url = "ProductController";

            // ================= CART =================
        } else if (action.equals("viewCart")
                || action.equals("AddCart")
                || action.equals("UpdateCart")
                || action.equals("RemoveCart")
                || action.equals("clearCart")) {

            url = "CartController";

            // ================= ORDER =================
        } else if (action.equals("createOrder")
                || action.equals("searchOrder")
                || action.equals("deleteOrder")
                || action.equals("updateOrder")
                || action.equals("saveUpdateOrder")
                || action.equals("statisticsOrder")) {

            url = "OrderController";

            // ================= 🔥 CHECKOUT FLOW (FIX CHUẨN) =================
        } else if (action.equals("checkout")) {

            HttpSession session = request.getSession();
            UserDTO user = (UserDTO) session.getAttribute("user");

            // ❌ chưa login
            if (user == null) {

                session.setAttribute("showLoginModal", true);
                url = "cart.jsp";

            } else {

                // ✔ check thiếu info
                if (user.getPhone() == null || user.getPhone().isEmpty()
                        || user.getAddress() == null || user.getAddress().isEmpty()) {

                    url = "information.jsp";

                } else {

                    // ✔ đủ info → set session
                    session.setAttribute("FULLNAME", user.getUsername());
                    session.setAttribute("EMAIL", user.getEmail());
                    session.setAttribute("PHONE", user.getPhone());
                    session.setAttribute("ADDRESS", user.getAddress());

                    url = "payment.jsp";
                }
            }

            // ================= PAYMENT =================
        } else if (action.startsWith("viewPayment")
                || action.startsWith("viewUserPayment")
                || action.startsWith("searchPayment")
                || action.startsWith("addPayment")
                || action.startsWith("updatePayment")
                || action.startsWith("deletePayment")
                || action.equals("paymentStatistic")) {

            url = "PaymentController";
        }

        request.getRequestDispatcher(url).forward(request, response);
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
        return "Main Controller";
    }
}
