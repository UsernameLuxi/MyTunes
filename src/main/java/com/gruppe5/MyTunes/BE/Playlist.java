package com.gruppe5.MyTunes.BE;

import java.util.List;

public class Playlist {

    private int id;
    private String name;
    private List<Song> songs;

    public Playlist(int id, String name, List<Song> songs) {
        this.id = id;
        this.name = name;
        this.songs = songs;
    }

    public Playlist(String name, List<Song> songs) {
        this.name = name;
        this.songs = songs;
    }
    
    public List<Song> getSongs() {
        return songs;
    }

    public void setSongs(List<Song> songs) {
        this.songs = songs;
    }

    public int getId(){
        return id;
    }

    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    @Override
    public String toString() {
        return name + ";" + songs.toString();
    }


}