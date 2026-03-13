package controller;

import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.*;
import javax.servlet.http.*;
import model.LaptopDAO;
import model.LaptopDTO;

public class LaptopController extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");

        if (action == null) {
            action = "list";
        }

        if (action.equals("list")) {

            LaptopDAO dao = new LaptopDAO();

            ArrayList<LaptopDTO> list = dao.getAllLaptop();
            ArrayList<LaptopDTO> listUnder25 = dao.getLaptopUnder25();
            ArrayList<LaptopDTO> listUnder30 = dao.getLaptopUnder30();
            ArrayList<LaptopDTO> listTop30 = dao.getLaptopTop30();

            request.setAttribute("list", list);
            request.setAttribute("listUnder25", listUnder25);
            request.setAttribute("listUnder30", listUnder30);
            request.setAttribute("listTop30", listTop30);

            request.getRequestDispatcher("BestSeller.jsp").forward(request, response);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");

        if (action.equals("addLaptop")) {

            String name = request.getParameter("name");
            String cpu = request.getParameter("cpu");
            String gpu = request.getParameter("gpu");
            String ram = request.getParameter("ram");
            String ssd = request.getParameter("ssd");
            String screen = request.getParameter("screen");
            String refresh = request.getParameter("refresh_rate");

            float old_price = Float.parseFloat(request.getParameter("old_price"));
            float new_price = Float.parseFloat(request.getParameter("new_price"));

            String image = request.getParameter("image_url");

            LaptopDTO l = new LaptopDTO(0, name, cpu, gpu, ram, ssd, screen, refresh, old_price, new_price, image);

            LaptopDAO dao = new LaptopDAO();
            dao.addLaptop(l);

            response.sendRedirect("LaptopController?action=list");
        }
    }
}
