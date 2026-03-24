package com.pao.laboratory05.angajati;


import java.util.Scanner;

/**
 * Exercise 3 — Angajați
 *
 * Cerințele complete se află în:
 *   src/com/pao/laboratory05/Readme.md  →  secțiunea "Exercise 3 — Angajați"
 *
 * Creează fișierele de la zero în acest pachet, apoi rulează Main.java
 * pentru a verifica output-ul așteptat din Readme.
 */
public class Main {
    public static void main(String[] args) {
        System.out.println("Cerințele se află în Readme.md — secțiunea Exercise 3.");
        AngajatService service= AngajatService.getInstance();

        while (true) {
            System.out.println("\n===== Gestionare Angajați =====");
            System.out.println("1. Adaugă angajat");
            System.out.println("2. Listare după salariu");
            System.out.println("3. Caută după departament");
            System.out.println("0. Ieșire");
            System.out.print("Opțiune: ");
            Scanner scanner = new Scanner(System.in);
            int n;
            n=scanner.nextInt();

            switch(n){
                case 1:
                    System.out.print("Introdu numele Angajatului: ");
                    String name = scanner.next();
                    System.out.print("Introdu numele departamnetului: ");
                    String numedep = scanner.next();
                    System.out.print("Introdu locatia departamentului: ");
                    String locatie = scanner.next();
                    System.out.print("Introdu salariul angajatului: ");
                    double salariu  = scanner.nextDouble();

                    Departament dep= new Departament(numedep, locatie);

                    service.addAngajat(new Angajat(name, dep, salariu));
                    break;


                case 2:
                    service.listBySalary();
                   break;

                case 3:
                    System.out.println("Introducti numele departamentului: ");
                    String depart=scanner.next();

                    service.findByDepartament(depart);
                    break;

                case 0:
                    System.exit(0);
                    break;




            }


            // citește opțiunea și execută acțiunea
        }
    }
}
