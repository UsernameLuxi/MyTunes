package com.gruppe5.MyTunes.BE;

public class Playlist {
    Song[] songs;

    public Playlist(Song[] songs) {
        this.songs = songs;
    }

    public Song[] getSongs() {
        return songs;
    }
}
