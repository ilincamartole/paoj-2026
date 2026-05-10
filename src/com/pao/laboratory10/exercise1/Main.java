package com.pao.laboratory10.exercise1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;


public class Main {
    public static void main(String[] args) throws IOException {
        // TODO: Implementează conform Readme.md
        BufferedReader br= new BufferedReader(new InputStreamReader(System.in));
        LinkedList<Tranzactie> l= new LinkedList<>();
        String line;

        while((line= br.readLine())!=null){
            String parts[]=line.split(" ");
            String c=parts[0];
            switch(c){
                case "ENQUEUE": {
                    int id = Integer.parseInt(parts[1]);
                    double suma = Double.parseDouble(parts[2]);
                    String data = parts[3];
                    TipTranzactie tip = TipTranzactie.valueOf(parts[4]);

                    l.addLast(new Tranzactie(id, suma, data, tip));
                    break;
                }
                case "DEQUEUE":{
                    if (l.isEmpty()) {
                        System.out.println("Coada goala.");
                    } else {
                        Tranzactie t = l.removeFirst();
                        System.out.println("Procesat: " + t);
                    }
                    break;
                }
                case "PUSH":{
                    int id = Integer.parseInt(parts[1]);
                    double suma = Double.parseDouble(parts[2]);
                    String data = parts[3];
                    TipTranzactie tip = TipTranzactie.valueOf(parts[4]);

                    l.addFirst(new Tranzactie(id, suma, data, tip));
                    break;
                }

                case "POP":{
                    if (l.isEmpty()) {
                        System.out.println("Coada goala.");
                    } else {
                        Tranzactie t = l.removeFirst();
                        System.out.println("Extras: " + t);
                    }
                    break;
                }

                case "REMOVE_DEBIT":{
                    int count = 0;

                    Iterator<Tranzactie> it = l.iterator();
                    while (it.hasNext()) {
                        Tranzactie t = it.next();

                        if (t.getTip() == TipTranzactie.DEBIT) {
                            it.remove();
                            count++;
                        }
                    }

                    System.out.println(
                            "Eliminat " + count + " tranzactii DEBIT.");
                    break;
                }

                case "REMOVE_BELOW":{
                    double threshold = Double.parseDouble(parts[1]);
                    int count = 0;

                    Iterator<Tranzactie> it = l.iterator();
                    while (it.hasNext()) {
                        Tranzactie t = it.next();

                        if (t.getSuma() < threshold) {
                            it.remove();
                            count++;
                        }
                    }

                    System.out.printf(
                            "Eliminat %d tranzactii sub %.2f RON.%n",
                            count, threshold
                    );
                    break;
                }

                case "PRINT":{
                    for (Tranzactie t : l) {
                        System.out.println(t);
                    }
                    break;
                }

                case "SIZE":{
                    System.out.println(
                            "Dimensiune coada: " + l.size());
                    break;
                }
            }


        }


    }
}
