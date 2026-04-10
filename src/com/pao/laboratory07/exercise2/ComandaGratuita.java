package com.pao.laboratory07.exercise2;

public final class  ComandaGratuita extends Comanda{

    public ComandaGratuita(String nume){
        this.nume=nume;
    }
    public String descriere(

    ){
        return "GIFT: %s, gratuit [PLACED]".formatted(this.nume);

    }

    public  double pretFinal(){
        return 0.0;

    }
}
