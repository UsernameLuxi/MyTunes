package com.gruppe5.MyTunes.BLL;

import com.gruppe5.MyTunes.BE.Playlist;
import com.gruppe5.MyTunes.BE.Song;
import com.gruppe5.MyTunes.DAL.DAO_DB;
import com.gruppe5.MyTunes.DAL.IDataAccess;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;
import java.sql.Time;
import java.util.List;

public class MyTunesLogic {
    private IDataAccess dataAccess = new DAO_DB();
    private MediaPlayer mediaPlayer;
    private Playlist selectedPlaylist = null;
    private Song selectedSong = null;

    public MyTunesLogic() throws Exception {
        try {
            System.out.println(dataAccess.getAllSongs().toString());
            playSong(dataAccess.getAllSongs().getFirst());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public boolean playSong(Song song)  {
        System.out.println("attempting to play song " + song.getTitle());
        // Dispose mediaplayer hvis en sang allerede afspiller
        if (mediaPlayer != null) {
            mediaPlayer.dispose();
        }

        String songName = song.getTitle();
        File songFile = new File(songName + ".mp3");

        if (!songFile.exists()) {
            System.out.println("File does not exist, returning");
            return false;
        }

        selectedSong = song;
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
        if (mediaPlayer == null) {
            return false;
        }

        mediaPlayer.pause();
        return true;
    }

    public boolean resumeSong() {
        if (mediaPlayer == null) {
            return false;
        }

        mediaPlayer.play();
        return true;
    }

    /**
     * Sets the volume of the mediaplayer
     * @param volumeValue the value from the volume slider, 0-100
     */
    public void setVolume(int volumeValue) {
        double valueToApply = (double)volumeValue / 100;
        mediaPlayer.setVolume(valueToApply);
    }
}
