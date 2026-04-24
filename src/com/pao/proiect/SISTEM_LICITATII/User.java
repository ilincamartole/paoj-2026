package com.pao.proiect.SISTEM_LICITATII;

public abstract class User {
    protected String nume;
    protected String prenume;
    protected String cnp;
    protected String email;


    public User(String nume, String cnp){
        this.nume=nume;
        this.cnp=cnp;
    }

    @Override
    public String toString() {
        return this.nume;
    }
    public String getCnp(){
        return this.cnp;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;
        return cnp.equals(user.cnp);
    }
}

