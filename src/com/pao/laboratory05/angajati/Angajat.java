package com.pao.laboratory05.angajati;

import com.pao.laboratory05.biblioteca.Carte;

public class Angajat implements Comparable <Angajat>{

private String nume;
private Departament departament;
private double salariu;

public Angajat(String nume, Departament departament, double salariu){
    this. nume=nume;
    this.departament=departament;
    this.salariu=salariu;
}

public String getNume(){
    return this.nume;

}

public Departament getDepartament(){
        return this.departament;

    }

    public double getSalariu(){
        return this.salariu;

    }

    public String toString(){
        return "Angajat{nume="+ this.getNume()+", departament="+ this.getDepartament()+" salariu="+ this.getSalariu()+"}";

    }

    public int compareTo(Angajat altul) {
        return Double.compare(altul.getSalariu(), this.getSalariu());
    }


}
