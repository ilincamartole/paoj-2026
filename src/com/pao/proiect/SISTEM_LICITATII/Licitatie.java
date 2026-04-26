package com.pao.proiect.SISTEM_LICITATII;

public class Licitatie{
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
        return "Produsul cu id : " + this.produs+ "cu valoare minima: "+ minValue;
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
