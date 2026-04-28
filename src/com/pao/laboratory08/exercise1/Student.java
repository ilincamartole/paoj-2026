package com.pao.laboratory08.exercise1;

public class Student implements Cloneable {
    private String nume;
    private int varsta;
    private Adresa adresa;


    public Student(String nume, int varsta, Adresa adresa){
        this.nume=nume;
        this.varsta=varsta;
        this.adresa=adresa;

    }

    public Adresa getAdresa() {
        return adresa;
    }

    public int getVarsta() {
        return varsta;
    }

    public String getNume() {
        return nume;
    }

    public void setAdresa(Adresa adresa) {
        this.adresa = adresa;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public void setVarsta(int varsta) {
        this.varsta = varsta;
    }

    @Override
    public String toString() {
        return "Student{nume='"+nume+"', varsta="+varsta+", adresa=Adresa{oras='"+adresa.getOras()+"', strada='"+adresa.getStrada()+"'}}";
    }

    public Student deepClone() throws CloneNotSupportedException {
        Student clona = (Student) super.clone();
        clona.setAdresa((Adresa) this.adresa.clone());
        return clona;
    }
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    // constructor(String nume, int varsta, Adresa adresa)
    // getteri, setteri
    // toString() → "Student{nume='...', varsta=..., adresa=Adresa{oras='...', strada='...'}}"

    // clone() — implementare diferită pentru shallow vs. deep (vezi mai jos)
}