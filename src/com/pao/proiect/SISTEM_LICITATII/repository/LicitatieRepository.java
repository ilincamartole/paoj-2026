package com.pao.proiect.SISTEM_LICITATII.repository;

import com.pao.proiect.SISTEM_LICITATII.utils.DatabaseConnection;
import com.pao.proiect.SISTEM_LICITATII.model.Licitatie;
import com.pao.proiect.SISTEM_LICITATII.model.Produs;
import com.pao.proiect.SISTEM_LICITATII.model.Seller;
import com.pao.proiect.SISTEM_LICITATII.enums.Categorie;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class LicitatieRepository implements Repository<Licitatie, Integer> {

    @Override
    public void save(Licitatie licitatie) {
        String sql = "INSERT INTO licitatie (id_produs, min_value) VALUES (?, ?)";
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setInt(1, licitatie.getProdus().getId());
            pstmt.setLong(2, licitatie.getMinValue());
            pstmt.executeUpdate();

            try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    licitatie.setId_licitatie(generatedKeys.getInt(1));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Optional<Licitatie> findById(Integer id) {
        String sql = "SELECT l.id_licitatie, l.min_value, " +
                "       p.id_produs, p.nume AS nume_produs, p.categorie, p.colectie, " +
                "       u.id_user, u.nume AS nume_seller, u.cnp, s.rating " +
                "FROM licitatie l " +
                "JOIN produs p ON l.id_produs = p.id_produs " +
                "JOIN utilizator u ON p.id_seller = u.id_user " +
                "JOIN utilizator_seller s ON u.id_user = s.id_seller " +
                "WHERE l.id_licitatie = ?";

        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    Seller seller = new Seller(
                            rs.getString("nume_seller"),
                            rs.getString("cnp"),
                            rs.getDouble("rating")
                    );
                    seller.setId_user(rs.getInt("id_user"));

                    Produs produs = new Produs(
                            rs.getString("nume_produs"),
                            Categorie.valueOf(rs.getString("categorie").toUpperCase()),
                            rs.getString("colectie"),
                            seller
                    );
                    produs.setId_produs(rs.getInt("id_produs"));



                    Licitatie l = new Licitatie(produs, rs.getLong("min_value"));
                    l.setId_licitatie(rs.getInt("id_licitatie"));
                    return Optional.of(l);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public List<Licitatie> findAll() {
        List<Licitatie> list = new ArrayList<>();
        String sql = "SELECT l.id_licitatie, l.min_value, " +
                "       p.id_produs, p.nume AS nume_produs, p.categorie, p.colectie, " +
                "       u.id_user, u.nume AS nume_seller, u.cnp, s.rating " +
                "FROM licitatie l " +
                "JOIN produs p ON l.id_produs = p.id_produs " +
                "JOIN utilizator u ON p.id_seller = u.id_user " +
                "JOIN utilizator_seller s ON u.id_user = s.id_seller";

        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                Seller seller = new Seller(
                        rs.getString("nume_seller"),
                        rs.getString("cnp"),
                        rs.getDouble("rating")
                );
                seller.setId_user(rs.getInt("id_user"));

                Produs produs = new Produs(
                        rs.getString("nume_produs"),
                        Categorie.valueOf(rs.getString("categorie").toUpperCase()),
                        rs.getString("colectie"),
                        seller
                );
                produs.setId_produs(rs.getInt("id_produs"));

                Licitatie l = new Licitatie(produs, rs.getLong("min_value"));
                l.setId_licitatie(rs.getInt("id_licitatie"));
                list.add(l);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public void update(Licitatie licitatie) {
        String sql = "UPDATE licitatie SET min_value = ? WHERE id_licitatie = ?";
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setLong(1, licitatie.getMinValue());
            pstmt.setInt(2, licitatie.getId_licitatie());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

//    @Override
//    public void delete(Integer id) {
//        String sql = "DELETE FROM licitatie WHERE id_licitatie = ?";
//        try (Connection conn = DatabaseConnection.getInstance().getConnection();
//             PreparedStatement pstmt = conn.prepareStatement(sql)) {
//
//            pstmt.setInt(1, id);
//            pstmt.executeUpdate();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }
@Override
public void delete(Integer id) {
    String sqlDeleteOferte = "DELETE FROM oferta WHERE id_licitatie = ?";
    String sqlDeleteLicitatie = "DELETE FROM licitatie WHERE id_licitatie = ?";

    Connection conn = null;
    try {
        conn = DatabaseConnection.getInstance().getConnection();
        conn.setAutoCommit(false);

        try {
            try (PreparedStatement pstmt = conn.prepareStatement(sqlDeleteOferte)) {
                pstmt.setInt(1, id);
                pstmt.executeUpdate();
            }

            try (PreparedStatement pstmt = conn.prepareStatement(sqlDeleteLicitatie)) {
                pstmt.setInt(1, id);
                pstmt.executeUpdate();
            }

            conn.commit();
            System.out.println("[TX] Licitatia " + id + " si ofertele ei au fost sterse.");

        } catch (SQLException e) {
            conn.rollback();
            System.out.println("[TX] Rollback — eroare la stergere: " + e.getMessage());
            throw e;
        } finally {
            conn.setAutoCommit(true);
        }

    } catch (SQLException e) {
        e.printStackTrace();
    } finally {
        if (conn != null) {
            try { conn.close(); } catch (SQLException e) { e.printStackTrace(); }
        }
    }
}
}