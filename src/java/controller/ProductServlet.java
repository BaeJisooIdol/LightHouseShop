/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import com.google.gson.JsonObject;
import dal.Data;
import dal.OrderDAO;
import dal.ProductDAO;
import dal.ReviewDAO;
import dal.UserDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import model.Product;
import model.Review;
import model.User;

/**
 *
 * @author admin
 */
@WebServlet(name = "ProductServlet", urlPatterns = {"/product"})
public class ProductServlet extends HttpServlet {

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
        HttpSession session = request.getSession(false);
        User user = (User) session.getAttribute("account");
        String getId = request.getParameter("id");
        String action = request.getParameter("action");
        String totalPrice = request.getParameter("totalPrice");
        String quantity = request.getParameter("quantity");
        String size = request.getParameter("size");
        String toppings = request.getParameter("toppings");
        String payment = request.getParameter("payment");
        JsonObject datas = new JsonObject();
        String author = (user == null || !user.getRole().equals("Admin")) ? "Customer" : "Admin";
        int userId = (user == null) ? -1 : Integer.parseInt(user.getId());
        int id = 0;
        try {
            id = Integer.parseInt(getId);
        } catch (NumberFormatException e) {
            response.getWriter().write("error-data");
        }
        if ("cartItem".equals(action)) {
            datas.addProperty("totalPrice", totalPrice);
            datas.addProperty("quantity", quantity);
            datas.addProperty("size", size);
            datas.addProperty("toppings", toppings);
            datas.addProperty("payment", payment);
        }
        ProductDAO pDAO = new ProductDAO();
        Product p = pDAO.getProductByPid(id, true);
        ReviewDAO rDAO = new ReviewDAO();
        List<Review> listReviews = rDAO.getReviewByPid(id);
        List<Integer> listUserId = new ArrayList<>();
        for (Review listReview : listReviews) {
            listUserId.add(listReview.getuId());
        }
        
        UserDAO uDAO = new UserDAO();
        List<User> listUsers = uDAO.getUserByUid(listUserId);
        OrderDAO oDAO = new OrderDAO();
        int sold = oDAO.getNumberOfProductSold(id);
        System.out.println(p);
        System.out.println(sold);
        if(p == null || sold == -1) {
            response.getWriter().write("There's somthing was wrong!");
        } else {
            Data d = new Data();
            String data = d.getInterfaceProductDetail(p, listReviews, listUsers, sold, datas, author, userId);
            response.getWriter().write(data);
        }
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

    }

}
