package com.pao.proiect.SISTEM_LICITATII.service;
import java.util.ArrayList;
import java.util.Arrays;
import com.pao.proiect.SISTEM_LICITATII.Licitatie;
import com.pao.proiect.SISTEM_LICITATII.Oferta;

import java.util.List;
import java.util.Scanner;

public class LicitatieService {

    private List<Licitatie> licitatii;

    private LicitatieService(){
        this. licitatii= new ArrayList<>();

    }

    public static class Holder{
        public static final LicitatieService INSTANCE = new LicitatieService();
    }

    public static LicitatieService getInstance(){
        return Holder.INSTANCE;
    }

    public List<Licitatie> getLicitatii(){
        return licitatii;
    }


    public void creazaLicitatie(Licitatie licitatie) {
        licitatii.add(licitatie);
        System.out.println("Am creat Licitatia:  " + licitatie);

//        int n;
//        Scanner scanner= new Scanner(System.in);
//
//        n=scanner.nextInt();
//
//        switch(n){
//            case 1:
//              System.out.println("1. Adauga o oferta: ");
//                System.out.println("Introduceti CNP-ul: ");
//                String cnp= scanner.next();
//
//        }
    }

        public void afiseazaLicitatii(){
            for(int i=0;i<licitatii.size();i++)
                System.out.println(i+". "+licitatii.get(i));}

        public void ofertaservice(Licitatie l, Oferta o){
            l.adaugaOferta(o);
        }

        public void stergeLicitatie(int index){

            if (0<=index && index<=licitatii.size()){
                licitatii.remove(index);
            }

        }

    public void cautaDupaNume(String nume) {

        for (Licitatie l : licitatii) {
            if (l.getProdus().getNume().equalsIgnoreCase(nume)) {
                System.out.println(l);
            }
        }


    }

    }







