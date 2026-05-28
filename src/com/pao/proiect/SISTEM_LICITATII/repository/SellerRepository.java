package com.pao.proiect.SISTEM_LICITATII.repository;

import com.pao.proiect.SISTEM_LICITATII.model.Seller;
import com.pao.proiect.SISTEM_LICITATII.utils.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SellerRepository implements Repository<Seller, Integer> {

    private final UserRepository userRepo = new UserRepository();

    @Override
    public void save(Seller seller) {
        String sql = "INSERT INTO utilizator_seller (id_seller, rating) VALUES (?, ?)";

        try (Connection conn = DatabaseConnection.getInstance().getConnection()) {
            conn.setAutoCommit(false); // Pornim tranzacția

            try {
                // 1. Salvăm în tabela părinte 'utilizator' și obținem ID-ul generat
                int userId = userRepo.save(seller, conn);
                seller.setId_user(userId);
                // 2. Salvăm în tabela copil 'utilizator_seller' folosind același ID
                try (PreparedStatement ps = conn.prepareStatement(sql)) {
                    ps.setInt(1, userId);
                    ps.setDouble(2, seller.getRating());
                    ps.executeUpdate();
                }

                conn.commit(); // Salvăm totul în DB dacă nu au fost erori
            } catch (SQLException e) {
                conn.rollback(); // Dăm înapoi TOT în caz de eșec
                throw e;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Optional<Seller> findById(Integer id) {
        String sql = "SELECT u.nume, u.cnp, s.rating " +
                "FROM utilizator u JOIN utilizator_seller s ON u.id_user = s.id_seller " +
                "WHERE u.id_user = ?";

        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Seller seller = new Seller(
                            rs.getString("nume"),
                            rs.getString("cnp"),
                            rs.getDouble("rating")
                    );
                    seller.setId_user(id); // Setează ID-ul moștenit de la User
                    return Optional.of(seller);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public List<Seller> findAll() {
        List<Seller> sellers = new ArrayList<>();
        String sql = "SELECT u.id_user, u.nume, u.cnp, s.rating " +
                "FROM utilizator u JOIN utilizator_seller s ON u.id_user = s.id_seller";

        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Seller seller = new Seller(
                        rs.getString("nume"),
                        rs.getString("cnp"),
                        rs.getDouble("rating")
                );
                seller.setId_user(rs.getInt("id_user"));
                sellers.add(seller);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return sellers;
    }

    @Override
    public void update(Seller seller) {
        String sql = "UPDATE utilizator_seller SET rating = ? WHERE id_seller = ?";
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setDouble(1, seller.getRating());
            ps.setInt(2, seller.getId_user());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Integer id) {
        // Ștergerea din 'utilizator' va șterge automat și din 'utilizator_seller'
        // datorită cheii străine cu ON DELETE CASCADE din SQL.
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