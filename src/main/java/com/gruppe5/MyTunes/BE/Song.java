package com.gruppe5.MyTunes.BE;

public class Song {
    private int id;
    private String title;
    private String artist;

    public Song(int id, String name, String artist) {
        this.id = id;
        this.title = name;
        this.artist = artist;
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
}
