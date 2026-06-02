
package com.pao.proiect.SISTEM_LICITATII;

import com.pao.proiect.SISTEM_LICITATII.enums.Categorie;
import com.pao.proiect.SISTEM_LICITATII.enums.UserType;
import com.pao.proiect.SISTEM_LICITATII.exceptions.CNPInvalidException;
import com.pao.proiect.SISTEM_LICITATII.exceptions.NuExistaAceastaLicitatie;
import com.pao.proiect.SISTEM_LICITATII.exceptions.UserInexistentException;
import com.pao.proiect.SISTEM_LICITATII.model.*;
import com.pao.proiect.SISTEM_LICITATII.repository.ProdusRepository;
import com.pao.proiect.SISTEM_LICITATII.service.LicitatieService;
import com.pao.proiect.SISTEM_LICITATII.service.UserService;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        LicitatieService serviceL = LicitatieService.getInstance();
        UserService serviceU = UserService.getInstance();

        ProdusRepository produsRepo = new ProdusRepository();


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

                        scanner.nextLine();
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
                        scanner.nextLine();
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
                        scanner.nextLine();
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