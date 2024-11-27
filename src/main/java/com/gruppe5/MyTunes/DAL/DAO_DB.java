package com.gruppe5.MyTunes.DAL;

import com.gruppe5.MyTunes.BE.Playlist;
import com.gruppe5.MyTunes.BE.Song;

import java.sql.*;
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
        List<Song> songs = new ArrayList<>();
        String sql = "SELECT Songs.Id, Songs.Title, Artist.ArtistName, Songs.Duration, Genre.GenreName, Songs.URL" +
                "FROM Songs" +
                "INNER JOIN Artist ON Songs.ArtistID = Artist.Id" +
                "INNER JOIN Genre ON Songs.GenreID = Genre.Id;"+
                "WHERE Songs.Title LIKE ?";
        try (Connection conn = new DBConnector().getConnection(); PreparedStatement ps = conn.prepareStatement(sql)){
            ps.setString(1, "%"+name+"%");
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                songs.add(createSong(rs));
            }
        }
        return songs;
    }

    @Override
    public List<Song> getSongByArtist(String artist) throws Exception{
        List<Song> songs = new ArrayList<>();
        String sql = "SELECT Songs.Id, Songs.Title, Artist.ArtistName, Songs.Duration, Genre.GenreName, Songs.URL" +
                "FROM Songs" +
                "INNER JOIN Artist ON Songs.ArtistID = Artist.Id" +
                "INNER JOIN Genre ON Songs.GenreID = Genre.Id;"+
                "WHERE Artist.ArtistName LIKE ?";
        try (Connection conn = new DBConnector().getConnection(); PreparedStatement ps = conn.prepareStatement(sql)){
            ps.setString(1, "%"+artist+"%");
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                songs.add(createSong(rs));
            }
        }
        return songs;
    }

    @Override
    public List<Song> getSongByArtist(int artistID) throws Exception{
        List<Song> songs = new ArrayList<>();
        String sql = "SELECT Songs.Id, Songs.Title, Artist.ArtistName, Songs.Duration, Genre.GenreName, Songs.URL" +
                "FROM Songs" +
                "INNER JOIN Artist ON Songs.ArtistID = Artist.Id" +
                "INNER JOIN Genre ON Songs.GenreID = Genre.Id;"+
                "WHERE Artist.Id LIKE ?";
        try (Connection conn = new DBConnector().getConnection(); PreparedStatement ps = conn.prepareStatement(sql)){
            ps.setInt(1, artistID);
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                songs.add(createSong(rs));
            }
        }
        return songs;
    }

    @Override
    public Song addSong(Song song) throws Exception{
        String sql_insert = "INSERT INTO Songs (Title, ArtistID, Duration, GenreID, URL) VALUES (?, ?, ?, ?, ?)";
        String sql_artist = "SELECT Artist.Id, Artist.ArtistName FROM Artist WHERE Artist.Name = ?";
        String sql_genre = "SELECT Genre.Id, Genre.GenreName FROM Genre WHERE Genre.Name = ?";

        try(Connection conn = new DBConnector().getConnection();
            PreparedStatement ps_insert = conn.prepareStatement(sql_insert);
            PreparedStatement ps_searchA = conn.prepareStatement(sql_artist);
            PreparedStatement ps_searchG = conn.prepareStatement(sql_genre)){

            // search the artist
            ps_searchA.setString(1, song.getArtist().trim());
            ResultSet aSearch = ps_searchA.executeQuery();
            if (aSearch.next()){
                ps_insert.setInt(2, aSearch.getInt("ArtistID"));
            }
            else{
                // create the artist in the database and give id
                PreparedStatement cArtist = conn.prepareStatement("INSERT INTO Artist (ArtistName) VALUES (?)");
                cArtist.setString(1, song.getArtist().trim());
                cArtist.executeUpdate();

                // get the artist id
                PreparedStatement gArtist = conn.prepareStatement("SELECT Artist.Id FROM Artist WHERE Artist.ArtistName = ?");
                gArtist.setString(1, song.getArtist().trim());
                ResultSet rs = gArtist.executeQuery();
                rs.next();
                ps_insert.setInt(2, rs.getInt("Id"));
            }

            // search the genre
            ps_searchG.setString(1, song.getGenre().trim());
            ResultSet gSearch = ps_searchG.executeQuery();
            if (gSearch.next()){
                ps_insert.setInt(4, gSearch.getInt("Id"));
            }
            else{
                // create the genre in the database
                PreparedStatement cGenre = conn.prepareStatement("INSERT INTO Genre (GenreName) VALUES (?)");
                cGenre.setString(1, song.getGenre().trim());
                cGenre.executeUpdate();

                // get the artist id
                PreparedStatement gGenre = conn.prepareStatement("SELECT Genre.Id FROM Genre WHERE Genre.GenreName = ?");
                gGenre.setString(1, song.getArtist().trim());
                ResultSet rs = gGenre.executeQuery();
                rs.next();
                ps_insert.setInt(4, rs.getInt("Id"));
            }

            // fill out the rest of the information
            ps_insert.setString(1, song.getTitle().trim());
            ps_insert.setTime(3, song.getDuration());
            ps_insert.setString(5, song.getURL());

            ps_insert.executeUpdate();

            // get the id
            PreparedStatement ps = conn.prepareStatement("SELECT Songs.Id FROM Songs WHERE Songs.Title = ? AND Songs.Duration = ? AND Songs.URL = ?");
            ps.setString(1, song.getTitle().trim());
            ps.setTime(2, song.getDuration());
            ps.setString(3, song.getURL());
            ResultSet rs = ps.executeQuery();
            rs.next();
            return new Song(rs.getInt("Id"), song.getTitle(), song.getArtist(), song.getDuration(), song.getGenre(), song.getURL());

        }
        catch(Exception e){
            throw new Exception("Unable to create the song " + song.getTitle().trim(), e);
        }
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
