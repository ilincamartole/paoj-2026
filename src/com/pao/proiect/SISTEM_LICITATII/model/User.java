package com.pao.proiect.SISTEM_LICITATII.model;

public abstract class User {
    protected int id_user;
    protected String nume;
    protected String cnp;


    public User(String nume, String cnp){
        this.nume=nume;
        this.cnp=cnp;
    }

    @Override
    public String toString() {
        return "User: "+this.nume+"\n CNP:"+this.cnp;


    }
    public String getCnp(){
        return this.cnp;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;

        User user = (User) o;
        return cnp.equals(user.cnp);
    }

    public int getId_user() {
        return id_user;
    }

    public void setId_user(int id_user) {
        this.id_user = id_user;
    }

    @Override
    public int hashCode() {
        return cnp.hashCode();
    }
    public String getNume(){
        return nume;}
}


