/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.time.LocalDateTime;

/**
 *
 * @author Pham Quoc Tu - CE181513
 */
public class Inventory {

    private int inventoryID;
    private int productID;
    private int quantity;
    private LocalDateTime lastUpdated;

    public Inventory(int productID, int quantity, LocalDateTime lastUpdated) {
        this.productID = productID;
        this.quantity = quantity;
        this.lastUpdated = lastUpdated;
    }

    public Inventory(int inventoryID, int productID, int quantity, LocalDateTime lastUpdated) {
        this.inventoryID = inventoryID;
        this.productID = productID;
        this.quantity = quantity;
        this.lastUpdated = lastUpdated;
    }

    public LocalDateTime getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(LocalDateTime lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public int getInventoryID() {
        return inventoryID;
    }

    public void setInventoryID(int inventoryID) {
        this.inventoryID = inventoryID;
    }

    public int getProductID() {
        return productID;
    }

    public void setProductID(int productID) {
        this.productID = productID;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Inventory{");
        sb.append("inventoryID=").append(inventoryID);
        sb.append(", productID=").append(productID);
        sb.append(", quantity=").append(quantity);
        sb.append(", lastUpdated=").append(lastUpdated);
        sb.append('}');
        return sb.toString();
    }
}
