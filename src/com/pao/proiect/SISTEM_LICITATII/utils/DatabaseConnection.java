package com.pao.proiect.SISTEM_LICITATII.utils;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public final class DatabaseConnection {

    // 1. Instanța unică (Singleton)
    private static DatabaseConnection instance;

    // Păstrăm doar parametrii de configurare în instanță
    private String url;
    private String user;
    private String pass;

    // 2. Constructor privat — prinde toate excepțiile intern ca să fie curat în exterior
    private DatabaseConnection() {
        Properties props = new Properties();

        // Citim db.properties din resources/
        try (InputStream is = getClass().getClassLoader().getResourceAsStream("db.properties")) {
            if (is == null) {
                throw new IOException("Nu s-a găsit fișierul db.properties în folderul resources/");
            }
            props.load(is);

            this.url = props.getProperty("db.url");
            this.user = props.getProperty("db.user");
            this.pass = props.getProperty("db.password");

            // Încărcăm explicit driverul de MySQL pentru siguranță
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("[DB] Configurația bazei de date a fost încărcată cu succes!");

        } catch (IOException e) {
            System.err.println("[DB] Eroare la citirea fișierului de proprietăți: " + e.getMessage());
        } catch (ClassNotFoundException e) {
            System.err.println("[DB] Driverul JDBC MySQL nu a fost găsit! " + e.getMessage());
        }
    }

    /**
     * 3. Metoda globală de acces pentru Singleton.
     * Nu mai aruncă nicio excepție (No throws!), deci funcționează perfect în repouri.
     */
    public static synchronized DatabaseConnection getInstance() {
        if (instance == null) {
            instance = new DatabaseConnection();
        }
        return instance;
    }

    /**
     * 4. Returnează o conexiune deschisă NOUĂ către MySQL la fiecare apel.
     * Aruncă doar SQLException, pe care repository-urile tale o prind deja în catch.
     */
    public Connection getConnection() throws SQLException {
        if (url == null || user == null || pass == null) {
            throw new SQLException("Configurația bazei de date nu a fost încărcată. Verifică db.properties!");
        }
        return DriverManager.getConnection(url, user, pass);
    }
}