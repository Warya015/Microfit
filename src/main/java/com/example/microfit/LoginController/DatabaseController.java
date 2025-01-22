package com.example.microfit.LoginController;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseController {

    // Database-URL, gebruikersnaam en wachtwoord
    private static final String URL = "jdbc:mysql://localhost:3306/microfit";
    private static final String USER = "root"; // Vervang met jouw MySQL-gebruikersnaam
    private static final String PASSWORD = "tymvym-8dajwu-woThyw"; // Vervang met jouw MySQL-wachtwoord

    /**

     Maakt verbinding met de MySQL-database.*
     @return Connection object als de verbinding slaagt, anders null.*/
    public static Connection getConnection() {
        try {// Probeer verbinding te maken met de database
            Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Verbinding met de database is gelukt.");
            return connection;} catch (SQLException e) {// Print de fout en geef null terug als verbinding mislukt
            e.printStackTrace();
            System.err.println("Fout: Verbinding met de database mislukt.");
            return null;}}
}