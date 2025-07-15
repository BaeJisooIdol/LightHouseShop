/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import model.New;

/**
 *
 * @author admin
 */
public class NewDAO extends DBContext {

    public List<New> getAllNews() {
        List<New> list = new ArrayList<>();
        String query = "select * from News";
        try {
            PreparedStatement pst = con.prepareStatement(query);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("NewID");
                String header = rs.getString("Header");
                String headerInfo = rs.getString("Header_Info");
                String img = rs.getString("Image");
                String content = rs.getString("Content");
                LocalDateTime start = rs.getTimestamp("StartDate").toLocalDateTime();
                LocalDateTime end = rs.getTimestamp("EndDate").toLocalDateTime();
                New n = new New(id, header, headerInfo, img, content, start, end);
                list.add(n);
            }
        } catch (SQLException e) {
            System.out.println("Error getAllNews: " + e);
        }
        return list;
    }

    public New getNewById(int id) {
        String query = "select * from News where NewID = ?";
        try {
            PreparedStatement pst = con.prepareStatement(query);
            pst.setInt(1, id);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                String header = rs.getString("Header");
                String headerInfo = rs.getString("Header_Info");
                String img = rs.getString("Image");
                String content = rs.getString("Content");
                LocalDateTime start = rs.getTimestamp("StartDate").toLocalDateTime();
                LocalDateTime end = rs.getTimestamp("EndDate").toLocalDateTime();
                New n = new New(id, header, headerInfo, img, content, start, end);
                return n;
            }
        } catch (SQLException e) {
            System.out.println("Error getAllNews: " + e);
        }
        return null;
    }

    public static void main(String[] args) {
        NewDAO nDAO = new NewDAO();
//        List<New> lists = nDAO.getAllNews();
//        for (New list : lists) {
//            System.out.println(list);
//        }
        New n = nDAO.getNewById(1);
        System.out.println(n);
    }
}
