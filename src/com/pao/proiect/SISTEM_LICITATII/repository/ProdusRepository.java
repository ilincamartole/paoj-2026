package com.pao.proiect.SISTEM_LICITATII.repository;

import com.pao.proiect.SISTEM_LICITATII.utils.DatabaseConnection;
import com.pao.proiect.SISTEM_LICITATII.enums.Categorie;
import com.pao.proiect.SISTEM_LICITATII.model.Produs;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ProdusRepository implements Repository<Produs, Integer> {

    @Override
    public void save(Produs produs) {
        String sql = "INSERT INTO produs (nume, categorie, colectie, id_seller) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setString(1, produs.getNume());
            pstmt.setString(2, produs.getCategorie().name());
            pstmt.setString(3, produs.getColectie());
            pstmt.setInt(4, produs.getSeller().getId_user());

            pstmt.executeUpdate();

            try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    produs.setId_produs(generatedKeys.getInt(1));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Optional<Produs> findById(Integer id) {
        String sql = "SELECT * FROM produs WHERE id_produs = ?";
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    Produs p = new Produs(
                            rs.getString("nume"),
                            Categorie.valueOf(rs.getString("categorie")),
                            rs.getString("colectie"),
                            null
                    );
                    p.setId_produs(rs.getInt("id_produs"));
                    return Optional.of(p);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public List<Produs> findAll() {
        List<Produs> produse = new ArrayList<>();
        String sql = "SELECT * FROM produs";
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                Produs p = new Produs(
                        rs.getString("nume"),
                        Categorie.valueOf(rs.getString("categorie")),
                        rs.getString("colectie"),
                        null
                );
                p.setId_produs(rs.getInt("id_produs"));
                produse.add(p);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return produse;
    }

    @Override
    public void update(Produs produs) {
        String sql = "UPDATE produs SET nume = ?, categorie = ?, colectie = ? WHERE id_produs = ?";
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, produs.getNume());
            pstmt.setString(2, produs.getCategorie().name());
            pstmt.setString(3, produs.getColectie());
            pstmt.setInt(4, produs.getId());

            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Integer id) {
        String sql = "DELETE FROM produs WHERE id_produs = ?";
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}