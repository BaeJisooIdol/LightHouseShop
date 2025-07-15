/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package controller;

import dal.NewDAO;
import dal.OrderDAO;
import dal.ProductDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.List;
import model.New;
import model.OrderDetail;
import model.Product;
import model.User;

/**
 *
 * @author admin
 */
@WebServlet(name="HomeServlet", urlPatterns={"/home"})
public class HomeServlet extends HttpServlet {

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /** 
     * Handles the HTTP <code>GET</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        ProductDAO dao = new ProductDAO();
        // Get 10 proudcts that newest
        List<Product> newProducts = dao.getNewProducts(10);
        // Get 6 proudcts that best seller
        List<Product> bestProducts = dao.getTopProducts(6);
        // Get all proudcts have promotion
        List<Product> hotProducts = dao.getHotProducts();
        // Get all news
        NewDAO nDAO = new NewDAO();
        List<New> listNews = nDAO.getAllNews();

        HttpSession session = request.getSession();
        User u = (User) session.getAttribute("account");
        
        OrderDAO o = new OrderDAO(); 
        // Get products in cart of user
        List<OrderDetail> listOrderDetails = null;
        if(u != null) listOrderDetails = o.getOrderDetailByUid(Integer.parseInt(u.getId()));
        
        request.setAttribute("newProducts", newProducts);
        request.setAttribute("bestProducts", bestProducts);
        request.setAttribute("hotProducts", hotProducts);
        request.setAttribute("listNews", listNews);
        request.setAttribute("listOrderDetails", listOrderDetails);
        request.getRequestDispatcher("home.jsp").forward(request, response);
    } 

    /** 
     * Handles the HTTP <code>POST</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        // processRequest(request, response);
    }
}
