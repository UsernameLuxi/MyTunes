package com.gruppe5.MyTunes.BLL;

import com.gruppe5.MyTunes.BE.Playlist;
import com.gruppe5.MyTunes.BE.Song;
import com.gruppe5.MyTunes.DAL.DAO_DB;
import com.gruppe5.MyTunes.DAL.IDataAccess;
import com.gruppe5.MyTunes.DAL.MOCK_DB;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;
import java.util.List;

public class MyTunesLogic {
    private IDataAccess dataAccess = new MOCK_DB();
    private MediaPlayer mediaPlayer;
    private Playlist selectedPlaylist = null;
    private Song selectedSong = null;

    public MyTunesLogic() {
        System.out.println(playSong(dataAccess.getAllSongs().getFirst()));
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
            return false;
        }

        selectedSong = song;
        mediaPlayer = new MediaPlayer(new Media(songFile.toURI().toString()));
        mediaPlayer.play();
        mediaPlayer.setOnEndOfMedia(this::nextSong);

        return true;
    }

    public boolean nextSong() {
        List<Song> songList = selectedPlaylist != null ? selectedPlaylist.getSongs() : dataAccess.getAllSongs();
        for (int i = 0; i < songList.size(); i++) {
            if (songList.get(i).getTitle().equals(selectedSong.getTitle())) {
                if ((i + 1) > songList.size()) {
                    playSong(songList.getFirst());
                } else {
                    playSong(songList.get(i + 1));
                }
                break;
            }
        }

        return true;
    }

    public boolean previousSong() {
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

        return true;
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
}
