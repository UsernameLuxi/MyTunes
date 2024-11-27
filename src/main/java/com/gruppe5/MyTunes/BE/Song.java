package com.gruppe5.MyTunes.BE;

import java.sql.Time;

public class Song {
    private int id;
    private String title;
    private String artist;
    private Time duration;
    private String genre;
    private String URL;

    /*
    public Song(int id, String title, String artist) {
        this.id = id;
        this.title = title;
        this.artist = artist;
    }
    */
    public Song(int id, String title, String artist, Time duration, String genre, String URL) {
        this.id = id;
        this.title = title;
        this.artist = artist;
        this.duration = duration;
        this.genre = genre;
        this.URL = URL;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getArtist() {
        return artist;
    }

    public String getURL(){
        return URL;
    }

    public Time getDuration() {
        return duration;
    }

    public String getGenre() {
        return genre;
    }
}
