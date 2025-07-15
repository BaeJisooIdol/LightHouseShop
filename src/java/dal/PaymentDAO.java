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
import model.Payment;

/**
 *
 * @author admin
 */
public class PaymentDAO extends DBContext {

    public int createPayment(String method, String describe) {
        String query = "select count(PaymentID) + 1 nextPaymentID from Payment";
        try {
            PreparedStatement pst = con.prepareStatement(query);
            ResultSet rsID = pst.executeQuery();
            if (rsID.next()) {
                int nextPaymentID = rsID.getInt("nextPaymentID");
                query = "insert into Payment\n"
                        + "values(?, ?, ?, GETDATE())";
                PreparedStatement pst2 = con.prepareStatement(query);
                pst2.setInt(1, nextPaymentID);
                pst2.setString(2, method);
                pst2.setString(3, describe);
                pst2.executeUpdate();
                return nextPaymentID;
            }
        } catch (SQLException e) {
            System.out.println("Error createPayment: " + e);
        }
        return -1;
    }
    
    public List<Payment> getAllPayment() {
        List<Payment> list = new ArrayList<>();
        String query = "SELECT * FROM Payment";
        try ( ResultSet rs = execSelectQuery(query)) {
            while (rs.next()) {
                int paymentID = rs.getInt("paymentID");
                String paymentMethod = rs.getString("paymentMethod");
                String description = rs.getString("description");
                list.add(new Payment(paymentID, paymentMethod, description));
            }
        } catch (SQLException exception) {
            System.out.println("getAllPayment: " + exception);
        }

        return list;
    }
    
    
}
