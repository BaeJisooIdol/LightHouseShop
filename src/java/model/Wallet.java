/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.time.LocalDateTime;

/**
 *
 * @author admin
 */
public class Wallet {
    private int id;
    private int uId;
    private double balance;
    private LocalDateTime lastUpdate;

    public Wallet(int id, int uId, double balance, LocalDateTime lastUpdate) {
        this.id = id;
        this.uId = uId;
        this.balance = balance;
        this.lastUpdate = lastUpdate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getuId() {
        return uId;
    }

    public void setuId(int uId) {
        this.uId = uId;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public LocalDateTime getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(LocalDateTime lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    @Override
    public String toString() {
        return "Wallet{" + "id=" + id + ", uId=" + uId + ", balance=" + balance + ", lastUpdate=" + lastUpdate + '}';
    }
    
}
