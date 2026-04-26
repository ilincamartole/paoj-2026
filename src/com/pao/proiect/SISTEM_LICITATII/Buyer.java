package com.pao.proiect.SISTEM_LICITATII;

import com.pao.proiect.SISTEM_LICITATII.enums.Categorie;

public class Buyer extends User{

    final Categorie categoriePref;

    public Buyer(String nume, String cnp,  Categorie categoriePref){
        super(nume,cnp);
        this.categoriePref=categoriePref;
    }

    public Categorie getCategoriePref() {
        return categoriePref;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;

        User user = (User) o;
        return cnp.equals(user.getCnp());
    }

    @Override
    public int hashCode() {
        return cnp.hashCode();
    }
}
