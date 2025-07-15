/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dal.Data;
import dal.OrderDAO;
import dal.PaymentDAO;
import dal.WalletDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.List;
import model.Order;
import model.OrderDetail;
import model.User;
import model.Wallet;
import model.WalletTransaction;

/**
 *
 * @author admin
 */
@WebServlet(name = "TransactionServlet", urlPatterns = {"/transaction"})
public class TransactionServlet extends HttpServlet {

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
        WalletDAO wDAO = new WalletDAO();
        OrderDAO oDAO = new OrderDAO();
        Wallet w = wDAO.getWalletByUid(userID);
        int walletID = w.getId();
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
        int totalWalletTransactions = wDAO.getNumberOfWalletTransactions(walletID);
        if (totalWalletTransactions != -1) {
            numberOfPages = (int) Math.ceil((double) totalWalletTransactions / item);
        }
        List<WalletTransaction> listWalletTransactions = wDAO.getAllWalletTransactions(walletID, page, item);

        Data d = new Data();

        // Get products in cart of user
        List<OrderDetail> listOrderDetails = oDAO.getOrderDetailByUid(userID);

        // Handel action
        switch (action) {
            case "payment":
                // Get params transaction from request
                String vnpTxnRef = request.getParameter("vnp_TxnRef");
                String vnpBankCode = request.getParameter("vnp_BankCode");
                String vnpOrderInfo = request.getParameter("vnp_OrderInfo");
                String vnpTransactionNo = request.getParameter("vnp_TransactionNo");
                String vnpResponseCode = request.getParameter("vnp_ResponseCode");
                String vnpAmountStr = request.getParameter("vnp_Amount");
                System.out.println("vnpTxnRef: " + vnpTxnRef);
                System.out.println("vnpTransactionNo: " + vnpTransactionNo);
                System.out.println("vnpResponseCode: " + vnpResponseCode);
                System.out.println("vnpAmountStr: " + vnpAmountStr);
                
                // Transaction successfully
                if ("00".equals(vnpResponseCode)) {
                    // Create an order
                    Order order = (Order) session.getAttribute("order");
                    if (order != null) {
                        PaymentDAO pDAO = new PaymentDAO();
                        int paymentID = pDAO.createPayment(vnpBankCode, vnpOrderInfo);
                        oDAO.createOrder(userID, order.getpId(), order.getTotalPrice(), order.getQuantity(), "Processing",
                                order.getSize(), order.getTopping(), paymentID);
                        
                        session.removeAttribute("orderId");
                        session.setAttribute("cos", user.formatPrice(order.getTotalPrice()));
                        response.sendRedirect("order");
                    }
                    // Deposit into wallet
                    WalletTransaction wt = (WalletTransaction) session.getAttribute("wallet");
                    if (wt != null) {
                        Wallet wallet = wDAO.getWalletByUid(userID);
                        int wtID = wDAO.createWalletTransaction(wallet.getId(), wt.getAmount(), LocalDateTime.now(), wt.getNote());
                        wDAO.updateWallet(userID, wt.getAmount(), "+");
                        session.removeAttribute("wallet");
                        session.setAttribute("recharge", user.formatPrice(wt.getAmount()));
                        // Increate balance
                        user.setBalance(user.getBalance() + wt.getAmount());
                        response.sendRedirect("profile");
                    }

                } else {
                    // Transaction fail
                    if (session.getAttribute("orderId") != null) {
                        session.removeAttribute("orderId");
                    }

                    System.out.println("transaction-fail");
                }
                break;
            case "paging":
                data = d.getInTransaction(listWalletTransactions);
                response.getWriter().write(data);
                break;
            default:
                request.setAttribute("listOrderDetails", listOrderDetails);
                request.setAttribute("listWalletTransactions", listWalletTransactions);
                request.setAttribute("numberOfPages", numberOfPages);
                request.getRequestDispatcher("transaction.jsp").forward(request, response);
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
        String value = request.getParameter("value");

        if (action == null) {
            action = "";
        }
        WalletDAO wDAO = new WalletDAO();
        Wallet w = wDAO.getWalletByUid(userID);
        int walletID = w.getId();
        List<WalletTransaction> listWalletTransactions;
        Data d = new Data();
        String data;
        switch (action) {
            case "search":
                if (value.isEmpty()) {
                    listWalletTransactions = wDAO.getAllWalletTransactions(walletID, 1, 5);
                } else {
                    listWalletTransactions = wDAO.getAllWalletTransactionsByDate(walletID, value);
                }

                if (listWalletTransactions.isEmpty()) {
                    response.getWriter().write("<tr><td style=\"font-size: 2.5rem\" colspan=\"4\">No transaction found!</td></tr>");
                    return;
                }
                data = d.getInTransaction(listWalletTransactions);
                response.getWriter().write(data);
                break;
            case "delete-transaction":
                String getId = request.getParameter("id");
                int id;
                try {
                    id = Integer.parseInt(getId);
                } catch (NumberFormatException e) {
                    response.getWriter().write("delete-transaction-fail");
                    return;
                }
                boolean isValidInfo = wDAO.checkInfoWalletTransaction(walletID, id);
                if (isValidInfo) {
                    wDAO.deleteWalletTransaction(id);
                    response.getWriter().write("delete-transaction-success");
                } else {
                    response.getWriter().write("delete-transaction-fail");
                }
                break;
            default:
        }
    }

}
