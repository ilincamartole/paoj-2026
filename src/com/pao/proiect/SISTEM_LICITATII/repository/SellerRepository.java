package com.pao.proiect.SISTEM_LICITATII.repository;

import com.pao.proiect.SISTEM_LICITATII.enums.Categorie;
import com.pao.proiect.SISTEM_LICITATII.model.Seller;
import com.pao.proiect.SISTEM_LICITATII.utils.DatabaseConnection;
import com.pao.proiect.SISTEM_LICITATII.model.User;
import com.pao.proiect.SISTEM_LICITATII.model.Buyer; // Exemplu simplu de instanțiere la citire

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SellerRepository {

    private final UserRepository userRepo = new UserRepository();

    public int save(Seller seller) {
        try (Connection conn = DatabaseConnection.getInstance().getConnection()) {

            conn.setAutoCommit(false);

            int userId = userRepo.save(seller);

            String sql = "INSERT INTO utilizator_seller (id_seller, rating) VALUES (?, ?)";

            try (PreparedStatement ps = conn.prepareStatement(sql)) {

                ps.setInt(1, userId);
                ps.setDouble(2, seller.getRating());
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
}