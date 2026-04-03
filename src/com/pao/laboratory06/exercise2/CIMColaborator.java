package com.pao.laboratory06.exercise2;

import java.util.Scanner;
public class CIMColaborator extends PersoanaFizica{

    private boolean BONUS=false;

    public void citeste(Scanner in) {

        this.nume = in.next();
        this.prenume = in.next();
        this.venitBrutLunar = in.nextDouble();

        if (in.hasNext()) {
            String nextVal = in.next();


            this.BONUS = nextVal.equals("DA");
        }
    }
    @Override
    public TipColaborator getTip() {
        return TipColaborator.CIM;
    }

    @Override
    public void afiseaza() {
        System.out.println(
                tipContract()+": "+ nume+" "+prenume+", "+ calculeazaVenitNetAnual()+" lei");
    }

    @Override
    public String tipContract() {
        return TipColaborator.CIM.name();
    }

    @Override
    public boolean areBonus() {
        return this.BONUS;
    }

    @Override
    public double calculeazaVenitNetAnual() {
        double aux;
        aux=this.venitBrutLunar*12*0.55;
        if (BONUS){
            aux=1.1*aux;

        }
        return aux;
    }
}
