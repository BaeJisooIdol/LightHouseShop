/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dal.Data;
import dal.UserDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import model.Email;

/**
 *
 * @author admin
 */
@WebServlet(name = "ForgotPasswordServlet", urlPatterns = {"/forgot-password"})
public class ForgotPasswordServlet extends HttpServlet {

    private final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm:ss");

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
        // Forward to account
        String method = request.getParameter("method");
        if (method == null) {
            response.sendRedirect("account?method=forgot-password");
            return;
        }

        // Get interface register from handel Ajax
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        out.println("<h1>Forgot Password</h1>\n"
                + "                                    <p>For recover your password</p>\n"
                + "                                    <form class=\"form-group\" action=\"forgot-password\" method=\"post\">\n"
                + "                                            <input type=\"text\" name=\"method\" value=\"sentEmail\" hidden>\n"
                + "                                        <div class=\"form-input\">\n"
                + "                                            <input type=\"email\" name=\"email\" required>\n"
                + "                                            <span>Email</span>\n"
                + "                                            <span class=\"message\"></span>\n"
                + "                                        </div>\n"
                + "                                       \n"
                + "                                        <button class=\"account-btn bttn\">\n"
                + "                                            <span>Sent email</span>\n"
                + "                                        </button>\n"
                + "                                    </form>");

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
        String method = request.getParameter("method");
        String email = request.getParameter("email");
        UserDAO uDAO = new UserDAO();
        Data d = new Data();
        String data = "";
        if (method.equals("sentEmail")) {
            boolean isExistEmail = uDAO.isExistEmail(email);
            if (isExistEmail) {
                int numberResent = uDAO.getNumberResentToken(email);
                if (numberResent >= 3) {
                    response.getWriter().write("exceeding");
                    return;
                }

                Email e = new Email();
                String token = e.createToken(); // For link on email
                LocalDateTime expireTime = e.expireToken();
                String renderTime = expireTime.format(TIME_FORMATTER);
                String code = e.generateRandomSixDigits();
                // Send reset password
                uDAO.updateToken(token, "", email, code, expireTime);
                String content = "<h1>Your email verification code is: " + code + "</h1>";
                e.sendEmail(email, "Verify Your Email To Reset Password", content);
                data = d.getInVerifyEmailToResetPassword(email, renderTime);
                response.getWriter().write(data);
            } else {
                data = "email-not-found";
                response.getWriter().write(data);
            }
        } else if (method.equals("code")) {
            // If method == code, then handel here
            String code = request.getParameter("enteredCode");
            String isValidCode = "";
            if (code.equals("")) {
                isValidCode = "Invalid Verification Code";
            } else {
                isValidCode = (uDAO.isValidCode(email, code) != null) ? uDAO.isValidCode(email, code) : "Invalid Verification Code";
            }

            switch (isValidCode) {
                case "Invalid Verification Code":
                    response.getWriter().write("The code is invalid. Please try again!");
                    break;
                case "Token Expired":
                    response.getWriter().write("The code is expired. Please try again!");
                    break;
                default:
                    // Valid
                    Email e = new Email();
                    String password = e.generateRandomSixDigits();
                    // Send reset password
                    uDAO.updatePasswordByEmail(password, email);
                    String content = "<h1>Your new password is: " + password + "</h1>";
                    e.sendEmail(email, "Your New Password", content);
                    response.getWriter().write("success");

            }
        } else {
            // If method == resent-code, then handel here
            int numberResent = uDAO.getNumberResentToken(email);
            if (numberResent >= 3) {
                response.getWriter().write("exceeding");
                return;
            }
            // Increase number of resent token
            uDAO.updateNumberResentToken(email);

            Email e = new Email();

            String token = e.createToken(); // For link on email
            LocalDateTime expireTime = e.expireToken();
            String renderTime = expireTime.format(TIME_FORMATTER);
            String code = e.generateRandomSixDigits();
            // Send reset password
            uDAO.updateToken(token, "", email, code, expireTime);
            String content = "<h1>Your email verification code is: " + code + "</h1>";
            e.sendEmail(email, "Verify Your Email To Reset Password", content);
            data = d.getInVerifyEmailToResetPassword(email, renderTime);
            response.getWriter().write(data);
        }
    }

}
