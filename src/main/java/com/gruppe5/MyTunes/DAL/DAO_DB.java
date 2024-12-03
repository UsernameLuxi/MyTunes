package com.gruppe5.MyTunes.DAL;

import com.gruppe5.MyTunes.BE.Playlist;
import com.gruppe5.MyTunes.BE.Song;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * This class serves the specific purpose collecting the data from the database. It collects the playlists and the songs from the playlist
 * since they are closely related - so it handles it in one place.
 */
public class DAO_DB  implements IDataAccess{
    /**
     * Get all the songs which are registered in the database
     * @return Returns a list of Song objects.
     * @throws Exception An SQLException can occur
     */
    @Override
    public List<Song> getAllSongs() throws Exception{
        List<Song> songs = new ArrayList<Song>();
        String sql = "SELECT Songs.Id, Songs.Title, Artist.ArtistName, Songs.Duration, Genre.GenreName, Songs.URL " +
                "FROM Songs " +
                "INNER JOIN Artist ON Songs.ArtistID = Artist.Id " +
                "INNER JOIN Genre ON Songs.GenreID = Genre.Id;";
        try(Connection conn = new DBConnector().getConnection(); PreparedStatement ps = conn.prepareStatement(sql)){
            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                songs.add(createSong(rs));
            }
        }

        return songs;
    }

    /**
     * Get a song based on the song id
     * @param id The songs id
     * @return returns the song with the inputted id
     * @throws Exception An SQLException can occur
     */
    @Override
    public Song getSong(int id) throws Exception{
        String sql = "SELECT Songs.Id, Songs.Title, Artist.ArtistName, Songs.Duration, Genre.GenreName, Songs.URL " +
                "FROM Songs " +
                "INNER JOIN Artist ON Songs.ArtistID = Artist.Id " +
                "INNER JOIN Genre ON Songs.GenreID = Genre.Id "+
                "WHERE Songs.Id = ? ";
        try(Connection conn = new DBConnector().getConnection(); PreparedStatement ps = conn.prepareStatement(sql)){
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if(rs.next())
                return createSong(rs);
            else
                return null;
        }
    }

    /**
     * Get a specific song by its name.
     * @param name the name of the song which you want to search
     * @return returns the song based on the name
     * @throws Exception An SQLException can occur
     */
    @Override
    public List<Song> getSongByName(String name) throws Exception{
        List<Song> songs = new ArrayList<>();
        String sql = "SELECT Songs.Id, Songs.Title, Artist.ArtistName, Songs.Duration, Genre.GenreName, Songs.URL " +
                "FROM Songs " +
                "INNER JOIN Artist ON Songs.ArtistID = Artist.Id " +
                "INNER JOIN Genre ON Songs.GenreID = Genre.Id "+
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

    /**
     * Get the songs by one artist
     * @param artist the artist name
     * @return returns a list of songs which the artist is related to
     * @throws Exception An SQLException can occur
     */
    @Override
    public List<Song> getSongByArtist(String artist) throws Exception{
        List<Song> songs = new ArrayList<>();
        String sql = "SELECT Songs.Id, Songs.Title, Artist.ArtistName, Songs.Duration, Genre.GenreName, Songs.URL " +
                "FROM Songs " +
                "INNER JOIN Artist ON Songs.ArtistID = Artist.Id " +
                "INNER JOIN Genre ON Songs.GenreID = Genre.Id "+
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

    /**
     * Get the songs by one artist
     * @param artistID the artist id
     * @return returns a list of songs which the artist is related to
     * @throws Exception An SQLException can occur
     */
    @Override
    public List<Song> getSongByArtist(int artistID) throws Exception{
        List<Song> songs = new ArrayList<>();
        String sql = "SELECT Songs.Id, Songs.Title, Artist.ArtistName, Songs.Duration, Genre.GenreName, Songs.URL " +
                "FROM Songs " +
                "INNER JOIN Artist ON Songs.ArtistID = Artist.Id " +
                "INNER JOIN Genre ON Songs.GenreID = Genre.Id "+
                "WHERE Artist.Id = ?";
        try (Connection conn = new DBConnector().getConnection(); PreparedStatement ps = conn.prepareStatement(sql)){
            ps.setInt(1, artistID);
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                songs.add(createSong(rs));
            }
        }
        return songs;
    }

    /**
     * Add a song to the database
     * @param song A song object without the id
     * @return returns the added song with a generated id, which the inputted song should lack
     * @throws Exception An SQLException can occur
     */
    @Override
    public Song addSong(Song song) throws Exception{
        String sql_insert = "INSERT INTO Songs (Title, ArtistID, Duration, GenreID, URL) VALUES (?, ?, ?, ?, ?)";

        try(Connection conn = new DBConnector().getConnection(); PreparedStatement ps_insert = conn.prepareStatement(sql_insert, Statement.RETURN_GENERATED_KEYS)){

            // fill out the information
            ps_insert.setString(1, song.getTitle().trim());

            // search the artist - and if the artist don't exist, the artist is created
            int artistID = getAritstID(song.getArtist(), conn);
            ps_insert.setInt(2, artistID);

            ps_insert.setInt(3, song.getDuration());

            // search the genre
            int genreID = getGenreID(song.getGenre(), conn);
            ps_insert.setInt(4, genreID);

            ps_insert.setString(5, song.getURL());

            // execute
            ps_insert.executeUpdate();

            // get the id - redundant
            ResultSet rs = ps_insert.getGeneratedKeys();
            rs.next();
            int songID = rs.getInt(1);
            return new Song(songID, song.getTitle(), song.getArtist(), song.getDuration(), song.getGenre(), song.getURL());

        }
        catch(Exception e){
            throw new Exception("Unable to create the song " + song.getTitle().trim(), e);
        }
    }

    /**
     * Update a specific song in the database
     * @param song a song that have different data than in the database
     * @return returns the updated song - which don't man sense, since you already have one
     * @throws Exception An SQLException can occur
     */
    @Override
    public Song updateSong(Song song) throws Exception{
        String sql = "UPDATE Songs SET Title = ?, ArtistID = ?, Duration = ?, GenreID = ?, URL = ? WHERE Songs.Id = ?";
        try(Connection conn = new DBConnector().getConnection(); PreparedStatement ps = conn.prepareStatement(sql)){
            // get the ids for from the database
            int artistId = getAritstID(song.getArtist(), conn);
            int genreId = getGenreID(song.getGenre(), conn);

            // fill out the information
            ps.setString(1, song.getTitle().trim());
            ps.setInt(2, artistId);
            ps.setInt(3, song.getDuration());
            ps.setInt(4, genreId);
            ps.setString(5, song.getURL());
            ps.setInt(6, song.getId());
            ps.executeUpdate();
        }
        catch(Exception e){
            throw new Exception("Unable to update song", e);
        }
        return song;
    }

    /**
     * Delete a song from the database
     * @param song the song which should be removed
     * @throws Exception An SQLException can occur
     */
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
     * Gets all the playlist in the database
     * @return returns a list of playlists
     * @throws Exception An SQLException can occur
     */
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

    /**
     * Get a playlist by its id
     * @param id the id of the playlist
     * @return returns the playlist with the id provided
     * @throws Exception An SQLException can occur
     */
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

    /**
     * Gets a specific playlist by its name - this is possible since the names have to be unique
     * @param name the name of the playlist
     * @return returns the playlist with the name provided
     * @throws Exception An SQLException can occur
     */
    @Override
    public Playlist getPlaylist(String name) throws Exception{
        String sql = "SELECT Playlists.Id, Playlists.PlaylistName FROM Playlists WHERE Playlists.PlaylistName LIKE ?;"; // use LIKE or = both could be used depending on what you want to achieve
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

    /**
     * Add a playlist to the database
     * @param playlist the playlist which should be added to the database (without an id)
     * @return returns the newly added playlist with an id
     * @throws Exception An SQLException can occur
     */
    @Override
    public Playlist addPlaylist(Playlist playlist) throws Exception{
        String sql = "INSERT INTO Playlists (PlaylistName) VALUES (?);";
        try(Connection conn = new DBConnector().getConnection(); PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)){
            // insert the playlist in the Playlist table
            ps.setString(1, playlist.getName());
            ps.executeUpdate();

            // get the playlist id from the auto-generation
            ResultSet keys = ps.getGeneratedKeys();
            keys.next();
            int playlistID = keys.getInt(1);

            // add the songs to the playlistContainer
            PreparedStatement addSong = conn.prepareStatement("INSERT INTO PlaylistContainer (PlaylistID, SongID, SongIndex) VALUES (?, ?, ?)");
            addSong.setInt(1, playlistID); // set the playlist id - we don't need to touch this again
            List<Song> songs = playlist.getSongs();
            for (int i = 0; i < songs.size(); i++) {
                int songID = songs.get(i).getId(); // get the id
                addSong.setInt(2, songID); // set the id in the query
                addSong.setInt(3, i);
                addSong.executeUpdate(); // execute the update
            }

            return new Playlist(playlistID, playlist.getName(), songs);
        }
        catch (Exception e) {
            String errorMessage = "Unable to add playlist " + playlist.getName().trim();
            errorMessage += (e.getMessage().contains("duplicate")) ? " because of duplicate names. Cannot have duplicate names" : "";
            throw new Exception(errorMessage, e);
        }
    }

    /**
     * Update a specific playlist
     * @param playlist the playlist which have been changed
     * @return returns the changed playlist, which is the one provided - so yeah
     * @throws Exception An SQLException can occur
     */
    @Override
    public Playlist updatePlaylist(Playlist playlist) throws Exception{
        String sql = "UPDATE Playlists SET PlaylistName = ? WHERE Id = ?";
        try(Connection conn = new DBConnector().getConnection(); PreparedStatement ps = conn.prepareStatement(sql)){
            // update the name
            ps.setString(1, playlist.getName());
            ps.setInt(2, playlist.getId());
            ps.executeUpdate();

            // update the songs
            // remove all the songs
            String sqlDelete = "DELETE FROM PlaylistContainer WHERE PlaylistID = ?";
            PreparedStatement deleteSong = conn.prepareStatement(sqlDelete);
            deleteSong.setInt(1, playlist.getId());
            deleteSong.executeUpdate();

            // add them again
            List<Song> songs = playlist.getSongs();
            PreparedStatement insert = conn.prepareStatement("INSERT INTO PlaylistContainer (PlaylistID, SongID, SongIndex) VALUES (?, ?, ?)");
            insert.setInt(1, playlist.getId()); // permanent playlist id for this case
            for (int i = 0; i < songs.size(); i++) {
                insert.setInt(2, songs.get(i).getId());
                insert.setInt(3, i);
                insert.executeUpdate();
            }
        }
        catch(Exception e){
            throw new Exception("Unable to update playlist " + playlist.getName().trim(), e);
        }
        return playlist;
    }

    /**
     * Delete a playlist from existence - and since there should be 'Delete Cascade' on -
     * then no worry about the things in the PlaylistContainer, they should also be removed.
     * @param playlist the playlist which should be removed
     * @throws Exception An SQLException can occur
     */
    @Override
    public void deletePlaylist(Playlist playlist) throws Exception{
        String sql = "DELETE FROM Playlists WHERE Id = ?";
        try(Connection conn = new DBConnector().getConnection(); PreparedStatement ps = conn.prepareStatement(sql)){
            ps.setInt(1, playlist.getId());
            ps.executeUpdate();
        }
        catch(Exception e){
            throw new Exception("Unable to delete playlist " + playlist.getName().trim(), e);
        }
    }

    // more method which is not in the Interface
    /**
     * Creates a song based on the current ResultSet
     * @param rs the ResultSet from the executed command
     * @return returns the song based on the information from the ResultSet
     * @throws Exception if something is wrong an exception is thrown - can you believe ut
     */
    private Song createSong(ResultSet rs) throws Exception{
        int id = rs.getInt("Id");
        String title = rs.getString("Title");
        int dur = rs.getInt("Duration");
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
        PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        ps.setString(1, name);
        ps.executeUpdate();

        ResultSet rs = ps.getGeneratedKeys();
        rs.next();
        return rs.getInt(1);
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
        PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        ps.setString(1, genreName);
        ps.executeUpdate();

        ResultSet rs = ps.getGeneratedKeys();
        rs.next();
        return rs.getInt(1);
    }

    /**
     * This method returns a search query in SQL language. This was made into a method because of the size.
     * You could argue that it could be a private final string, but this was made to lessen confusion -
     * because by reading the name of the method you understand what it's trying to communicate,
     * and if one was curious you could simply navigate to the method.
     * @return Returns the search query described by the name.
     */
    private String getSQL_SearchSongsInPlaylistContainerByPlaylistID_OrderedBySongIndex(){
        return "SELECT PlaylistContainer.SongID, Songs.Title, Artist.ArtistName, Songs.Duration, Genre.GenreName, Songs.URL "+
                "FROM PlaylistContainer "+
                "INNER JOIN Songs ON PlaylistContainer.SongID = Songs.Id "+
                "INNER JOIN Artist ON Songs.ArtistID = Artist.Id "+
                "INNER JOIN Genre ON Songs.GenreID = Genre.Id "+
                "WHERE PlaylistContainer.PlaylistID = ? " +
                "ORDER BY PlaylistContainer.SongIndex;";
    }

    /**
     * Gets all the songs in a playlist. It searches through the playlistContainer and adds the songs to the list.
     * The songs are in order
     * @param playlistID The id of the playlist which songs should be returned.
     * @param conn The connection to the database.
     * @return Returns a list of songs.
     * @throws Exception If something goes wrong.
     */
    private List<Song> getSongsInPlaylist(int playlistID, Connection conn) throws Exception{
        List<Song> songs = new ArrayList<>();
        String sql = getSQL_SearchSongsInPlaylistContainerByPlaylistID_OrderedBySongIndex();
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, playlistID);
        ResultSet rs = ps.executeQuery();
        while(rs.next()){
            Song s = new Song(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getInt(4), rs.getString(5), rs.getString(6));
            songs.add(s);
        }
        return songs;
    }

    public List<String> getGenres() throws Exception {
        List<String> genres = new ArrayList<>();
        String sql = "SELECT Genre.GenreName FROM Genre";

        try(Connection conn = new DBConnector().getConnection(); PreparedStatement ps = conn.prepareStatement(sql)){
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                genres.add(rs.getString("GenreName").trim());
            }
        }
        catch(Exception e){
            throw new Exception("Unable to retrieve genres", e);
        }

        return genres;
    }
}
