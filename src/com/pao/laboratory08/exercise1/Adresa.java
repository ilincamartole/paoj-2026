package com.pao.laboratory08.exercise1;

public class Adresa implements Cloneable {
    private String oras;
    private String strada;

    // constructor(String oras, String strada)
    // getteri, setteri
    // toString() → "Adresa{oras='...', strada='...'}"

    public Adresa(String oras, String strada){
    this.oras=oras;
    this.strada=strada;
    }

    public String getStrada(){
        return strada;

    }

    public String getOras() {
        return oras;
    }
    public String toString(){
        return "Adresa{oras="+oras+", strada="+strada+"}";
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    public void setOras(String modificat) {
        this.oras=modificat;
    }
}