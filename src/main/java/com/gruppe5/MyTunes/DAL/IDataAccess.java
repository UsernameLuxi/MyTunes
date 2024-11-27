package com.gruppe5.MyTunes.DAL;

import com.gruppe5.MyTunes.BE.Song;
import com.gruppe5.MyTunes.BE.Playlist;

import java.sql.SQLException;
import java.util.List;

public interface IDataAccess {
    List<Song> getAllSongs() throws Exception;

    Song getSong(int id) throws Exception;

    List<Song> getSongByName(String name) throws Exception;

    List<Song> getSongByArtist(String artist) throws Exception;

    List<Song> getSongByArtist(int artistID) throws Exception;

    Song addSong(Song song) throws Exception;

    Song updateSong(Song song) throws Exception;

    void deleteSong(Song song) throws Exception;


    // playlist part ;)
    List<Playlist> getAllPlaylists() throws Exception;

    Playlist getPlaylist(int id) throws Exception;

    Playlist getPlaylist(String name) throws Exception;

    Playlist addPlaylist(Playlist playlist) throws Exception;

    Playlist updatePlaylist(Playlist playlist) throws Exception;

    void deletePlaylist(Playlist playlist) throws Exception;

}
