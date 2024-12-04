package com.gruppe5.MyTunes.GUI.Controller;

import com.gruppe5.MyTunes.BE.Playlist;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class DeleteSongPopUpController {
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
        parent.deleteSongConfirm();
        Stage stage = (Stage) btnCancel.getScene().getWindow();
        stage.close();
    }
    public void onCancelButtonClick(ActionEvent actionEvent) throws Exception {
        Stage stage = (Stage) btnCancel.getScene().getWindow();
        stage.close();
    }
}

