package com.pao.proiect.SISTEM_LICITATII.service;
import java.util.*;

import com.pao.proiect.SISTEM_LICITATII.Licitatie;
import com.pao.proiect.SISTEM_LICITATII.Oferta;
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

        licitatii.add(licitatie);
        System.out.println("Am creat Licitatia:  " + licitatie);
        if (!licitatie.getProdus().getSeller()
                .getIstoricProduse()
                .contains(licitatie.getProdus())) {

            licitatie.getProdus().getSeller()
                    .getIstoricProduse()
                    .add(licitatie.getProdus());
        }


    }

        public void afiseazaLicitatii(){
            for(int i=0;i<licitatii.size();i++)
                System.out.println(i+". "+licitatii.get(i));}

        public void ofertaservice(Licitatie l, Oferta o){
            if (o.getValoare()<l.getMinValue())
            { throw new OfertaInvalidaException("Oferta prea mica!");}
            l.adaugaOferta(o);
        }

        public void stergeLicitatie(int id) {

            boolean ok = false;
            for (
                    int i=0;i< licitatii.size();i++) {
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
        boolean ok=false;
        for (Licitatie l : licitatii) {
            if (l.getProdus().getNume().equalsIgnoreCase(nume)) {
                System.out.println(l);
                ok=true;


            }

        }
        if (ok==false){
            throw new NuExistaAceastaLicitatie(" Nu exista licittaii pt un produs cu acest nume!");
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









