package com.gruppe5.MyTunes.GUI.Controller;

import com.gruppe5.MyTunes.BE.Playlist;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class AddPlaylistPopUpController {
    private MyTunesController parent;

    @FXML
    private TextField txtName;

    @FXML
    private Button btnSave;

    @FXML
    private Button btnCancel;
    /**
     * Sets a reference to the main window controller (parent window)
     * @param parentParam
     */
    public void setParent(MyTunesController parentParam) {
        this.parent = parentParam;
    }

        public void onCancelButtonClick(ActionEvent actionEvent) throws Exception {
        Stage stage = (Stage) btnCancel.getScene().getWindow();
        stage.close();
        }

        public void onSaveButtonClick(ActionEvent actionEvent) throws Exception {
            String name = txtName.getText();
            if (!parent.isCreating) {
                parent.getPlaylist().setName(name);
                parent.updatePlaylist();
            }
            else if (parent.isCreating) {
            parent.createPlaylist(name);
            }
            Stage stage = (Stage) btnCancel.getScene().getWindow();
            stage.close();
        }
    }


