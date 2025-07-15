/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.admin;

import dal.Data;
import dal.SupplierDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;
import model.Supplier;

/**
 *
 * @author admin
 */
@WebServlet(name = "SupplierServlet", urlPatterns = {"/admin/supplier"})
public class SupplierServlet extends HttpServlet {

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
        String action = request.getParameter("action");
        String getValue = request.getParameter("value");
        int item = 5;
        int numberOfPages = 0;
        // Handel null case
        if (action == null) {
            action = "";
        }
        // Handel wrong value
        int value;
        try {
            value = Integer.parseInt(getValue);
        } catch (NumberFormatException e) {
            value = 1;
        }

        SupplierDAO sDAO = new SupplierDAO();
        int totalSupplier = sDAO.getTotalSupplier();
        if (totalSupplier != -1) {
            numberOfPages = (int) Math.ceil((double) totalSupplier / item);
        }
        if (item == totalSupplier) value = 1;
        List<Supplier> listSuppliers = null;
        switch (action) {
            case "paging":
                listSuppliers = sDAO.getAllSuppliers(value, item);
                Data d = new Data();
                String data = d.getInterfaceSupplier(listSuppliers);
                response.getWriter().write(data);
                break;
            case "create-supplier":
                request.getRequestDispatcher("../WEB-INF/admin/create-supplier.jsp").forward(request, response);
                break;
            case "edit-supplier":
                String getId = request.getParameter("id");
                int id;
                Supplier supplier = null;
                try {
                    id = Integer.parseInt(getId);
                    supplier = sDAO.getSupplierById(id);
                    if (supplier == null) {
                        throw new NumberFormatException("Wrong ID");
                    }
                } catch (NumberFormatException e) {
                    listSuppliers = sDAO.getAllSuppliers(1, item);
                    request.setAttribute("wrongID", "Wrong ID. Please try again!");
                    request.setAttribute("numberOfPages", numberOfPages);
                    request.setAttribute("listSuppliers", listSuppliers);
                    request.getRequestDispatcher("../WEB-INF/admin/supplier.jsp").forward(request, response);
                    return;
                }
                request.setAttribute("supplier", supplier);
                request.getRequestDispatcher("../WEB-INF/admin/edit-supplier.jsp").forward(request, response);
                break;
            default:
                listSuppliers = sDAO.getAllSuppliers(value, item);
                request.setAttribute("numberOfPages", numberOfPages);
                request.setAttribute("currPage", value);
                request.setAttribute("listSuppliers", listSuppliers);
                request.getRequestDispatcher("../WEB-INF/admin/supplier.jsp").forward(request, response);
        }
    }

    /**
     * s
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
        String action = request.getParameter("action");
        SupplierDAO sDAO = new SupplierDAO();
        String id;
        String supplierName;
        String contactName;
        String phone;
        String email;
        String address;
        switch (action) {
            case "search":
                String value = request.getParameter("value");
                List<Supplier> listSuppliers = sDAO.getSupplierBySupplierName(value);
                if(listSuppliers.isEmpty()) {
                    response.getWriter().write("emptyData");
                    return;
                }
                Data d = new Data();
                String data = d.getInterfaceSupplier(listSuppliers);
                response.getWriter().write(data);
                break;
            case "create-supplier":
                supplierName = request.getParameter("suppliername");
                contactName = request.getParameter("contactname");
                phone = request.getParameter("phone");
                email = request.getParameter("email");
                address = request.getParameter("address");
                sDAO.addSupplier(supplierName, contactName, phone, email, address);
                response.getWriter().write("create-supplier-success");
                break;
            case "edit-supplier":
                id = request.getParameter("id");
                supplierName = request.getParameter("suppliername");
                contactName = request.getParameter("contactname");
                phone = request.getParameter("phone");
                email = request.getParameter("email");
                address = request.getParameter("address");
                sDAO.editSupplier(id, supplierName, contactName, phone, email, address);
                response.getWriter().write("edit-supplier-success");
                break;
            case "delete-supplier":
                id = request.getParameter("id");
                int getId;
                Supplier supplier = null;
                try {
                    getId = Integer.parseInt(id);
                    supplier = sDAO.getSupplierById(getId);
                    if (supplier == null) {
                        throw new NumberFormatException("Wrong ID");
                    }
                } catch (NumberFormatException e) {
                    response.getWriter().write("delete-supplier-fail");
                    return;
                }
                sDAO.deteleSupplier(getId);
                response.getWriter().write("delete-supplier-success");
                break;
            default:
                throw new AssertionError();
        }
    }

}
