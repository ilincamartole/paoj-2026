package com.pao.proiect.SISTEM_LICITATII.service;

import com.pao.proiect.SISTEM_LICITATII.*;
import com.pao.proiect.SISTEM_LICITATII.exceptions.UserInexistentException;
import com.sun.source.tree.Tree;
import com.pao.proiect.SISTEM_LICITATII.enums.UserType;
import com.pao.proiect.SISTEM_LICITATII.enums.Categorie;

import java.util.*;


public class UserService {

    private User[] useri;
    TreeMap<UserType, List<User>> map = new TreeMap<>();

    private UserService() {
        map.put(UserType.SELLER, new ArrayList<>());
        map.put(UserType.BUYER, new ArrayList<>());
        map.put(UserType.PREMIUMBUYER, new ArrayList<>());


    }


    public static class Holder {
        public static final UserService INSTANCE = new UserService();
    }

    public static UserService getInstance() {
        return Holder.INSTANCE;
    }

    public void adaugaUser(User user) {

        List<User> lista = map.get(
                (user instanceof Seller) ? UserType.SELLER : UserType.BUYER
        );

        if (!lista.contains(user)) {
            lista.add(user);
        }
    }

    public User[] getUseri() {
        return this.useri;
    }

    public void afiseazaUseri() {

        for (UserType type : map.keySet()) {
            for (User u : map.get(type))

            {if (u instanceof PremiumBuyer) {
                System.out.println(u + "--PREMIUM BUYER\n");}
            else if (u instanceof Seller){
                System.out.println(u+"--SELLER\n");
            }else if((u instanceof Buyer)){
                System.out.println(u+"--BUYER\n");


            }
            }
        }
    }

    public User cautaUser(UserType tip, String cnp) {

        List<User> lista = map.get(tip);

        if (lista == null) return null;

        for (User u : lista) {
            if (u.getCnp().equals(cnp)) {
                return u;
            }
        }

        throw new UserInexistentException("Nu exista user cu acest CNP!");

    }

    public void categoriePrefBuyeri() {
        TreeMap<Categorie, Integer> map1 = new TreeMap<>();
        map1.put(Categorie.MOBILA, 0);
        map1.put(Categorie.ARTA, 0);
        map1.put(Categorie.HAINE, 0);
        map1.put(Categorie.AUTOMOBILE, 0);
        map1.put(Categorie.BIJUTERII, 0);

        for (User u : map.get(UserType.BUYER)) {
            Buyer b = (Buyer) u;
            if (b.getCategoriePref() == Categorie.ARTA) {
                map1.put(Categorie.ARTA, map1.getOrDefault(Categorie.ARTA, 0) + 1);
            }
            if (b.getCategoriePref() == Categorie.MOBILA) {
                map1.put(Categorie.MOBILA, map1.getOrDefault(Categorie.MOBILA, 0) + 1);
            }
            if (b.getCategoriePref() == Categorie.BIJUTERII) {
                map1.put(Categorie.BIJUTERII, map1.getOrDefault(Categorie.BIJUTERII, 0) + 1);
            }
            if (b.getCategoriePref() == Categorie.AUTOMOBILE) {
                map1.put(Categorie.AUTOMOBILE, map1.getOrDefault(Categorie.AUTOMOBILE, 0) + 1);
            }
            if (b.getCategoriePref() == Categorie.HAINE) {
                map1.put(Categorie.HAINE, map1.getOrDefault(Categorie.HAINE, 0) + 1);
            }
        }
        Categorie maxKey = null;
        int maxValue = -1;

        for (Categorie c : map1.keySet()) {
            int val = map1.get(c);

            if (val > maxValue) {
                maxValue = val;
                maxKey = c;
            }
        }


        System.out.println(maxKey.afisare());

    }

    public int upgradePremiumBuyers(List<Licitatie> licitatii) {

        Map<Buyer, Integer> participari = new HashMap<>();

        for (Licitatie l : licitatii) {
            for (Oferta o : l.getOferte()) {

                Buyer b = o.getBuyer();

                participari.put(b, participari.getOrDefault(b, 0) + 1);
            }
        }
        int nr = 0;
        for (Buyer b : participari.keySet()) {

            if (participari.get(b) >= 3) {

                PremiumBuyer pb = new PremiumBuyer(
                        b.getNume(),
                        b.getCnp(),
                        b.getCategoriePref(),
                        participari.get(b)
                );

                map.get(UserType.BUYER).removeIf(
                        u -> u.getCnp().equals(b.getCnp())
                );

                map.get(UserType.PREMIUMBUYER).add(pb);

                nr++;
            }
        }
        return nr;
    }

    public void afiseazaIstoricSeller(Seller s){
        System.out.println("Produsele licitate de acest seller sunt: \n");
        Collections.sort(s.getIstoricProduse());
        for(Produs p: s.getIstoricProduse()){
            System.out.println(p+"\n");

        }
    }

}
