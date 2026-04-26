package com.pao.proiect.SISTEM_LICITATII.model;

import com.pao.proiect.SISTEM_LICITATII.enums.Categorie;

public final class PremiumBuyer extends Buyer {

    final private float discountRate;

    public PremiumBuyer(String nume, String cnp, Categorie categoriePref, float discountRate) {
        super(nume, cnp, categoriePref);
        this.discountRate = discountRate;

    }
}