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
import model.Category;

/**
 *
 * @author admin
 */
public class CategoryDAO extends DBContext {
    public int getTotalCategory() {
        String query = "select count(CategoryID) Total \n"
                + "from Categories";
        try {
            PreparedStatement pst = con.prepareStatement(query);
            ResultSet rs = pst.executeQuery();
            if(rs.next()) {
                return rs.getInt("Total");
            }
        } catch (SQLException e) {
            System.out.println("getTotalCategory: " + e);
        }
        return -1;
    }
    
    public List<Category> getAllCategory() {

        List<Category> list = new ArrayList<>();

        String query = "SELECT * FROM CATEGORIES";

        try ( ResultSet rs = execSelectQuery(query)) {

            while (rs.next()) {
                list.add(new Category(rs.getInt(1), rs.getString(2), rs.getString(3)));
            }

        } catch (SQLException exception) {
            System.out.println(exception);
        }
        return list;
    }
}
