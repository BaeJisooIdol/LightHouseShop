/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dal.Data;
import dal.OrderDAO;
import dal.PaymentDAO;
import dal.ProductDAO;
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
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;
import model.Order;
import model.OrderDetail;
import model.User;
import model.Wallet;
import model.WalletTransaction;

/**
 *
 * @author admin
 */
@WebServlet(name = "OrderServlet", urlPatterns = {"/order"})
public class OrderServlet extends HttpServlet {

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
        String action = request.getParameter("action");
        String getPage = request.getParameter("page");
        String data = "";

        // Handel null case
        if (action == null) {
            action = "";
        }
        int item = 5;
        int numberOfPages = 0;
        // Handel wrong value
        int page;
        try {
            page = Integer.parseInt(getPage);
        } catch (NumberFormatException e) {
            page = 1;
        }
        OrderDAO oDAO = new OrderDAO();
        int totalOrders = oDAO.getNumberOfOrders(userID);
        if (totalOrders != -1) {
            numberOfPages = (int) Math.ceil((double) totalOrders / item);
        }
        List<Order> listOrders = oDAO.getAllOrders(userID, page, item);
        Data d = new Data();

        // Get products in cart of user
        List<OrderDetail> listOrderDetails = oDAO.getOrderDetailByUid(userID);

        // Handel action
        switch (action) {

            case "paging":
                data = d.getInOrder(listOrders);
                response.getWriter().write(data);
                break;
            case "delete-order":
                // Detete an order
                int id = Integer.parseInt(request.getParameter("id"));
                int status = oDAO.deleteOrder_OrderDetail(id);
                if (status == 1) {
                    response.getWriter().write("Detele Successfullly");
                } else {
                    response.getWriter().write("Detele Fail");
                }
                break;
            default:
                request.setAttribute("listOrderDetails", listOrderDetails);
                request.setAttribute("numberOfPages", numberOfPages);
                request.setAttribute("listOrders", listOrders);
                request.getRequestDispatcher("order.jsp").forward(request, response);
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
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("account");
        int userID = Integer.parseInt(user.getId());
        String action = request.getParameter("action");
        String desc = request.getParameter("desc");
        String status = ("buy".equals(action)) ? "Processing" : "Pending";
        String getProductID = (request.getParameter("pId") != null) ? request.getParameter("pId") : "0";
        String getPrice = (request.getParameter("price") != null) ? request.getParameter("price") : "0.0";
        String getQuantity = (request.getParameter("quantity") != null) ? request.getParameter("quantity") : "0";
        String size = (request.getParameter("size") != null) ? request.getParameter("size") : "";
        String toppings = (request.getParameter("topping") != null) ? request.getParameter("topping") : "";
        String payment = (request.getParameter("payment") != null) ? request.getParameter("payment") : "0";
        String[] payments = {"Cash", "Wallet", "VNPay"};
        String value = request.getParameter("value");
        int pId = 0;
        double price = 0;
        int quantity = 0;
        boolean checkInputs = true;
        ProductDAO pDAO = new ProductDAO();
        OrderDAO oDAO = new OrderDAO();
        WalletDAO wDAO = new WalletDAO();
        PaymentDAO payDAO = new PaymentDAO();
        Wallet wallet = wDAO.getWalletByUid(userID);

        // If action is buy or cart then check input
        try {
            pId = Integer.parseInt(getProductID);
            price = Double.parseDouble(getPrice);
            quantity = Integer.parseInt(getQuantity);
            payment = payments[Integer.parseInt(payment)];
        } catch (NumberFormatException e) {
            response.getWriter().write("error-data"); // There's something was wrong! (data)
            return;
        }

        if (size.isEmpty()) {
            size = null;
        }

        if (toppings.isEmpty()) {
            toppings = null;
        }

        if ("cart".equals(action) || "buyProduct".equals(desc)) {
            checkInputs = pDAO.isValidProduct(pId, price, quantity, size, toppings);
        }

        boolean isValidInfo;
        List<Order> listTransactions;
        Data d = new Data();
        String data;
        String getOrderID;
        int orderID;
        if (checkInputs) {

            switch (action) {
                case "buy":
                    String describe;
                    if ("recharge".equals(desc)) {
                        describe = "Deposit money into wallet";
                        WalletTransaction wt = new WalletTransaction(price, LocalDateTime.now(), describe);
                        session.setAttribute("wallet", wt);
                    } else {
                        // If product is available
                        
                        isValidInfo = pDAO.isAvailableProduct(pId, quantity);
                        if(!isValidInfo) {
                            response.getWriter().write("insufficient-quantity");
                            return;
                        }
                        
                        // If user's address is empty, then require address
                        if (user.getAddress() == null || user.getAddress().trim().isEmpty()) {
                            response.getWriter().write("address-require");
                            return;
                        }
                        if (payment.equals("Wallet")) {
                            if (user.getBalance() < price) {
                                response.getWriter().write("insufficient-balance");
                                return;
                            } else {
                                // Create an order
                                int paymentID = payDAO.createPayment("Wallet", "Pay for the order: " + user.formatPrice(price) + "đ");
                                oDAO.createOrder(userID, pId, price, quantity, "Processing", size, toppings, paymentID);
                                // Create a transaction
                                wDAO.createWalletTransaction(wallet.getId(), price, LocalDateTime.now(), "Pay for the bill");
                                // Update user's balance
                                wDAO.updateWallet(userID, price, "-");
                                user.setBalance(user.getBalance() - price);
                                session.setAttribute("cos", user.formatPrice(price));
                                // Forward to order
                                response.getWriter().write("order");
                                return;
                            }
                        } else if (payment.equals("Cash")) {
                            // Create an order
                            int paymentID = payDAO.createPayment("Cash", "Pay for the order: " + user.formatPrice(price) + "đ");
                            oDAO.createOrder(userID, pId, price, quantity, "Processing", size, toppings, paymentID);
                            session.setAttribute("cos", user.formatPrice(price));
                            // Forward to order
                            response.getWriter().write("order");
                            return;
                        }
                        // Payment is VNPay
                        Order order = new Order(pId, size, toppings, quantity, price);
                        session.setAttribute("order", order);
                        describe = "Pay for the order";
                    }

                    String vnp_Version = "2.1.0";
                    String vnp_Command = "pay";
                    String orderType = "other";
                    long amount = (long) (price * 100);
                    String bankCode = request.getParameter("bankCode");

                    String vnp_TxnRef = model.VNPayConfig.getRandomNumber(8);
                    String vnp_IpAddr = model.VNPayConfig.getIpAddress(request);

                    String vnp_TmnCode = model.VNPayConfig.VNP_TMN_CODE;

                    Map<String, String> vnp_Params = new HashMap<>();
                    vnp_Params.put("vnp_Version", vnp_Version);
                    vnp_Params.put("vnp_Command", vnp_Command);
                    vnp_Params.put("vnp_TmnCode", vnp_TmnCode);
                    vnp_Params.put("vnp_Amount", String.valueOf(amount));
                    vnp_Params.put("vnp_CurrCode", "VND");

                    if (bankCode != null && !bankCode.isEmpty()) {
                        vnp_Params.put("vnp_BankCode", bankCode);
                    }
                    vnp_Params.put("vnp_TxnRef", vnp_TxnRef);
                    vnp_Params.put("vnp_OrderInfo", describe + ": " + user.formatPrice(price) + "đ");
                    vnp_Params.put("vnp_OrderType", orderType);

                    String locate = request.getParameter("language");
                    if (locate != null && !locate.isEmpty()) {
                        vnp_Params.put("vnp_Locale", locate);
                    } else {
                        vnp_Params.put("vnp_Locale", "vn");
                    }
                    vnp_Params.put("vnp_ReturnUrl", model.VNPayConfig.VNP_RETURN_URL);
                    vnp_Params.put("vnp_IpAddr", vnp_IpAddr);

                    Calendar cld = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7"));
                    SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
                    String vnp_CreateDate = formatter.format(cld.getTime());
                    vnp_Params.put("vnp_CreateDate", vnp_CreateDate);

                    cld.add(Calendar.MINUTE, 15);
                    String vnp_ExpireDate = formatter.format(cld.getTime());
                    vnp_Params.put("vnp_ExpireDate", vnp_ExpireDate);

                    List fieldNames = new ArrayList(vnp_Params.keySet());
                    Collections.sort(fieldNames);
                    StringBuilder hashData = new StringBuilder();
                    StringBuilder query = new StringBuilder();
                    Iterator itr = fieldNames.iterator();
                    while (itr.hasNext()) {
                        String fieldName = (String) itr.next();
                        String fieldValue = (String) vnp_Params.get(fieldName);
                        if ((fieldValue != null) && (fieldValue.length() > 0)) {
                            // Build hash data
                            hashData.append(fieldName);
                            hashData.append('=');
                            hashData.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
                            // Build query
                            query.append(URLEncoder.encode(fieldName, StandardCharsets.US_ASCII.toString()));
                            query.append('=');
                            query.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
                            if (itr.hasNext()) {
                                query.append('&');
                                hashData.append('&');
                            }
                        }
                    }
                    String queryUrl = query.toString();
                    String vnp_SecureHash = model.VNPayConfig.hmacSHA512(model.VNPayConfig.VNP_HASH_SECRET, hashData.toString());
                    queryUrl += "&vnp_SecureHash=" + vnp_SecureHash;
                    String paymentUrl = model.VNPayConfig.VNP_URL + "?" + queryUrl;
                    System.out.println("paymentUrl: " + paymentUrl);
                    response.getWriter().write(paymentUrl);
                    break;
                case "search":
                    if (value.isEmpty()) {
                        listTransactions = oDAO.getAllOrders(userID, 1, 5);
                    } else {
                        listTransactions = oDAO.getAllOrdersByProductName(userID, value);
                    }

                    if (listTransactions.isEmpty()) {
                        response.getWriter().write("<p style=\"font-size: 2.5rem;\" class=\"order-empty\">No orders found!</p>");
                        return;
                    }
                    data = d.getInOrder(listTransactions);
                    response.getWriter().write(data);
                    break;
                case "cancel-order":
                    getOrderID = request.getParameter("id");
                    try {
                        orderID = Integer.parseInt(getOrderID);
                    } catch (NumberFormatException e) {
                        response.getWriter().write("error-data");
                        return;
                    }

                    Order validOrder = oDAO.isValidInforToCancelOrderByUserId(userID, orderID);
                    if (validOrder == null) {
                        response.getWriter().write("error-data");
                        return;
                    }
                    oDAO.cancelOrder(orderID);
                    // If method payment is different 'Cash', then refund
                    if (!"Cash".equals(validOrder.getPayMethod())) {
                        wDAO.createWalletTransaction(wallet.getId(), validOrder.getTotalPrice(), LocalDateTime.now(), "Refund");
                        wDAO.updateWallet(userID, validOrder.getTotalPrice(), "+");
                        user.setBalance(user.getBalance() + validOrder.getTotalPrice());
                    }

                    response.getWriter().write("cancel-order-success");
                    break;
                case "delete-order":
                    getOrderID = request.getParameter("id");
                    try {
                        orderID = Integer.parseInt(getOrderID);
                    } catch (NumberFormatException e) {
                        response.getWriter().write("error-data");
                        return;
                    }

                    String isValidInfoToDelete = oDAO.isValidInforToDeleteOrderByUserId(userID, orderID);
                    if (isValidInfoToDelete == null) {
                        response.getWriter().write("error-data");
                        return;
                    }

                    oDAO.deleteOrder(orderID, isValidInfoToDelete);
                    response.getWriter().write("delete-order-success");
                    break;
                default:
                    // Do cart
                    int paymentID = payDAO.createPayment(payment, "Pay for the order: " + price + "đ");
                    int orderId = oDAO.createOrder(userID, pId, price, quantity, "Pending", size, toppings, paymentID);
                    if (orderId == -1) {
                        response.getWriter().write("There's something was wrong! (data)");
                        return;
                    }
                    data = d.getItemInCart(orderId, pDAO.getProductByPid(pId, false), price, quantity, size, toppings, payment);
                    response.getWriter().write(data);
            }

        } else {
            response.getWriter().write("error-data"); // There's something was wrong! (input)
        }

    }

}
