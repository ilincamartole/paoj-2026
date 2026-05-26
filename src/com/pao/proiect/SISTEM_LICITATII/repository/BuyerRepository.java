package com.pao.proiect.SISTEM_LICITATII.repository;

import com.pao.proiect.SISTEM_LICITATII.enums.Categorie;
import com.pao.proiect.SISTEM_LICITATII.utils.DatabaseConnection;
import com.pao.proiect.SISTEM_LICITATII.model.User;
import com.pao.proiect.SISTEM_LICITATII.model.Buyer; // Exemplu simplu de instanțiere la citire

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class BuyerRepository {

    private final UserRepository userRepo = new UserRepository();

    public int save(Buyer buyer) {
        try (Connection conn = DatabaseConnection.getInstance().getConnection()) {

            conn.setAutoCommit(false);

            int userId = userRepo.save(buyer);

            String sql = "INSERT INTO utilizator_buyer (id_buyer, categorie_preferata) VALUES (?, ?)";

            try (PreparedStatement ps = conn.prepareStatement(sql)) {

                ps.setInt(1, userId);
                ps.setString(2, buyer.getCategoriePref().name());
                ps.executeUpdate();

                conn.commit();
                return userId;

            } catch (SQLException e) {
                conn.rollback();
                e.printStackTrace();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return -1;
    }

    public Optional<Buyer> findById(int id) {
        String sql =
                "SELECT u.nume, u.cnp, b.categorie_preferata " +
                        "FROM utilizator u JOIN utilizator_buyer b ON u.id_user = b.id_buyer " +
                        "WHERE u.id_user = ?";

        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                Buyer b = new Buyer(
                        rs.getString("nume"),
                        rs.getString("cnp"),
                        Enum.valueOf(Categorie.class, rs.getString("categorie_preferata"))
                );

                return Optional.of(b);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return Optional.empty();
    }
}