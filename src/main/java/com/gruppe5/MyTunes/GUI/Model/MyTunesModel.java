package com.gruppe5.MyTunes.GUI.Model;

import com.gruppe5.MyTunes.BE.Playlist;
import com.gruppe5.MyTunes.BE.Song;
import com.gruppe5.MyTunes.BLL.MyTunesLogic;
import javafx.collections.ObservableList;

import java.util.List;

public class MyTunesModel {
    private final MyTunesLogic myTunesLogic;
    ObservableList<Song> playlist;

    public MyTunesModel() throws Exception {
        myTunesLogic = new MyTunesLogic();
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

    public Playlist updatePlaylist(List<Song> songs) throws Exception {
        Playlist p = myTunesLogic.getSelectedPlaylist();
        p.setSongs(songs);
        return myTunesLogic.updatePlaylist(p);
    }
}
