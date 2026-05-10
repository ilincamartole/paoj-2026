package com.pao.laboratory09.exercise1;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class Tranzactie implements Serializable {
    private static final long serialVersionUID = 1L;

    private int id;
    private double suma;
    private String data; // format yyyy-MM-dd
    private String contSursa;
    private String contDestinatie;
    private TipTranzactie tip;
    private transient String note;


    public Tranzactie(int id, double suma, String data, String contSursa, String contDestinatie,
                      TipTranzactie tip) {
        this.id = id;
        this.suma = suma;
        this.data = data;
        this.contSursa = contSursa;
        this.contDestinatie = contDestinatie;
        this.tip = tip;
    }

//    private void writeObject(ObjectOutputStream out) throws IOException {
//        note = "procesat";
//        out.defaultWriteObject();
//    }
//
//    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
//        in.defaultReadObject();
//        note = null;
//    }


    @Override
    public String toString() {
        return "Tranzactie{" +
                "id=" + id +
                ", suma=" + suma +
                ", data='" + data + '\'' +
                ", contSursa='" + contSursa + '\'' +
                ", contDestinatie='" + contDestinatie + '\'' +
                ", tip=" + tip +
                ", note='" + note + '\'' +
                '}';
    }

    public int getId() {
        return id;
    }

    public double getSuma() {
        return suma;
    }

    public String getNote() {
        return note;
    }

    public TipTranzactie getTip() {
        return tip;
    }

    public String getContSursa() {
        return contSursa;
    }

    public String getContDestinatie() {
        return contDestinatie;
    }

    public String getData() {
        return data;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
