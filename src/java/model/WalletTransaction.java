/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

/**
 *
 * @author admin
 */
public class WalletTransaction {
    private int id;
    private int wId;
    private double amount;
    private LocalDateTime transactionDate;
    private String note;
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    NumberFormat formatterAmount = NumberFormat.getInstance(new Locale("vi", "VN"));

    public WalletTransaction(int id, int wId, double amount, LocalDateTime transactionDate, String note) {
        this.id = id;
        this.wId = wId;
        this.amount = amount;
        this.transactionDate = transactionDate;
        this.note = note;
    }

    public WalletTransaction(double amount, LocalDateTime transactionDate, String note) {
        this.amount = amount;
        this.transactionDate = transactionDate;
        this.note = note;
    }    

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getwId() {
        return wId;
    }

    public void setwId(int wId) {
        this.wId = wId;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getTransactionDate() {
        return transactionDate.format(formatter);
    }

    public void setTransactionDate(LocalDateTime transactionDate) {
        this.transactionDate = transactionDate;
    }

    @Override
    public String toString() {
        return "WalletTransaction{" + "id=" + id + ", wId=" + wId + ", amount=" + amount + ", transactionDate=" + transactionDate + ", note=" + note + '}';
    }
    
    public String formatPrice(double price) {
        return formatterAmount.format(amount);
    }
    
}
