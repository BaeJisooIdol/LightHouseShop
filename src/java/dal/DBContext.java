/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author admin
 */
public class DBContext {

    String url = "jdbc:sqlserver://localhost:1433;databaseName=Light_House_Shop;encrypt=false";
    String user = "sa";
    String password = "1";

    protected Connection con;

    public DBContext() {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver"); // Nạp driver JDBC
            con = DriverManager.getConnection(url, user, password);
            // System.out.println("Connection established.");
        } catch (ClassNotFoundException e) {
            System.err.println("Driver not found: " + e.getMessage());
        } catch (SQLException e) {
            System.err.println("Connection failed: " + e.getMessage());
        }
    }

    public void closeConnection() {
        if (con != null) {
            try {
                con.close();
                System.out.println("Đã đóng kết nối!");
            } catch (SQLException e) {
                System.err.println("Lỗi khi đóng kết nối: " + e.getMessage());
            }
        }
    }
    
    /// Pham Quoc Tu
    public Connection getConnection() {
        return con;
    }

    public ResultSet execSelectQuery(String query, Object[] params) throws SQLException {
        if (con == null || con.isClosed()) {
            throw new SQLException("Connection is not established.");
        }
        PreparedStatement preparedStatement = con.prepareStatement(query);
        if (params != null) {
            for (int i = 0; i < params.length; i++) {
                preparedStatement.setObject(i + 1, params[i]);
            }
        }
        return preparedStatement.executeQuery();
    }

    public ResultSet execSelectQuery(String query) throws SQLException {
        return this.execSelectQuery(query, null);
    }

    public int execQuery(String query, Object[] params) throws SQLException {
        if (con == null || con.isClosed()) {
            throw new SQLException("Connection is not established.");
        }
        PreparedStatement preparedStatement = con.prepareStatement(query);
        if (params != null) {
            for (int i = 0; i < params.length; i++) {
                preparedStatement.setObject(i + 1, params[i]);
            }
        }
        return preparedStatement.executeUpdate();
    }
    /// Pham Quoc Tu

}
