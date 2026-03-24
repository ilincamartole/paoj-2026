package com.pao.laboratory05.angajati;

import java.util.Arrays;

public class AngajatService {

    private Angajat[] angajati;

    private AngajatService() {
        this.angajati = new Angajat[0];
    }

    public static class Holder {
        public static final AngajatService INSTANCE = new AngajatService();
    }

    public static AngajatService getInstance() {
        return Holder.INSTANCE;
    }

    public void addAngajat(Angajat a) {
        Angajat[] temp = new Angajat[angajati.length + 1];
        System.arraycopy(angajati, 0, temp, 0, angajati.length);
        temp[temp.length - 1] = a;
        angajati = temp;

        System.out.println("Angajat adaugat: " + a);
    }

    void printAll() {
        for (Angajat a : angajati) {
            System.out.println(a);
        }
    }

    public void listBySalary() {
        Angajat[] copy = angajati.clone();
        Arrays.sort(copy); // presupune Comparable

        for (Angajat a : copy) {
            System.out.println(a);
        }
    }

    public void findByDepartament(String numeDept) {
        boolean found = false;

        for (Angajat a : angajati) {
            if (a.getDepartament().nume().equalsIgnoreCase(numeDept)) {
                System.out.println(a);
                found = true;
            }
        }

        if (!found) {
            System.out.println("Niciun angajat în departamentul: " + numeDept);
        }
    }
}