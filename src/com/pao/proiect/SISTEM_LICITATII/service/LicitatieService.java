package com.pao.proiect.SISTEM_LICITATII.service;
import java.util.*;

import com.pao.proiect.SISTEM_LICITATII.model.Licitatie;
import com.pao.proiect.SISTEM_LICITATII.model.Oferta;
import com.pao.proiect.SISTEM_LICITATII.exceptions.NuExistaAceastaLicitatie;
import com.pao.proiect.SISTEM_LICITATII.exceptions.OfertaInvalidaException;

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
        if (licitatie == null) {
            throw new IllegalArgumentException("Licitatia primita este null!");
        }
        if (licitatie.getProdus() == null) {
            throw new IllegalArgumentException("Licitatia trebuie sa contina un produs valid!");
        }
        if (licitatie.getProdus().getSeller() == null) {
            throw new IllegalArgumentException("Produsul trebuie sa aiba un seller asociat!");
        }

        licitatii.add(licitatie);
        System.out.println("Am creat Licitatia:  " + licitatie);


        licitatie.getProdus().getSeller().getIstoricProduse().add(licitatie.getProdus());
    }

    public void afiseazaLicitatii(){
        for(int i=0;i<licitatii.size();i++)
            System.out.println(licitatii.get(i));}

    public void ofertaservice(Licitatie l, Oferta o) {
        if (l == null) {
            throw new IllegalArgumentException("Licitatia specificata nu exista (null)!");
        }
        if (o == null) {
            throw new IllegalArgumentException("Oferta nu poate fi null!");
        }

        if (o.getValoare() < l.getMinValue()) {
            throw new OfertaInvalidaException("Oferta prea mica!");
        }

        l.adaugaOferta(o);
    }

    public void stergeLicitatie(int id) {

        boolean ok = false;
            for (int i=0;i< licitatii.size();i++) {
                if (licitatii.get(i).getProdus().getId() == id) {
                    licitatii.remove(i);
                    ok=true;
                    break;


                }

            }
            if (ok == false) {
                throw new NuExistaAceastaLicitatie("Nu exista aceasta licitatie! Incercati alt id?");
            }
        }

    public void cautaDupaNume(String nume) {
        if (nume == null || nume.isBlank()) {
            throw new IllegalArgumentException("Numele cautat nu poate fi gol!");
        }

        boolean ok = false;
        for (Licitatie l : licitatii) {
            if (l.getProdus() != null && l.getProdus().getNume().equalsIgnoreCase(nume)) {
                System.out.println(l);
                ok = true;
            }
        }
        if (!ok) {
            throw new NuExistaAceastaLicitatie("Nu exista licitatii pt un produs cu acest nume!");
        }
    }
    public void afiseazaLicitatiiSortate(){
        List<Licitatie> copy = new ArrayList<>(licitatii);
        copy.sort(Comparator.comparingInt((Licitatie l) -> l.getOferte().length).reversed());

        for (Licitatie c:copy){
            System.out.println(c+"\n");

        }




    }



    }









