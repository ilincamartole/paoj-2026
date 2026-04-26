package com.pao.proiect.SISTEM_LICITATII.model;

import com.pao.proiect.SISTEM_LICITATII.enums.Categorie;

public class Buyer extends User {

    final Categorie categoriePref;

    public Buyer(String nume, String cnp,  Categorie categoriePref){
        super(nume,cnp);
        this.categoriePref=categoriePref;
    }

    public Categorie getCategoriePref() {
        return categoriePref;
    }


}
