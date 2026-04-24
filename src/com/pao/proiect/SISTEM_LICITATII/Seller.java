package com.pao.proiect.SISTEM_LICITATII;

public class Seller extends User{

    private float rating;
    private Produs[] istoricProduse;
    public Seller(String nume, String cnp, float rating){
        super(nume, cnp);
        this.rating=rating;
        this.istoricProduse= new Produs[0];
    }



    @Override
    public String toString() {
        return super.toString()+" \n Rating: "+ this.rating;
    }
}
