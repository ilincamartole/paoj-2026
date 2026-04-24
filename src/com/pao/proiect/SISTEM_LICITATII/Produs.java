package com.pao.proiect.SISTEM_LICITATII;

public class Produs {

    private Categorie categorie;
    private String nume;
    private String colectie;
    private Seller seller;

    public Produs(String nume,  Categorie categorie, String colectie, Seller seller){
        this. nume=nume;
        this.categorie=categorie;
        this.colectie=colectie;
        this. seller=seller;
    }

    @Override
    public String toString(){
        return "Nume: "+ this.nume + "\n Categorie: "+ this.categorie+ "\n Colectie: "+ this.colectie+ "\n VAndut de: "+ this.seller;
    }
    public String getNume(){
        return nume;
    }
}
