package com.gruppe5.MyTunes.GUI.Model;

import com.gruppe5.MyTunes.BLL.MyTunesLogic;

public class MyTunesModel {
    public MyTunesLogic myTunesLogic;

    public MyTunesModel() throws Exception {
        myTunesLogic = new MyTunesLogic();
    }
}
