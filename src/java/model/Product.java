/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.util.Locale;

/**
 *
 * @author admin
 */
public class Product {

    private int id;
    private int cId;
    private String name;
    private String description;
    private String image;
    private String size;
    private String topping;
    private double price;
    private double discount;
    private LocalDateTime createdAt;
    private int rating;
    private String label;
    private double newPrice;
    private int quantity;
    NumberFormat formatter = NumberFormat.getInstance(new Locale("vi", "VN"));

    /// Pham Quoc Tu
    private int promotionId;
    private String status;
    private String categoryName;
    private String totalQuantity;
    private int supplierID;
    /// Pham Quoc Tu
    
    /// Pham Quoc Tu
    public Product(int id, String categoryName, String name, String image, double price, String status) {
        this.id = id;
        this.categoryName = categoryName;
        this.name = name;
        this.image = image;
        this.price = price;
        this.status = status;

    }

    public Product(int cId, String name, String description, String image, String size, String topping, double price, LocalDateTime createdAt, int promotionId, String status, int supplierID) {
        this.cId = cId;
        this.name = name;
        this.description = description;
        this.image = image;
        this.size = size;
        this.topping = topping;
        this.price = price;
        this.createdAt = createdAt;
        this.promotionId = promotionId;
        this.status = status;
        this.supplierID = supplierID;
    }

    public Product(int cId, String name, String description, String image, String size, String topping, double price, int promotionId, String status) {
        this.cId = cId;
        this.name = name;
        this.description = description;
        this.image = image;
        this.size = size;
        this.topping = topping;
        this.price = price;
        this.promotionId = promotionId;
        this.status = status;
    }

    public Product(int cId, String name, String description, String image, String size, String topping, double price, int promotionId, String status, int supplierID) {
        this.cId = cId;
        this.name = name;
        this.description = description;
        this.image = image;
        this.size = size;
        this.topping = topping;
        this.price = price;
        this.promotionId = promotionId;
        this.status = status;
        this.supplierID = supplierID;
    }

    /**
     * <h1> this is cons for editProductCreated </h1>
     * @param id
     * @param cId
     * @param name
     * @param description
     * @param image
     * @param size
     * @param topping
     * @param price
     * @param createdAt
     * @param promotionId
     * @param status
     * @param supplierID
     */
    public Product(int id, int cId, String name, String description, String image, String size, String topping, double price, LocalDateTime createdAt, int promotionId, String status, int supplierID) {
        this.id = id;
        this.cId = cId;
        this.name = name;
        this.description = description;
        this.image = image;
        this.size = size;
        this.topping = topping;
        this.price = price;
        this.createdAt = createdAt;
        this.promotionId = promotionId;
        this.status = status;
        this.supplierID = supplierID;
    }

    public Product(int id, String categoryName, String name, String image, double price, String status, String totalQuantity) {
        this.id = id;
        this.categoryName = categoryName;
        this.name = name;
        this.image = image;
        this.price = price;
        this.status = status;
        this.totalQuantity = totalQuantity;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getPromotionId() {
        return promotionId;
    }

    public void setPromotionId(int promotionId) {
        this.promotionId = promotionId;
    }

    public String getTotalQuantity() {
        return totalQuantity;
    }

    public void setTotalQuantity(String totalQuantity) {
        this.totalQuantity = totalQuantity;
    }

    public int getSupplierID() {
        return supplierID;
    }

    public void setSupplierID(int SupplierID) {
        this.supplierID = SupplierID;
    }
    /// Pham Quoc Tu
    
    public Product() {
        
    }
    
    public Product(int pId, int cId, String name, String description, String image, String size, String topping, double price, LocalDateTime createdAt, int rating, String label, double discount) {
        this.id = pId;
        this.cId = cId;
        this.name = name;
        this.description = description;
        this.image = image;
        this.size = size;
        this.topping = topping;
        this.price = price;
        this.createdAt = createdAt;
        this.rating = rating;
        this.label = label;
        this.discount = discount;
        if(discount > 0.0) {
            newPrice = Math.round((price - (price * (discount / 100))) / 1000) * 1000;
        }
    }

    public Product(int id, String name, String image, double price, int rating, int quantity) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.price = price;
        this.rating = rating;
        this.quantity = quantity;
    }
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getcId() {
        return cId;
    }

    public void setcId(int cId) {
        this.cId = cId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
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

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getNewPrice() {
        return newPrice;
    }

    public void setNewPrice(double newPrice) {
        this.newPrice = newPrice;
    }
    

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    
    @Override
    public String toString() {
        return "Product{" + "id=" + id + ", cId=" + cId + ", name=" + name + ", description=" + description + ", image=" + image + ", size=" + size + ", topping=" + topping + ", price=" + price + ", discount=" + discount + ", createdAt=" + createdAt + ", rating=" + rating + ", label=" + label + '}';
    }
    
    public String formatPrice(double price) {
        return formatter.format(price);
    }

}
