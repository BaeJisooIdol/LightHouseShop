/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.admin;

import dal.OrderDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 *
 * @author admin
 */
@WebServlet(name = "ChartServlet", urlPatterns = {"/admin/chart"})
public class ChartServlet extends HttpServlet {

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
        String type = request.getParameter("type");
        if ("year".equals(type)) {
            request.getRequestDispatcher("../WEB-INF/admin/chart-year.jsp").forward(request, response);
        } else {
            request.getRequestDispatcher("../WEB-INF/admin/chart-month.jsp").forward(request, response);
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
        String type = request.getParameter("type");
        String getYear = request.getParameter("year");
        OrderDAO oDAO = new OrderDAO();
        PrintWriter out = response.getWriter();
        if ("year".equals(type)) {
            String[] years = getYear.split(" - ");
            if (years != null) {
                int fromYear, toYear;
                try {
                    fromYear = Integer.parseInt(years[0]);
                    toYear = Integer.parseInt(years[1]);
                } catch (NumberFormatException e) {
                    out.print("Year is not valid!");
                    return;
                }
                String datas = oDAO.getRevenueByYear(fromYear, toYear);
                out.print(datas);
            } else {
                out.print("Year is not valid!");
            }
        } else {
            int year;
            try {
                year = Integer.parseInt(getYear);
            } catch (NumberFormatException e) {
                out.print("Year is not valid!");
                return;
            }
            String datas = oDAO.getRevenueByMonth(year);
            out.print(datas);
        }

    }

}
