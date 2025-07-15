/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import com.google.gson.Gson;
import dal.ReviewDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.time.LocalDate;
import java.time.LocalDateTime;
import model.Review;
import model.User;

/**
 *
 * @author admin
 */
@WebServlet(name = "CommentServlet", urlPatterns = {"/comment"})
public class CommentServlet extends HttpServlet {

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
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("account");
        int rating;
        int pId;
        int uId;
        String action = (request.getParameter("action") != null) ? request.getParameter("action") : "";
        ReviewDAO rDAO = new ReviewDAO();
        switch (action) {
            case "create":
                String message = request.getParameter("message");
                String getRating = request.getParameter("rating");
                String productID = request.getParameter("id");
                try {
                    uId = Integer.parseInt(user.getId());
                    pId = Integer.parseInt(productID);
                    rating = Integer.parseInt(getRating);
                } catch (NumberFormatException e) {
                    response.getWriter().write("{\"fail\":\"fail\"}");
                    return;
                }

                // Check valid number of comment
                boolean isValid = rDAO.checkValidToComment(uId, 5);
                if (isValid) {
                    response.getWriter().write("{\"limit\":\"fail\"}");
                    return;
                }

                int rId = rDAO.createReview(uId, pId, rating, message);
                if (!user.getRole().equals("Admin")) {
                    rDAO.updateNumberOfComment(uId);
                }
                if (rId == -1) {
                    response.getWriter().write("{\"fail\":\"fail\"}");
                    return;
                }
                Review review = new Review(rId, user.getPicture(), rating, message, LocalDate.now().toString());
                String json = new Gson().toJson(review);
                response.getWriter().write(json);
                break;
            case "delete":
                String getId = request.getParameter("id");
                int id;
                try {
                    id = Integer.parseInt(getId);
                } catch (NumberFormatException e) {
                    response.getWriter().write("fail");
                    return;
                }
                rDAO.deleteReview(id);
                response.getWriter().write("success");
                break;
        }

    }

}
