package com.pao.laboratory06.exercise2;
import java.util.Scanner;

public class PFAColaborator extends PersoanaFizica{
    private double cheltuieliLunare;
    private static final double sal_min_anual = 4050 * 12;

    @Override
    public void citeste(Scanner in) {
        this.nume = in.next();
        this.prenume = in.next();
        this.venitBrutLunar = in.nextDouble();
        this.cheltuieliLunare = in.nextDouble();
    }
    @Override
    public TipColaborator getTip() {
        return TipColaborator.PFA;
    }

    @Override
    public double calculeazaVenitNetAnual() {
        double venitNet = (venitBrutLunar - cheltuieliLunare) * 12;

        double impozit = 0.10 * venitNet;

        double cass;
        if (venitNet < 6 * sal_min_anual) {
            cass = 0.10 * (6 * sal_min_anual);
        } else if (venitNet <= 72 * sal_min_anual) {
            cass = 0.10 * venitNet;
        } else {
            cass = 0.10 * (72 * sal_min_anual);
        }

        double cas;
        if (venitNet < 12 * sal_min_anual) {
            cas = 0;
        } else if (venitNet <= 24 * sal_min_anual) {
            cas = 0.25 * (12 * sal_min_anual);
        } else {
            cas = 0.25 * (24 * sal_min_anual);
        }

        return venitNet - impozit - cass - cas;
    }
    @Override
    public void afiseaza() {
        System.out.println(
                tipContract()+": "+ nume+" "+prenume+", "+ calculeazaVenitNetAnual()+" lei");
    }

    @Override
    public String tipContract() {
        return "PFA";
    }
}
