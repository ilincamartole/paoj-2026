package com.pao.laboratory07.exercise2;

public final class ComandaStandard extends Comanda{

    public ComandaStandard(String nume, double pret){
        this.nume=nume;
        this.pret=pret;
    }
    public String descriere(

    ){
        return "STANDARD: %s, pret: %.2f lei [PLACED]".formatted(this.nume, this.pret);
    }

    public  double pretFinal(){
        return pret;

    }
}
