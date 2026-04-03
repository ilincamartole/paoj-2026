package com.pao.laboratory06.exercise2;

public abstract class Colaborator implements IOperatiiCitireScriere {
    String nume;
    String prenume;
    double venitBrutLunar;

    public abstract double calculeazaVenitNetAnual();
    public abstract TipColaborator getTip();
    @Override
    public String tipContract() {
        return getTip().toString();
    }
}
