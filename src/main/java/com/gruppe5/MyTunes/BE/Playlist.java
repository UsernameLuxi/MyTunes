package com.gruppe5.MyTunes.BE;

import java.util.List;

public class Playlist {
    private List<Song> songs;

    public Playlist(List<Song> songs) {
        this.songs = songs;
    }

    public List<Song> getSongs() {
        return songs;
    }
}
