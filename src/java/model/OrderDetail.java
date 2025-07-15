/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.text.NumberFormat;
import java.util.Locale;

/**
 *
 * @author admin
 */
public class OrderDetail {
    private int oId;
    private Product product;
    private double totalPrice;
    private int quantity;
    private String size;
    private String topping;
    private String payment;
    NumberFormat formatter = NumberFormat.getInstance(new Locale("vi", "VN"));

    public OrderDetail(int oId, Product product, double totalPrice, int quantity, String size, String topping, String payment) {
        this.oId = oId;
        this.product = product;
        this.totalPrice = totalPrice;
        this.quantity = quantity;
        this.size = size;
        this.topping = topping;
        this.payment = payment;
    }


    public int getoId() {
        return oId;
    }

    public void setoId(int oId) {
        this.oId = oId;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
   

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
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
    
    public String formatPrice() {
        return formatter.format(totalPrice);
    }

    public String getPayment() {
        return payment;
    }

    public void setPayment(String payment) {
        this.payment = payment;
    }

    @Override
    public String toString() {
        return "OrderDetail{" + "oId=" + oId + ", product=" + product + ", totalPrice=" + totalPrice + ", quantity=" + quantity + ", size=" + size + ", topping=" + topping + ", payment=" + payment + ", formatter=" + formatter + '}';
    }
    
    
}
