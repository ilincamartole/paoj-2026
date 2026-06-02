//package com.pao.proiect.SISTEM_LICITATII.service;
//
//import com.pao.proiect.SISTEM_LICITATII.exceptions.UserInexistentException;
//import com.pao.proiect.SISTEM_LICITATII.model.*;
//import com.pao.proiect.SISTEM_LICITATII.enums.UserType;
//import com.pao.proiect.SISTEM_LICITATII.enums.Categorie;
//
//import java.util.*;
//
//
//public class UserService {
//
//
//    TreeMap<UserType, List<User>> map = new TreeMap<>();
//
//    private UserService() {
//        map.put(UserType.SELLER, new ArrayList<>());
//        map.put(UserType.BUYER, new ArrayList<>());
//        map.put(UserType.PREMIUMBUYER, new ArrayList<>());
//
//
//    }
//
//
//    public static class Holder {
//        public static final UserService INSTANCE = new UserService();
//    }
//
//    public static UserService getInstance() {
//        return Holder.INSTANCE;
//    }
//
//    public void adaugaUser(User user) {
//        if (user == null) {
//            System.out.println("Eroare: Nu poți adăuga un user null.");
//            return;
//        }
//
//        for (List<User> listaCurenta : map.values()) {
//            for (User u : listaCurenta) {
//                if (u.getCnp().equals(user.getCnp())) {
//                    System.out.println("Eroare: Userul cu CNP " + user.getCnp() + " exista deja în sistem!");
//                    return;
//                }
//            }
//        }
//
//        UserType tip = (user instanceof Seller) ? UserType.SELLER : UserType.BUYER;
//        map.get(tip).add(user);
//        System.out.println("User adaugat cu succes.");
//    }
//
//
//
//    public void afiseazaUseri() {
//
//        for (UserType type : map.keySet()) {
//            for (User u : map.get(type))
//
//            {if (u instanceof PremiumBuyer) {
//                System.out.println(u + "--PREMIUM BUYER\n");}
//            else if (u instanceof Seller){
//                System.out.println(u+"--SELLER\n");
//            }else if((u instanceof Buyer)){
//                System.out.println(u+"--BUYER\n");
//
//
//            }
//            }
//        }
//    }
//
//    public User cautaUser(UserType tip, String cnp) {
//
//        List<User> lista = map.get(tip);
//
//        if (lista == null) return null;
//
//        for (User u : lista) {
//            if (u.getCnp().equals(cnp)) {
//                return u;
//            }
//        }
//
//        throw new UserInexistentException("Nu exista user cu acest CNP!");
//
//    }
//
//    public void categoriePrefBuyeri() {
//        TreeMap<Categorie, Integer> map1 = new TreeMap<>();
//
//        map1.put(Categorie.MOBILA, 0);
//        map1.put(Categorie.ARTA, 0);
//        map1.put(Categorie.HAINE, 0);
//        map1.put(Categorie.AUTOMOBILE, 0);
//        map1.put(Categorie.BIJUTERII, 0);
//
//        for (User u : map.get(UserType.BUYER)) {
//            Buyer b = (Buyer) u;
//            if (b.getCategoriePref() == Categorie.ARTA) {
//                map1.put(Categorie.ARTA, map1.getOrDefault(Categorie.ARTA, 0) + 1);
//            }
//            if (b.getCategoriePref() == Categorie.MOBILA) {
//                map1.put(Categorie.MOBILA, map1.getOrDefault(Categorie.MOBILA, 0) + 1);
//            }
//            if (b.getCategoriePref() == Categorie.BIJUTERII) {
//                map1.put(Categorie.BIJUTERII, map1.getOrDefault(Categorie.BIJUTERII, 0) + 1);
//            }
//            if (b.getCategoriePref() == Categorie.AUTOMOBILE) {
//                map1.put(Categorie.AUTOMOBILE, map1.getOrDefault(Categorie.AUTOMOBILE, 0) + 1);
//            }
//            if (b.getCategoriePref() == Categorie.HAINE) {
//                map1.put(Categorie.HAINE, map1.getOrDefault(Categorie.HAINE, 0) + 1);
//            }
//        }
//        Categorie maxKey = null;
//        int maxValue = -1;
//
//        for (Categorie c : map1.keySet()) {
//            int val = map1.get(c);
//
//            if (val > maxValue) {
//                maxValue = val;
//                maxKey = c;
//            }
//        }
//
//
//        System.out.println(maxKey.afisare());
//
//    }
//
//    public int upgradePremiumBuyers(List<Licitatie> licitatii) {
//
//        Map<Buyer, Integer> participari = new HashMap<>();
//
//        for (Licitatie l : licitatii) {
//            for (Oferta o : l.getOferte()) {
//
//                Buyer b = o.getBuyer();
//
//                participari.put(b, participari.getOrDefault(b, 0) + 1);
//            }
//        }
//        int nr = 0;
//        for (Buyer b : participari.keySet()) {
//
//            if (participari.get(b) >= 3) {
//
//                PremiumBuyer pb = new PremiumBuyer(
//                        b.getNume(),
//                        b.getCnp(),
//                        b.getCategoriePref(),
//                        participari.get(b)
//                );
//
//                map.get(UserType.BUYER).removeIf(
//                        u -> u.getCnp().equals(b.getCnp())
//                );
//
//                map.get(UserType.PREMIUMBUYER).add(pb);
//
//                nr++;
//            }
//        }
//        return nr;
//    }
//
//    public void afiseazaIstoricSeller(Seller s){
//        System.out.println("Produsele licitate de acest seller sunt: \n");
//        Collections.sort(s.getIstoricProduse());
//        for(Produs p: s.getIstoricProduse()){
//            System.out.println(p+"\n");
//
//        }
//    }
//
//}
package com.pao.proiect.SISTEM_LICITATII.service;

import com.pao.proiect.SISTEM_LICITATII.exceptions.UserInexistentException;
import com.pao.proiect.SISTEM_LICITATII.model.*;
import com.pao.proiect.SISTEM_LICITATII.enums.UserType;
import com.pao.proiect.SISTEM_LICITATII.enums.Categorie;
import com.pao.proiect.SISTEM_LICITATII.repository.BuyerRepository;
import com.pao.proiect.SISTEM_LICITATII.repository.SellerRepository;
import com.pao.proiect.SISTEM_LICITATII.repository.PremiumBuyerRepository;
import com.pao.proiect.SISTEM_LICITATII.utils.DatabaseConnection;

import java.util.*;
import java.sql.*;

public class UserService {

    private final BuyerRepository buyerRepo = new BuyerRepository();
    private final SellerRepository sellerRepo = new SellerRepository();
    private final PremiumBuyerRepository premiumRepo = new PremiumBuyerRepository();

    private UserService() {}

    public static class Holder {
        public static final UserService INSTANCE = new UserService();
    }

    public static UserService getInstance() {
        return Holder.INSTANCE;
    }

    public void adaugaUser(User user) {
        if (user == null) {
            System.out.println("Eroare: Nu poți adăuga un user null.");
            return;
        }

        boolean exista = cautaInDBDupaCnp(user.getCnp());
        if (exista) {
            System.out.println("Eroare: Userul cu CNP " + user.getCnp() + " exista deja în sistem!");
            return;
        }

        if (user instanceof PremiumBuyer) {
            premiumRepo.save((PremiumBuyer) user);
        } else if (user instanceof Buyer) {
            buyerRepo.save((Buyer) user);
        } else if (user instanceof Seller) {
            sellerRepo.save((Seller) user);
        }
        AuditService.getInstance().log("creare_user");

        System.out.println("User salvat cu succes în baza de date.");
    }

    public void afiseazaUseri() {
        List<Buyer> buyers = buyerRepo.findAll();
        List<Seller> sellers = sellerRepo.findAll();
        List<PremiumBuyer> premiums = premiumRepo.findAll();
        AuditService.getInstance().log("afisare_useri");

        for (PremiumBuyer pb : premiums) System.out.println(pb + " --PREMIUM BUYER\n");
        for (Seller s : sellers) System.out.println(s + " --SELLER\n");
        for (Buyer b : buyers) System.out.println(b + " --BUYER\n");
    }

    public User cautaUser(UserType tip, String cnp) {
        if (tip == UserType.BUYER) {
            return buyerRepo.findAll().stream().filter(u -> u.getCnp().equals(cnp)).findFirst()
                    .orElseThrow(() -> new UserInexistentException("Nu exista buyer cu acest CNP!"));

        } else if (tip == UserType.SELLER) {
            return sellerRepo.findAll().stream().filter(u -> u.getCnp().equals(cnp)).findFirst()
                    .orElseThrow(() -> new UserInexistentException("Nu exista seller cu acest CNP!"));
        } else {
            return premiumRepo.findAll().stream().filter(u -> u.getCnp().equals(cnp)).findFirst()
                    .orElseThrow(() -> new UserInexistentException("Nu exista premium buyer cu acest CNP!"));
        }

    }

    public void categoriePrefBuyeri() {
        TreeMap<Categorie, Integer> counter = new TreeMap<>();
        for (Categorie c : Categorie.values()) counter.put(c, 0);

        List<Buyer> totiBuyeri = buyerRepo.findAll();
        for (Buyer b : totiBuyeri) {
            counter.put(b.getCategoriePref(), counter.get(b.getCategoriePref()) + 1);
        }

        Categorie maxKey = null;
        int maxValue = -1;
        for (Categorie c : counter.keySet()) {
            if (counter.get(c) > maxValue) {
                maxValue = counter.get(c);
                maxKey = c;
            }
        }
        if (maxKey != null) {
            System.out.println("Cea mai căutată categorie: " + maxKey.name());
        }
        AuditService.getInstance().log("cea_mai_cautata_categorie");

    }

    public int upgradePremiumBuyers(List<Licitatie> licitatii) {
        Map<Buyer, Integer> participari = new HashMap<>();

        for (Licitatie l : licitatii) {
            for (Oferta o : l.getOferte()) {
                Buyer b = o.getBuyer();
                if (b != null) {
                    participari.put(b, participari.getOrDefault(b, 0) + 1);
                }
            }
        }

        int nr = 0;
        for (Buyer b : participari.keySet()) {
            if (participari.get(b) >= 3) {
                PremiumBuyer pb = new PremiumBuyer(b.getNume(), b.getCnp(), b.getCategoriePref(), participari.get(b));

                buyerRepo.delete(b.getId_user());
                premiumRepo.save(pb);
                nr++;
            }
        }
        return nr;
    }

    public void afiseazaIstoricSeller(Seller s) {
        String sql = "SELECT p.nume, p.categorie, p.colectie " +
                "FROM produs p " +
                "JOIN utilizator u ON p.id_seller = u.id_user " +
                "JOIN licitatie l ON l.id_produs = p.id_produs " +
                "WHERE u.id_user = ? " +
                "ORDER BY p.nume ASC";

        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, s.getId_user());

            try (ResultSet rs = pstmt.executeQuery()) {
                AuditService.getInstance().log("afisare_istoric_seller");

                System.out.println("Produsele licitate de " + s.getNume() + ":");
                while (rs.next()) {
                    System.out.println("- " + rs.getString("nume") +
                            " | Categorie: " + rs.getString("categorie") +
                            " | Colectie: " + rs.getString("colectie"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private boolean cautaInDBDupaCnp(String cnp) {
        boolean inBuyer = buyerRepo.findAll().stream().anyMatch(u -> u.getCnp().equals(cnp));
        boolean inSeller = sellerRepo.findAll().stream().anyMatch(u -> u.getCnp().equals(cnp));
        boolean inPremium = premiumRepo.findAll().stream().anyMatch(u -> u.getCnp().equals(cnp));
        return inBuyer || inSeller || inPremium;
    }
}