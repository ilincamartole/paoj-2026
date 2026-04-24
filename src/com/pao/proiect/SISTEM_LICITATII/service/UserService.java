package com.pao.proiect.SISTEM_LICITATII.service;

import com.pao.proiect.SISTEM_LICITATII.Licitatie;
import com.pao.proiect.SISTEM_LICITATII.User;
import com.sun.source.tree.Tree;

import java.util.TreeMap;
import java.util.List;

public class UserService {

    private User[] useri;
    TreeMap<String, List <User> >map =new TreeMap<>();

    private UserService(){
        this.useri= new User[0];

    }

    public static class Holder{
        public static final UserService INSTANCE = new UserService();
    }

    public static UserService getInstance(){
        return Holder.INSTANCE;
    }

    public void adaugaUser(User user, String tip){
        User[] temp = new User[useri.length + 1];
        System.arraycopy(useri, 0, temp, 0, useri.length);
        temp[temp.length - 1] = user;
        useri = temp;

        String key = tip;
        map.putIfAbsent(key, new java.util.ArrayList<>());


        map.get(key).add(user);
        System.out.println("Am adaugat user:  " + user);
    }

    public User[] getUseri(){
        return this.useri;}

    public void afiseazaUseri(){
        for (int i=0;i< useri.length;i++){
            System.out.println(useri[i]);
        }
    }

    public User cautaUser(String tip, String cnp) {

        List<User> lista = map.get(tip);

        for (User u : lista) {
            if (u.getCnp().equals(cnp)) {
                return u;
            }
        }

        return null;
    }
}
