package com.pao.laboratory07.exercise2;

public final class ComandaRedusa extends Comanda{

    private int discountProcent;
    public ComandaRedusa(String nume, double pret, int discoutProcent){
        this.nume=nume;

        this.pret=pret;
        this.discountProcent=discoutProcent;


    }
    public String descriere(

    ){
        return "DISCOUNTED: %s, pret: %.2f lei (-%d%%) [PLACED]".formatted(this.nume, pretFinal(), this.discountProcent);

    }

    public  double pretFinal(){

    return pret * (1 - discountProcent / 100.0);
    }
}
