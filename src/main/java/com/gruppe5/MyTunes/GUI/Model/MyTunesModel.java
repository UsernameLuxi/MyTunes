package com.gruppe5.MyTunes.GUI.Model;

import com.gruppe5.MyTunes.BLL.MyTunesLogic;
import com.gruppe5.MyTunes.GUI.Controller.MyTunesController;

public class MyTunesModel {
    private final MyTunesLogic myTunesLogic;
    private MyTunesController myTunesController;

    public MyTunesModel(MyTunesController myTunesController) throws Exception {
        this.myTunesController = myTunesController;
        myTunesLogic = new MyTunesLogic(this);
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
    public void changePlayingSongText(String songTitle) {
        myTunesController.lblCurrentSong.setText(songTitle);
    }
}
