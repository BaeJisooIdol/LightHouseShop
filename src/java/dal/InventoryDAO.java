/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import java.sql.ResultSet;
import java.time.LocalDateTime;
import model.Inventory;

/**
 *
 * @author Pham Quoc Tu - CE181513
 */
public class InventoryDAO extends DBContext {

    public Inventory getInventoryByproductID(int productID) {
        String query = "SELECT * FROM Inventory\n"
                + "WHERE ProductID = ?";
        Object[] params = {productID};

        try ( ResultSet rs = execSelectQuery(query, params)) {
            if (rs.next()) {
                return new Inventory(rs.getInt("InventoryID"), rs.getInt("ProductID"), rs.getInt("quantity"), rs.getTimestamp("LastUpdated").toLocalDateTime());
            }
        } catch (Exception e) {
            System.out.println("getInventoryByproductID: " + e);
        }
        return null;
    }

    public int createNewInventory(Inventory inventory) {
        String getNextIdInventory = "SELECT max([InventoryID]) + 1 as nextInventoryID FROM Inventory";
        String query = "INSERT Inventory(InventoryID,ProductID,Quantity,LastUpdated)\n"
                + "VALUES(?,?,?,?)";
        try ( ResultSet rs = execSelectQuery(getNextIdInventory)) {
            if (rs.next()) {
                int nextIdInventory = rs.getInt("nextInventoryID");
                Object[] params = {nextIdInventory, inventory.getProductID(), inventory.getQuantity(), inventory.getLastUpdated()};
                return execQuery(query, params);
            }
        } catch (Exception e) {
            System.out.println("createNewInventory: " + e);
        }
        return 0;
    }

    public int editInventoryCreatedByProductID(int quantity, LocalDateTime LastUpdated, int ProductID) {
        String query = "UPDATE Inventory SET Quantity = ?, LastUpdated = ?\n"
                + "WHERE ProductID = ?";
        Object[] params = {quantity, LastUpdated, ProductID};
        try {
            return execQuery(query, params);
        } catch (Exception e) {
            System.out.println("editInventoryCreatedByProductID: " + e);
        }
        return 0;
    }

    public int deleteInventoryByProductID(int ProductID) {
        String query = "DELETE FROM Inventory WHERE ProductID = ?";
        try {
            Object[] params = {ProductID};
            return execQuery(query, params);
        } catch (Exception e) {
            System.out.println("deleteInventoryByProductID: " + e);
        }
        return 0;
    }

    public static void main(String[] args) {
        InventoryDAO aO = new InventoryDAO();
        aO.editInventoryCreatedByProductID(15, LocalDateTime.now(), 27);
    }
}
