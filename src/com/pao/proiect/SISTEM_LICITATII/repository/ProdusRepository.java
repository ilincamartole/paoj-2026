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
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, produs.getNume());
            pstmt.setString(2, produs.toString()); // Sau metodă de luat String-ul din Enum
            // Pentru simplitate, presupunem că avem o metodă ajutătoare sau salvăm numele enum-ului
            // pstmt.setString(2, produs.getCategorie().name());
            pstmt.setString(3, "Colectie Generica");
            pstmt.setInt(4, 1); // un ID de seller de test existent în DB
            pstmt.executeUpdate();
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
                    Produs p = new Produs(rs.getString("nume"), Categorie.ARTA, rs.getString("colectie"), null);
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
                produse.add(new Produs(rs.getString("nume"), Categorie.ARTA, rs.getString("colectie"), null));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return produse;
    }

    @Override
    public void update(Produs produs) {
        String sql = "UPDATE produs SET nume = ?, colectie = ? WHERE id_produs = ?";
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, produs.getNume());
            pstmt.setString(2, "Colectie Noua");
            pstmt.setInt(3, produs.getId());
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