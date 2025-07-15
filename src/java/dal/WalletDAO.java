/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import model.Wallet;
import model.WalletTransaction;

/**
 *
 * @author admin
 */
public class WalletDAO extends DBContext {

    public Wallet getWalletByUid(int userID) {
        String query = "select * from Wallet where UserID = ?";
        try {
            PreparedStatement pst = con.prepareStatement(query);
            pst.setInt(1, userID);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                int wId = rs.getInt("WalletID");
                double balance = rs.getDouble("balance");
                LocalDateTime last = rs.getTimestamp("LastUpdated").toLocalDateTime();
                return new Wallet(wId, userID, balance, last);
            }
        } catch (SQLException e) {
            System.out.println("Error getWalletByUid: " + e);
        }
        return null;
    }

    public int createWalletTransaction(int walletID, double amount, LocalDateTime date, String note) {
        String query = "select count(TransactionID) + 1 nextID from WalletTransactions";
        try {
            PreparedStatement pst = con.prepareStatement(query);
            ResultSet rsID = pst.executeQuery();
            if (rsID.next()) {
                int nextID = rsID.getInt("nextID");
                query = "insert into WalletTransactions\n"
                        + "values(?, ?, ?, ?, ?)";
                PreparedStatement pst2 = con.prepareStatement(query);
                pst2.setInt(1, nextID);
                pst2.setInt(2, walletID);
                pst2.setDouble(3, amount);
                pst2.setTimestamp(4, Timestamp.valueOf(date));
                pst2.setString(5, note);
                pst2.executeUpdate();
                return nextID;
            }
        } catch (SQLException e) {
            System.out.println("Error createWalletTransaction: " + e);
        }
        return -1;
    }

    public void updateWallet(int userID, double money, String action) {
        String query = "update Wallet\n"
                + "set Balance = Balance "+ action +" ?\n"
                + "where UserID = ?";
        try {
            PreparedStatement pst = con.prepareStatement(query);
            pst.setDouble(1, money);
            pst.setInt(2, userID);
            pst.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error updateWallet: " + e);
        }
    }

    public int createWallet(int userID) {
        String query = "select max(WalletID) + 1 nextID from Wallet";
        try {
            PreparedStatement pst = con.prepareStatement(query);
            ResultSet rsID = pst.executeQuery();
            if (rsID.next()) {
                int nextID = rsID.getInt("nextID");
                query = "insert into Wallet\n"
                        + "values(?, ?, ?, ?)";
                PreparedStatement pst2 = con.prepareStatement(query);
                pst2.setInt(1, nextID);
                pst2.setInt(2, userID);
                pst2.setDouble(3, 0.0);
                pst2.setTimestamp(4, Timestamp.valueOf(LocalDateTime.now()));
                pst2.executeUpdate();
                return nextID;
            }
        } catch (SQLException e) {
            System.out.println("Error createWallet: " + e);
        }
        return -1;
    }

    public int getNumberOfWalletTransactions(int walletID) {
        String query = "select count(TransactionID) numberOfTransactions\n"
                + "from WalletTransactions\n"
                + "where WalletID = ?";
        try {
            PreparedStatement pst = con.prepareStatement(query);
            pst.setInt(1, walletID);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                return rs.getInt("numberOfTransactions");
            }
        } catch (SQLException e) {
            System.out.println("Error getNumberOfWalletTransactions: " + e);
        }
        return -1;
    }

    public List<WalletTransaction> getAllWalletTransactions(int walletID, int page, int item) {
        List<WalletTransaction> list = new ArrayList<>();
        String query = "select *\n"
                + "from WalletTransactions\n"
                + "where WalletID = ?\n"
                + "order by TransactionDate desc\n"
                + "offset ? rows fetch next ? rows only";
        try {
            PreparedStatement pst = con.prepareStatement(query);
            pst.setInt(1, walletID);
            pst.setInt(2, (page - 1) * item);
            pst.setInt(3, item);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("TransactionID");
                double amount = rs.getDouble("Amount");
                LocalDateTime date = rs.getTimestamp("TransactionDate").toLocalDateTime();
                String note = rs.getString("Note");
                WalletTransaction wt = new WalletTransaction(id, walletID, amount, date, note);
                list.add(wt);
            }
        } catch (SQLException e) {
            System.out.println("Error getAllWalletTransactions: " + e);
        }
        return list;
    }

    public List<WalletTransaction> getAllWalletTransactionsByDate(int walletID, String value) {
        List<WalletTransaction> list = new ArrayList<>();
       String query = "select * from WalletTransactions " +
               "where WalletID = ? and format(TransactionDate, 'yyyy-MM-dd HH:mm:ss') like ? " +
               "order by TransactionDate desc";

        try {
            PreparedStatement pst = con.prepareStatement(query);
            pst.setInt(1, walletID);
            pst.setString(2, "%" + value + "%");
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("TransactionID");
                double amount = rs.getDouble("Amount");
                LocalDateTime date = rs.getTimestamp("TransactionDate").toLocalDateTime();
                String note = rs.getString("Note");
                WalletTransaction wt = new WalletTransaction(id, walletID, amount, date, note);
                list.add(wt);
            }
        } catch (SQLException e) {
            System.out.println("Error getAllWalletTransactionsByDate: " + e);
        }
        return list;
    }

    public boolean checkInfoWalletTransaction(int walletID, int id) {
        String query = "select TransactionID\n"
                + "from WalletTransactions \n"
                + "where WalletID = ? and TransactionID = ?";
        try {
            PreparedStatement pst = con.prepareStatement(query);
            pst.setInt(1, walletID);
            pst.setInt(2, id);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                return true;
            }
        } catch (SQLException e) {
            System.out.println("Error checkInfoWalletTransaction: " + e);
        }
        return false;
    }
    
    public void deleteWalletTransaction(int id) {
        String query = "delete from WalletTransactions where TransactionID = ?";
        try {
            PreparedStatement pst = con.prepareStatement(query);
            pst.setInt(1, id);
            pst.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error deleteWalletTransaction: " + e);
        }
    }

    public static void main(String[] args) {
        WalletDAO w = new WalletDAO();
//        List<WalletTransaction> lists = w.getAllWalletTransactionsByDesc(1, "a");
//        for (WalletTransaction list : lists) {
//            System.out.println(list);
//        }
    }
}
