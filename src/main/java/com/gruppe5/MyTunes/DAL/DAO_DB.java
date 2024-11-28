package com.gruppe5.MyTunes.DAL;

import com.gruppe5.MyTunes.BE.Playlist;
import com.gruppe5.MyTunes.BE.Song;

import javax.print.DocFlavor;
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

        try(Connection conn = new DBConnector().getConnection(); PreparedStatement ps_insert = conn.prepareStatement(sql_insert);){

            // fill out the information
            ps_insert.setString(1, song.getTitle().trim());

            // search the artist - and if the artist don't exist, the artist is created
            int artistID = getAritstID(song.getArtist(), conn);
            ps_insert.setInt(2, artistID);

            ps_insert.setTime(3, song.getDuration());

            // search the genre
            int genreID = getGenreID(song.getGenre(), conn);
            ps_insert.setInt(4, genreID);

            ps_insert.setString(5, song.getURL());

            // execute
            ps_insert.executeUpdate();

            // get the id - redundant
            ResultSet rs = ps_insert.getGeneratedKeys();
            rs.next();
            int songID = rs.getInt("Id");
            return new Song(songID, song.getTitle(), song.getArtist(), song.getDuration(), song.getGenre(), song.getURL());

        }
        catch(Exception e){
            throw new Exception("Unable to create the song " + song.getTitle().trim(), e);
        }
    }

    @Override
    public Song updateSong(Song song) throws Exception{
        String sql = "UPDATE Songs SET Title = ?, ArtistID = ?, Duration = ?, GenreID = ?, URL = ? WHERE Songs.Id = ?";
        try(Connection conn = new DBConnector().getConnection(); PreparedStatement ps = conn.prepareStatement(sql)){
            // get the ids for from the database
            int artistId = getAritstID(song.getArtist(), conn);
            int genreId = getGenreID(song.getGenre(), conn);

            ps.setString(1, song.getTitle().trim());
            ps.setInt(2, artistId);
            ps.setTime(3, song.getDuration());
            ps.setInt(4, genreId);
            ps.setString(5, song.getURL());
            ps.executeUpdate();
        }
        catch(Exception e){
            throw new Exception("Unable to update song", e);
        }
        return song;
    }

    @Override
    public void deleteSong(Song song) throws Exception{
        String sql = "DELETE FROM Songs WHERE Songs.Id = ?";

        try(Connection conn = new DBConnector().getConnection(); PreparedStatement ps = conn.prepareStatement(sql)){
            ps.setInt(1, song.getId());
            ps.executeUpdate();
        }
        catch(Exception e){
            throw new Exception("Unable to delete song " + song.getTitle().trim(), e);
        }

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

    /**
     * Get the artist id by their name, this is used all around the code, therefore it can be its own method
     * @param Name The name of the artist - if the name is not in the database, it adds the name to it
     * @param conn The connection to the database
     * @return returns the id of the artist
     * @throws Exception It throws an exception if it's unable to execute the statement
     */
    private int getAritstID(String Name, Connection conn) throws Exception{
        int id;
        String sql = "SELECT Artist.Id FROM Artist WHERE Artist.ArtistName = ?";

        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, Name);
        ResultSet rs = ps.executeQuery();
        if (rs.next()){
            id = rs.getInt("Id");
        }
        else{
            id = addArtist(Name, conn);
        }
        return id;
    }

    /**
     * Adds an artist to the database
     * @param name The name of the artist
     * @param conn The connection to the database
     * @return returns the id of the artist
     * @throws Exception If the query is unable to execute or the connection fails
     */
    private int addArtist(String name, Connection conn) throws Exception{
        String sql = "INSERT INTO Artist (ArtistName) VALUES (?)";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, name);
        ps.executeUpdate();

        ResultSet rs = ps.getGeneratedKeys();
        rs.next();
        return rs.getInt("Id");
    }

    /**
     * Get the artist id by their name, this is used all around the code, therefore it can be its own method
     * @param genre The name of the genre - if the name is not in the database, it adds the name to it
     * @param conn The connection to the database
     * @return returns the id of the genre
     * @throws Exception It throws an exception if it's unable to execute the statement or connect to the database
     */
    private int getGenreID(String genre, Connection conn) throws Exception{
        int id;
        String sql = "SELECT Genre.Id FROM Genre WHERE Genre.GenreName = ?";

        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, genre);
        ResultSet rs = ps.executeQuery();
        if (rs.next()){
            id = rs.getInt("Id");
        }
        else{
            id = addGenre(genre, conn);
        }
        return id;
    }

    /**
     * Adds a genre to the database
     * @param genreName The name of the genre
     * @param conn The connection to the database
     * @return returns the id of the genre
     * @throws Exception If the query is unable to execute or the connection fails
     */
    private int addGenre(String genreName, Connection conn) throws Exception{
        String sql = "INSERT INTO Genre (GenreName) VALUES (?)";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, genreName);
        ps.executeUpdate();

        ResultSet rs = ps.getGeneratedKeys();
        rs.next();
        return rs.getInt("Id");
    }

    /**
     * This method returns a search query in SQL language. This was made into a method because of the size.
     * You could argue that it could be a private final string, but this was made to lessen confusion -
     * because by reading the name of the method you understand what it's trying to communicate,
     * and if one was curious you could simply navigate to the method.
     * @return Returns the search query described by the name.
     */
    private String getSQL_SearchSongsInPlaylistContainerByPlaylistID(){
        return "SELECT PlaylistContainer.SongID, Songs.Title, Artist.ArtistName, Songs.Duration, Genre.GenreName, Songs.URL "+
                "FROM PlaylistContainer "+
                "INNER JOIN Songs ON PlaylistContainer.SongID = Songs.Id "+
                "INNER JOIN Artist ON Songs.ArtistID = Artist.Id "+
                "INNER JOIN Genre ON Songs.GenreID = Genre.Id "+
                "WHERE PlaylistContainer.PlaylistID = ?;";
    }

    /**
     * Gets all the songs in a playlist. It searches through the playlistContainer and adds the songs to the list.
     * @param playlistID The id of the playlist which songs should be returned.
     * @param conn The connection to the database.
     * @return Returns a list of songs.
     * @throws Exception If something goes wrong.
     */
    private List<Song> getSongsInPlaylist(int playlistID, Connection conn) throws Exception{
        List<Song> songs = new ArrayList<>();
        String sql = getSQL_SearchSongsInPlaylistContainerByPlaylistID();
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, playlistID);
        ResultSet rs = ps.executeQuery();
        while(rs.next()){
            Song s = new Song(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getTime(4), rs.getString(5), rs.getString(6));
            songs.add(s);
        }
        return songs;
    }

    @Override
    public List<Playlist> getAllPlaylists() throws Exception{
        String sql = "SELECT Playlists.Id, Playlists.PlaylistName FROM Playlists";
        List<Playlist> playlists = new ArrayList<>();
        try(Connection conn = new DBConnector().getConnection(); PreparedStatement ps = conn.prepareStatement(sql)){
            ResultSet rs = ps.executeQuery();

            // loops through the playlists to add the all to the list
            while(rs.next()){
                Playlist p = new Playlist(rs.getInt("Id"), rs.getString("PlaylistName"), getSongsInPlaylist(rs.getInt("Id"), conn));
                playlists.add(p);
            }

            return playlists;
        }
        catch (Exception e) {
            throw new Exception("Unable to get all playlists", e);
        }
    }

    @Override
    public Playlist getPlaylist(int id) throws Exception{
        String sql = "SELECT Playlists.Id, Playlists.PlaylistName FROM Playlists WHERE Playlists.Id = ?";
        try(Connection conn = new DBConnector().getConnection(); PreparedStatement ps = conn.prepareStatement(sql)){
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                // returns the playlist
                return new Playlist(id, rs.getString("PlaylistName"), getSongsInPlaylist(id, conn));

            }
            else{
                return null;
            }
        }
        catch (Exception e) {
            throw new Exception("Unable to get playlist with id: " + id, e);
        }
    }

    @Override
    public Playlist getPlaylist(String name) throws Exception{
        String sql = "SELECT Playlists.Id, Playlists.PlaylistName FROM Playlists WHERE Playlists.PlaylistName = ?";
        try(Connection conn = new DBConnector().getConnection(); PreparedStatement ps = conn.prepareStatement(sql)){
            ps.setString(1, name.trim());
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                // returns the playlist
                return new Playlist(rs.getInt("Id"), name.trim(), getSongsInPlaylist(rs.getInt("Id"), conn));

            }
            else{
                return null;
            }
        }
        catch (Exception e) {
            throw new Exception("Unable to get playlist with name: " + name, e);
        }
    }

    @Override
    public Playlist addPlaylist(Playlist playlist) throws Exception{
        String sql = "INSERT INTO Playlists (PlaylistName) VALUES (?)";
        try(Connection conn = new DBConnector().getConnection(); PreparedStatement ps = conn.prepareStatement(sql)){
            // insert the playlist in the Playlist table
            ps.setString(1, playlist.getName());
            ps.executeUpdate();

            // get the playlist id from the auto-generation
            ResultSet keys = ps.getGeneratedKeys();
            keys.next();
            int playlistID = keys.getInt("Id");

            // add the songs to the playlistContainer
            PreparedStatement addSong = conn.prepareStatement("INSERT INTO PlaylistContainer (PlaylistID, SongID) VALUES (?, ?)");
            addSong.setInt(1, playlistID); // set the playlist id - we don't need to touch this again
            List<Song> songs = playlist.getSongs();
            for (int i = 0; i < songs.size(); i++) {
                int songID = songs.get(i).getId(); // get the id
                addSong.setInt(2, songID); // set the id in the query
                addSong.executeUpdate(); // execute the update
            }

            return new Playlist(playlistID, playlist.getName(), songs);
        }
        catch (Exception e) {
            throw new Exception("Unable to add playlist " + playlist.getName().trim(), e);
        }
    }

    @Override
    public Playlist updatePlaylist(Playlist playlist) throws Exception{
        return null;
    }

    @Override
    public void deletePlaylist(Playlist playlist) throws Exception{

    }
}
