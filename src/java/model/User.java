/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.util.Locale;

/**
 *
 * @author admin
 */
public class User {

    private String id;
    private String name;
    private String password;
    private String email;
    private String fullName;
    private String phone;
    private String address;
    private String role;
    private LocalDateTime createdAt;
    private String picture;
    private String spending;
    private double balance;
    private boolean status;
    private NumberFormat formatter = NumberFormat.getInstance(new Locale("vi", "VN"));

    public User(String id, String name, String password, String email, String fullName, String phone, String address, String role, LocalDateTime createdAt, String picture, double balance) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.email = email;
        this.fullName = fullName;
        this.phone = phone;
        this.address = address;
        this.role = role;
        this.createdAt = createdAt;
        this.picture = picture;
        this.balance = balance;
    }

    public User(String id, String name, String email, String picture) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.picture = picture;
    }

    public User(String id, String name, String email, String fullName, String picture, double spending) {
        Product p = new Product();
        this.id = id;
        this.name = name;
        this.email = email;
        this.fullName = fullName;
        this.picture = picture;
        this.spending = p.formatPrice(spending);
    }

    public User(String id, String name, String password, String email, String fullName, String phone ,String role, boolean status, String picture) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.password = password;
        this.fullName = fullName;
        this.role = role;
        this.status = status;
        this.picture = picture;
    }

    public User(String name, String password, String fullName, String phone, String email, String role, String picture, boolean status) {
        this.name = name;
        this.password = password;
        this.fullName = fullName;
        this.phone = phone;
        this.email = email;
        this.role = role;
        this.picture = picture;
        this.status = status;
    }
    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getSpending() {
        return spending;
    }

    public void setSpending(String spending) {
        this.spending = spending;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    @Override
    public String toString() {
        return "User{" + "id=" + id + ", name=" + name + ", password=" + password + ", email=" + email + ", fullName=" + fullName + ", phone=" + phone + ", address=" + address + ", role=" + role + ", createdAt=" + createdAt + ", picture=" + picture + ", spending=" + spending + ", balance=" + balance + '}';
    }
    
    
    
    public String formatPrice(double price) {
        return formatter.format(price);
    }

    public NumberFormat getFormatter() {
        return formatter;
    }

    public void setFormatter(NumberFormat formatter) {
        this.formatter = formatter;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
