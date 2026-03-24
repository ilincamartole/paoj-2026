package com.pao.laboratory05.audit;

import com.pao.laboratory05.angajati.Angajat;
import com.pao.laboratory05.angajati.AngajatService;
import com.pao.laboratory05.angajati.Departament;

import java.util.Scanner;

/**
 * Exercise 4 (Bonus) — Audit Log
 *
 * Cerințele complete se află în:
 *   src/com/pao/laboratory05/Readme.md  →  secțiunea "Exercise 4 (Bonus) — Audit"
 *
 * Extinde soluția de la Exercise 3 cu un sistem de audit bazat pe record.
 * Creează fișierele de la zero în acest pachet, apoi rulează Main.java
 * pentru a verifica output-ul așteptat din Readme.
 */
public class Main {
    public static void main(String[] args) {
        System.out.println("Cerințele se află în Readme.md — secțiunea Exercise 3.");
        AuditService service= AuditService.getInstance();

        while (true) {
            System.out.println("\n===== Gestionare Angajați =====");
            System.out.println("1. Adaugă angajat");
            System.out.println("2. Listare după salariu");
            System.out.println("3. Caută după departament");
            System.out.println("4. Print Audit");
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

                case 4:
                    service.printAuditLog();
                    break;


                case 0:
                    System.exit(0);
                    break;




            }


            // citește opțiunea și execută acțiunea
        }
    }
}
