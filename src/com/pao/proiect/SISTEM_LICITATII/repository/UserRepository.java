package com.pao.proiect.SISTEM_LICITATII.repository;

import com.pao.proiect.SISTEM_LICITATII.model.User;
import java.sql.*;

public class UserRepository {

    // IMPORTANT: Primim conexiunea de la Repository-ul copil pentru a păstra tranzacția intactă
    public int save(User user, Connection conn) throws SQLException {
        String sql = "INSERT INTO utilizator (nume, cnp) VALUES (?, ?)";

        try (PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, user.getNume());
            ps.setString(2, user.getCnp());
            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        }
        throw new SQLException("Nu s-a putut genera ID-ul pentru utilizator.");
    }
}