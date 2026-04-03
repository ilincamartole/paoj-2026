package com.pao.laboratory06.exercise2;

import java.util.Scanner;
public class SRLColaborator extends PersoanaJuridica{
    private double CheltuieliLunare;

    @Override
    public void citeste(Scanner in) {
        // Format: SRL Nume Prenume VenitLunar CheltuieliLunare
        this.nume = in.next();
        this.prenume = in.next();
        this.venitBrutLunar = in.nextDouble();
        this.CheltuieliLunare = in.nextDouble();
    }
    @Override
    public TipColaborator getTip() {
        return TipColaborator.SRL;
    }

    @Override
    public double calculeazaVenitNetAnual() {

        double aux;
        aux=(this.venitBrutLunar-this.CheltuieliLunare)*12*0.84;

        return 0;
    }
    @Override
    public void afiseaza() {
        System.out.println(
                tipContract()+": "+ nume+" "+prenume+", "+ calculeazaVenitNetAnual()+" lei");
    }

    @Override
    public String tipContract() {
        return "SRL";
    }
}
