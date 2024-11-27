package com.gruppe5.MyTunes.DAL;

import com.gruppe5.MyTunes.BE.Playlist;
import com.gruppe5.MyTunes.BE.Song;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

public class DAO_DB  implements IDataAccess{
    @Override
    public List<Song> getAllSongs() throws Exception{
        List<Song> songs = new ArrayList<Song>();
        String sql = "SELECT Songs.Id, Songs.Title, Artist.ArtistName, Songs.Duration, Genre.GenreName, Songs.URL" +
                "FROM Songs" +
                "INNER JOIN Artist ON Songs.ArtistID = Artist.Id" +
                "INNER JOIN Genre ON Songs.GenreID = Genre.Id;";
        try(Connection conn = new DBConnector().getConnection(); PreparedStatement ps = conn.prepareStatement(sql)){
            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                songs.add(createSong(rs));
            }
        }

        return songs;
    }

    @Override
    public Song getSong(int id) throws Exception{
        String sql = "SELECT Songs.Id, Songs.Title, Artist.ArtistName, Songs.Duration, Genre.GenreName, Songs.URL" +
                "FROM Songs" +
                "INNER JOIN Artist ON Songs.ArtistID = Artist.Id" +
                "INNER JOIN Genre ON Songs.GenreID = Genre.Id;"+
                "WHERE Songs.Id = ?";
        try(Connection conn = new DBConnector().getConnection(); PreparedStatement ps = conn.prepareStatement(sql)){
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if(rs.next())
                return createSong(rs);
            else
                return null;
        }
    }

    @Override
    public List<Song> getSongByName(String name) throws Exception{
        return null;
    }

    @Override
    public List<Song> getSongByArtist(String artist) throws Exception{
        return null;
    }

    @Override
    public List<Song> getSongByArtist(int artistID) throws Exception{
        return null;
    }

    @Override
    public Song addSong(Song song) throws Exception{
        return null;
    }

    @Override
    public Song updateSong(Song song) throws Exception{
        return null;
    }

    @Override
    public void deleteSong(Song song) throws Exception{

    }

    /**
     * Creates a song based on the current ResultSet
     * @param rs the ResultSet from the executed command
     * @return returns the song based on the information from the ResultSet
     * @throws Exception if something is wrong an exception is thrown - can you believe ut
     */
    private Song createSong(ResultSet rs) throws Exception{
        int id = rs.getInt("Id");
        String title = rs.getString("Title");
        Time dur = rs.getTime("Duration");
        String URL = rs.getString("URL");
        String genre = rs.getString("GenreName");
        String artist = rs.getString("ArtistName");
        return new Song(id, title, artist, dur, genre, URL);
    }

    @Override
    public List<Playlist> getAllPlaylists() throws Exception{
        return null;
    }

    @Override
    public Playlist getPlaylist(int id) throws Exception{
        return null;
    }

    @Override
    public Playlist getPlaylist(String name) throws Exception{
        return null;
    }

    @Override
    public Playlist addPlaylist(Playlist playlist) throws Exception{
        return null;
    }

    @Override
    public Playlist updatePlaylist(Playlist playlist) throws Exception{
        return null;
    }

    @Override
    public void deletePlaylist(Playlist playlist) throws Exception{

    }
}
