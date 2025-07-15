/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dal.OrderDAO;
import dal.UserDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;
import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.List;
import model.OrderDetail;
import model.User;

/**
 *
 * @author admin
 */
@MultipartConfig
@WebServlet(name = "ProfileServlet", urlPatterns = {"/profile"})
public class ProfileServlet extends HttpServlet {

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
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("account");
        int userID = Integer.parseInt(user.getId());
        // Get products in cart of user
        OrderDAO oDAO = new OrderDAO();
        List<OrderDetail> listOrderDetails = oDAO.getOrderDetailByUid(userID);
        request.setAttribute("listOrderDetails", listOrderDetails);
        request.getRequestDispatcher("profile.jsp").forward(request, response);
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
        if (user == null) {
            response.getWriter().write("No file selected!");
            return;
        }
        int uId = Integer.parseInt(user.getId());
        UserDAO uDAO = new UserDAO();
        String method = request.getParameter("method");
        // if the action is update the img
        if ("updateImage".equals(method)) {
            Part filePart = request.getPart("image"); // Lấy file từ request

            if (filePart != null && filePart.getSize() > 0) {
                
                String fileName = filePart.getSubmittedFileName(); // Lấy tên file gốc

                // String uploadDir = getServletContext().getRealPath("/") + "assets/imgs"; // point to build
                String uploadDir = "C:\\PRJ301_Learning\\LightHouseShop\\web\\assets\\imgs";

                // Tạo thư mục nếu chưa tồn tại
                File uploadFolder = new File(uploadDir);
                if (!uploadFolder.exists()) {
                    uploadFolder.mkdir();
                }

                // Đường dẫn file lưu trữ
                File file = new File(uploadDir, fileName);

                // Ghi file vào thư mục imgs/
                try ( InputStream fileContent = filePart.getInputStream()) {
                    Files.copy(fileContent, file.toPath(), StandardCopyOption.REPLACE_EXISTING);
                }

                String src = "./assets/imgs/" + fileName;
                uDAO.updateInforUser(uId, src);
                user.setPicture(src);
                response.getWriter().write("Image uploaded successfully: " + fileName);
            } else {
                response.getWriter().write("No file selected!");
            }
        } else {
            // if the action is update the info user
            String uname = request.getParameter("username");
            String password = request.getParameter("password");
            String fullname = request.getParameter("fullname");
            String phone = request.getParameter("phone");
            String address = request.getParameter("address");
            
            // if the info not change, then don't need to do more
            if(user.getName().equals(uname) 
               && user.getPassword().equals(password)
               && user.getFullName().equals(fullname)
               && user.getPhone().equals(phone)
               && address.equals(user.getAddress())) {
                // System.out.println("not chnage");
                response.getWriter().write("update-profile-success");
                return;
            }

            boolean isExistUName;
            // if username not change, then don't need to check
            if(user.getName().equals(uname)) {
                isExistUName = false;
            } else {
                isExistUName = uDAO.isExistUserName(uname);
            }
            
            if (isExistUName) {
                response.getWriter().write("userNameExisted");
            } else {
                uDAO.updateInforUser(uId, uname, password, fullname, phone, address);
                user.setName(uname);
                user.setPassword(password);
                user.setFullName(fullname);
                user.setPhone(phone);
                user.setAddress(address);
                response.getWriter().write("update-profile-success");
            }
        }

    }

}
