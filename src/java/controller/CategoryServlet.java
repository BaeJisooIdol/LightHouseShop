/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dal.Data;
import dal.ProductDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;
import model.Product;

/**
 *
 * @author admin
 */
@WebServlet(name = "CategoryServlet", urlPatterns = {"/category"})
public class CategoryServlet extends HttpServlet {

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurss
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int cId = Integer.parseInt(request.getParameter("id"));
        String getPage = request.getParameter("page");
        int page;
        int item = 12;
        int numberOfPages;
        ProductDAO dao = new ProductDAO();
        List<Product> list;
        Data d = new Data();
        String data;
        if (getPage != null) {
            page = Integer.parseInt(getPage);
            list = dao.getProductsByCid(cId, page, item);
            data = d.getInProduct(list);
        } else {
            page = 1;
            int numberOfProducts = dao.totalProductByCid(cId);
            numberOfPages = (int) Math.ceil((double) numberOfProducts / item);
            list = dao.getProductsByCid(cId, page, item);
            data = d.getInProduct(list, cId, numberOfPages);
        }
        response.getWriter().write(data);

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
        int cId = Integer.parseInt(request.getParameter("id"));
        String action = request.getParameter("action");
        String value = request.getParameter("value");
        ProductDAO dao = new ProductDAO();
        List<Product> list = null;
        Data d = new Data();
        String data;
        switch (action) {
            case "searchByName":
                if(value.isEmpty()) {
                    list = dao.getProductsByCid(cId, 1, 12);
                } else {
                    list = dao.searchByName(cId, value);
                }
                break;
            case "searchByPrice":
                String[] cut = value.split("-");
                double fromPrice = (double) Integer.parseInt(cut[0]) / 1000;
                double toPrice = (double) Integer.parseInt(cut[1]) / 1000;
                list = dao.searchByPrice(cId, fromPrice, toPrice);
                break;
            case "searchByStar":
                list = dao.searchByStar(cId, Integer.parseInt(value));
                break;
            case "searchByDiscount":
                list = dao.searchByDiscount(cId, Integer.parseInt(value));
                break;
        }
        if (list.isEmpty()) {
            data = "<span style='font-size: 2rem'>No products were found!</span>";
        } else {
            data = d.getInProduct(list);
        }
        response.getWriter().write(data);

    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
