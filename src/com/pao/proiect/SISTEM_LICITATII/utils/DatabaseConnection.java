package com.pao.proiect.SISTEM_LICITATII.utils;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DatabaseConnection {
    // 1. Instanța unică (Singleton)
    private static DatabaseConnection instance;
    private Connection connection;

    // 2. Constructor privat - previne instanțierea din exterior
    private DatabaseConnection() {
        Properties properties = new Properties();

        // Citim fișierul db.properties din folderul resources
        try (InputStream input = getClass().getClassLoader().getResourceAsStream("db.properties")) {
            if (input == null) {
                System.out.println("Ne pare rău, nu am găsit fișierul db.properties!");
                return;
            }
            properties.load(input);

            String url = properties.getProperty("db.url");
            String user = properties.getProperty("db.user");
            String password = properties.getProperty("db.password");

            // Înregistrăm driverul de MySQL (opțional în versiunile noi, dar sigur pentru facultate)
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Creăm conexiunea fizică
            this.connection = DriverManager.getConnection(url, user, password);
            System.out.println("Conexiunea la baza de date a fost realizată cu succes!");

        } catch (IOException e) {
            System.out.println("Eroare la citirea fișierului de proprietăți: " + e.getMessage());
        } catch (ClassNotFoundException e) {
            System.out.println("Driverul JDBC MySQL nu a fost găsit! Adaugă .jar-ul în proiect. " + e.getMessage());
        } catch (SQLException e) {
            System.out.println("Eroare la conectarea la baza de date: " + e.getMessage());
        }
    }

    // 3. Metoda globală de acces pentru Singleton (Thread-safe elementar)
    public static synchronized DatabaseConnection getInstance() {
        if (instance == null) {
            instance = new DatabaseConnection();
        }
        return instance;
    }

    // Metodă pentru a obține obiectul Connection reutilizabil
    public Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            Properties properties = new Properties();
            try (InputStream input = getClass().getClassLoader().getResourceAsStream("db.properties")) {
                if (input != null) {
                    properties.load(input);
                    String url = properties.getProperty("db.url");
                    String user = properties.getProperty("db.user");
                    String password = properties.getProperty("db.password");
                    this.connection = DriverManager.getConnection(url, user, password);
                }
            } catch (IOException e) {
                throw new SQLException("Nu s-a putut reîncărca db.properties", e);
            }
        }
        return connection;
    }
}
