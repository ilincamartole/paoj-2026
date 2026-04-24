package com.pao.proiect.SISTEM_LICITATII;


import com.pao.proiect.SISTEM_LICITATII.service.LicitatieService;
import com.pao.proiect.SISTEM_LICITATII.service.UserService;

import java.util.Scanner;

public class Main {

        public static void main(String[] args) {
            LicitatieService serviceL= LicitatieService.getInstance();
            UserService serviceU=UserService.getInstance();

            while (true) {
                System.out.println("\n===== Gestionare Liciitatii =====");
                System.out.println("1. Adaugă licitatie");
                System.out.println("2. Listare licitatii");
                System.out.println("3. Adauga user");
                System.out.println("4. Afiseaza userii");
                System.out.println("5. Fa o oferta la o licitatie");
                System.out.println("6. Sterge o licittaie");//de scris in main
                System.out.println("7. Cauta o licitatie dupa numele produsului");//de scris in main
                System.out.println("8. Afiseaza licitatiile sortate dupa numarul de oferte");//de implementat

                System.out.println("0. Ieșire");
                System.out.print("Opțiune: ");
                Scanner scanner = new Scanner(System.in);
                int n;
                n=scanner.nextInt();

                switch(n){
                    case 1:
                        System.out.print("Introduceti cnp: ");
                        String cnp = scanner.next();


                        int idx=-1;
//                        for (int i=0;i<serviceU.getUseri().length;i++)
//                            {
//                                if(cnp.equals(serviceU.getUseri()[i].cnp)){
//                                    idx=i;
//
//
//                                }
//
//                            }
                        User u=serviceU.cautaUser("SELLER", cnp);
                        if (u!=null){

                            System.out.print("Introduceti rating: ");
                            float r=scanner.nextFloat();
                            Seller s= new Seller(u.nume, u.cnp,r);
                            System.out.print("Introdu numele produsului ");
                            String name = scanner.next();
                            System.out.print("Introdu categoria: ");
                            String categ = scanner.next();
                            Categorie c=Categorie.valueOf(categ.toUpperCase());
                            System.out.print("Introdu colectia: ");
                            String colectie = scanner.next();
                            System.out.println("Introduceti suma minima: ");
                            long suma= scanner.nextLong();

                            Produs p= new Produs(name, c, colectie, s);

                            Licitatie l= new Licitatie(p,suma);
                            serviceL.creazaLicitatie(l);


                        }
                        else{
                            System.out.println("Nu sunteti un seller. Trebuie sa fiti adaugat ca user!");
                            break;
                        }
                        break;
                    case 2:
                        serviceL.afiseazaLicitatii();
                        break;


                    case 3:
                        System.out.println("Introducti numele dvs: ");
                        String name=scanner.next();
                        System.out.println("Introducti CNP-ul dvs: ");
                        String CNP=scanner.next();
                        System.out.println("Vreti sa va inregistrati ca Buyer sau Seller?");
                        String tip=scanner.next().toUpperCase();

                        if (tip.equals("BUYER")){
                            System.out.print("Introdu categoria prefarata: ");
                            String categ = scanner.next();
                            Categorie c=Categorie.valueOf(categ.toUpperCase());
                            Buyer b= new Buyer(name, CNP, c);

                            serviceU.adaugaUser(b,tip);

                        }
                        else{
                            if(tip.equals("SELLER")) {
                                System.out.print("Introdu  rating: ");
                                float r = scanner.nextFloat();
                                Seller s = new Seller(name, CNP, r);
                                serviceU.adaugaUser(s, tip);


                            }

                        }


                        break;
                    case 4:
                        serviceU.afiseazaUseri();
                        break;

                    case 5:
                        System.out.println("Introduceti cnp: ");
                        String cnp1=scanner.next();
                        User USER=serviceU.cautaUser("BUYER", cnp1);
                        if (USER==null){
                            System.out.println("Trebuie sa va inregistrati mai intai ca BUYER.");

                        }

                        else{
                            System.out.println("Introduceti id ul licitatiei la care vreti sa participati: ");
                            int id= scanner.nextInt();
                            System.out.println("Introduceti id ul valoarea ofertei:  ");
                            int val= scanner.nextInt();
                            Oferta o=new Oferta(val, USER);


                            serviceL.ofertaservice(serviceL.getLicitatii().get(id), o );
                        }

                    case 0:
                        System.exit(0);
                        break;




                }


                // citește opțiunea și execută acțiunea
            }
        }
    }

