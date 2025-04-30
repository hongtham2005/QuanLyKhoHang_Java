/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author hong tham
 */
public class MySQLConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/quanlykho";
    private static final String USER = "root";
    private static final String PASSWORD = "nht666234";

    public static Connection getConnection() throws SQLException {
        try {
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            throw new SQLException("Lỗi kết nối cơ sở dữ liệu: " + e.getMessage(), e);
        }
    }
}