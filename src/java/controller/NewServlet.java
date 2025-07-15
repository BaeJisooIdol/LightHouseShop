/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dal.Data;
import dal.NewDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;
import model.New;

/**
 *
 * @author admin
 */
@WebServlet(name = "NewServlet", urlPatterns = {"/news"})
public class NewServlet extends HttpServlet {

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
        String getId = request.getParameter("id");
        NewDAO nDAO = new NewDAO();
        // Handle ajax
        int id = 0;
        try {
            id = Integer.parseInt(getId);
        } catch (NumberFormatException e) {
            response.getWriter().write("error");
        }

        Data d = new Data();
        New news;
        List<New> listNews;
        String data = "";
        if(id == 0) {
            listNews = nDAO.getAllNews();
            data = d.getInAllNews(listNews);
        } else {
            news = nDAO.getNewById(id);
            data = d.getInNews(news);
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

    }

}
