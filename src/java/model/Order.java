/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

/**
 *
 * @author admin
 */
public class Order {
    private int totalOrder;
    private String categoryName;
    private int id;
    private int uId;
    private int pId;
    private String pName;
    private String pImage;
    private String status;
    private LocalDateTime payDate;
    private String payMethod;
    private String size;
    private String topping;
    private int quantity;
    private double totalPrice;
    private int isDeleted;
    
    private int orderID;
    private String userName;
    private String productName;
    private LocalDateTime orderDate;
    private String paymentMethod;
    private double totalAmount;
    private int productID;
    
    private final DateTimeFormatter formatterDateTime = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    NumberFormat formatter = NumberFormat.getInstance(new Locale("vi", "VN"));

    public Order(int id, int uId, int pId, String pName, String pImage, String status, LocalDateTime payDate, String payMethod, String size, String topping, int quantity, double totalPrice) {
        this.id = id;
        this.uId = uId;
        this.pId = pId;
        this.pName = pName;
        this.pImage = pImage;
        this.status = status;
        this.payDate = payDate;
        this.payMethod = payMethod;
        this.size = size;
        this.topping = topping;
        this.quantity = quantity;
        this.totalPrice = totalPrice;
    }

    public Order(int pId, String size, String topping, int quantity, double totalPrice) {
        this.pId = pId;
        this.size = size;
        this.topping = topping;
        this.quantity = quantity;
        this.totalPrice = totalPrice;
    }
    
    public Order(int totalOrder, String categoryName) {
        this.totalOrder = totalOrder;
        this.categoryName = categoryName;
    }

    public Order(String payMethod, double totalPrice) {
        this.payMethod = payMethod;
        this.totalPrice = totalPrice;
    }
    
    public Order(int orderID, String userName, String productName, int quantity, LocalDateTime orderDate, String paymentMethod, double totalAmount, String status) {
        this.orderID = orderID;
        this.userName = userName;
        this.productName = productName;
        this.quantity = quantity;
        this.orderDate = orderDate;
        this.paymentMethod = paymentMethod;
        this.totalAmount = totalAmount;
        this.status = status;
    }

    public Order(int orderID, String userName, String productName, int quantity, LocalDateTime orderDate, String paymentMethod, double totalAmount, String status, int productID) {
        this.orderID = orderID;
        this.userName = userName;
        this.productName = productName;
        this.quantity = quantity;
        this.orderDate = orderDate;
        this.paymentMethod = paymentMethod;
        this.totalAmount = totalAmount;
        this.status = status;
        this.productID = productID;
    }
    
    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public int getTotalOrder() {
        return totalOrder;
    }

    public void setTotalOrder(int totalOrder) {
        this.totalOrder = totalOrder;
    }

    public int getTotal(List<Order> list) {
        int total = 0;
        for (Order order : list) {
            total += order.getTotalOrder();
        }
        return total;
    }
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getuId() {
        return uId;
    }

    public void setuId(int uId) {
        this.uId = uId;
    }

    public int getpId() {
        return pId;
    }

    public void setpId(int pId) {
        this.pId = pId;
    }

    public String getpName() {
        return pName;
    }

    public void setpName(String pName) {
        this.pName = pName;
    }

    public String getpImage() {
        return pImage;
    }

    public void setpImage(String pImage) {
        this.pImage = pImage;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


    public String getPayDate() {
        return payDate.format(formatterDateTime);
    }

    public void setPayDate(LocalDateTime payDate) {
        this.payDate = payDate;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getTopping() {
        return topping;
    }

    public void setTopping(String topping) {
        this.topping = topping;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }
    
    @Override
    public String toString() {
        return "Order{" + "id=" + id + ", uId=" + uId + ", pId=" + pId + ", pName=" + pName + ", pImage=" + pImage + ", status=" + status + ", payDate=" + payDate + ", payMehthod=" + payMethod + ", size=" + size + ", topping=" + topping + ", quantity=" + quantity + ", totalPrice=" + totalPrice + '}';
    }

    public String getPayMethod() {
        return payMethod;
    }

    public void setPayMethod(String payMethod) {
        this.payMethod = payMethod;
    }
    
    public String formatPrice(double price) {
        return formatter.format(price);
    }

    public int getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(int isDeleted) {
        this.isDeleted = isDeleted;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getOrderDate() {
        return orderDate.toLocalDate().toString();
    }

    public void setOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public int getProductID() {
        return productID;
    }

    public void setProductID(int productID) {
        this.productID = productID;
    }

    public int getOrderID() {
        return orderID;
    }

    public void setOrderID(int orderID) {
        this.orderID = orderID;
    }

    
    
}
