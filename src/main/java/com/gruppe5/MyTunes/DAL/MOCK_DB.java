package com.gruppe5.MyTunes.DAL;

import com.gruppe5.MyTunes.BE.Playlist;
import com.gruppe5.MyTunes.BE.Song;

import java.util.List;

public class MOCK_DB implements IDataAccess {

    @Override
    public List<Song> getAllSongs() {
        return List.of(
                new Song(1, "song1", "artist1"),
                new Song(2, "song2", "artist2"),
                new Song(3, "song3", "artist3")
        );
    }

    @Override
    public Song getSong(int id) {
        return null;
    }

    @Override
    public List<Song> getSongByName(String name) {
        return List.of();
    }

    @Override
    public List<Song> getSongByArtist(String artist) {
        return List.of();
    }

    @Override
    public List<Song> getSongByArtist(int artistID) {
        return List.of();
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
        return List.of();
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
