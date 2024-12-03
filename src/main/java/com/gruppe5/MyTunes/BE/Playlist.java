package com.gruppe5.MyTunes.BE;

import java.sql.Time;
import java.time.Duration;
import java.util.List;

public class Playlist {

    private int id;
    private String name;
    private List<Song> songs;
    private Time totalDuration;

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

    public Time getTotalDuration(){
        calculateTotalDuration();
        return totalDuration;
    }
    private void calculateTotalDuration() {
        int time = 0;
        for (Song song : songs) {
            time += song.getDuration();
        }

        totalDuration = new Time((time * 1000L) - (1000 * 60 * 60));

    }

    public Integer getSize() {
        return songs.size();
    }

    @Override
    public String toString() {
        return name + ";" + songs.toString();
    }


}