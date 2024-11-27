package com.gruppe5.MyTunes.DAL;

import com.gruppe5.MyTunes.BE.Song;
import com.gruppe5.MyTunes.BE.Playlist;

import java.nio.file.Path;
import java.util.List;

public interface IDataAccess {
    List<Song> getAllSongs();

    Song getSong(int id);

    List<Song> getSongByName(String name);

    List<Song> getSongByArtist(String artist);

    List<Song> getSongByArtist(int artistID);

    Song addSong(Song song);

    Song updateSong(Song song);

    void deleteSong(Song song);


    // playlist part ;)
    List<Playlist> getAllPlaylists();

    Playlist getPlaylist(int id);

    Playlist getPlaylist(String name);

    Playlist addPlaylist(Playlist playlist);

    Playlist updatePlaylist(Playlist playlist);

    void deletePlaylist(Playlist playlist);

}
