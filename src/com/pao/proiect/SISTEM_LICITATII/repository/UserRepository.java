package com.pao.proiect.SISTEM_LICITATII.repository;

import com.pao.proiect.SISTEM_LICITATII.utils.DatabaseConnection;
import com.pao.proiect.SISTEM_LICITATII.model.User;
import com.pao.proiect.SISTEM_LICITATII.model.Buyer; // Exemplu simplu de instanțiere la citire

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserRepository implements Repository<User, Integer> {

    @Override
    public void save(User user) {
        String sql = "INSERT INTO utilizator (nume, cnp) VALUES (?, ?)";
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, user.getNume());
            pstmt.setString(2, user.getCnp());
            pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Optional<User> findById(Integer id) {
        String sql = "SELECT * FROM utilizator WHERE id_user = ?";
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    // Deoarece User este abstract, instanțiem o formă de bază anonimă sau un tip concret pentru test
                    User user = new Buyer(rs.getString("nume"), rs.getString("cnp"), null);
                    return Optional.of(user);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public List<User> findAll() {
        List<User> useri = new ArrayList<>();
        String sql = "SELECT * FROM utilizator";
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                useri.add(new Buyer(rs.getString("nume"), rs.getString("cnp"), null));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return useri;
    }

    @Override
    public void update(User user) {
        // În mod normal în Java id-ul e final la tine, dar în DB identificăm după CNP sau după un ID primit separat
        String sql = "UPDATE utilizator SET nume = ? WHERE cnp = ?";
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, user.getNume());
            pstmt.setString(2, user.getCnp());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Integer id) {
        String sql = "DELETE FROM utilizator WHERE id_user = ?";
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}