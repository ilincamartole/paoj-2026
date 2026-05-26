package com.pao.proiect.SISTEM_LICITATII.repository;

import com.pao.proiect.SISTEM_LICITATII.utils.DatabaseConnection;
import com.pao.proiect.SISTEM_LICITATII.model.Licitatie;

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
                    // Setează ID-ul generat direct în obiectul Licitatie primit ca parametru
                    licitatie.setId_licitatie(generatedKeys.getInt(1));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Optional<Licitatie> findById(Integer id) {
        String sql = "SELECT * FROM licitatie WHERE id_licitatie = ?";
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    Licitatie l = new Licitatie(null, rs.getLong("min_value"));
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
        String sql = "SELECT * FROM licitatie";
        // CORECTAT: rs este adăugat direct în resursele blocului try principale
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                Licitatie l = new Licitatie(null, rs.getLong("min_value"));
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
        String sql = "UPDATE licitatie SET min_value = ? WHERE id_produs = ?";
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setLong(1, licitatie.getMinValue());
            pstmt.setInt(2, licitatie.getProdus().getId());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Integer id) {
        String sql = "DELETE FROM licitatie WHERE id_licitatie = ?";
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}