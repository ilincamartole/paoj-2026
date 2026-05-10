package com.pao.laboratory09.exercise1;

import java.io.*;
import java.util.*;

public class Main {
    private static final String OUTPUT_FILE = "output/lab09_ex1.ser";

    public static void main(String[] args) throws Exception {
        // TODO: Implementează conform Readme.md
        //
        // 1. Citește N din stdin, apoi cele N tranzacții (id suma data contSursa contDestinatie tip)
        // 2. Setează câmpul note = "procesat" pe fiecare tranzacție înainte de serializare
        // 3. Serializează lista de tranzacții în OUTPUT_FILE cu ObjectOutputStream (try-with-resources)
        // 4. Deserializează lista din OUTPUT_FILE cu ObjectInputStream (try-with-resources)
        // 5. Procesează comenzile din stdin până la EOF:
        //    - LIST          → afișează toate tranzacțiile, câte una pe linie
        //    - FILTER yyyy-MM → afișează tranzacțiile cu data care începe cu yyyy-MM
        //                       sau "Niciun rezultat." dacă nu există
        //    - NOTE id        → afișează "NOTE[id]: <valoarea câmpului note>"
        //                       sau "NOTE[id]: not found" dacă id-ul nu există
        //
        // Format linie tranzacție:
        //   [id] data tip: suma RON | contSursa -> contDestinatie
        //   Ex: [1] 2024-01-15 CREDIT: 1500.00 RON | RO01SRC1 -> RO01DST1

//        System.out.println("TODO: implementează exercițiul 1");

        List<Tranzactie> tranzactii = new ArrayList<>();

        try (Scanner sc = new Scanner(System.in)) {
            // 1. Citire N
            int N = Integer.parseInt(sc.nextLine());

            // 1. Citire tranzacții
            for (int i = 0; i < N; i++) {
                String line = sc.nextLine();
                String[] parts = line.split(" ");

                int id = Integer.parseInt(parts[0]);
                double suma = Double.parseDouble(parts[1]);
                String data = parts[2];
                String contSursa = parts[3];
                String contDest = parts[4];
                TipTranzactie tip = TipTranzactie.valueOf(parts[5]);

                Tranzactie t = new Tranzactie(id, suma, data, contSursa, contDest, tip);

                t.setNote("procesat");

                tranzactii.add(t);
            }

            // 4.. Serializare
            new File("output").mkdirs();
            try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(OUTPUT_FILE))) {
                oos.writeObject(tranzactii);
            }

            // 4. Deserializare
            List<Tranzactie> lista;
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(OUTPUT_FILE))) {
                lista = (List<Tranzactie>) ois.readObject();
            }



            // 5. Comenzi până la EOF
            while (sc.hasNextLine()) {
                String commandLine = sc.nextLine().trim();

                if (commandLine.isEmpty()) continue;

                if (commandLine.equals("LIST")) {
                    for (Tranzactie t : lista) {
                        System.out.println(formatTranzactie(t));
                    }
                } else if (commandLine.startsWith("FILTER")) {
                    String[] parts = commandLine.split(" ");
                    String prefix = parts[1];

                    boolean found = false;
                    for (Tranzactie t : lista) {
                        if (t.getData().startsWith(prefix)) {
                            System.out.println(formatTranzactie(t));
                            found = true;
                        }
                    }

                    if (!found) {
                        System.out.println("Niciun rezultat.");
                    }
                } else if (commandLine.startsWith("NOTE")) {
                    String[] parts = commandLine.split(" ");
                    int id = Integer.parseInt(parts[1]);

                    Tranzactie found = null;
                    for (Tranzactie t : lista) {
                        if (t.getId() == id) {
                            found = t;
                            break;
                        }
                    }

                    if (found == null) {
                        System.out.println("NOTE[" + id + "]: not found");
                    } else {
                        System.out.println("NOTE[" + id + "]: " + found.getNote());
                    }
                }
            }

        } catch (
                Exception e) {
            e.printStackTrace();
        }


    }

    private static String formatTranzactie(Tranzactie t) {
        return String.format("[%d] %s %s: %.2f RON | %s -> %s",
                t.getId(),
                t.getData(),
                t.getTip(),
                t.getSuma(),
                t.getContSursa(),
                t.getContDestinatie());
    }
}

