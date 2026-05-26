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
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setInt(1, oferta.getValoare());
            pstmt.setInt(2, oferta.getBuyer().getId_user());       // ID-ul moștenit de Buyer de la User
            pstmt.setInt(3, oferta.getIdLicitatie());         // 🔴 Luat direct din obiectul Oferta!

            pstmt.executeUpdate();

            try(ResultSet generatedKeys = pstmt.getGeneratedKeys()){
                if (generatedKeys.next()) {
                    // Setează ID-ul generat direct în obiectul Licitatie primit ca parametru
                    oferta.setId_oferta(generatedKeys.getInt(1));
                }
            }
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
        String sql = "SELECT * FROM oferta";
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                Oferta o = new Oferta(rs.getInt("valoare"), null, rs.getInt("id_licitatie"));
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