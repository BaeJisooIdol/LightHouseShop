/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package controller.admin;

import dal.CategoryDAO;
import dal.OrderDAO;
import dal.ProductDAO;
import dal.SupplierDAO;
import dal.UserDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;
import model.Order;

/**
 *
 * @author admin
 */
@WebServlet(name="DashBoardServlet", urlPatterns={"/admin/dashboard"})
public class DashBoardServlet extends HttpServlet {

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
        // Product
        ProductDAO pDAO = new ProductDAO();
        int totalProduct = pDAO.getTotalProduct();
        List<Order> listProductSold = pDAO.getTotalProductSold();
        // Order
        OrderDAO oDAO = new OrderDAO();
        String getTotalSale = oDAO.getTotalSale();
        // User
        UserDAO uDAO = new UserDAO();
        int getTotalUser = uDAO.getTotalUser();
        // Category
        CategoryDAO cDAO = new CategoryDAO();
        int getTotalCategory = cDAO.getTotalCategory();
        // Suplier
        SupplierDAO sDAO = new SupplierDAO();
        int getTotalSupplier = sDAO.getTotalSupplier();
        
        request.setAttribute("totalProduct", totalProduct);
        request.setAttribute("listProductSold", listProductSold);
        request.setAttribute("getTotalSale", getTotalSale);
        request.setAttribute("getTotalUser", getTotalUser);
        request.setAttribute("getTotalCategory", getTotalCategory);
        request.setAttribute("getTotalSupplier", getTotalSupplier);
        request.getRequestDispatcher("../WEB-INF/admin/dashboard.jsp").forward(request, response);
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
        
    }
}
