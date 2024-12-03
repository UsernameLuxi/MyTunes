package com.gruppe5.MyTunes.GUI.Controller;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class AddPlaylistPopUpController {
    private MyTunesController parent;

    @FXML
    private TextField txtName;


    /**
     * Sets a reference to the main window controller (parent window)
     * @param parentParam
     */
    public void setParent(MyTunesController parentParam) {
        this.parent = parentParam;
    }

}