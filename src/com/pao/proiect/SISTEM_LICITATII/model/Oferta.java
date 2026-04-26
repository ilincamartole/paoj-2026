package com.pao.proiect.SISTEM_LICITATII.model;

public class Oferta implements Comparable <Oferta> {

    private int valoare;
    private Buyer buyer;
    public Oferta(int valoare, Buyer buyer){
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
