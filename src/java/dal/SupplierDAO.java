/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.Supplier;

/**
 *
 * @author admin
 */
public class SupplierDAO extends DBContext {

    public List<Supplier> getAllSuppliers() {
        List<Supplier> list = new ArrayList<>();
        String query = "select * from Suppliers";
        try ( PreparedStatement pst = con.prepareStatement(query);) {
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("SupplierID");
                String name = rs.getString("SupplierName");
                String contactName = rs.getString("ContactName");
                String phone = rs.getString("Phone");
                String email = rs.getString("Email");
                String address = rs.getString("Address");
                Supplier s = new Supplier(id, name, contactName, phone, email, address);
                list.add(s);
            }
        } catch (SQLException e) {
            System.out.println("getAllSuppliers: " + e);
        }
        return list;
    }

    public List<Supplier> getAllSuppliers(int page, int item) {
        List<Supplier> list = new ArrayList<>();
        String query = "select *\n"
                + "from Suppliers\n"
                + "order by [SupplierID]\n"
                + "offset ? rows fetch next ? rows only";
        try ( PreparedStatement pst = con.prepareStatement(query);) {
            pst.setInt(1, (page - 1) * item );
            pst.setInt(2, item);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("SupplierID");
                String name = rs.getString("SupplierName");
                String contactName = rs.getString("ContactName");
                String phone = rs.getString("Phone");
                String email = rs.getString("Email");
                String address = rs.getString("Address");
                Supplier s = new Supplier(id, name, contactName, phone, email, address);
                list.add(s);
            }
        } catch (SQLException e) {
            System.out.println("getAllSuppliers - Paging: " + e);
        }
        return list;
    }

    public List<Supplier> getSupplierBySupplierName(String value) {
        List<Supplier> list = new ArrayList<>();
        String query = "select * from Suppliers where SupplierName like '%";
        query += value + "%'";
        try ( PreparedStatement pst = con.prepareStatement(query);) {
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("SupplierID");
                String name = rs.getString("SupplierName");
                String contactName = rs.getString("ContactName");
                String phone = rs.getString("Phone");
                String email = rs.getString("Email");
                String address = rs.getString("Address");
                Supplier s = new Supplier(id, name, contactName, phone, email, address);
                list.add(s);
            }
        } catch (SQLException e) {
            System.out.println("getSupplierBySupplierName: " + e);
        }
        return list;
    }

    public int getTotalSupplier() {
        String query = "select count(SupplierID) total from Suppliers";
        try ( PreparedStatement pst = con.prepareStatement(query);) {
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                return rs.getInt("total");
            }
        } catch (SQLException e) {
            System.out.println("getTotalSupplier: " + e);
        }
        return -1;
    }
    
    public void addSupplier(String supplierName, String contactName, String phone, String email, String address) {
        String query = "insert into Suppliers values(?, ?, ?, ?, ?)";
        try ( PreparedStatement pst = con.prepareStatement(query);) {
            pst.setString(1, supplierName);
            pst.setString(2, contactName);
            pst.setString(3, phone);
            pst.setString(4, email);
            pst.setString(5, address);
            pst.executeUpdate();
            
        } catch (SQLException e) {
            System.out.println("addSupplier: " + e);
        }
    }
    
    public void editSupplier(String id, String supplierName, String contactName, String phone, String email, String address) {
        String query = "update Suppliers set SupplierName = ?, ContactName = ?, Phone = ?, Email = ?, Address = ? where SupplierID = ?";
        try ( PreparedStatement pst = con.prepareStatement(query);) {
            pst.setString(1, supplierName);
            pst.setString(2, contactName);
            pst.setString(3, phone);
            pst.setString(4, email);
            pst.setString(5, address);
            pst.setInt(6, Integer.parseInt(id));
            pst.executeUpdate();
            
        } catch (SQLException e) {
            System.out.println("editSupplier: " + e);
        }
    }
    
    public void deteleSupplier(int id) {
        String query = "delete from Suppliers where SupplierID = ?";
        try ( PreparedStatement pst = con.prepareStatement(query);) {
            pst.setInt(1, id);
            pst.executeUpdate();
            
        } catch (SQLException e) {
            System.out.println("deteleSupplier: " + e);
        }
    }
    
    public Supplier getSupplierById(int id) {
         String query = "select * from Suppliers where SupplierID = ?";
        try ( PreparedStatement pst = con.prepareStatement(query);) {
            pst.setInt(1, id);
            ResultSet rs = pst.executeQuery();
            if(rs.next()) {
                int sId = rs.getInt("SupplierID");
                String name = rs.getString("SupplierName");
                String contactName = rs.getString("ContactName");
                String phone = rs.getString("Phone");
                String email = rs.getString("Email");
                String address = rs.getString("Address");
                Supplier s = new Supplier(sId, name, contactName, phone, email, address);
                return s;
            }
            
        } catch (SQLException e) {
            System.out.println("isExistSupplier: " + e);
        }
        return null;
    }

    public static void main(String[] args) {
        SupplierDAO sDAO = new SupplierDAO();
        List<Supplier> list = sDAO.getSupplierBySupplierName("phê");
        for (Supplier supplier : list) {
            System.out.println(supplier);
        }
    }
}
