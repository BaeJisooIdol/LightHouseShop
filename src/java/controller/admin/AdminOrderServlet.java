/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.admin;

import dal.Data;
import dal.OrderDAO;
import dal.PaymentDAO;
import dal.ProductDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;
import model.Order;
import model.Payment;

/**
 *
 * @author Pham Quoc Tu - CE181513
 */
@WebServlet(name = "AdminOrderServlet", urlPatterns = {"/admin/order"})
public class AdminOrderServlet extends HttpServlet {

    private int item = 5;
    private int numberOfPagesOfOrder = 0;

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
        /// Pham Quoc Tu
        String action = (request.getParameter("action")) != null ? request.getParameter("action") : "";
        OrderDAO orderDAO = new OrderDAO();
        int totalNumberOfOrder = orderDAO.checkIdOrder();
        if (totalNumberOfOrder != -1) {
            numberOfPagesOfOrder = (int) Math.ceil((double) totalNumberOfOrder / item);
        }
        switch (action) {
            case "searchOrder":
                searchOrder(request, response);
                break;
            case "pagingOrder":
                pagingOrder(request, response);
                break;
            case "editOrder":
                showEditOrder(request, response);
                break;
            default:
                orderProduct(request, response);
        }
        /// Pham Quoc Tu
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
        String method = request.getParameter("method") != null ? request.getParameter("method") : "";

        switch (method) {
            case "edit":
                editOrder(request, response);
                break;
            default:
                orderProduct(request, response);
        }

    }

    private void searchOrder(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String value = request.getParameter("value");
        OrderDAO orderDAO = new OrderDAO();
        List<Order> listOrder; 
        if(value.isEmpty()) {
            listOrder = orderDAO.getAllOrder(1, item);
        } else {
            listOrder = orderDAO.searchOrderbyUserName(value);
        }
        
        Data d = new Data();
        String data = d.getInterfaceOrder(listOrder);
        response.getWriter().write(data);
    }

    private void pagingOrder(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        OrderDAO orderDAO = new OrderDAO();
        String value = request.getParameter("value");
        List<Order> listOrder = orderDAO.getAllOrder(Integer.parseInt(value), item);
        Data d = new Data();
        String data = d.getInterfaceOrder(listOrder);
        response.getWriter().write(data);
    }

    private void orderProduct(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        OrderDAO orderDAO = new OrderDAO();
        List<Order> listOrder = orderDAO.getAllOrder(1, item);
        request.setAttribute("listOrder", listOrder);
        request.setAttribute("numberOfPages", numberOfPagesOfOrder);
        request.getRequestDispatcher("../WEB-INF/admin/order.jsp").forward(request, response);
    }

    private void showEditOrder(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        OrderDAO orderDAO = new OrderDAO();
        Order order;
        String getid = request.getParameter("id");
        String getproductid = request.getParameter("productID");
        int id;
        int productid;
        try {
            id = Integer.parseInt(getid);
            productid = Integer.parseInt(getproductid);
            order = orderDAO.getOrderById(id, productid);
            if (order == null) {
                throw new NumberFormatException("Error Order ID or ProductID");
            }
        } catch (NumberFormatException exception) {
            System.out.println("showEditOrder: " + exception);
            response.sendRedirect("order");
            return;
        }

        PaymentDAO paymentDAO = new PaymentDAO();
        List<Payment> listPayment = paymentDAO.getAllPayment();
        request.setAttribute("productid", productid);
        request.setAttribute("id", id);
        request.setAttribute("order", order);
        request.setAttribute("listPayment", listPayment);
        request.getRequestDispatcher("../WEB-INF/admin/edit-order.jsp").forward(request, response);
    }

    private void editOrder(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        OrderDAO orderDAO = new OrderDAO();
        String productID = request.getParameter("productID");
        String order_id = request.getParameter("order_id");
        String quantity = request.getParameter("quantity");
        String status_order = request.getParameter("status_order");

        int ev_1 = orderDAO.editQuantityOfOrderDetail(Integer.parseInt(quantity), Integer.parseInt(order_id), Integer.parseInt(productID));
        int ev_2 = orderDAO.editPaymentID_StatusOfOrder(status_order, Integer.parseInt(order_id));

        if (ev_1 == 1 && ev_2 == 1) {
            PrintWriter out = response.getWriter();
            out.println("<script type='text/javascript'>");
            out.println("alert('Edit order successfully!');");
            out.println("window.location.href='order';"); // Chuyển hướng sau khi bấm OK
            out.println("</script>");

        } else {
            PrintWriter out = response.getWriter();
            out.println("<script type='text/javascript'>");
            out.println("alert('Edit order failure!');");
            out.println("window.location.href='order?action=editOrder&id=" + order_id + "&productID=" + productID + "';"); // Chuyển hướng sau khi bấm OK
            out.println("</script>");

        }
    }
}
