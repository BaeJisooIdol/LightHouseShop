/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import model.Order;
import model.OrderDetail;
import model.Product;

/**
 *
 * @author admin
 */
public class OrderDAO extends DBContext {

    public int createOrder(int uId, int pId, double price, int quantity, String status, String size, String topping, int paymentId) {
        String query = "insert into Orders (OrderDate, TotalAmount, [Status], UserID, PaymentID, isDeleted) values(?, ?, ?, ?, ?, ?)";
        int orderId = -1;
        try {
            PreparedStatement pst = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);

            pst.setTimestamp(1, Timestamp.valueOf(LocalDateTime.now()));
            pst.setDouble(2, price);
            pst.setString(3, status);
            pst.setInt(4, uId);
            pst.setInt(5, paymentId);
            pst.setInt(6, 0);

            pst.executeUpdate();

            // Lấy generated key (OrderID)
            ResultSet rs = pst.getGeneratedKeys();
            if (rs.next()) {
                orderId = rs.getInt(1); // OrderID vừa được tạo

                // Chèn dữ liệu vào bảng OrderDetails
                String orderDetailQuery = "insert into OrderDetails (OrderID, ProductID, Quantity, Size, Topping) values (?, ?, ?, ?, ?)";
                PreparedStatement pstOrderDetail = con.prepareStatement(orderDetailQuery);

                pstOrderDetail.setInt(1, orderId);
                pstOrderDetail.setInt(2, pId);
                pstOrderDetail.setInt(3, quantity);
                pstOrderDetail.setString(4, size);
                pstOrderDetail.setString(5, topping);

                pstOrderDetail.executeUpdate();
            }
        } catch (SQLException e) {
            System.out.println("err createOrder: " + e);
        }
        return orderId;
    }

    public int getNumberOfProductSold(int id) {
        String query = "select sum(od.Quantity) Sold\n"
                + "from Orders o\n"
                + "left join OrderDetails od on o.OrderID = od.OrderID\n"
                + "where od.ProductID = ? and o.[Status] = 'Completed'";
        try {
            PreparedStatement pst = con.prepareStatement(query);
            pst.setInt(1, id);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                return rs.getInt("Sold");
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return -1;
    }

    public List<OrderDetail> getOrderDetailByUid(int id) {
        List<OrderDetail> list = new ArrayList<>();
        String query = "select o.OrderID, od.ProductID, o.TotalAmount, od.Quantity, od.Size, od.Topping, p.PaymentMethod\n"
                + "from Orders o\n"
                + "join OrderDetails od on od.OrderID = o.OrderID\n"
                + "join Payment p on p.PaymentID = o.PaymentID\n"
                + "where o.UserID = ? and o.[Status] = 'Pending'";
        try {
            PreparedStatement pst = con.prepareStatement(query);
            pst.setInt(1, id);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                int oId = rs.getInt("OrderID");
                ProductDAO pDAO = new ProductDAO();
                int pId = rs.getInt("ProductID");
                Product p = pDAO.getProductByPid(pId, false);
                double totalPrice = rs.getDouble("TotalAmount");
                int quantity = rs.getInt("Quantity");
                String size = rs.getString("Size");
                String topping = rs.getString("Topping");
                String payment = rs.getString("PaymentMethod");
                OrderDetail o = new OrderDetail(oId, p, totalPrice, quantity, size, topping, payment);
                list.add(o);
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return list;
    }

    public int deleteOrder_OrderDetail(int oId) {
        String deleteOrderDetails = "DELETE FROM OrderDetails WHERE OrderID = ?";
        String deleteOrders = "DELETE FROM Orders WHERE OrderID = ?";

        try {
            // Tắt auto-commit để đảm bảo tính toàn vẹn dữ liệu
            con.setAutoCommit(false);

            // Xóa dữ liệu trong OrderDetails trước
            try ( PreparedStatement pst1 = con.prepareStatement(deleteOrderDetails)) {
                pst1.setInt(1, oId);
                pst1.executeUpdate();
            }

            // Xóa dữ liệu trong Orders
            try ( PreparedStatement pst2 = con.prepareStatement(deleteOrders)) {
                pst2.setInt(1, oId);
                pst2.executeUpdate();
            }

            // Nếu không lỗi, commit thay đổi
            con.commit();
        } catch (SQLException e) {
            try {
                con.rollback(); // Quay lại trạng thái ban đầu nếu có lỗi
            } catch (SQLException rollbackEx) {
                System.out.println(rollbackEx);
            }
            System.out.println(e);
            return -1;
        } finally {
            try {
                con.setAutoCommit(true); // Bật lại auto-commit sau khi xử lý
            } catch (SQLException ex) {
                System.out.println(ex);
            }
        }
        return 1;
    }

    public String getTotalSale() {
        String query = "select sum(TotalAmount) totalAmount \n"
                + "from Orders\n"
                + "where [Status] = 'Completed'";
        try {
            PreparedStatement pst = con.prepareStatement(query);
            Product p = new Product();
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                return p.formatPrice(rs.getDouble("totalAmount"));
            }
        } catch (SQLException e) {
            System.out.println("err getTotalSale: " + e);
        }
        return null;
    }

    public String getRevenueByMonth(int year) {
        Map<String, String> listLabels = new LinkedHashMap<>();
        Map<String, Double> listCoffee = new LinkedHashMap<>();
        Map<String, Double> listBakery = new LinkedHashMap<>();
        Map<String, Double> listMilkTea = new LinkedHashMap<>();
        String query = "select month(o.OrderDate) [Month], sum(o.TotalAmount) [RevenueByMonth], c.CategoryName\n"
                + "from Orders o\n"
                + "join OrderDetails od on od.OrderID = o.OrderID\n"
                + "join Products p on p.ProductID = od.ProductID\n"
                + "join Categories c on c.CategoryID = p.CategoryID\n"
                + "where year(o.OrderDate) = ? and o.[Status] = 'Completed'\n"
                + "group by month(o.OrderDate), c.CategoryName";
        String[] months = {
            "January", "February", "March", "April", "May", "June",
            "July", "August", "September", "October", "November", "December"
        };
        for (String month : months) {
            listLabels.put("\"" + month + "\"", "\"" + month + "\"");
            listCoffee.put("\"" + month + "\"", 0.0);
            listBakery.put("\"" + month + "\"", 0.0);
            listMilkTea.put("\"" + month + "\"", 0.0);
        }
        try {
            PreparedStatement pst = con.prepareStatement(query);
            pst.setInt(1, year);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                int month = rs.getInt("Month");
                double revenue = rs.getDouble("RevenueByMonth");
                String categoryName = rs.getString("CategoryName");
                switch (categoryName) {
                    case "Coffee":
                        listCoffee.put("\"" + months[month - 1] + "\"", revenue);
                        break;
                    case "Cake":
                        listBakery.put("\"" + months[month - 1] + "\"", revenue);
                        break;
                    case "Milk Tea":
                        listMilkTea.put("\"" + months[month - 1] + "\"", revenue);
                        break;
                }
            }
            String labels = listLabels.values().toString();
            String revenueCoffee = listCoffee.values().toString();
            String revenueBakery = listBakery.values().toString();
            String revenueMilkTea = listMilkTea.values().toString();
            String datas = "{";
            datas += "\"Labels\": " + labels + ",";
            datas += "\"Coffee\": " + revenueCoffee + ",";
            datas += "\"Bakery\": " + revenueBakery + ",";
            datas += "\"MilkTea\": " + revenueMilkTea;
            datas += "}";
            return datas;
        } catch (SQLException ex) {
            System.out.println("Error getRevenueByMonth: " + ex);
        }
        return null;
    }

    public String getRevenueByYear(int fromYear, int toYear) {
        Map<String, String> listLabels = new LinkedHashMap<>();
        Map<String, Double> listCoffee = new LinkedHashMap<>();
        Map<String, Double> listBakery = new LinkedHashMap<>();
        Map<String, Double> listMilkTea = new LinkedHashMap<>();
        String query = "select year(o.OrderDate) [Year], sum(o.TotalAmount) RevenueByYear, c.CategoryName\n"
                + "from Orders o\n"
                + "join OrderDetails od on od.OrderID = o.OrderID\n"
                + "join Products p on p.ProductID = od.ProductID\n"
                + "join Categories c on c.CategoryID = p.CategoryID\n"
                + "where year(o.OrderDate) between ? and ? and o.[Status] = 'Completed'\n"
                + "group by year(o.OrderDate), c.CategoryName";

        for (int i = fromYear; i <= toYear; i++) {
            listLabels.put("\"" + i + "\"", "\"" + i + "\"");
            listCoffee.put("\"" + i + "\"", 0.0);
            listBakery.put("\"" + i + "\"", 0.0);
            listMilkTea.put("\"" + i + "\"", 0.0);
        }

        try {
            PreparedStatement pst = con.prepareStatement(query);
            pst.setInt(1, fromYear);
            pst.setInt(2, toYear);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                int year = rs.getInt("Year");
                double revenue = rs.getDouble("RevenueByYear");
                String categoryName = rs.getString("CategoryName");
                switch (categoryName) {
                    case "Coffee":
                        listCoffee.put("\"" + year + "\"", revenue);
                        break;
                    case "Cake":
                        listBakery.put("\"" + year + "\"", revenue);
                        break;
                    case "Milk Tea":
                        listMilkTea.put("\"" + year + "\"", revenue);
                        break;
                }
            }
            String labels = listLabels.values().toString();
            String revenueCoffee = listCoffee.values().toString();
            String revenueBakery = listBakery.values().toString();
            String revenueMilkTea = listMilkTea.values().toString();
            String datas = "{";
            datas += "\"Labels\": " + labels + ",";
            datas += "\"Coffee\": " + revenueCoffee + ",";
            datas += "\"Bakery\": " + revenueBakery + ",";
            datas += "\"MilkTea\": " + revenueMilkTea;
            datas += "}";
            return datas;
        } catch (SQLException ex) {
            System.out.println("Error getRevenueByYear: " + ex);
        }
        return null;
    }

    public int getNumberOfOrders(int uId) {
        String query = "select count(o.OrderID) 'numberOfOrders'\n"
                + "from Orders o\n"
                + "join Users u on u.UserID = o.UserID\n"
                + "where o.[Status] in ('Completed', 'Processing', 'Cancelled') and o.isDeleted = 0\n"
                + "and u.UserID = ?";
        try {
            PreparedStatement pst = con.prepareStatement(query);
            pst.setInt(1, uId);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                return rs.getInt("numberOfOrders");
            }
        } catch (SQLException e) {
            System.out.println("Error getNumberOfOrders: " + e);
        }
        return -1;
    }

    public List<Order> getAllOrders(int userId, int page, int item) {
        List<Order> list = new ArrayList<>();
        String query = "select o.OrderID , u.[UserID], pd.ProductID, pd.ProductName,\n"
                + "pd.[Image], o.[Status],p.[PaymentDate], p.PaymentMethod, od.Size,\n"
                + "od.Topping, od.Quantity, o.TotalAmount\n"
                + "from Orders o\n"
                + "left join Users u on u.UserID = o.UserID\n"
                + "left join Payment p on p.PaymentID = o.PaymentID\n"
                + "left join OrderDetails od on od.OrderID = o.OrderID\n"
                + "left join Products pd on pd.ProductID = od.ProductID\n"
                + "where o.[Status] in ('Completed', 'Processing', 'Cancelled') and o.isDeleted = 0\n"
                + "and u.UserID = ?\n"
                + "order by p.[PaymentDate] desc\n"
                + "offset ? rows fetch next ? rows only";
        try {
            PreparedStatement pst = con.prepareStatement(query);
            pst.setInt(1, userId);
            pst.setInt(2, (page - 1) * item);
            pst.setInt(3, item);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("OrderID");
                int uId = rs.getInt("UserID");
                int pId = rs.getInt("ProductID");
                String pName = rs.getString("ProductName");
                String pImage = getFirstImage(rs.getString("Image"));
                String status = rs.getString("Status");
                LocalDateTime PaymentDate = rs.getTimestamp("PaymentDate").toLocalDateTime();
                String payMethod = rs.getString("PaymentMethod");
                String size = (rs.getString("Size") != null) ? rs.getString("Size") : "";
                String topping = (rs.getString("Topping") != null) ? rs.getString("Topping") : "";
                int quantity = rs.getInt("Quantity");
                double totalPrice = rs.getDouble("TotalAmount");
                Order o = new Order(id, uId, pId, pName, pImage, status, PaymentDate, payMethod, size, topping, quantity, totalPrice);
                list.add(o);
            }
        } catch (SQLException e) {
            System.out.println("Error getAllOrders: " + e);
        }
        return list;
    }

    private String getFirstImage(String data) {
        String[] cut = data.split(";");
        return cut[0];
    }

    public List<Order> getAllOrdersByProductName(int userId, String productName) {
        List<Order> list = new ArrayList<>();
        String query = "select o.OrderID , u.[UserID], pd.ProductID, pd.ProductName, pd.[Image], o.[Status], p.[PaymentDate], p.PaymentMethod, od.Size, od.Topping, od.Quantity, o.TotalAmount\n"
                + "from Orders o\n"
                + "left join Users u on u.UserID = o.UserID\n"
                + "left join Payment p on p.PaymentID = o.PaymentID\n"
                + "left join OrderDetails od on od.OrderID = o.OrderID\n"
                + "left join Products pd on pd.ProductID = od.ProductID\n"
                + "where o.[Status] in ('Completed', 'Processing', 'Cancelled') and pd.ProductName like ?\n"
                + "and u.UserID = ?\n"
                + "order by  p.[PaymentDate] desc";
        try {
            PreparedStatement pst = con.prepareStatement(query);
            pst.setString(1, "%" + productName + "%");
            pst.setInt(2, userId);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("OrderID");
                int uId = rs.getInt("UserID");
                int pId = rs.getInt("ProductID");
                String pName = rs.getString("ProductName");
                String pImage = getFirstImage(rs.getString("Image"));
                String status = rs.getString("Status");
                LocalDateTime PaymentDate = rs.getTimestamp("PaymentDate").toLocalDateTime();
                String payMethod = rs.getString("PaymentMethod");
                String size = (rs.getString("Size") != null) ? rs.getString("Size") : "";
                String topping = (rs.getString("Topping") != null) ? rs.getString("Topping") : "";
                int quantity = rs.getInt("Quantity");
                double totalPrice = rs.getDouble("TotalAmount");
                Order o = new Order(id, uId, pId, pName, pImage, status, PaymentDate, payMethod, size, topping, quantity, totalPrice);
                list.add(o);
            }
        } catch (SQLException e) {
            System.out.println("Error getAllOrdersByProductName: " + e);
        }
        return list;
    }

    public Order isValidInforToCancelOrderByUserId(int userID, int orderID) {
        String query = "select o.TotalAmount, p.PaymentMethod\n"
                + "from Orders o\n"
                + "join Payment p on p.PaymentID = o.PaymentID\n"
                + "where UserID = ?\n"
                + "and OrderID = ?\n"
                + "and [Status] = 'Processing'";
        try {
            PreparedStatement pst = con.prepareStatement(query);
            pst.setInt(1, userID);
            pst.setInt(2, orderID);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                double totalAmount = rs.getDouble("TotalAmount");
                String paymentMethod = rs.getString("PaymentMethod");
                return new Order(paymentMethod, totalAmount);
            }
        } catch (SQLException e) {
            System.out.println("Error isValidInforToCancelOrderByUserId: " + e);
        }
        return null;
    }

    public String isValidInforToDeleteOrderByUserId(int userID, int orderID) {
        String query = "select Status\n"
                + "from Orders\n"
                + "where UserID = ?\n"
                + "and OrderID = ?\n"
                + "and [Status] in ('Completed', 'Cancelled')";
        try {
            PreparedStatement pst = con.prepareStatement(query);
            pst.setInt(1, userID);
            pst.setInt(2, orderID);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                return rs.getString("Status");
            }
        } catch (SQLException e) {
            System.out.println("Error isValidInforToCancelOrderByUserId: " + e);
        }
        return null;
    }

    public void cancelOrder(int orderID) {
        String query = "update Orders\n"
                + "set [Status] = 'Cancelled'\n"
                + "where OrderID = ?";
        try {
            PreparedStatement pst = con.prepareStatement(query);
            pst.setInt(1, orderID);
            pst.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error cancelOrder: " + e);
        }
    }

    public void deleteOrder(int orderID, String status) {
        String query;
        // If status is cancel, then delete completely
        if (status.equals("Cancelled")) {
            query = "delete from OrderDetails\n"
                    + "where OrderID = ?";
            try {
                PreparedStatement pstOrderDetail = con.prepareStatement(query);
                pstOrderDetail.setInt(1, orderID);
                pstOrderDetail.executeUpdate();

                query = "delete from Orders\n"
                        + "where OrderID = ?";
                PreparedStatement pstOrder = con.prepareStatement(query);
                pstOrder.setInt(1, orderID);
                pstOrder.executeUpdate();

            } catch (SQLException e) {
                System.out.println("Error deleteOrder: " + e);
            }
        } else {
            query = "update Orders\n"
                    + "set isDeleted = 1\n"
                    + "where OrderID = ?";
            try {
                PreparedStatement pst = con.prepareStatement(query);
                pst.setInt(1, orderID);
                pst.executeUpdate();

            } catch (SQLException e) {
                System.out.println("Error deleteOrder: " + e);
            }
        }

    }

    /// Pham Quoc Tu
    public List<Order> getAllOrder() {
        List<Order> list = new ArrayList<>();
        String query = "select o.OrderID , u.[userName], pd.ProductName,\n"
                + "o.[Status], p.[PaymentDate], p.PaymentMethod, o.orderDate,\n"
                + "od.Quantity, o.TotalAmount\n"
                + "from Orders o\n"
                + "left join Users u on u.UserID = o.UserID\n"
                + "left join Payment p on p.PaymentID = o.PaymentID\n"
                + "left join OrderDetails od on od.OrderID = o.OrderID\n"
                + "left join Products pd on pd.ProductID = od.ProductID\n"
                + "where o.[Status] in ('Completed', 'Processing', 'Cancelled') and o.isDeleted = 0";
        try ( ResultSet rs = execSelectQuery(query)) {
            while (rs.next()) {
                int orderID = rs.getInt("orderID");
                String userName = rs.getString("userName");
                String ProductName = rs.getString("ProductName");
                int quantity = rs.getInt("quantity");
                LocalDateTime orderDate = rs.getTimestamp("orderDate").toLocalDateTime();
                String paymentMethod = rs.getString("paymentMethod");
                double totalAmount = rs.getDouble("totalAmount");
                String status = rs.getString("status");
                list.add(new Order(orderID, userName, ProductName, quantity, orderDate, paymentMethod, totalAmount, status));
            }
        } catch (SQLException exception) {
            System.out.println("getAllOrder: " + exception);
        }
        return list;
    }

    public Order getOrderById(int idOrder, int idProduct) {
        String query = "SELECT o.OrderID, u.Username, pr.ProductName, od.Quantity, o.OrderDate, p.PaymentMethod, o.TotalAmount, o.[Status], pr.ProductID\n"
                + "FROM Orders o\n"
                + "LEFT JOIN Payment p ON o.PaymentID = p.PaymentID\n"
                + "LEFT JOIN Users u ON u.UserID = o.UserID\n"
                + "LEFT JOIN OrderDetails od ON od.OrderID = o.OrderID\n"
                + "LEFT JOIN Products pr ON pr.ProductID = od.ProductID\n"
                + "WHERE o.OrderID = ? AND pr.ProductID = ?";
        Object[] params = {idOrder, idProduct};
        try ( ResultSet rs = execSelectQuery(query, params)) {
            if (rs.next()) {
                int orderID = rs.getInt("orderID");
                String userName = rs.getString("userName");
                String ProductName = rs.getString("ProductName");
                int quantity = rs.getInt("quantity");
                LocalDateTime orderDate = rs.getTimestamp("orderDate").toLocalDateTime();
                String paymentMethod = rs.getString("paymentMethod");
                double totalAmount = rs.getDouble("totalAmount");
                String status = rs.getString("status");
                int ProductID = rs.getInt("ProductID");
                return new Order(orderID, userName, ProductName, quantity, orderDate, paymentMethod, totalAmount, status, ProductID);
            }
        } catch (SQLException exception) {
            System.out.println("getOrderById: " + exception);
        }
        return null;
    }

    public List<Order> getAllOrder(int page, int item) {
        List<Order> list = new ArrayList<>();
        String query = "select o.OrderID , u.[userName], pd.ProductName,\n"
                + "o.[Status], p.[PaymentDate], pd.[ProductID], p.PaymentMethod, o.orderDate,\n"
                + "od.Quantity, o.TotalAmount\n"
                + "from Orders o\n"
                + "left join Users u on u.UserID = o.UserID\n"
                + "left join Payment p on p.PaymentID = o.PaymentID\n"
                + "left join OrderDetails od on od.OrderID = o.OrderID\n"
                + "left join Products pd on pd.ProductID = od.ProductID\n"
                + "where o.[Status] in ('Completed', 'Processing', 'Cancelled') and o.isDeleted = 0\n"
                + "ORDER BY o.Status desc, o.orderDate desc\n"
                + "OFFSET ?  ROWS FETCH NEXT ? ROWS ONLY";
        Object[] params = {(page - 1) * item, item};
        try ( ResultSet rs = execSelectQuery(query, params)) {
            while (rs.next()) {
                int orderID = rs.getInt("orderID");
                String userName = rs.getString("userName");
                String ProductName = rs.getString("ProductName");
                int quantity = rs.getInt("quantity");
                LocalDateTime orderDate = rs.getTimestamp("orderDate").toLocalDateTime();
                String paymentMethod = rs.getString("paymentMethod");
                double totalAmount = rs.getDouble("totalAmount");
                String status = rs.getString("status");
                int ProductID = rs.getInt("ProductID");
                list.add(new Order(orderID, userName, ProductName, quantity, orderDate, paymentMethod, totalAmount, status, ProductID));
            }
        } catch (SQLException exception) {
            System.out.println("getAllOrder(int page, int item): " + exception);
        }
        return list;
    }

    public List<Order> searchOrderbyUserName(String valueSearch) {
        List<Order> list = new ArrayList<>();
        String query = "SELECT [o].OrderID, [u].Username, [pr].ProductName, [od].Quantity, [o].OrderDate, [p].PaymentMethod, [o].TotalAmount, [o].[Status] \n"
                + "FROM Orders [o]\n"
                + "LEFT JOIN Payment [p] ON [o].PaymentID = [p].PaymentID\n"
                + "LEFT JOIN Users [u] ON [u].UserID = [o].UserID\n"
                + "LEFT JOIN OrderDetails [od] ON [od].OrderID = [o].OrderID\n"
                + "LEFT JOIN Products [pr] ON [pr].ProductID = [od].ProductID\n"
                + "WHERE [u].Username like ?";
        Object[] params = {"%" + valueSearch + "%"};
        try ( ResultSet rs = execSelectQuery(query, params)) {
            while (rs.next()) {
                int orderID = rs.getInt("orderID");
                String userName = rs.getString("userName");
                String ProductName = rs.getString("ProductName");
                int quantity = rs.getInt("quantity");
                LocalDateTime orderDate = rs.getTimestamp("orderDate").toLocalDateTime();
                String paymentMethod = rs.getString("paymentMethod");
                double totalAmount = rs.getDouble("totalAmount");
                String status = rs.getString("status");
                list.add(new Order(orderID, userName, ProductName, quantity, orderDate, paymentMethod, totalAmount, status));
            }
        } catch (SQLException exception) {
            System.out.println("searchOrderbyUserName: " + exception);
        }
        return list;
    }

    public int checkIdOrder() {
        int numberOfOrderId = 0;
        String query = "SELECT COUNT(o.OrderID) as totalOrder\n"
                + "from Orders o\n"
                + "left join Users u on u.UserID = o.UserID\n"
                + "left join Payment p on p.PaymentID = o.PaymentID\n"
                + "left join OrderDetails od on od.OrderID = o.OrderID\n"
                + "left join Products pd on pd.ProductID = od.ProductID\n"
                + "where o.[Status] in ('Completed', 'Processing', 'Cancelled') and o.isDeleted = 0";
        try ( ResultSet rs = execSelectQuery(query)) {
            if (rs.next()) {
                return rs.getInt("totalOrder");
            }

        } catch (SQLException exception) {
            System.out.println("checkIdOrder: " + exception);
        }
        return numberOfOrderId;
    }

    public int editQuantityOfOrderDetail(int quantity, int orderID, int productID) {
        String query = "UPDATE OrderDetails SET Quantity = ?\n"
                + "WHERE OrderID = ? AND ProductID = ?";
        Object[] params = {quantity, orderID, productID};
        try {
            return execQuery(query, params);
        } catch (SQLException exception) {
            System.out.println("editQuantityOfOrderDetail(int quantity, int orderID, int productID): " + exception);
        }
        return 0;
    }

    public int editPaymentID_StatusOfOrder(String Status, int orderID) {
        String query = "UPDATE Orders SET [Status] = ?\n"
                + "WHERE OrderID = ?";
        Object[] params = {Status, orderID};
        try {
            return execQuery(query, params);
        } catch (SQLException exception) {
            System.out.println("editPaymentID_StatusOfOrder(int paymentID, String Status, int orderID): " + exception);
        }
        return 0;
    }

    /// Pham Quoc Tu
    public static void main(String[] args) {
        OrderDAO o = new OrderDAO();
    }
}
