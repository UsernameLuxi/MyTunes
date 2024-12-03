package com.gruppe5.MyTunes.GUI.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;

import java.io.File;

public class AddSongsPopUpController {
    private MyTunesController parent;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtFile;

    /**
     * Sets a reference to the main window controller (parent window)
     * @param parentParam
     */
    public void setParent(MyTunesController parentParam) {
        this.parent = parentParam;
    }

    @FXML
    private void onFileChoose(ActionEvent actionEvent) {
        FileChooser chooser = new FileChooser();
        chooser.setTitle("Choose a file");
        chooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Wav Files", "*.wav"),
                new FileChooser.ExtensionFilter("Mp3 Files", "*.mp3")
        );
        File file = chooser.showOpenDialog(null);
        if (file != null) {
            txtFile.setText(file.getPath());
        }
    }
}
