package com.pao.proiect.SISTEM_LICITATII;

import com.pao.proiect.SISTEM_LICITATII.enums.Categorie;

import java.util.Objects;

public class Produs implements Comparable<Produs> {

    private Categorie categorie;
    private String nume;
    private String colectie;
    private Seller seller;
    private final int id;
    private static int generatorID = 0;

    public Produs(String nume, Categorie categorie, String colectie, Seller seller) {
        this.id = generatorID++;
        this.nume = nume;
        this.categorie = categorie;
        this.colectie = colectie;
        this.seller = seller;
    }

    @Override
    public String toString() {
        return "ID. "+this.id+"Nume: " + this.nume + "\n Categorie: " + this.categorie + "\n Colectie: " + this.colectie + "\n VAndut de: " + this.seller;
    }

    public String getNume() {
        return nume;
    }

    public Seller getSeller() {
        return seller;
    }

    public int getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Produs)) return false;

        Produs produs = (Produs) o;

        return nume.equalsIgnoreCase(produs.nume)
                && categorie == produs.categorie;
    }

    @Override
    public int hashCode() {
        return Objects.hash(nume.toLowerCase(), categorie);
    }
    @Override
    public int compareTo(Produs other) {
        return this.nume.compareToIgnoreCase(other.nume);
    }
}