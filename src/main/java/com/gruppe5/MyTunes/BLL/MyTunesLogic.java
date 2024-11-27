package com.gruppe5.MyTunes.BLL;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;

public class MyTunesLogic {
    private MediaPlayer mediaPlayer;


    public boolean playSong(String songName)  {
        // Dispose mediaplayer hvis en sang allerede afspiller
        if (mediaPlayer != null) {
            mediaPlayer.dispose();
        }

        File songFile = new File(songName + ".mp3");

        if (!songFile.exists()) {
            return false;
        }

        mediaPlayer = new MediaPlayer(new Media(songFile.toURI().toString()));
        mediaPlayer.play();
        mediaPlayer.setOnEndOfMedia(() -> {
          playSong(songName);
            // [TODO] Her skal den næste sang fra listen afspilles i stedet for songName
        });

        return true;
    }

    public boolean nextSong() {
        String
        playSong();
    }

    public boolean previousSong() {

    }

    public boolean pauseSong() {
        if (mediaPlayer == null) {
            return false;
        }

        mediaPlayer.pause();
        return true;
    }
}
