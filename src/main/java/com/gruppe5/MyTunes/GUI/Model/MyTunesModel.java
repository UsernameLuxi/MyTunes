package com.gruppe5.MyTunes.GUI.Model;

import com.gruppe5.MyTunes.BE.Playlist;
import com.gruppe5.MyTunes.BE.Song;
import com.gruppe5.MyTunes.BLL.MyTunesLogic;
import com.gruppe5.MyTunes.GUI.Controller.AddSongsPopUpController;
import com.gruppe5.MyTunes.GUI.Controller.MyTunesController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.List;

public class MyTunesModel {
    private final MyTunesLogic myTunesLogic;
    private final MyTunesController myTunesController;
    private Playlist playlist; // current playlist?
    private ObservableList<Song> currentPlaylistSongs;
    private ObservableList<Playlist> playlists; // all playlists
    private ObservableList<Song> songs;

    public MyTunesModel(MyTunesController myTunesController) throws Exception {
        this.myTunesController = myTunesController;
        myTunesLogic = new MyTunesLogic(this);

        playlists = FXCollections.observableArrayList();
        playlists.addAll(myTunesLogic.getAllPlaylists());
        currentPlaylistSongs = FXCollections.observableArrayList();
        songs = FXCollections.observableArrayList();
        songs.addAll(myTunesLogic.getAllSongs());
    }

    public void nextSong() throws Exception {
        myTunesLogic.nextSong();
    }

    public void prevSong() throws Exception {
        myTunesLogic.previousSong();
    }

    public void setVolume(int volumeVal) {
        myTunesLogic.setVolume(volumeVal);
    }

    public void setPlaylist(Playlist playlist){
        this.playlist = playlist;
        currentPlaylistSongs.setAll(playlist.getSongs());
    }

    public Playlist getPlaylist() {
        return playlist;
    }

    public List<String> getGenres() throws Exception {
        return myTunesLogic.getGenres();
    }

    public ObservableList<Song> getCurrentPlaylistSongs() {
        return currentPlaylistSongs;
    }

    public ObservableList<Playlist> getPlaylists() {
        return playlists;
    }

    public ObservableList<Song> getSongs() {
        return songs;
    }

    public Playlist updatePlaylist(Playlist p) throws Exception {
        currentPlaylistSongs.setAll(p.getSongs());
        return myTunesLogic.updatePlaylist(p);
    }

    public void changePlayingSongText(String songTitle) {
        myTunesController.lblCurrentSong.setText(songTitle);
    }

    public void createPlaylist(String playlistName) throws Exception {
        Playlist playlist = myTunesLogic.createPlaylist(playlistName);
        playlists.add(playlist);
    }

    public void deletePlaylist(Playlist playlist) throws Exception {
        myTunesLogic.deletePlaylist(playlist);
        playlists.remove(playlist);
    }

    public void getSongByName(String query) throws Exception {
        songs.setAll(myTunesLogic.getSongByName(query));
    }

    public void playFromNewPlace(int index, ObservableList<Song> songs) {
        myTunesLogic.playFromNewPlace(index, songs);
    }

    public void pauseSong(){
        myTunesLogic.pauseSong();
    }

    public void resumeSong(){
        myTunesLogic.resumeSong();
    }

    public Song addSong(Song song) throws Exception{
        Song s = myTunesLogic.addSong(song);
        songs.add(s);
        return s;
    }

    public void setDurationOfFile(String path, AddSongsPopUpController caller){
        myTunesLogic.getDurationOfFile(path, integer -> {displayDurationOfFile(integer, caller);});
    }

    private void displayDurationOfFile(Integer value, AddSongsPopUpController caller){
        caller.setTimeField(value);
    }

}
