package com.gruppe5.MyTunes.BLL;

import com.gruppe5.MyTunes.BE.Playlist;
import com.gruppe5.MyTunes.BE.Song;
import com.gruppe5.MyTunes.DAL.DAO_DB;
import com.gruppe5.MyTunes.DAL.IDataAccess;
import com.gruppe5.MyTunes.GUI.Model.MyTunesModel;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class MyTunesLogic {
    private final IDataAccess dataAccess = new DAO_DB();
    private MediaPlayer mediaPlayer;
    private final MyTunesModel myTunesModel;
    private int currentIndex;
    private List<Song> currentSongs;
    private int volume;

    public MyTunesLogic(MyTunesModel myTunesModel) throws Exception {
        this.myTunesModel = myTunesModel;

        playFromNewPlace(0, dataAccess.getAllSongs());
    }

    public void playSong() {
        // Hvis mediaplayer eksisterer, fjern den inden ny sang bliver afspillet
        if (mediaPlayer != null) {
            mediaPlayer.dispose();
        }

        Song song = currentSongs.get(currentIndex);
        String songPath = song.getURL();
        File songFile = new File(song.getURL());
        if (!songFile.exists()) {
            System.out.println("File does not exist at " + songPath);
            return;
        }

        myTunesModel.changePlayingSongText(song.getTitle());

        mediaPlayer = new MediaPlayer(new Media(songFile.toURI().toString()));
        setVolume(volume);
        mediaPlayer.play();
        mediaPlayer.setOnEndOfMedia(this::nextSong);
    }

    public void nextSong() {
        // Hvis indekset overstiger antallet af indeks, gå til start
        if (currentIndex + 1 >= currentSongs.size()) {
            currentIndex = 0;
        } else {
            currentIndex = currentIndex + 1;
        }
        playSong();
    }

    public void previousSong() {
        // Hvis den nuværende sang er den første i listen, gå til slutningen
        if (currentIndex - 1 < 0) {
            currentIndex = currentSongs.size() - 1;
        } else {
            currentIndex = currentIndex - 1;
        }
        playSong();
    }

    public void pauseSong() {
        if (mediaPlayer == null) { return; }

        mediaPlayer.pause();
    }

    public void resumeSong() {
        if (mediaPlayer == null) { return; }

        mediaPlayer.play();
    }

    /**
     * Sets the volume of the mediaplayer
     * @param volumeValue the value from the volume slider, 0-100
     */
    public void setVolume(int volumeValue) {
        if (mediaPlayer == null) { return; }

        double valueToApply = (double)volumeValue / 100;
        volume = volumeValue;
        mediaPlayer.setVolume(valueToApply);
    }


    public Playlist updatePlaylist(Playlist playlist) throws Exception {
        return dataAccess.updatePlaylist(playlist);
    }

    public void playFromNewPlace(int index, List<Song> songs) {
        currentSongs = songs;
        currentIndex = index;
        playSong();
    }

    public Playlist createPlaylist(String playlistName) throws Exception {
        Playlist playlist = new Playlist(playlistName, new ArrayList<>());
        return dataAccess.addPlaylist(playlist);
    }

    public List<Playlist> getAllPlaylists() throws Exception {
        return dataAccess.getAllPlaylists();
    }

    public List<Song> getAllSongs() throws Exception {
        return dataAccess.getAllSongs();
    }

    public List<Song> getSongByName(String query) throws Exception {
        return dataAccess.getSongByName(query);
    }
    public void deletePlaylist(Playlist playlist) throws Exception {
        dataAccess.deletePlaylist(playlist);
    }

    public List<String> getGenres() throws Exception {
        return dataAccess.getGenres();
    }

    public Song addSong(Song song) throws Exception{
        return dataAccess.addSong(song);
    }

    /**
     * Get the duration of the song or sound file in seconds
     * @param path the path to the file
     * @param callback that's a good question
     */
    public void getDurationOfFile(String path, Consumer<Integer> callback){
        File songFile = new File(path);
        MediaPlayer mediaPlayer = new MediaPlayer(new Media(songFile.toURI().toString()));
        mediaPlayer.setOnReady(() -> {
            Duration duration = mediaPlayer.getTotalDuration();
            Integer time = (int) (duration.toMillis() / 1000);
            callback.accept(time);
            mediaPlayer.dispose();
        });
    }
    public void deleteSong(Song song) throws Exception {
        dataAccess.deleteSong(song);
    }
}
