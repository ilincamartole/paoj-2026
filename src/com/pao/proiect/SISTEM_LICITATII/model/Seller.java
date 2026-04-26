package com.pao.proiect.SISTEM_LICITATII.model;

import java.util.ArrayList;
import java.util.List;

public class Seller extends User {

    private double rating;
    private List<Produs> istoricProduse;
    public Seller(String nume, String cnp, double rating){
        super(nume, cnp);
        this.rating=rating;
        this.istoricProduse= new ArrayList<>(0);

    }



    @Override
    public String toString() {
        return super.toString()+ "\nRating: "+this.rating;
    }

    public List<Produs> getIstoricProduse(){
        return istoricProduse;
    }
}
