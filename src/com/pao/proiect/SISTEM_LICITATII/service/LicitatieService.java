//package com.pao.proiect.SISTEM_LICITATII.service;
//import java.util.*;
//
//import com.pao.proiect.SISTEM_LICITATII.model.Licitatie;
//import com.pao.proiect.SISTEM_LICITATII.model.Oferta;
//import com.pao.proiect.SISTEM_LICITATII.exceptions.NuExistaAceastaLicitatie;
//import com.pao.proiect.SISTEM_LICITATII.exceptions.OfertaInvalidaException;
//
//public class LicitatieService {
//
//    private List<Licitatie> licitatii;
//
//    private LicitatieService(){
//        this. licitatii= new ArrayList<>();
//
//    }
//
//    public static class Holder{
//        public static final LicitatieService INSTANCE = new LicitatieService();
//    }
//
//    public static LicitatieService getInstance(){
//        return Holder.INSTANCE;
//    }
//
//    public List<Licitatie> getLicitatii(){
//        return licitatii;
//    }
//
//
//
//
//    public void creazaLicitatie(Licitatie licitatie) {
//        if (licitatie == null) {
//            throw new IllegalArgumentException("Licitatia primita este null!");
//        }
//        if (licitatie.getProdus() == null) {
//            throw new IllegalArgumentException("Licitatia trebuie sa contina un produs valid!");
//        }
//        if (licitatie.getProdus().getSeller() == null) {
//            throw new IllegalArgumentException("Produsul trebuie sa aiba un seller asociat!");
//        }
//
//        licitatii.add(licitatie);
//        System.out.println("Am creat Licitatia:  " + licitatie);
//
//
//        licitatie.getProdus().getSeller().getIstoricProduse().add(licitatie.getProdus());
//    }
//
//    public void afiseazaLicitatii(){
//        for(int i=0;i<licitatii.size();i++)
//            System.out.println(licitatii.get(i));}
//
//    public void ofertaservice(Licitatie l, Oferta o) {
//        if (l == null) {
//            throw new IllegalArgumentException("Licitatia specificata nu exista (null)!");
//        }
//        if (o == null) {
//            throw new IllegalArgumentException("Oferta nu poate fi null!");
//        }
//
//        if (o.getValoare() < l.getMinValue()) {
//            throw new OfertaInvalidaException("Oferta prea mica!");
//        }
//
//        l.adaugaOferta(o);
//    }
//
//    public void stergeLicitatie(int id) {
//
//        boolean ok = false;
//            for (int i=0;i< licitatii.size();i++) {
//                if (licitatii.get(i).getProdus().getId() == id) {
//                    licitatii.remove(i);
//                    ok=true;
//                    break;
//
//
//                }
//
//            }
//            if (ok == false) {
//                throw new NuExistaAceastaLicitatie("Nu exista aceasta licitatie! Incercati alt id?");
//            }
//        }
//
//    public void cautaDupaNume(String nume) {
//        if (nume == null || nume.isBlank()) {
//            throw new IllegalArgumentException("Numele cautat nu poate fi gol!");
//        }
//
//        boolean ok = false;
//        for (Licitatie l : licitatii) {
//            if (l.getProdus() != null && l.getProdus().getNume().equalsIgnoreCase(nume)) {
//                System.out.println(l);
//                ok = true;
//            }
//        }
//        if (!ok) {
//            throw new NuExistaAceastaLicitatie("Nu exista licitatii pt un produs cu acest nume!");
//        }
//    }
//    public void afiseazaLicitatiiSortate(){
//        List<Licitatie> copy = new ArrayList<>(licitatii);
//        copy.sort(Comparator.comparingInt((Licitatie l) -> l.getOferte().length).reversed());
//
//        for (Licitatie c:copy){
//            System.out.println(c+"\n");
//
//        }
//
//
//
//
//    }
//
//
//
//    }
//
//
//
//
//
//
//
//
//
package com.pao.proiect.SISTEM_LICITATII.service;

import java.util.*;
import java.sql.*;

import com.pao.proiect.SISTEM_LICITATII.model.Buyer;
import com.pao.proiect.SISTEM_LICITATII.model.Licitatie;
import com.pao.proiect.SISTEM_LICITATII.model.Oferta;
import com.pao.proiect.SISTEM_LICITATII.exceptions.NuExistaAceastaLicitatie;
import com.pao.proiect.SISTEM_LICITATII.exceptions.OfertaInvalidaException;
import com.pao.proiect.SISTEM_LICITATII.repository.LicitatieRepository;
import com.pao.proiect.SISTEM_LICITATII.repository.OfertaRepository;
import com.pao.proiect.SISTEM_LICITATII.utils.DatabaseConnection;

public class LicitatieService {

    private final LicitatieRepository licitatieRepo = new LicitatieRepository();
    private final OfertaRepository ofertaRepo = new OfertaRepository();

    private LicitatieService() {}

    public static class Holder {
        public static final LicitatieService INSTANCE = new LicitatieService();
    }

    public static LicitatieService getInstance() {
        return Holder.INSTANCE;
    }


    public List<Licitatie> getLicitatii() {
        return licitatieRepo.findAll();
    }

    public void creazaLicitatie(Licitatie licitatie) {
        if (licitatie == null) {
            throw new IllegalArgumentException("Licitatia primita este null!");
        }
        if (licitatie.getProdus() == null) {
            throw new IllegalArgumentException("Licitatia trebuie sa contina un produs valid!");
        }
        if (licitatie.getProdus().getSeller() == null) {
            throw new IllegalArgumentException("Produsul trebuie sa aiba un seller asociat!");
        }

        licitatieRepo.save(licitatie);
        AuditService.getInstance().log("creare_licitatie");
        System.out.println("Am salvat în DB Licitatia: " + licitatie);
    }

    public void afiseazaLicitatii() {
        List<Licitatie> active = licitatieRepo.findAll(); // Citire din DB
        for (Licitatie l : active) {
            System.out.println(l);
        }
        AuditService.getInstance().log("afiseaza_licitatii");

    }

//    public void afiseaza_licitatie_si_oferte(Integer id) {
//        Optional<Licitatie> opt = licitatieRepo.findById(id);
//
//        if (opt.isEmpty()) {
//            System.out.println("Nu exista licitatie cu ID-ul " + id);
//            return;
//        }
//
//        Licitatie lic = opt.get();
//        System.out.println("=== LICITATIE ===");
//        System.out.println(lic);
//
//        System.out.println("=== OFERTE ===");
//        List<Oferta> oferte = ofertaRepo.findAll().stream()
//                .filter(o -> o.getIdLicitatie() == id)
//                .toList();
//
//        if (oferte.isEmpty()) {
//            System.out.println("Nu exista oferte pentru aceasta licitatie.");
//        } else {
//            for (Oferta o : oferte) {
//                System.out.println(o);
//            }
//        }
//    }

    public void afiseaza_licitatie_si_oferte(Integer id) {
        String sql = "SELECT l.id_licitatie, l.min_value, " +
                "       p.nume AS nume_produs, p.categorie, p.colectie, " +
                "       u_seller.nume AS nume_seller, " +
                "       o.id_oferta, o.valoare, " +
                "       u_buyer.nume AS nume_buyer, u_buyer.cnp AS cnp_buyer " +
                "FROM licitatie l " +
                "JOIN produs p ON l.id_produs = p.id_produs " +
                "JOIN utilizator u_seller ON p.id_seller = u_seller.id_user " +
                "LEFT JOIN oferta o ON l.id_licitatie = o.id_licitatie " +
                "LEFT JOIN utilizator u_buyer ON o.id_buyer = u_buyer.id_user " +
                "WHERE l.id_licitatie = ?";

        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);

            try (ResultSet rs = pstmt.executeQuery()) {
                boolean primul = true;
                while (rs.next()) {
                    if (primul) {
                        System.out.println("=== LICITATIE ===");
                        System.out.println("ID: " + rs.getInt("id_licitatie"));
                        System.out.println("Produs: " + rs.getString("nume_produs"));
                        System.out.println("Categorie: " + rs.getString("categorie"));
                        System.out.println("Colectie: " + rs.getString("colectie"));
                        System.out.println("Seller: " + rs.getString("nume_seller"));
                        System.out.println("Pret minim: " + rs.getLong("min_value"));
                        System.out.println("=== OFERTE ===");
                        primul = false;
                    }

                    if (rs.getInt("id_oferta") != 0) {
                        System.out.println("- Oferta: " + rs.getInt("valoare") +
                                " | Buyer: " + rs.getString("nume_buyer") +
                                " | CNP: " + rs.getString("cnp_buyer"));
                        AuditService.getInstance().log("afiseaza_licitatie+oferte");

                    } else {
                        System.out.println("Nu exista oferte pentru aceasta licitatie.");
                    }
                }

                if (primul) {
                    System.out.println("Nu exista licitatie cu ID-ul " + id);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void ofertaservice(Licitatie l, Oferta o) {

        if (l == null) {
            throw new IllegalArgumentException("Licitatia specificata nu exista (null)!");
        }

        if (o == null) {
            throw new IllegalArgumentException("Oferta nu poate fi null!");
        }

        if (o.getValoare() < l.getMinValue()) {
            throw new OfertaInvalidaException("Oferta prea mica!");
        }

        if (o.getBuyer() == null || o.getBuyer().getId_user() == 0) {
            throw new IllegalStateException("Buyer invalid sau nesalvat in DB!");
        }

        ofertaRepo.save(o);
        AuditService.getInstance().log("lanseaza_oferta");


        l.adaugaOferta(o);
    }

    public void stergeLicitatie(int id) {
        // Verificăm dacă există înainte de ștergere
        Optional<Licitatie> licitatieOpt = licitatieRepo.findById(id);
        if (licitatieOpt.isEmpty()) {
            throw new NuExistaAceastaLicitatie("Nu exista aceasta licitatie! Incercati alt id?");
        }

        licitatieRepo.delete(id);
        AuditService.getInstance().log("stergere_licitatie");

        System.out.println("Licitatia cu ID-ul " + id + " a fost ștearsă din DB.");
    }

    public void cautaDupaNume(String nume) {
        if (nume == null || nume.isBlank()) {
            throw new IllegalArgumentException("Numele cautat nu poate fi gol!");
        }

        List<Licitatie> toate = licitatieRepo.findAll();
        boolean ok = false;
        for (Licitatie l : toate) {
            if (l.getProdus() != null && l.getProdus().getNume().equalsIgnoreCase(nume)) {
                System.out.println(l);
                ok = true;
                AuditService.getInstance().log("cauta_licitatie");

            }
        }
        if (!ok) {
            throw new NuExistaAceastaLicitatie("Nu exista licitatii pt un produs cu acest nume!");
        }
    }

    public void afiseazaLicitatiiSortate() {
        List<Licitatie> copy = licitatieRepo.findAll();
        copy.sort(Comparator.comparingInt((Licitatie l) -> l.getOferte().length).reversed());

        for (Licitatie c : copy) {
            System.out.println(c + "\n");
            AuditService.getInstance().log("afiseaza_licitatii_sortate");

        }
    }

    public void terminaLicitatie(int id) {
        String sqlCastigator = "SELECT u.nume, u.cnp, MAX(o.valoare) AS oferta_maxima " +
                "FROM oferta o " +
                "JOIN utilizator u ON o.id_buyer = u.id_user " +
                "WHERE o.id_licitatie = ? " +
                "GROUP BY u.id_user, u.nume, u.cnp " +
                "ORDER BY oferta_maxima DESC " +
                "LIMIT 1";

        String sqlDeleteOferte = "DELETE FROM oferta WHERE id_licitatie = ?";
        String sqlDeleteLicitatie = "DELETE FROM licitatie WHERE id_licitatie = ?";

        Connection conn = null;
        try {
            conn = DatabaseConnection.getInstance().getConnection();

            try (PreparedStatement pstmt = conn.prepareStatement(sqlCastigator)) {
                pstmt.setInt(1, id);
                try (ResultSet rs = pstmt.executeQuery()) {
                    if (rs.next()) {
                        System.out.println("=== LICITATIE INCHEIATA ===");
                        System.out.println("Castigator: " + rs.getString("nume"));
                        System.out.println("CNP: " + rs.getString("cnp"));
                        System.out.println("Oferta castigatoare: " + rs.getInt("oferta_maxima"));
                    } else {
                        System.out.println("Licitatia nu are nicio oferta — nu exista castigator.");
                    }
                }
            }

            conn.setAutoCommit(false);
            try {
                try (PreparedStatement pstmt = conn.prepareStatement(sqlDeleteOferte)) {
                    pstmt.setInt(1, id);
                    pstmt.executeUpdate();
                }

                try (PreparedStatement pstmt = conn.prepareStatement(sqlDeleteLicitatie)) {
                    pstmt.setInt(1, id);
                    pstmt.executeUpdate();
                }

                conn.commit();
                AuditService.getInstance().log("inchidere_licitatie");

                System.out.println("Licitatia #" + id + " a fost inchisa si stearsa din sistem.");

            } catch (SQLException e) {
                conn.rollback();
                System.out.println("[TX] Rollback — eroare la inchiderea licitatiei: " + e.getMessage());
                throw e;
            } finally {
                conn.setAutoCommit(true);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (conn != null) {
                try { conn.close(); } catch (SQLException e) { e.printStackTrace(); }
            }
        }
    }
}