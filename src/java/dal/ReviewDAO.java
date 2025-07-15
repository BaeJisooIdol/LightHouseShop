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
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import model.Review;

/**
 *
 * @author admin
 */
public class ReviewDAO extends DBContext {

    public List<Review> getReviewByPid(int id) {
        List<Review> list = new ArrayList<>();
        String query = "select * from Reviews where ProductID = ?";
        try {
            PreparedStatement pst = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            pst.setInt(1, id);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                int rId = rs.getInt("ReviewID");
                int pId = rs.getInt("ProductID");
                int uId = rs.getInt("UserID");
                int rating = rs.getInt("Rating");
                String comment = rs.getString("Comment");
                LocalDateTime reviewDateTime = rs.getTimestamp("ReviewDate").toLocalDateTime();
                LocalDate reviewDate = reviewDateTime.toLocalDate();
                Review r = new Review(rId, pId, uId, rating, comment, reviewDate);
                list.add(r);
            }

        } catch (SQLException e) {
            System.out.println("err getReviewByPid: " + e);
        }
        return list;
    }

    public int createReview(int userID, int productID, int rating, String comment) {
        String query = "select max(ReviewID) + 1 nextID from Reviews";
        try {
            PreparedStatement pstID = con.prepareStatement(query);
            ResultSet rs = pstID.executeQuery();
            if (rs.next()) {
                int nextID = rs.getInt("nextID");
                query = "insert into Reviews\n"
                        + "values(?, ?, ?, ?, ?, ?)";
                PreparedStatement pstReview = con.prepareStatement(query);
                pstReview.setInt(1, nextID);
                pstReview.setInt(2, productID);
                pstReview.setInt(3, userID);
                pstReview.setInt(4, rating);
                pstReview.setString(5, comment);
                pstReview.setTimestamp(6, Timestamp.valueOf(LocalDateTime.now()));
                pstReview.executeUpdate();
                return nextID;
            }

        } catch (SQLException e) {
            System.out.println("err createReview: " + e);
        }
        return -1;
    }

    public void deleteReview(int id) {
        String query = "delete from Reviews where ReviewID = ?";
        try {
            PreparedStatement pst = con.prepareStatement(query);
            pst.setInt(1, id);
            pst.executeUpdate();

        } catch (SQLException e) {
            System.out.println("err deleteReview: " + e);
        }
    }

    public boolean checkValidToComment(int uId, int limit) {
        String query = "select NumberOfComment\n"
                + "from Users\n"
                + "where UserID = ?";
        try {
            PreparedStatement pst = con.prepareStatement(query);
            pst.setInt(1, uId);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                return rs.getInt("NumberOfComment") >= limit;
            }

        } catch (SQLException e) {
            System.out.println("err checkValidToComment: " + e);
        }
        return false;
    }

    public void updateNumberOfComment(int uId) {
        System.out.println("up");
        String query = "UPDATE Users \n"
                + "SET NumberOfComment = COALESCE(NumberOfComment, 0) + 1 \n"
                + "WHERE UserID = ?";
        try {
            PreparedStatement pst = con.prepareStatement(query);
            pst.setInt(1, uId);
            pst.executeUpdate();

        } catch (SQLException e) {
            System.out.println("err updateNumberOfComment: " + e);
        }
    }

    public static void main(String[] args) {
        ReviewDAO d = new ReviewDAO();
        List<Review> list = d.getReviewByPid(3);
        for (Review review : list) {
            System.out.println(review);
        }
    }
}
