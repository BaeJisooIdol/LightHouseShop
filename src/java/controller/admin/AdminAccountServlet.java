package controller.admin;

import dal.Data;
import dal.UserDAO;
import dal.WalletDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import java.util.List;
import model.User;

@WebServlet(name = "AdminAccountServlet", urlPatterns = {"/admin/account"})
public class AdminAccountServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        String getValue = request.getParameter("value");
        int item = 5;
        int numberOfPages = 0;

        // Handle null case
        if (action == null) {
            action = "";
        }

        // Handle wrong value
        int value;
        try {
            value = Integer.parseInt(getValue);
        } catch (NumberFormatException e) {
            value = 1;
        }

        UserDAO uDAO = new UserDAO();
        int totalUser = uDAO.getTotalUser();
        if (totalUser != -1) {
            numberOfPages = (int) Math.ceil((double) totalUser / item);
        }
        if (item == totalUser) {
            value = 1;
        }
        List<User> listUsers = null;

        switch (action) {
            case "paging":
                value = Integer.parseInt(request.getParameter("value"));
                listUsers = uDAO.getAllUsers(value, item);
                Data d = new Data();
                String data = d.getInterfaceUser(listUsers);
                response.getWriter().write(data);
                break;
            case "create-account":
                request.getRequestDispatcher("../WEB-INF/admin/create-account.jsp").forward(request, response);
                break;
            case "edit-account":
                String getId = request.getParameter("id");
                int id;
                User user = null;
                try {
                    id = Integer.parseInt(getId);
                    user = uDAO.getUserId(id);
                    if (user == null) {
                        throw new NumberFormatException("Wrong ID");
                    }
                } catch (NumberFormatException e) {
                    listUsers = uDAO.getAllUsers(1, item);
                    request.setAttribute("wrongID", "Wrong ID. Please try again!");
                    request.setAttribute("numberOfPages", numberOfPages);
                    request.setAttribute("listUsers", listUsers);
                    request.getRequestDispatcher("../WEB-INF/admin/account.jsp").forward(request, response);
                    return;
                }
                request.setAttribute("user", user);
                request.getRequestDispatcher("../WEB-INF/admin/edit-account.jsp").forward(request, response);
                break;

            default:
                listUsers = uDAO.getAllUsers(value, item);
                request.setAttribute("numberOfPages", numberOfPages);
                request.setAttribute("currPage", value);
                request.setAttribute("listUsers", listUsers);
                request.getRequestDispatcher("../WEB-INF/admin/account.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        UserDAO uDAO = new UserDAO();
        String id;
        String userName;
        String password;
        String fullName;
        String phone;
        String role;
        int status;  // Default status
        String email;
        String address;
        String image;
        User user;
        switch (action) {
            case "search":
                String value = request.getParameter("value");
                List<User> listUsers = uDAO.getUserByName(value);
                if (listUsers.isEmpty()) {
                    response.getWriter().write("emptyData");
                    return;
                }
                Data d = new Data();
                String data = d.getInterfaceUser(listUsers);
                response.getWriter().write(data);
                break;

            case "create-account":
                userName = request.getParameter("username");
                password = request.getParameter("password");
                fullName = request.getParameter("fullname");
                phone = request.getParameter("phone");
                role = request.getParameter("role");
                email = request.getParameter("email");
                status = request.getParameter("status").equals("ON") ? 1 : 0;
                image = request.getParameter("image");

                // Create an account
                uDAO.insertUser(userName, password, email, fullName, phone, image, role, LocalDateTime.now(), "", LocalDateTime.now(), 0, "", status);
                // Create an wallet
                user = uDAO.getUserByEmail(email);
                WalletDAO wDAO = new WalletDAO();
                wDAO.createWallet(Integer.parseInt(user.getId()));
                user.setBalance(0.0);
                
                request.setAttribute("createAccountSuccess", "success");
                request.getRequestDispatcher("../WEB-INF/admin/create-account.jsp").forward(request, response);
                break;

            case "edit-account":
                id = request.getParameter("id");
                userName = request.getParameter("username");
                password = request.getParameter("password");
                fullName = request.getParameter("fullname");
                phone = request.getParameter("phone");
                role = request.getParameter("role");
                email = request.getParameter("email");
                boolean getStatus = Boolean.parseBoolean(request.getParameter("status"));
                image = request.getParameter("image");
                user = new User(id, userName, password, email, fullName, phone, role, getStatus, image);
                uDAO.editUser(user);
                request.setAttribute("editAccountSuccess", user.getId());
                request.getRequestDispatcher("../WEB-INF/admin/edit-account.jsp").forward(request, response);
                break;

            case "delete-account":
                id = request.getParameter("id");
                int getId;
                try {
                    getId = Integer.parseInt(id);
                    user = uDAO.getUserId(getId);
                    if (user == null) {
                        throw new NumberFormatException("Wrong ID");
                    }
                } catch (NumberFormatException e) {
                    response.getWriter().write("delete-account-fail");
                    return;
                }
                uDAO.deleteUser(getId);
                response.getWriter().write("delete-account-success");
                break;
            default:
                throw new AssertionError();
        }
    }

}
