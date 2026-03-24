package com.pao.laboratory05.biblioteca;

import com.pao.laboratory05.playlist.Song;
import com.pao.laboratory05.playlist.SongDurationComparator;

import java.util.Arrays;
import java.util.Comparator;

public class BibliotecaService {
    private Carte[] carti;

    private BibliotecaService() {
        this.carti = new Carte[0];
    }
    private static class Holder{
        private static final BibliotecaService INSTANCE=new BibliotecaService();
    }public static BibliotecaService getInstance(){
        return Holder.INSTANCE;
    }

    void addCarte(Carte carte) {

        Carte[] temp = new Carte[carti.length + 1];
        System.arraycopy(carti, 0, temp, 0, carti.length);
        temp[temp.length - 1] = carte;
        carti = temp;
    }

    void listSortedByRating() {
        Carte[] copy = carti.clone();
        Arrays.sort(copy);
        for (int i = 0; i < copy.length; i++) {
            System.out.println(copy[i]);

        }
    }

    void listSortedBy(Comparator<Carte> comparator) {
        Carte[] copy = carti.clone();
        Arrays.sort(copy, comparator);
        for (int i = 0; i < copy.length; i++) {
            System.out.println(copy[i]);

        }
    }
}


