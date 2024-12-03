package com.gruppe5.MyTunes.GUI.Controller;

import com.gruppe5.MyTunes.BE.Playlist;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class DeletePlaylistPopUpController {
    private MyTunesController parent;

    @FXML
    private TextField txtName;

    @FXML
    private Button btnDelete;
    @FXML
    private Button btnCancel;

    /**
     * Sets a reference to the main window controller (parent window)
     * @param parentParam
     */
    public void setParent(MyTunesController parentParam) {
        this.parent = parentParam;
    }


    public void onDeleteButtonClick(ActionEvent actionEvent) throws Exception {

        parent.deletePlaylistConfirm();
    }
}

