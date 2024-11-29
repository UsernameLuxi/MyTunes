package com.gruppe5.MyTunes.BE;

import java.sql.Time;

public class Song {
    private int id;
    private String title;
    private String artist;
    private Time duration;
    private String genre;
    private String URL;

    public Song(int id, String title, String artist) {
        this.id = id;
        this.title = title;
        this.artist = artist;
    }

    public Song(String title, String artist, Time duration, String genre, String URL) {
        this.title = title;
        this.artist = artist;
        this.duration = duration;
        this.genre = genre;
        this.URL = URL;
    }

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

    public void setTitle(String title) {
        this.title = title;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getURL(){
        return URL;
    }

    public void setURL(String URL) {
        this.URL = URL;
    }

    public Time getDuration() {
        return duration;
    }

    public void setDuration(Time duration) {
        this.duration = duration;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    @Override
    public String toString() {
        return title + "," + artist + "," + duration + "," + genre + "," + URL;
    }
}
