/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dal.UserDAO;
import dal.WalletDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.time.LocalDateTime;
import model.FacebookLogin;
import model.GoogleLogin;
import model.User;

/**
 *
 * @author admin
 */
@WebServlet(name = "LoginServlet", urlPatterns = {"/login"})
public class LoginServlet extends HttpServlet {

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
        String method = request.getParameter("method");
        if (method != null) {
            String code = request.getParameter("code");
            HttpSession session = request.getSession();
            UserDAO uDao = new UserDAO();
            String url;
            switch (method) {
                case "googleLogin":
                    url = "https://accounts.google.com/o/oauth2/auth?client_id=" + GoogleLogin.CLIENT_ID
                            + "&redirect_uri=" + GoogleLogin.REDIRECT_URI
                            + "&response_type=code"
                            + "&scope=email profile"
                            + "&access_type=offline"
                            + "&prompt=select_account";
                    response.sendRedirect(url);
                    break;
                case "google":
                    handleGoogleLogin(code, session, uDao, request, response);
                    break;
                case "facebookLogin":
                    url = "https://www.facebook.com/v21.0/dialog/oauth?client_id=" + FacebookLogin.FACEBOOK_CLIENT_ID
                            + "&redirect_uri=" + FacebookLogin.FACEBOOK_REDIRECT_URI
                            + "&scope=email";
                    System.out.println(url);
                    response.sendRedirect(url);
                    break;
                case "facebook":
                    handleFacebookLogin(code, session, uDao, request, response);
                    break;
            }
        } else {
            request.getRequestDispatcher("account.jsp").forward(request, response);
        }
    }

    private void handleGoogleLogin(String code, HttpSession session, UserDAO uDAO, HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            GoogleLogin googleLogin = new GoogleLogin();
            String token = googleLogin.getToken(code);
            User account = googleLogin.getInfoUser(token);

            if (account != null) {
                if (!uDAO.isExistEmail(account.getEmail())) {
                    int userID = uDAO.insertUser(account.getName(), account.getPassword(), account.getEmail(), account.getFullName(), account.getPhone(),
                            "./assets/imgs/default-user.png", "Customer", LocalDateTime.now(), "", LocalDateTime.now(), 1, "", 1);
                    uDAO.deleteAccountsDuplicate(account.getEmail());
                    // Create an wallet
                    WalletDAO wDAO = new WalletDAO();
                    int walletID = wDAO.createWallet(userID);
                }
                User user = uDAO.getUserByEmail(account.getEmail());
                if (user == null) {
                    throw new Exception("Google login failed due to USER ID");
                } else {
                    if (user.getPicture().equals("./assets/imgs/default-user.png")) {
                        user.setPicture(account.getPicture());
                    }
                    if (user.getName().isEmpty()) {
                        user.setName(account.getName());
                    }
                    session.setAttribute("account", user);
                    response.sendRedirect("home");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Google login failed");
        }
    }

    private void handleFacebookLogin(String code, HttpSession session, UserDAO uDAO, HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            FacebookLogin facebookLogin = new FacebookLogin();
            String token = facebookLogin.getToken(code);
            User account = facebookLogin.getUserInfo(token);
            if (account != null) {
                if (!uDAO.isExistEmail(account.getEmail())) {
                    int userID = uDAO.insertUser(account.getName(), account.getPassword(), account.getEmail(), account.getFullName(), account.getPhone(),
                            "./assets/imgs/default-user.png", "Customer", LocalDateTime.now(), "", LocalDateTime.now(), 1, "", 1);
                    uDAO.deleteAccountsDuplicate(account.getEmail());
                    // Create an wallet
                    WalletDAO wDAO = new WalletDAO();
                    int walletID = wDAO.createWallet(userID);
                }
                User user = uDAO.getUserByEmail(account.getEmail());
                if (user == null) {
                    throw new Exception("Facbook login failed due to USER ID");
                } else {
                    if (user.getPicture().equals("./assets/imgs/default-user.png")) {
                        user.setPicture(account.getPicture());
                    }
                    if (user.getName().isEmpty()) {
                        user.setName(account.getName());
                    }
                    session.setAttribute("account", user);
                    response.sendRedirect("home");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Facebook login failed");
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
        String name = request.getParameter("username");
        String password = request.getParameter("password");
        String isRemember = request.getParameter("remember");
        UserDAO uDAO = new UserDAO();
        User user = uDAO.getUser(name, password);

        if (isRemember == null) {
            Cookie[] cookie = request.getCookies();
            if (cookie != null) {
                for (Cookie c : cookie) {
                    if (c.getName().equals("username") || c.getName().equals("isRemember")) {
                        c.setMaxAge(0);
                        response.addCookie(c);
                    }
                }
            }
        }

        if (user != null) {
            if (isRemember != null) {
                Cookie userNameCookie = new Cookie("username", name);
                Cookie isRemCookie = new Cookie("isRemember", "checked");
                userNameCookie.setMaxAge(24 * 60 * 60 * 7); // 1 week
                isRemCookie.setMaxAge(24 * 60 * 60 * 7); // 1 week
                response.addCookie(userNameCookie);
                response.addCookie(isRemCookie);
            }
            HttpSession session = request.getSession();
            session.setAttribute("account", user);
            response.sendRedirect("home");

        } else {
            request.setAttribute("errorLogin", "User name or password are incorrect!");
            request.getRequestDispatcher("account.jsp").forward(request, response);
        }
    }

}
