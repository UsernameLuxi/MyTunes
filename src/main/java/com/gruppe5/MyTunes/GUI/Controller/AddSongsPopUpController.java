package com.gruppe5.MyTunes.GUI.Controller;

import com.gruppe5.MyTunes.BE.Song;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

public class AddSongsPopUpController {
    private MyTunesController parent;
    private int time = 0;

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
            MenuItem mi = new MenuItem(string);
            mi.setOnAction(event -> {
                MenuItem mt = (MenuItem) event.getSource();
                mbtnCategory.setText(mt.getText());
            });
            items.add(mi);
        }
        this.mbtnCategory.getItems().clear();
        this.mbtnCategory.getItems().addAll(items);

    }

    @FXML
    private void onFileChoose(ActionEvent actionEvent) {
        FileChooser chooser = new FileChooser();
        chooser.setTitle("Choose a file");
        chooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Mp3 Files", "*.mp3"),
                new FileChooser.ExtensionFilter("Wav Files", "*.wav")
        );
        File file = chooser.showOpenDialog(null);
        if (file != null) {
            txtFile.setText(file.getPath());
            parent.getMyTunesModel().setDurationOfFile(file.getPath(), this);
        }
    }

    @FXML
    private void onCancel(ActionEvent actionEvent) {
        Stage stage = (Stage) btnCancel.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void onSave(ActionEvent actionEvent) throws Exception{
        if (time != 0){
            String title = txtTitle.getText();
            String genre = mbtnCategory.getText();
            String artist = txtArtist.getText();
            String path = txtFile.getText();
            Song s = new Song(title, artist, time, genre, path);
            parent.getMyTunesModel().addSong(s);
            // close stage
            onCancel(actionEvent);
        }
        else{
            throw new Exception("Time is invalid, or not ready");
        }
    }

    public void setTimeField(Integer value) {
        time = value;
        System.out.println((long) value);
        txtTime.setText(new Time((long) (value * 1000) - (1000 * 3600)).toString()); //  convert to milliseconds, the subtract the annoying hour
    }

    @FXML
    private void onMorePress(ActionEvent actionEvent) throws Exception {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/com/gruppe5/MyTunes/NewGenre.fxml"));

        Parent scene = loader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(scene));
        stage.setTitle("New Category");

        // Get the controller reference
        NewGenreController controller = loader.getController();

        // Send a reference to the parent to NewGenreController
        controller.setParent(this);

        // Set the modality to Application (you must close Window1 before going to the parent window
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();
    }

    public void addGenre(String genre){
        MenuItem mi = new MenuItem(genre.trim());
        mbtnCategory.getItems().add(mi);
        mbtnCategory.setText(mi.getText());
    }
}
