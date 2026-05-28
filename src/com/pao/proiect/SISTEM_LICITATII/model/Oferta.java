package com.pao.proiect.SISTEM_LICITATII.model;

public class Oferta implements Comparable <Oferta> {
    protected int id_oferta;
    private static int generatorID=0;
    private int valoare;
    private Buyer buyer;
    private int idLicitatie;
    public Oferta(int valoare, Buyer buyer, int idLicitatie){
        this. valoare=valoare;
        this.buyer=buyer;
        this.idLicitatie=idLicitatie;


    }


    public int getId_oferta() {
        return id_oferta;
    }

    public void setId_oferta(int id_oferta) {
        this.id_oferta = id_oferta;
    }

    public int getIdLicitatie() { return idLicitatie; }
    public void setIdLicitatie(int idLicitatie) { this.idLicitatie = idLicitatie; }

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
