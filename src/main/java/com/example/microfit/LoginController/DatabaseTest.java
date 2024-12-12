package com.example.microfit.LoginController;

import java.sql.Connection;

public class DatabaseTest {
    public static void main(String[] args) {
        System.out.println("Testing database connection...");
        Connection connection = DatabaseController.getConnection();

        if (connection != null) {
            System.out.println("Database connection successful!");
        } else {
            System.out.println("Database connection failed.");
        }
    }
}