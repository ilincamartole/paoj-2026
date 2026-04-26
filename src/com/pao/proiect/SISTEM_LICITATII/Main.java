package com.pao.proiect.SISTEM_LICITATII;


import com.pao.proiect.SISTEM_LICITATII.enums.Categorie;
import com.pao.proiect.SISTEM_LICITATII.enums.UserType;
import com.pao.proiect.SISTEM_LICITATII.exceptions.CNPInvalidException;
import com.pao.proiect.SISTEM_LICITATII.exceptions.NuExistaAceastaLicitatie;
import com.pao.proiect.SISTEM_LICITATII.exceptions.UserInexistentException;
import com.pao.proiect.SISTEM_LICITATII.model.*;
import com.pao.proiect.SISTEM_LICITATII.service.LicitatieService;
import com.pao.proiect.SISTEM_LICITATII.service.UserService;


import java.util.Scanner;

public class Main {

        public static void main(String[] args) {
            LicitatieService serviceL= LicitatieService.getInstance();
            UserService serviceU=UserService.getInstance();


            Buyer b1 = new Buyer("Ion", "123", Categorie.ARTA);
            Buyer b2 = new Buyer("Maria", "124", Categorie.MOBILA);
            Buyer b3 = new Buyer("Alex", "125", Categorie.BIJUTERII);
            Buyer b4 = new Buyer("Elena", "126", Categorie.ARTA);

            Seller s1 = new Seller("Gigel", "200", 4.5);
            Seller s2 = new Seller("Vlad", "201", 3.8);

            serviceU.adaugaUser(b1);
            serviceU.adaugaUser(b2);
            serviceU.adaugaUser(b3);
            serviceU.adaugaUser(b4);
            serviceU.adaugaUser(s1);
            serviceU.adaugaUser(s2);


            Produs p1 = new Produs("Tablou Van Gogh", Categorie.ARTA, "Colectia 1", s1);
            Produs p2 = new Produs("Canapea IKEA", Categorie.MOBILA, "Living", s2);
            Produs p3 = new Produs("Inel aur", Categorie.BIJUTERII, "Luxury", s1);
            Produs p4 = new Produs("Sculptura moderna", Categorie.ARTA, "Colectia 2", s2);

            Licitatie l1 = new Licitatie(p1, 1000);
            Licitatie l2 = new Licitatie(p2, 500);
            Licitatie l3 = new Licitatie(p3, 2000);
            Licitatie l4 = new Licitatie(p4, 1500);

            serviceL.creazaLicitatie(l1);
            serviceL.creazaLicitatie(l2);
            serviceL.creazaLicitatie(l3);
            serviceL.creazaLicitatie(l4);


            serviceL.ofertaservice(l1, new Oferta(1200, b1));
            serviceL.ofertaservice(l1, new Oferta(1300, b2));
            serviceL.ofertaservice(l1, new Oferta(1400, b3));

            serviceL.ofertaservice(l2, new Oferta(600, b2));
            serviceL.ofertaservice(l2, new Oferta(700, b1));

            serviceL.ofertaservice(l3, new Oferta(2100, b3));
            serviceL.ofertaservice(l3, new Oferta(2200, b4));
            serviceL.ofertaservice(l3, new Oferta(2300, b1));

            serviceL.ofertaservice(l4, new Oferta(1600, b4));
            serviceL.ofertaservice(l4, new Oferta(1700, b2));

            Scanner scanner = new Scanner(System.in);

            while (true) {

                System.out.println("\n===== Gestionare Liciitatii =====");
                System.out.println("1. Adaugă licitatie");
                System.out.println("2. Listare licitatii");
                System.out.println("3. Adauga user");
                System.out.println("4. Afiseaza userii");
                System.out.println("5. Fa o oferta la o licitatie");
                System.out.println("6. Sterge o licittaie");
                System.out.println("7. Cauta o licitatie dupa numele produsului");
                System.out.println("8. Afiseaza licitatiile sortate descrescator dupa numarul de oferte");
                System.out.println("9. Afisati care este cea mai cautata categorie a Buyerilor");
                System.out.println("10. Calculati care buyeri trebuie sa devina PremiumBuyeri si dati le acest statut.");
                System.out.println("11. Afisati pt un seller dat de la tastaura toate produsele pe care le-a licitat, sortate dupa nume.");

                System.out.println("0. Ieșire");
                System.out.print("Opțiune: ");
                int n = scanner.nextInt();

                switch (n) {

                    case 1:
                        try {
                            System.out.print("Introduceti cnp: ");
                            String cnp = scanner.next();

                            if (!cnp.matches("\\d+")) {
                                throw new CNPInvalidException("CNP-ul trebuie sa contina doar cifre!");
                            }

                            Seller u = null;
                            try {
                                u = (Seller) serviceU.cautaUser(UserType.SELLER, cnp);
                            } catch (UserInexistentException e) {
                                System.out.println((e.getMessage()));
                                break;
                            }

                            scanner.nextLine();
                            System.out.print("Introdu numele produsului ");
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
                            System.out.println("Introduceti suma minima: ");

                            long suma = scanner.nextLong();

                            if (suma < 0) {
                                System.out.println("Pretul nu poate fi negativ!");
                                break;
                            }

                            Produs p = new Produs(name, c, colectie, u);
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
                            scanner.nextLine();
                            System.out.println("Introducti numele dvs: ");
                            String name1 = scanner.nextLine();
                            System.out.println("Introducti CNP-ul dvs: ");
                            String CNP = scanner.next();

                            if (!CNP.matches("\\d+")) {
                                throw new CNPInvalidException("CNP invalid!");
                            }


                            System.out.println("Vreti sa va inregistrati ca Buyer sau Seller?");
                            UserType tip = UserType.valueOf(scanner.next().toUpperCase());

                            if (tip == UserType.BUYER) {
                                System.out.print("Introdu categoria prefarata: ");
                                String categ1 = scanner.next();
                                Categorie c1 = Categorie.valueOf(categ1.toUpperCase());

                                Buyer b = new Buyer(name1, CNP, c1);
                                serviceU.adaugaUser(b);

                            } else {
                                if (tip == UserType.SELLER) {
                                    System.out.print("Introdu  rating: ");
                                    float r = scanner.nextFloat();
                                    Seller s = new Seller(name1, CNP, r);
                                    serviceU.adaugaUser(s);
                                }
                            }

                        } catch (CNPInvalidException e) {
                            System.out.println(e.getMessage());
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
                            System.out.println("Introduceti cnp: ");
                            String cnp1 = scanner.next();

                            if (!cnp1.matches("\\d+")) {
                                throw new CNPInvalidException("CNP invalid!");
                            }

                            Buyer buyer = (Buyer) serviceU.cautaUser(UserType.BUYER, cnp1);

                            if (buyer == null) {
                                System.out.println("Trebuie sa va inregistrati mai intai ca BUYER.");
                                break;
                            }

                            System.out.println("Introduceti id ul licitatiei la care vreti sa participati: ");
                            int id = scanner.nextInt();

                            System.out.println("Introduceti valoarea ofertei:  ");
                            int val = scanner.nextInt();

                            Oferta o = new Oferta(val, buyer);

                            serviceL.ofertaservice(serviceL.getLicitatii().get(id), o);

                        } catch (Exception e) {
                            System.out.println(e.getMessage());
                        }
                        break;

                    case 6:
                        try {
                            System.out.println("Introduceti id ul licitatiei: ");
                            int id = scanner.nextInt();
                            serviceL.stergeLicitatie(id);
                        } catch (Exception e) {
                            System.out.println(e.getMessage());
                        }
                        break;

                    case 7:
                        try {
                            scanner.nextLine();
                            System.out.println("Introduceti numele produsului licitat: ");
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
                            System.out.println(nr);
                        } catch (Exception e) {
                            System.out.println(e.getMessage());
                        }
                        break;

                    case 11:
                        try {
                            System.out.println("Introduceti cnp-ul sellerului: )");
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

                    case 0:
                        System.exit(0);
                        break;
                }
            }
        }
    }

