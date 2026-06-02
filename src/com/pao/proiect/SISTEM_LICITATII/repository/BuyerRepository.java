package com.pao.proiect.SISTEM_LICITATII.repository;

import com.pao.proiect.SISTEM_LICITATII.enums.Categorie;
import com.pao.proiect.SISTEM_LICITATII.model.Buyer;
import com.pao.proiect.SISTEM_LICITATII.utils.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class BuyerRepository implements Repository<Buyer, Integer> {

    private final UserRepository userRepo = new UserRepository();

    @Override
    public void save(Buyer buyer) {

        String sql = "INSERT INTO utilizator_buyer (id_buyer, categorie_preferata) VALUES (?, ?)";

        try (Connection conn = DatabaseConnection.getInstance().getConnection()) {
            conn.setAutoCommit(false);

            try {
                int userId = userRepo.save(buyer, conn);


                buyer.setId_user(userId);
                buyer.setId_user(userId);

                try (PreparedStatement ps = conn.prepareStatement(sql)) {
                    ps.setInt(1, userId);
                    ps.setString(2, buyer.getCategoriePref().name());
                    ps.executeUpdate();
                }

                conn.commit();

            } catch (SQLException e) {
                conn.rollback();
                throw e;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Optional<Buyer> findById(Integer id) {
        String sql = "SELECT u.id_user as id_buyer, u.nume, u.cnp, b.categorie_preferata " +
                "FROM utilizator u JOIN utilizator_buyer b ON u.id_user = b.id_buyer " +
                "WHERE u.id_user = ?";

        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Buyer b = new Buyer(
                            rs.getString("nume"),
                            rs.getString("cnp"),
                            Categorie.valueOf(rs.getString("categorie_preferata"))
                    );
                    b.setId_user(rs.getInt("id_buyer"));
                    return Optional.of(b);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public List<Buyer> findAll() {
        List<Buyer> buyers = new ArrayList<>();
        String sql = "SELECT u.id_user AS id_buyer, u.nume, u.cnp, b.categorie_preferata " +
                "FROM utilizator u JOIN utilizator_buyer b ON u.id_user = b.id_buyer";

        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Buyer buyer = new Buyer(
                        rs.getString("nume"),
                        rs.getString("cnp"),
                        Categorie.valueOf(rs.getString("categorie_preferata"))
                );
                buyer.setId_user(rs.getInt("id_buyer"));
                buyers.add(buyer);
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }
        return buyers;
    }

    @Override
    public void update(Buyer buyer) {
        String sql = "UPDATE utilizator_buyer SET categorie_preferata = ? WHERE id_buyer = ?";
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, buyer.getCategoriePref().name());
            ps.setInt(2, buyer.getId_user());


            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Integer id) {

        String sql = "DELETE FROM utilizator WHERE id_user = ?";
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}