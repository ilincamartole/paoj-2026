package com.pao.proiect.SISTEM_LICITATII.model;

public class Oferta implements Comparable <Oferta> {
    private final int id_oferta;
    private static int generatorID=0;
    private int valoare;
    private Buyer buyer;
    public Oferta(int valoare, Buyer buyer){
        this.id_oferta=generatorID++;
        this. valoare=valoare;
        this.buyer=buyer;


    }

    @Override
    public String toString() {
        return "suma: "+ valoare+ "\nBuyer: "+ buyer;
    }

    @Override
    public int compareTo(Oferta o) {
        return Integer.compare(this.valoare, o.valoare);
    }

    public Buyer getBuyer(){
        return buyer;
    }
    public int getValoare(){
        return valoare;
    }
}
