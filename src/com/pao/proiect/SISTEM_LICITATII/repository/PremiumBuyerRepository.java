package com.pao.proiect.SISTEM_LICITATII.repository;

import com.pao.proiect.SISTEM_LICITATII.enums.Categorie;
import com.pao.proiect.SISTEM_LICITATII.model.PremiumBuyer;
import com.pao.proiect.SISTEM_LICITATII.utils.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PremiumBuyerRepository implements Repository<PremiumBuyer, Integer> {

    private final UserRepository userRepo = new UserRepository();

    @Override
    public void save(PremiumBuyer pb) {
        String sqlBuyer = "INSERT INTO utilizator_buyer (id_buyer, categorie_preferata) VALUES (?, ?)";
        String sqlPremium = "INSERT INTO utilizator_premiumbuyer (id_pBuyer, discount_rate) VALUES (?, ?)";

        try (Connection conn = DatabaseConnection.getInstance().getConnection()) {
            conn.setAutoCommit(false); // Începe tranzacția pe 3 tabele simultan

            try {
                // 1. Inserează în tabela 'utilizator' prin userRepo
                int userId = userRepo.save(pb, conn);

                // 2. Inserează în tabela 'utilizator_buyer'
                try (PreparedStatement psBuyer = conn.prepareStatement(sqlBuyer)) {
                    psBuyer.setInt(1, userId);
                    psBuyer.setString(2, pb.getCategoriePref().name());
                    psBuyer.executeUpdate();
                }

                // 3. Inserează în tabela 'utilizator_premiumbuyer'
                try (PreparedStatement psPremium = conn.prepareStatement(sqlPremium)) {
                    psPremium.setInt(1, userId);
                    psPremium.setFloat(2, pb.getDiscountRate());
                    psPremium.executeUpdate();
                }

                conn.commit(); // Totul a funcționat, salvăm definitiv
            } catch (SQLException e) {
                conn.rollback(); // A picat ceva? Dăm înapoi toate cele 3 tabele!
                throw e;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Optional<PremiumBuyer> findById(Integer id) {
        String sql = "SELECT u.nume, u.cnp, b.categorie_preferata, p.discount_rate " +
                "FROM utilizator u " +
                "JOIN utilizator_buyer b ON u.id_user = b.id_buyer " +
                "JOIN utilizator_premiumbuyer p ON u.id_user = p.id_pBuyer " +
                "WHERE u.id_user = ?";

        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    PremiumBuyer pb = new PremiumBuyer(
                            rs.getString("nume"),
                            rs.getString("cnp"),
                            Categorie.valueOf(rs.getString("categorie_preferata")),
                            rs.getFloat("discount_rate")
                    );
                    pb.setId_user(id);
                    return Optional.of(pb);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public List<PremiumBuyer> findAll() {
        List<PremiumBuyer> list = new ArrayList<>();
        String sql = "SELECT u.id_user, u.nume, u.cnp, b.categorie_preferata, p.discount_rate " +
                "FROM utilizator u " +
                "JOIN utilizator_buyer b ON u.id_user = b.id_buyer " +
                "JOIN utilizator_premiumbuyer p ON u.id_user = p.id_pBuyer";

        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                PremiumBuyer pb = new PremiumBuyer(
                        rs.getString("nume"),
                        rs.getString("cnp"),
                        Categorie.valueOf(rs.getString("categorie_preferata")),
                        rs.getFloat("discount_rate")
                );
                pb.setId_user(rs.getInt("id_user"));
                list.add(pb);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public void update(PremiumBuyer pb) {
        // Actualizăm rata de discount în tabela specifică și categoria în tabela de Buyer
        String sqlPremium = "UPDATE utilizator_premiumbuyer SET discount_rate = ? WHERE id_pBuyer = ?";
        String sqlBuyer = "UPDATE utilizator_buyer SET categorie_preferata = ? WHERE id_buyer = ?";

        try (Connection conn = DatabaseConnection.getInstance().getConnection()) {
            conn.setAutoCommit(false);
            try {
                try (PreparedStatement psP = conn.prepareStatement(sqlPremium)) {
                    psP.setFloat(1, pb.getDiscountRate());
                    psP.setInt(2, pb.getId_user());
                    psP.executeUpdate();
                }
                try (PreparedStatement psB = conn.prepareStatement(sqlBuyer)) {
                    psB.setString(1, pb.getCategoriePref().name());
                    psB.setInt(2, pb.getId_user());
                    psB.executeUpdate();
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
    public void delete(Integer id) {
        // Ștergerea din tabela de bază 'utilizator' va curăța automat tabelele copil prin CASCADE
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