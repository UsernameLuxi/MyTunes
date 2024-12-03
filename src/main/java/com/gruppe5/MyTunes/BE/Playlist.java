package com.gruppe5.MyTunes.BE;

import java.util.List;

public class Playlist {

    private int id;
    private String name;
    private List<Song> songs;
    private int totalDuration;

    public Playlist(int id, String name, List<Song> songs) {
        this.id = id;
        this.name = name;
        this.songs = songs;
        calculateTotalDuration();
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

    public int getTotalDuration(){
        calculateTotalDuration();
        return totalDuration;
    }
    private void calculateTotalDuration() {
        int time = 0;
        for (Song song : songs) {
            time += song.getDuration();
        }

        totalDuration = time;
        // convert to time
    }

    public Integer getSize() {
        return songs.size();
    }

    @Override
    public String toString() {
        return name + ";" + songs.toString();
    }


}