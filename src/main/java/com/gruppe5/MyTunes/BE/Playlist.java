package com.gruppe5.MyTunes.BE;

import java.sql.Time;
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

    public Time getTotalDuration(){
        Time time = new Time(0);
        for (Song song : songs) {
            time.setTime(time.getTime() + song.getDuration().getTime());
        }
        return time;
    }

    @Override
    public String toString() {
        return name + ";" + songs.toString();
    }


}