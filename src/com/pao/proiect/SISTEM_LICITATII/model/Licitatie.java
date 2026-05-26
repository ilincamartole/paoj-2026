package com.pao.proiect.SISTEM_LICITATII.model;

public class Licitatie{
    protected int id_licitatie;
    private Produs produs;
    private Oferta[] oferte;
    private long minValue;


    public Licitatie(Produs produs, long minValue){
        this.produs=produs;
        this.oferte= new Oferta[0];
        this.minValue=minValue;
    }


    @Override
    public String toString() {
        return  this.getProdus().getId()+". Produsul : " + this.produs+ "\ncu valoare minima: "+ minValue;
    }

    public int getId_licitatie() {
        return id_licitatie;
    }


    public void setId_licitatie(int id_licitatie) {
        this.id_licitatie = id_licitatie;
    }

    public void adaugaOferta(Oferta o){

        Oferta[] temp = new Oferta[oferte.length + 1];
        System.arraycopy(oferte, 0, temp, 0, oferte.length);
        temp[temp.length - 1] = o;
        oferte = temp;
        System.out.println("Am adaugat oferta: " + o);

    }
    public long getMinValue(){
        return minValue;
    }
    public Produs getProdus(){
        return produs;
    }


    public Oferta[] getOferte(){
        return oferte;
    }

    public void ofertaS(Oferta oferta) {

    }
}
