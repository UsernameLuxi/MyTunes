package com.gruppe5.MyTunes.DAL;

import com.gruppe5.MyTunes.BE.Playlist;
import com.gruppe5.MyTunes.BE.Song;

import java.util.List;

public class DAO_DB  implements IDataAccess{
    @Override
    public List<Song> getAllSongs() {
        return null;
    }

    @Override
    public Song getSong(int id) {
        return null;
    }

    @Override
    public List<Song> getSongByName(String name) {
        return null;
    }

    @Override
    public List<Song> getSongByArtist(String artist) {
        return null;
    }

    @Override
    public List<Song> getSongByArtist(int artistID) {
        return null;
    }

    @Override
    public Song addSong(Song song) {
        return null;
    }

    @Override
    public Song updateSong(Song song) {
        return null;
    }

    @Override
    public void deleteSong(Song song) {

    }

    @Override
    public List<Playlist> getAllPlaylists() {
        return null;
    }

    @Override
    public Playlist getPlaylist(int id) {
        return null;
    }

    @Override
    public Playlist getPlaylist(String name) {
        return null;
    }

    @Override
    public Playlist addPlaylist(Playlist playlist) {
        return null;
    }

    @Override
    public Playlist updatePlaylist(Playlist playlist) {
        return null;
    }

    @Override
    public void deletePlaylist(Playlist playlist) {

    }
}
