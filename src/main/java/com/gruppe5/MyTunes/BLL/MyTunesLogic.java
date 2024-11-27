package com.gruppe5.MyTunes.BLL;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class MyTunesLogic {
    private MediaPlayer mediaPlayer;

    public MyTunesLogic() {
    }

    public void playSong(String songName) {
        if (mediaPlayer != null) {
            mediaPlayer.dispose();
        }

        mediaPlayer = new MediaPlayer(new Media("/songs/" + songName + ".mp3"));
        mediaPlayer.play();
    }
}
