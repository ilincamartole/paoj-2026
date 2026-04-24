package com.pao.proiect.SISTEM_LICITATII;

public final class PremiumBuyer extends Buyer{

    final private float discountRate;

    public PremiumBuyer(String nume, String cnp, String email, Categorie categoriePref, float discountRate) {
        super(nume, cnp, categoriePref);
        this.discountRate = discountRate;

    }
}