package com.pao.proiect.SISTEM_LICITATII.repository;

import com.pao.proiect.SISTEM_LICITATII.enums.Categorie;
import com.pao.proiect.SISTEM_LICITATII.model.Buyer;
import com.pao.proiect.SISTEM_LICITATII.utils.DatabaseConnection;
import com.pao.proiect.SISTEM_LICITATII.model.Oferta;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class OfertaRepository implements Repository<Oferta, Integer> {

//    @Override
//    public void save(Oferta oferta) {
//        String sql = "INSERT INTO oferta (valoare, id_buyer, id_licitatie) VALUES (?, ?, ?)";
//        try (Connection conn = DatabaseConnection.getInstance().getConnection();
//             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
//
//            pstmt.setInt(1, oferta.getValoare());
//            pstmt.setInt(2, oferta.getBuyer().getId_user());       // ID-ul moștenit de Buyer de la User
//            pstmt.setInt(3, oferta.getIdLicitatie());         // 🔴 Luat direct din obiectul Oferta!
//            System.out.println("=== DEBUG OFERTA ===");
//            System.out.println("Buyer ID: " + oferta.getBuyer().getId_user());
//            System.out.println("Licitatie ID: " + oferta.getIdLicitatie());
//            System.out.println("Valoare: " + oferta.getValoare());
//            pstmt.executeUpdate();
//
//            try(ResultSet generatedKeys = pstmt.getGeneratedKeys()){
//                if (generatedKeys.next()) {
//                    // Setează ID-ul generat direct în obiectul Licitatie primit ca parametru
//                    oferta.setId_oferta(generatedKeys.getInt(1));
//                }
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }

    @Override
    public void save(Oferta oferta) {
        String sqlInsert = "INSERT INTO oferta (valoare, id_buyer, id_licitatie) VALUES (?, ?, ?)";
        String sqlUpdate = "UPDATE licitatie SET min_value = ? WHERE id_licitatie = ?";

        Connection conn = null;
        try {
            conn = DatabaseConnection.getInstance().getConnection();
            conn.setAutoCommit(false); // ✅ dezactivam commit automat

            try {
                // Operatie 1: inserezi oferta
                try (PreparedStatement pstmt = conn.prepareStatement(sqlInsert, Statement.RETURN_GENERATED_KEYS)) {
                    pstmt.setInt(1, oferta.getValoare());
                    pstmt.setInt(2, oferta.getBuyer().getId_user());
                    pstmt.setInt(3, oferta.getIdLicitatie());
                    pstmt.executeUpdate();

                    try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                        if (generatedKeys.next()) {
                            oferta.setId_oferta(generatedKeys.getInt(1));
                        }
                    }
                }

                // Operatie 2: actualizezi min_value in licitatie
                try (PreparedStatement pstmt = conn.prepareStatement(sqlUpdate)) {
                    pstmt.setInt(1, oferta.getValoare());
                    pstmt.setInt(2, oferta.getIdLicitatie());
                    pstmt.executeUpdate();
                }

                conn.commit(); // ✅ ambele au reusit

            } catch (SQLException e) {
                conn.rollback(); // ✅ ceva a esuat, anulam tot
                throw e;
            } finally {
                conn.setAutoCommit(true); // ✅ restauram comportamentul implicit
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (conn != null) {
                try { conn.close(); } catch (SQLException e) { e.printStackTrace(); }
            }
        }
    }

    @Override
    public Optional<Oferta> findById(Integer id) {
        String sql = "SELECT o.id_oferta, o.valoare, o.id_licitatie, " +
                "       u.id_user, u.nume, u.cnp, b.categorie_preferata " +
                "FROM oferta o " +
                "JOIN utilizator u ON o.id_buyer = u.id_user " +
                "JOIN utilizator_buyer b ON u.id_user = b.id_buyer WHERE id_oferta = ?";
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    // Reconstituim obiectul citit din baza de date
                    Oferta o = new Oferta(rs.getInt("valoare"), null, rs.getInt("id_licitatie"));
                    o.setId_oferta(rs.getInt("id_oferta"));
                    return Optional.of(o);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public List<Oferta> findAll() {
        List<Oferta> oferte = new ArrayList<>();
        String sql = "SELECT o.id_oferta, o.valoare, o.id_licitatie, " +
                "       u.id_user, u.nume, u.cnp, b.categorie_preferata " +
                "FROM oferta o " +
                "JOIN utilizator u ON o.id_buyer = u.id_user " +
                "JOIN utilizator_buyer b ON u.id_user = b.id_buyer";
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                Buyer buyer = new Buyer(
                        rs.getString("nume"),
                        rs.getString("cnp"),
                        Categorie.valueOf(rs.getString("categorie_preferata"))
                );
                buyer.setId_user(rs.getInt("id_user"));

                Oferta o = new Oferta(rs.getInt("valoare"), buyer, rs.getInt("id_licitatie"));
                o.setId_oferta(rs.getInt("id_oferta"));
                oferte.add(o);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return oferte;
    }

    @Override
    public void update(Oferta oferta) {
        String sql = "UPDATE oferta SET valoare = ?, id_licitatie = ? WHERE id_oferta = ?";
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, oferta.getValoare());
            pstmt.setInt(2, oferta.getIdLicitatie());
            pstmt.setInt(3, oferta.getId_oferta());

            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Integer id) {
        String sql = "DELETE FROM oferta WHERE id_oferta = ?";
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}