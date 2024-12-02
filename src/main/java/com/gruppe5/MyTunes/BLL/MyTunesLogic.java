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
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class MyTunesLogic {
    private IDataAccess dataAccess = new DAO_DB();
    private MediaPlayer mediaPlayer;
    private Playlist selectedPlaylist = null;
    private Song selectedSong = null;
    private MyTunesModel myTunesModel;

    public MyTunesLogic(MyTunesModel myTunesModel) {
        this.myTunesModel = myTunesModel;

        try {
            System.out.println(dataAccess.getAllSongs().toString());
            playSong(dataAccess.getAllSongs().getFirst());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public boolean playSong(Song song) {
        if (mediaPlayer != null) {
            mediaPlayer.dispose();
        }

        String songPath = song.getURL();
        File songFile = new File(song.getURL());
        if (!songFile.exists()) {
            System.out.println("File does not exist at " + songPath);
            return false;
        }

        myTunesModel.changePlayingSongText(song.getTitle());
        selectedSong = song;
        getSongDuration(song, time -> {
            System.out.println(time);
        });
        mediaPlayer = new MediaPlayer(new Media(songFile.toURI().toString()));
        mediaPlayer.play();
        mediaPlayer.setOnEndOfMedia(() -> {
            try {
                this.nextSong();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

        return true;
    }

    public void nextSong() throws Exception {
        List<Song> songList = selectedPlaylist != null ? selectedPlaylist.getSongs() : dataAccess.getAllSongs();
        for (int i = 0; i < songList.size(); i++) {
            if (songList.get(i).getTitle().equals(selectedSong.getTitle())) {
                if ((i + 1) >= songList.size()) {
                    playSong(songList.getFirst());
                } else {
                    playSong(songList.get(i + 1));
                }
                break;
            }
        }

    }

    public void previousSong() throws Exception {
        List<Song> songList = selectedPlaylist != null ? selectedPlaylist.getSongs() : dataAccess.getAllSongs();
        for (int i = 0; i < songList.size(); i++) {
            if (songList.get(i).getTitle().equals(selectedSong.getTitle())) {
                if ((i - 1) < 0) {
                    playSong(songList.getLast());
                } else {
                    playSong(songList.get(i - 1));
                }
                break;
            }
        }
    }

    public boolean pauseSong() {
        if (mediaPlayer == null) { return false; }

        mediaPlayer.pause();
        return true;
    }

    public boolean resumeSong() {
        if (mediaPlayer == null) { return false; }

        mediaPlayer.play();
        return true;
    }

    /**
     * Sets the volume of the mediaplayer
     * @param volumeValue the value from the volume slider, 0-100
     */
    public void setVolume(int volumeValue) {
        if (mediaPlayer == null) { return; }

        double valueToApply = (double)volumeValue / 100;
        mediaPlayer.setVolume(valueToApply);
    }


    public Playlist updatePlaylist(Playlist playlist) throws Exception {
        return dataAccess.updatePlaylist(playlist);
    }

    public Playlist getSelectedPlaylist() {
        return selectedPlaylist;
    }

    public void getSongDuration(Song song, Consumer<Time> callback)  {
        File songFile = new File(song.getURL());
        MediaPlayer mediaPlayer = new MediaPlayer(new Media(songFile.toURI().toString()));
        mediaPlayer.setOnReady(() -> {
            Duration duration = mediaPlayer.getTotalDuration();
            Time time = new Time((long) duration.toMillis() - (1000 * 3600)); // 1 time for meget åbenbart?, derfor trækker vi en time fra
            callback.accept(time);
            mediaPlayer.dispose();
        });
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
}
