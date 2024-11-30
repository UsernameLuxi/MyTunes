package com.gruppe5.MyTunes.GUI.Model;

import com.gruppe5.MyTunes.BLL.MyTunesLogic;

public class MyTunesModel {
    private final MyTunesLogic myTunesLogic;

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
}
