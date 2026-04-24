package com.pao.proiect.SISTEM_LICITATII;

public class Oferta implements Comparable <Oferta> {

    private int valoare;
    private User buyer;
    public Oferta(int valoare, User buyer){
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
}
