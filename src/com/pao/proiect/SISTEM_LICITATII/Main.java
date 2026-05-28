//package com.pao.proiect.SISTEM_LICITATII;
//
//
//import com.pao.proiect.SISTEM_LICITATII.enums.Categorie;
//import com.pao.proiect.SISTEM_LICITATII.enums.UserType;
//import com.pao.proiect.SISTEM_LICITATII.exceptions.CNPInvalidException;
//import com.pao.proiect.SISTEM_LICITATII.exceptions.NuExistaAceastaLicitatie;
//import com.pao.proiect.SISTEM_LICITATII.exceptions.UserInexistentException;
//import com.pao.proiect.SISTEM_LICITATII.model.*;
//import com.pao.proiect.SISTEM_LICITATII.repository.*;
//import com.pao.proiect.SISTEM_LICITATII.service.LicitatieService;
//import com.pao.proiect.SISTEM_LICITATII.service.UserService;
//
//
//import java.util.Scanner;
//
//public class Main {
//
//        public static void main(String[] args) {
//            LicitatieService serviceL= LicitatieService.getInstance();
//            UserService serviceU=UserService.getInstance();
//
//            BuyerRepository buyerRepo = new BuyerRepository();
//            SellerRepository sellerRepo = new SellerRepository();
//            ProdusRepository produsRepo = new ProdusRepository();
//            LicitatieRepository licitatieRepo = new LicitatieRepository();
//            OfertaRepository ofertaRepo = new OfertaRepository();
//
//            Buyer b1 = new Buyer("Ion", "123", Categorie.ARTA);
//            Buyer b2 = new Buyer("Maria", "124", Categorie.MOBILA);
//            Buyer b3 = new Buyer("Alex", "125", Categorie.BIJUTERII);
//            Buyer b4 = new Buyer("Elena", "126", Categorie.ARTA);
//
//            Seller s1 = new Seller("Gigel", "200", 4.5);
//            Seller s2 = new Seller("Vlad", "201", 3.8);
//
//            sellerRepo.save(s1);
//            sellerRepo.save(s2);
////
////            serviceU.adaugaUser(b1);
////            serviceU.adaugaUser(b2);
////            serviceU.adaugaUser(b3);
////            serviceU.adaugaUser(b4);
////            serviceU.adaugaUser(s1);
////            serviceU.adaugaUser(s2);
//
//
//            Produs p1 = new Produs("Tablou Van Gogh", Categorie.ARTA, "Colectia 1", s1);
//            Produs p2 = new Produs("Canapea IKEA", Categorie.MOBILA, "Living", s2);
//            Produs p3 = new Produs("Inel aur", Categorie.BIJUTERII, "Luxury", s1);
//            Produs p4 = new Produs("Sculptura moderna", Categorie.ARTA, "Colectia 2", s2);
//
//            produsRepo.save(p1);
//            produsRepo.save(p2);
//            produsRepo.save(p3);
//            produsRepo.save(p4);
//
//            Licitatie l1 = new Licitatie(p1, 1000);
//            Licitatie l2 = new Licitatie(p2, 500);
//            Licitatie l3 = new Licitatie(p3, 2000);
//            Licitatie l4 = new Licitatie(p4, 1500);
//
//            licitatieRepo.save(l1);
//            licitatieRepo.save(l2);
//            licitatieRepo.save(l3);
//            licitatieRepo.save(l4);
//
//            Oferta o1 = new Oferta(1200, b1, l1.getId_licitatie());
//            Oferta o2 = new Oferta(1300, b2, l1.getId_licitatie());
//            Oferta o3 = new Oferta(1400, b3, l1.getId_licitatie());
//
//            Oferta o4 = new Oferta(600, b2, l2.getId_licitatie());
//            Oferta o5 = new Oferta(700, b1, l2.getId_licitatie());
//
//            Oferta o6 = new Oferta(2100, b3, l3.getId_licitatie());
//            Oferta o7 = new Oferta(2200, b4, l3.getId_licitatie());
//            Oferta o8 = new Oferta(2300, b1, l3.getId_licitatie());
//
//            Oferta o9 = new Oferta(1600, b4, l4.getId_licitatie());
//            Oferta o10 = new Oferta(1700, b2, l4.getId_licitatie());
//
//            ofertaRepo.save(o1);
//            ofertaRepo.save(o2);
//            ofertaRepo.save(o3);
//            ofertaRepo.save(o4);
//            ofertaRepo.save(o5);
//            ofertaRepo.save(o6);
//            ofertaRepo.save(o7);
//            ofertaRepo.save(o8);
//            ofertaRepo.save(o9);
//            ofertaRepo.save(o10);
//
////
////            serviceL.ofertaservice(l1, new Oferta(1200, b1));
////            serviceL.ofertaservice(l1, new Oferta(1300, b2));
////            serviceL.ofertaservice(l1, new Oferta(1400, b3));
////
////            serviceL.ofertaservice(l2, new Oferta(600, b2));
////            serviceL.ofertaservice(l2, new Oferta(700, b1));
////
////            serviceL.ofertaservice(l3, new Oferta(2100, b3));
////            serviceL.ofertaservice(l3, new Oferta(2200, b4));
////            serviceL.ofertaservice(l3, new Oferta(2300, b1));
////
////            serviceL.ofertaservice(l4, new Oferta(1600, b4));
////            serviceL.ofertaservice(l4, new Oferta(1700, b2));
//
//            Scanner scanner = new Scanner(System.in);
//
//            while (true) {
//
//                System.out.println("\n===== Gestionare Liciitatii =====");
//                System.out.println("1. Adaugă licitatie");
//                System.out.println("2. Listare licitatii");
//                System.out.println("3. Adauga user");
//                System.out.println("4. Afiseaza userii");
//                System.out.println("5. Fa o oferta la o licitatie");
//                System.out.println("6. Sterge o licitaie");
//                System.out.println("7. Cauta o licitatie dupa numele produsului");
//                System.out.println("8. Afiseaza licitatiile sortate descrescator dupa numarul de oferte");
//                System.out.println("9. Afisati care este cea mai cautata categorie a Buyerilor");
//                System.out.println("10. Calculati care buyeri trebuie sa devina PremiumBuyeri si dati le acest statut.");
//                System.out.println("11. Afisati pt un seller dat de la tastaura toate produsele pe care le-a licitat, sortate dupa nume.");
//
//                System.out.println("0. Ieșire");
//                System.out.print("Opțiune: ");
//                int n = scanner.nextInt();
//
//                switch (n) {
//
//                    case 1:
//                        try {
//                            System.out.print("Introduceti cnp: ");
//                            String cnp = scanner.next();
//
//                            if (!cnp.matches("\\d+")) {
//                                throw new CNPInvalidException("CNP-ul trebuie sa contina doar cifre!");
//                            }
//
//                            Seller u = null;
//                            try {
//                                u = (Seller) serviceU.cautaUser(UserType.SELLER, cnp);
//                            } catch (UserInexistentException e) {
//                                System.out.println((e.getMessage()));
//                                break;
//                            }
//
//                            scanner.nextLine();
//                            System.out.print("Introdu numele produsului ");
//                            String name = scanner.nextLine();
//                            System.out.print("Introdu categoria: ");
//                            String categ = scanner.next();
//                            Categorie c;
//                            try {
//                                c = Categorie.valueOf(categ.toUpperCase());
//                            } catch (Exception e) {
//                                System.out.println("Categorie invalida!");
//                                break;
//                            }
//
//                            System.out.print("Introdu colectia: ");
//                            String colectie = scanner.next();
//                            System.out.println("Introduceti suma minima: ");
//
//                            long suma = scanner.nextLong();
//
//                            if (suma < 0) {
//                                System.out.println("Pretul nu poate fi negativ!");
//                                break;
//                            }
//
//                            Produs p = new Produs(name, c, colectie, u);
//                            Licitatie l = new Licitatie(p, suma);
//                            serviceL.creazaLicitatie(l);
//
//                        } catch (CNPInvalidException e) {
//                            System.out.println(e.getMessage());
//                        }
//                        break;
//
//                    case 2:
//                        try {
//                            serviceL.afiseazaLicitatii();
//                        } catch (Exception e) {
//                            System.out.println(e.getMessage());
//                        }
//                        break;
//
//                    case 3:
//                        try {
//                            scanner.nextLine();
//                            System.out.println("Introducti numele dvs: ");
//                            String name1 = scanner.nextLine();
//                            System.out.println("Introducti CNP-ul dvs: ");
//                            String CNP = scanner.next();
//
//                            if (!CNP.matches("\\d+")) {
//                                throw new CNPInvalidException("CNP invalid!");
//                            }
//
//
//                            System.out.println("Vreti sa va inregistrati ca Buyer sau Seller?");
//                            UserType tip = UserType.valueOf(scanner.next().toUpperCase());
//
//                            if (tip == UserType.BUYER) {
//                                System.out.print("Introdu categoria prefarata: ");
//                                String categ1 = scanner.next();
//                                Categorie c1 = Categorie.valueOf(categ1.toUpperCase());
//
//                                Buyer b = new Buyer(name1, CNP, c1);
//                                serviceU.adaugaUser(b);
//
//                            } else {
//                                if (tip == UserType.SELLER) {
//                                    System.out.print("Introdu  rating: ");
//                                    float r = scanner.nextFloat();
//                                    Seller s = new Seller(name1, CNP, r);
//                                    serviceU.adaugaUser(s);
//                                }
//                            }
//
//                        } catch (CNPInvalidException e) {
//                            System.out.println(e.getMessage());
//                        }
//                        break;
//
//                    case 4:
//                        try {
//                            serviceU.afiseazaUseri();
//                        } catch (Exception e) {
//                            System.out.println(e.getMessage());
//                        }
//                        break;
//
//                    case 5:
//                        try {
//                            System.out.println("Introduceti cnp: ");
//                            String cnp1 = scanner.next();
//
//                            if (!cnp1.matches("\\d+")) {
//                                throw new CNPInvalidException("CNP invalid!");
//                            }
//
//                            Buyer buyer = (Buyer) serviceU.cautaUser(UserType.BUYER, cnp1);
//
//                            if (buyer == null) {
//                                System.out.println("Trebuie sa va inregistrati mai intai ca BUYER.");
//                                break;
//                            }
//
//                            System.out.println("Introduceti id ul licitatiei la care vreti sa participati: ");
//                            int id = scanner.nextInt();
//
//                            System.out.println("Introduceti valoarea ofertei:  ");
//                            int val = scanner.nextInt();
//
//                            Oferta o = new Oferta(val, buyer,id);
//
//                            serviceL.ofertaservice(serviceL.getLicitatii().get(id), o);
//
//                        } catch (Exception e) {
//                            System.out.println(e.getMessage());
//                        }
//                        break;
//
//                    case 6:
//                        try {
//                            System.out.println("Introduceti id ul licitatiei: ");
//                            int id = scanner.nextInt();
//                            serviceL.stergeLicitatie(id);
//                        } catch (Exception e) {
//                            System.out.println(e.getMessage());
//                        }
//                        break;
//
//                    case 7:
//                        try {
//                            scanner.nextLine();
//                            System.out.println("Introduceti numele produsului licitat: ");
//                            String numeP = scanner.nextLine();
//                            serviceL.cautaDupaNume(numeP);
//                        } catch (NuExistaAceastaLicitatie e) {
//                            System.out.println(e.getMessage());
//                        }
//                        break;
//
//                    case 8:
//                        try {
//                            serviceL.afiseazaLicitatiiSortate();
//                        } catch (Exception e) {
//                            System.out.println(e.getMessage());
//                        }
//                        break;
//
//                    case 9:
//                        try {
//                            serviceU.categoriePrefBuyeri();
//                        } catch (Exception e) {
//                            System.out.println(e.getMessage());
//                        }
//                        break;
//
//                    case 10:
//                        try {
//                            int nr = serviceU.upgradePremiumBuyers(serviceL.getLicitatii());
//                            System.out.println(nr);
//                        } catch (Exception e) {
//                            System.out.println(e.getMessage());
//                        }
//                        break;
//
//                    case 11:
//                        try {
//                            System.out.println("Introduceti cnp-ul sellerului: )");
//                            String CNP1 = scanner.next();
//                            Seller seller = (Seller) serviceU.cautaUser(UserType.SELLER, CNP1);
//
//                            if (seller == null) {
//                                System.out.println("Userul introdus nu este seller.");
//                            } else {
//                                serviceU.afiseazaIstoricSeller(seller);
//                            }
//
//                        } catch (Exception e) {
//                            System.out.println(e.getMessage());
//                        }
//                        break;
//
//                    case 0:
//                        System.exit(0);
//                        break;
//                }
//            }
//        }
//    }
//
package com.pao.proiect.SISTEM_LICITATII;

import com.pao.proiect.SISTEM_LICITATII.enums.Categorie;
import com.pao.proiect.SISTEM_LICITATII.enums.UserType;
import com.pao.proiect.SISTEM_LICITATII.exceptions.CNPInvalidException;
import com.pao.proiect.SISTEM_LICITATII.exceptions.NuExistaAceastaLicitatie;
import com.pao.proiect.SISTEM_LICITATII.exceptions.UserInexistentException;
import com.pao.proiect.SISTEM_LICITATII.model.*;
import com.pao.proiect.SISTEM_LICITATII.repository.ProdusRepository; // Adăugat importul de repo pentru produse
import com.pao.proiect.SISTEM_LICITATII.service.LicitatieService;
import com.pao.proiect.SISTEM_LICITATII.service.UserService;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        LicitatieService serviceL = LicitatieService.getInstance();
        UserService serviceU = UserService.getInstance();

        // Instanțiem ProdusRepository pentru a salva produsele din Main înainte de licitații
        ProdusRepository produsRepo = new ProdusRepository();

        // ==========================================
        // 1. DATE DE TEST - POPULARE AUTOMATĂ LA PORNIRE
        // ==========================================
//        Buyer b1 = new Buyer("Ion", "123", Categorie.ARTA);
//        Buyer b2 = new Buyer("Maria", "124", Categorie.MOBILA);
//        Buyer b3 = new Buyer("Alex", "125", Categorie.BIJUTERII);
//        Buyer b4 = new Buyer("Elena", "126", Categorie.ARTA);
//
//        Seller s1 = new Seller("Gigel", "200", 4.5);
//        Seller s2 = new Seller("Vlad", "201", 3.8);
//
//        // Pasul A: Salvăm utilizatorii în DB (Serviciul apelează Buyer/Seller Repo intern)
//        serviceU.adaugaUser(b1);
//        serviceU.adaugaUser(b2);
//        serviceU.adaugaUser(b3);
//        serviceU.adaugaUser(b4);
//        serviceU.adaugaUser(s1);
//        serviceU.adaugaUser(s2);
//
//        // Pasul B: Creăm produsele atașate de Selleri
//        Produs p1 = new Produs("Tablou Van Gogh", Categorie.ARTA, "Colectia 1", s1);
//        Produs p2 = new Produs("Canapea IKEA", Categorie.MOBILA, "Living", s2);
//        Produs p3 = new Produs("Inel aur", Categorie.BIJUTERII, "Luxury", s1);
//        Produs p4 = new Produs("Sculptura moderna", Categorie.ARTA, "Colectia 2", s2);
//
//        //  REZOLVARE EROARE: Salvăm produsele în DB pentru a genera id_produs în MySQL înainte de licitații!
//        produsRepo.save(p1);
//        produsRepo.save(p2);
//        produsRepo.save(p3);
//        produsRepo.save(p4);
//
//        // Pasul C: Creăm licitațiile pe baza produselor deja salvate (au ID-uri valide acum)
//        Licitatie l1 = new Licitatie(p1, 1000);
//        Licitatie l2 = new Licitatie(p2, 500);
//        Licitatie l3 = new Licitatie(p3, 2000);
//        Licitatie l4 = new Licitatie(p4, 1500);
//
//        serviceL.creazaLicitatie(l1);
//        serviceL.creazaLicitatie(l2);
//        serviceL.creazaLicitatie(l3);
//        serviceL.creazaLicitatie(l4);
//
//        // Pasul D: Ofertele inițiale atașate de ID-urile reale ale licitațiilor din DB
//        serviceL.ofertaservice(l1, new Oferta(1200, b1, l1.getId_licitatie()));
//        serviceL.ofertaservice(l1, new Oferta(1300, b2, l1.getId_licitatie()));
//        serviceL.ofertaservice(l1, new Oferta(1400, b3, l1.getId_licitatie()));
//
//        serviceL.ofertaservice(l2, new Oferta(600, b2, l2.getId_licitatie()));
//        serviceL.ofertaservice(l2, new Oferta(700, b1, l2.getId_licitatie()));
//
//        serviceL.ofertaservice(l3, new Oferta(2100, b3, l3.getId_licitatie()));
//        serviceL.ofertaservice(l3, new Oferta(2200, b4, l3.getId_licitatie()));
//        serviceL.ofertaservice(l3, new Oferta(2300, b1, l3.getId_licitatie()));
//
//        serviceL.ofertaservice(l4, new Oferta(1600, b4, l4.getId_licitatie()));
//        serviceL.ofertaservice(l4, new Oferta(1700, b2, l4.getId_licitatie()));


        // ==========================================
        // 2. INTERFAȚA INTERACTIVĂ (MENIU CONSOLĂ)
        // ==========================================
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n===== Gestionare Licitatii =====");
            System.out.println("1. Adaugă licitatie");
            System.out.println("2. Listare licitatii");
            System.out.println("3. Adauga user");
            System.out.println("4. Afiseaza userii");
            System.out.println("5. Fa o oferta la o licitatie");
            System.out.println("6. Sterge o licitatie");
            System.out.println("7. Cauta o licitatie dupa numele produsului");
            System.out.println("8. Afiseaza licitatiile sortate descrescator dupa numarul de oferte");
            System.out.println("9. Afisati care este cea mai cautata categorie a Buyerilor");
            System.out.println("10. Calculati care buyeri trebuie sa devina PremiumBuyeri si dati-le acest statut.");
            System.out.println("11. Afisati pt un seller dat de la tastatura toate produsele pe care le-a licitat, sortate dupa nume.");
            System.out.println(("12. Afisati pt o licitatie toate ofertele"));
            System.out.println("13. Inchide o licitatie");
            System.out.println("0. Ieșire");
            System.out.print("Opțiune: ");

            int n = scanner.nextInt();

            switch (n) {
                case 1:
                    try {
                        System.out.print("Introduceti cnp seller: ");
                        String cnp = scanner.next();

                        if (!cnp.matches("\\d+")) {
                            throw new CNPInvalidException("CNP-ul trebuie sa contina doar cifre!");
                        }

                        Seller u = null;
                        try {
                            u = (Seller) serviceU.cautaUser(UserType.SELLER, cnp);
                        } catch (UserInexistentException e) {
                            System.out.println(e.getMessage());
                            break;
                        }

                        scanner.nextLine(); // Consumă newline
                        System.out.print("Introdu numele produsului: ");
                        String name = scanner.nextLine();
                        System.out.print("Introdu categoria: ");
                        String categ = scanner.next();
                        Categorie c;
                        try {
                            c = Categorie.valueOf(categ.toUpperCase());
                        } catch (Exception e) {
                            System.out.println("Categorie invalida!");
                            break;
                        }

                        System.out.print("Introdu colectia: ");
                        String colectie = scanner.next();
                        System.out.print("Introduceti suma minima: ");
                        long suma = scanner.nextLong();

                        if (suma < 0) {
                            System.out.println("Pretul nu poate fi negativ!");
                            break;
                        }

                        Produs p = new Produs(name, c, colectie, u);
                        // Salvăm produsul în DB mai întâi
                        produsRepo.save(p);

                        Licitatie l = new Licitatie(p, suma);
                        serviceL.creazaLicitatie(l);

                    } catch (CNPInvalidException e) {
                        System.out.println(e.getMessage());
                    }
                    break;

                case 2:
                    try {
                        serviceL.afiseazaLicitatii();
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                    break;

                case 3:
                    try {
                        scanner.nextLine(); // Consumă newline
                        System.out.print("Introduceti numele dvs: ");
                        String name1 = scanner.nextLine();
                        System.out.print("Introduceti CNP-ul dvs: ");
                        String CNP = scanner.next();

                        if (!CNP.matches("\\d+")) {
                            throw new CNPInvalidException("CNP invalid!");
                        }

                        System.out.print("Vreti sa va inregistrati ca Buyer sau Seller? ");
                        UserType tip = UserType.valueOf(scanner.next().toUpperCase());

                        if (tip == UserType.BUYER) {
                            System.out.print("Introdu categoria preferata: ");
                            String categ1 = scanner.next();
                            Categorie c1 = Categorie.valueOf(categ1.toUpperCase());

                            Buyer b = new Buyer(name1, CNP, c1);
                            serviceU.adaugaUser(b);

                        } else if (tip == UserType.SELLER) {
                            System.out.print("Introdu rating: ");
                            float r = scanner.nextFloat();
                            Seller s = new Seller(name1, CNP, r);
                            serviceU.adaugaUser(s);
                        }

                    } catch (CNPInvalidException e) {
                        System.out.println(e.getMessage());
                    } catch (IllegalArgumentException e) {
                        System.out.println("Tip utilizator sau categorie invalida!");
                    }
                    break;

                case 4:
                    try {
                        serviceU.afiseazaUseri();
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                    break;

                case 5:
                    try {
                        System.out.print("Introduceti cnp buyer: ");
                        String cnp1 = scanner.next();

                        if (!cnp1.matches("\\d+")) {
                            throw new CNPInvalidException("CNP invalid!");
                        }

                        Buyer buyer = (Buyer) serviceU.cautaUser(UserType.BUYER, cnp1);

                        if (buyer == null) {
                            System.out.println("Trebuie sa va inregistrati mai intai ca BUYER.");
                            break;
                        }

                        System.out.print("Introduceti id-ul licitatiei din DB: ");
                        int id = scanner.nextInt();

                        System.out.print("Introduceti valoarea ofertei: ");
                        int val = scanner.nextInt();

                        Oferta o = new Oferta(val, buyer, id);

                        // Căutăm direct licitația în listă/DB din serviciu
                        Licitatie licitatieTinta = serviceL.getLicitatii().stream()
                                .filter(l -> l.getId_licitatie() == id)
                                .findFirst()
                                .orElse(null);

                        if (licitatieTinta == null) {
                            System.out.println("Licitatia cu ID-ul specificat nu exista în baza de date!");
                        } else {
                            serviceL.ofertaservice(licitatieTinta, o);
                        }

                    } catch (Exception e) {
                        System.out.println("Eroare la plasarea ofertei: " + e.getMessage());
                    }
                    break;

                case 6:
                    try {
                        System.out.print("Introduceti id-ul licitatiei de sters: ");
                        int id = scanner.nextInt();
                        serviceL.stergeLicitatie(id);
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                    break;

                case 7:
                    try {
                        scanner.nextLine(); // Consumă newline
                        System.out.print("Introduceti numele produsului licitat: ");
                        String numeP = scanner.nextLine();
                        serviceL.cautaDupaNume(numeP);
                    } catch (NuExistaAceastaLicitatie e) {
                        System.out.println(e.getMessage());
                    }
                    break;

                case 8:
                    try {
                        serviceL.afiseazaLicitatiiSortate();
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                    break;

                case 9:
                    try {
                        serviceU.categoriePrefBuyeri();
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                    break;

                case 10:
                    try {
                        int nr = serviceU.upgradePremiumBuyers(serviceL.getLicitatii());
                        System.out.println("Numar de buyeri convertiti la Premium: " + nr);
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                    break;

                case 11:
                    try {
                        System.out.print("Introduceti cnp-ul sellerului: ");
                        String CNP1 = scanner.next();
                        Seller seller = (Seller) serviceU.cautaUser(UserType.SELLER, CNP1);

                        if (seller == null) {
                            System.out.println("Userul introdus nu este seller.");
                        } else {
                            serviceU.afiseazaIstoricSeller(seller);
                        }

                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case 12:
                    try{
                        System.out.println("Introduceti id-ul licitatiei: ");
                        int id=scanner.nextInt();
                        serviceL.afiseaza_licitatie_si_oferte(id);
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case 13:
                    try {
                        System.out.print("Introduceti id-ul licitatiei de inchis: ");
                        int id = scanner.nextInt();
                        serviceL.terminaLicitatie(id);
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case 0:
                    System.out.println("Se inchide aplicatia...");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Opțiune invalidă! Încercați din nou.");
            }
        }
    }
}