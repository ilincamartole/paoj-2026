package com.pao.proiect.SISTEM_LICITATII;

public class Buyer extends User{

    final Categorie categoriePref;

    public Buyer(String nume, String cnp,  Categorie categoriePref){
        super(nume,cnp);
        this.categoriePref=categoriePref;
    }


}
