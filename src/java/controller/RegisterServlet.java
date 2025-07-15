/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dal.Data;
import dal.UserDAO;
import dal.WalletDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import model.Email;
import model.User;

/**
 *
 * @author admin
 */
@WebServlet(name = "RegisterServlet", urlPatterns = {"/register"})
public class RegisterServlet extends HttpServlet {

    private final String VERIFY_EMAIL_ATTRIBUTE = "verify_email";
    private final String REDIRECT_VERIFY_EMAIL = "account?method=verify-email";
    private final String REDIRECT_REGISTER = "account?method=register";
    private final DateTimeFormatter INPUT_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS");
    private final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm:ss");

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Data d = new Data();
        String getToken = request.getParameter("code");
        String data = "";
        // Encode email  data
        String encodedEmail = "";
        String encodedData = "";

        // Create URL completely
        String url = "";
        if (getToken != null) {
            String email = request.getParameter("email");
            if (email != null) {
                UserDAO uDAO = new UserDAO();
                List<String> tokens = uDAO.getTokenByEmail(email);
                if (tokens != null) {
                    String token = getValidToken(tokens, getToken);
                    if (token != null) {

                        Email e = new Email();
                        LocalDateTime dateTime = LocalDateTime.parse(request.getParameter("expireTime"), INPUT_FORMATTER);

                        // if token is expired
                        if (e.isExpireToken(dateTime)) {
                            data += "<h3 style='color: red'>The code has expired. Please generate a new one!</h3>"
                                    + " <button onclick='resentCode(\"" + email + "\", \"" + token + "\")' type='submit' class=\"account-btn bttn\">\n"
                                    + "                                        <span>Resent code</span>\n"
                                    + "                                    </button>";
                        } else {
                            // All valid
                            String expireTime = dateTime.format(TIME_FORMATTER);

                            data = d.getInVerifyEmail(email, expireTime, token);
                        }
                    } else {
                        data += "<h3 style='color: red'>The code is incorrect!</h3>";
                    }
                } else {
                    data += "<h3 style='color: red'>The code or email is incorrect!</h3>";
                }
            } else {
                data += "<h3 style='color: red'>Email is invalid!</h3>";
            }
            encodedEmail = URLEncoder.encode(email, StandardCharsets.UTF_8.toString());
            encodedData = URLEncoder.encode(data, StandardCharsets.UTF_8.toString());
            url = "account?method=verify-email&email=" + encodedEmail + "&data=" + encodedData;
            response.sendRedirect(url);
            return;
        }

        // Forward to account
        String method = request.getParameter("method");
        if (method == null) {
            response.sendRedirect(REDIRECT_REGISTER);
            return;
        }

        // Get interface register from handel Ajax
        response.setContentType("text/html;charset=UTF-8");
        data = d.getInRegister();
        response.getWriter().write(data);
    }

    private String getValidToken(List<String> list, String token) {
        for (String t : list) {
            if (t.equals(token)) {
                return token;
            }
        }
        return null;
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
        response.setContentType("text/html;charset=UTF-8");
        Data d = new Data();
        String data = "";
        String method = request.getParameter("method");
        String getEmail = request.getParameter("email");
        UserDAO uDAO = new UserDAO();

        // Do verify code or resent code
        if (method != null) {
            if (getEmail == null) {
                response.getWriter().write("There're something was wrong. Please reload the page!");
                return;
            }
            if (uDAO.isVerify(getEmail)) {
                response.getWriter().write("Your account has been verified. No need to worry about this step!");
                return;
            }

            if (method.equals("resent-code")) {
                String token = request.getParameter("token");
                int numberResent = uDAO.getNumberResentToken(getEmail);
                if (numberResent >= 3) {
                    response.getWriter().write("exceeding");
                    return;
                }
                // Increase number of resent token
                uDAO.updateNumberResentToken(getEmail);

                Email e = new Email();

                String newToken = e.createToken(); // For link on email
                LocalDateTime expireTime = e.expireToken();
                String renderTime = expireTime.format(TIME_FORMATTER);
                String code = e.generateRandomSixDigits();
                // Send reset email
                uDAO.updateToken(token, newToken, getEmail, code, expireTime);   /// BUGGGGG
                String resetLink = "http://localhost:8080/lighthouse/register?code=" + newToken + "&email=" + getEmail + "&expireTime=" + expireTime;
                String content = "<h3>Your email verification code is: " + code + "</h3>" + "\n" + "<span>Click here to verity: </span> <a href=" + resetLink + ">Click here</a> ";
                e.sendEmail(getEmail, "Verify Your Email", content);
                data = d.getInVerifyEmail(getEmail, renderTime, newToken);
                response.getWriter().write(data);
                return;
            }

            if (method.equals("code")) {
                String code = request.getParameter("enteredCode");
                String isValidCode = "";
                if (code.equals("")) {
                    isValidCode = "Invalid Verification Code";
                } else {
                    isValidCode = (uDAO.isValidCode(getEmail, code) != null) ? uDAO.isValidCode(getEmail, code) : "Invalid Verification Code";
                }
                switch (isValidCode) {
                    case "Invalid Verification Code":
                        response.getWriter().write("invalid");
                        break;
                    case "Token Expired":
                        response.getWriter().write("expired");
                        break;
                    default:
                        // Valid
                        uDAO.updateStatusUser(getEmail, code);
                        uDAO.deleteAccountsDuplicate(getEmail);
                        // Create an wallet
                        User user = uDAO.getUserByEmail(getEmail);
                        WalletDAO wDAO = new WalletDAO();
                        int walletID = wDAO.createWallet(Integer.parseInt(user.getId()));
                        user.setBalance(0.0);
                        HttpSession session = request.getSession();
                        session.setAttribute("account", user);
                        response.getWriter().write("success");
                }
                return;
            }
        }

        // if method == null, then create a new user
        String uname = request.getParameter("username");
        String password = request.getParameter("password");
        String email = request.getParameter("email");
        String fullname = request.getParameter("fullname");
        String phone = request.getParameter("phone");

        boolean isExistUName = uDAO.isExistUserName(uname);
        boolean isExistEml = uDAO.isExistEmail(email);
        if (isExistUName) {
            response.getWriter().write("userNameExisted");
        } else if (isExistEml) {
            response.getWriter().write("emailExisted");
        } else {
            Email e = new Email();
            String token = e.createToken();
            LocalDateTime expireTime = e.expireToken();
            String renderTime = expireTime.format(TIME_FORMATTER);
            String code = e.generateRandomSixDigits();

            uDAO.insertUser(uname, password, email, fullname, phone, "./assets/imgs/default-user.png", "Customer", LocalDateTime.now(), token, expireTime, 0, code, 0);
            String resetLink = "http://localhost:8080/lighthouse/register?code=" + token + "&email=" + email + "&expireTime=" + expireTime;
            String content = "<h3>Your email verification code is: " + code + "</h3>" + "\n" + "<span>Click here to verity: </span> <a href=" + resetLink + ">Click here</a> ";
            e.sendEmail(email, "Verify Your Email", content);

            data = d.getInVerifyEmail(getEmail, renderTime, token);
            response.getWriter().write(data);

        }

    }

}
