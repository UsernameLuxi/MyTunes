package com.gruppe5.MyTunes.GUI.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class AddSongsPopUpController {
    private MyTunesController parent;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtFile;
    @FXML
    private Button btnSave;
    @FXML
    private Button btnCancel;
    @FXML
    private Button btnFile;
    @FXML
    private Button btnCategory;
    @FXML
    private TextField txtTime;
    @FXML
    private MenuButton mbtnCategory;
    @FXML
    private TextField txtTitle;
    @FXML
    private TextField txtArtist;

    /**
     * Sets a reference to the main window controller (parent window)
     * @param parentParam
     */
    public void setParent(MyTunesController parentParam) throws Exception {
        this.parent = parentParam;

        List<MenuItem> items = new ArrayList<>();
        for (String string : parent.getMyTunesModel().getGenres()) {
            items.add(new MenuItem(string));
        }
        this.mbtnCategory.getItems().clear();
        this.mbtnCategory.getItems().addAll(items);

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

    @FXML
    private void onCancel(ActionEvent actionEvent) {
        Stage stage = (Stage) btnCancel.getScene().getWindow();
        stage.close();
    }
}
