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
import model.Store;

/**
 *
 * @author admin
 */
public class StoreDAO extends DBContext {
    public List<Store> getAllStores() {
        List<Store> list = new ArrayList<>();
        String query = "select * from Stores";
        try {
            PreparedStatement pst = con.prepareStatement(query);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("StoreID");
                String header = rs.getString("Header");
                String info = rs.getString("Info");
                String img = rs.getString("Image");
                String time = rs.getString("TimeActivity");
                Store s = new Store(id, header, time, info, img);
                list.add(s);
            }
        } catch (SQLException e) {
            System.out.println("Error getAllStores: " + e);
        }
        return list;
    }
    
    public static void main(String[] args) {
        StoreDAO sDAO = new StoreDAO();
        List<Store> listStores = sDAO.getAllStores();
        for (Store listStore : listStores) {
            System.out.println(listStore);
        }
    }
}
