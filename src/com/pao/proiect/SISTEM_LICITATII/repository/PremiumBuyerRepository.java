import com.pao.proiect.SISTEM_LICITATII.enums.Categorie;
import com.pao.proiect.SISTEM_LICITATII.model.PremiumBuyer;
import com.pao.proiect.SISTEM_LICITATII.repository.BuyerRepository;
import com.pao.proiect.SISTEM_LICITATII.utils.DatabaseConnection;
import com.pao.proiect.SISTEM_LICITATII.model.User;
import com.pao.proiect.SISTEM_LICITATII.model.Buyer; // Exemplu simplu de instanțiere la citire

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
public class PremiumBuyerRepository {

    private final BuyerRepository buyerRepo = new BuyerRepository();

    public int save(PremiumBuyer pb) {

        try (Connection conn = DatabaseConnection.getInstance().getConnection()) {

            conn.setAutoCommit(false);

            int buyerId = buyerRepo.save(pb);

            String sql =
                    "INSERT INTO utilizator_premiumbuyer (id_pBuyer, discount_rate) VALUES (?, ?)";

            try (PreparedStatement ps = conn.prepareStatement(sql)) {

                ps.setInt(1, buyerId);
                ps.setFloat(2, pb.getDiscountRate());
                ps.executeUpdate();

                conn.commit();
                return buyerId;

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