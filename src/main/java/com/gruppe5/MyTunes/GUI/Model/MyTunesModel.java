package com.gruppe5.MyTunes.GUI.Model;

import com.gruppe5.MyTunes.BE.Playlist;
import com.gruppe5.MyTunes.BE.Song;
import com.gruppe5.MyTunes.BLL.MyTunesLogic;
import com.gruppe5.MyTunes.GUI.Controller.MyTunesController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.List;

public class MyTunesModel {
    private final MyTunesLogic myTunesLogic;
    private final MyTunesController myTunesController;
    private ObservableList<Song> playlist; // current playlist?
    private ObservableList<Playlist> playlists; // all playlists
    private ObservableList<Song> songs;

    public MyTunesModel(MyTunesController myTunesController) throws Exception {
        this.myTunesController = myTunesController;
        myTunesLogic = new MyTunesLogic(this);

        playlists = FXCollections.observableArrayList();
        playlists.addAll(myTunesLogic.getAllPlaylists());
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

    public ObservableList<Song> getPlaylist() {
        return playlist;
    }

    public ObservableList<Playlist> getPlaylists() {
        return playlists;
    }

    public ObservableList<Song> getSongs() {
        return songs;
    }

    public Playlist updatePlaylist(List<Song> songs) throws Exception {
        Playlist p = myTunesLogic.getSelectedPlaylist();
        p.setSongs(songs);
        return myTunesLogic.updatePlaylist(p);
    }

    public void changePlayingSongText(String songTitle) {
        myTunesController.lblCurrentSong.setText(songTitle);
    }

    public void createPlaylist(String playlistName) throws Exception {
        Playlist playlist = myTunesLogic.createPlaylist(playlistName);
        myTunesController.tblPlaylists.getItems().add(playlist);
    }

    public void deletePlaylist(Playlist playlist) throws Exception {
        myTunesLogic.deletePlaylist(playlist);
    }

    public void getSongByName(String query) throws Exception {
        songs.setAll(myTunesLogic.getSongByName(query));
    }
}
