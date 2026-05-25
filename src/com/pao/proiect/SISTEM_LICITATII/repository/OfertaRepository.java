package com.pao.proiect.SISTEM_LICITATII.repository;

import com.pao.proiect.SISTEM_LICITATII.utils.DatabaseConnection;
import com.pao.proiect.SISTEM_LICITATII.model.Oferta;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class OfertaRepository implements Repository<Oferta, Integer> {

    @Override
    public void save(Oferta oferta) {
        String sql = "INSERT INTO oferta (valoare, id_buyer, id_licitatie) VALUES (?, ?, ?)";
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, oferta.getValoare());
            pstmt.setInt(2, 1); // ID-ul unui Buyer existent de test
            pstmt.setInt(3, 1); // ID-ul unei Licitații existente de test
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Optional<Oferta> findById(Integer id) {
        String sql = "SELECT * FROM oferta WHERE id_oferta = ?";
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    Oferta o = new Oferta(rs.getInt("valoare"), null);
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
        String sql = "SELECT * FROM oferta";
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                oferte.add(new Oferta(rs.getInt("valoare"), null));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return oferte;
    }

    @Override
    public void update(Oferta oferta) {
        String sql = "UPDATE oferta SET valoare = ? WHERE id_oferta = ?";
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // Folosim o valoare de test sau o metodă getId() dacă o adaugi în clasă
            pstmt.setInt(1, oferta.getValoare());
            pstmt.setInt(2, 1);
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