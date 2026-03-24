package com.pao.laboratory05.playlist;

import java.util.Arrays;

public class Playlist {
    String name;
    private Song[] songs;

    Playlist(String name){
        this.name=name;
        this.songs=new Song[0];
    }

    void addSong(Song song){
        Song[] temp= new Song[songs.length+1];
        System.arraycopy(songs, 0, temp, 0, songs.length);
        temp[temp.length-1]=song;
        songs=temp;
    }

    void printSortedByTitle(){
        Song[] copy=songs.clone();
        Arrays.sort(copy);
        for (int i=0;i<copy.length;i++){
            System.out.println(copy[i]);

        }
    }

    void printSortedByDuration(){
        Song[] copy=songs.clone();
        Arrays.sort(copy, new SongDurationComparator());
        for (int i=0;i<copy.length;i++){
            System.out.println(copy[i]);

        }
    }

    int getTotalDuration(){
        int s=0;

        for (int i=0;i<songs.length;i++){
            s=s+songs[i].durationSeconds();
        }

        return s;

    }


    public String getName() {
        return this.name;
    }
}
