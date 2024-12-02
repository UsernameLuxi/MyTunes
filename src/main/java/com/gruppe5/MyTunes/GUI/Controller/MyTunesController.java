package com.gruppe5.MyTunes.GUI.Controller;

import com.gruppe5.MyTunes.BE.Song;
import com.gruppe5.MyTunes.GUI.Model.MyTunesModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;


public class MyTunesController {
    private MyTunesModel myTunesModel;

    @FXML
    public TableView tblPlaylists;

    @FXML
    public TableView tblSongs;

    @FXML
    public ListView<Song> lstSongsInPlaylist;

    @FXML
    public Button btnBack;

    @FXML
    public Button BtnPlay;

    @FXML
    public Button btnSkip;

    @FXML
    public Button btnFilter;

    @FXML
    public Button btnTransferSongs;

    @FXML
    public Button btnPlaylistsNew;

    @FXML
    public Button btnPlaylistsEdit;

    @FXML
    public Button btnPlaylistsDel;

    @FXML
    public Button btnSongInPlaylistUp;

    @FXML
    public Button btnSongInPlaylistDown;

    @FXML
    public Button btnSongInPlaylistDel;

    @FXML
    public Button btnSongsNew;

    @FXML
    public Button btnSongsEdit;

    @FXML
    public Button btnSongsDel;

    @FXML
    public Button btnClose;

    @FXML
    public Slider sliderVol;

    @FXML
    public Label lblCurrentSong;

    @FXML
    public TextField txtFilter;

    @FXML
    private Label testText;

    @FXML
    private Label sliderVolLabel;

    public void initialize(MyTunesModel myTunesModel) {
        this.myTunesModel = myTunesModel;

        sliderVol.valueProperty().addListener((observable, oldValue, newValue) -> {
            sliderVolumeChanged(newValue);
        });

        sliderVolumeChanged(50);
    }

    /**
     * Sets the volume in the BLL and changes the volume slider label text.
     * @param newValue the value from the volume slider, 0-100
     */
    private void sliderVolumeChanged(Number newValue) {
        myTunesModel.setVolume(newValue.intValue());
        sliderVolLabel.setText(String.valueOf(newValue.intValue()));
    }

    public void onNewSongButtonClick() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/com/gruppe5/MyTunes/AddSongsPopUp.fxml"));

        Parent scene = loader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(scene));

        // Get the controller reference
        AddSongsPopUpController controller = loader.getController();

        // Send a reference to the parent to Window1Controller
        controller.setParent(this); // this refers to this MainWindowController object

        // Set the modality to Application (you must close Window1 before going to the parent window
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();
    }

    public void btnBackClicked() throws Exception {
        myTunesModel.prevSong();
    }

    public void btnSkipClicked() throws Exception {
        myTunesModel.nextSong();
    }

    @FXML
    private void onMoveSongUp(ActionEvent actionEvent) throws Exception {
        if (lstSongsInPlaylist.getSelectionModel().getSelectedItem() != null) {
            Song selectedSong = lstSongsInPlaylist.getSelectionModel().getSelectedItem();
            List<Song> playlist = myTunesModel.getPlaylist();
            for (int i = 0; i < playlist.size(); i++) {
                if (playlist.get(i).equals(selectedSong) && i != 0) {
                    Song tempsong = playlist.get(i - 1);
                    playlist.set(i - 1, selectedSong);
                    playlist.set(i, tempsong);
                    break;
                }
            }
            myTunesModel.updatePlaylist(playlist);
        }

    }

    @FXML
    private void onMoveSongDown(ActionEvent actionEvent) throws Exception {
        if (lstSongsInPlaylist.getSelectionModel().getSelectedItem() != null) {
            Song selectedSong = lstSongsInPlaylist.getSelectionModel().getSelectedItem();
            List<Song> playlist = myTunesModel.getPlaylist();
            for (int i = 0; i < playlist.size() - 1; i++) {
                if (playlist.get(i).equals(selectedSong)) {
                    Song tempsong = playlist.get(i + 1);
                    playlist.set(i + 1, selectedSong);
                    playlist.set(i, tempsong);
                    break;
                }
            }
            myTunesModel.updatePlaylist(playlist);
        }
    }

    @FXML
    private void onSongDeleteInPlaylist(ActionEvent actionEvent) {
        if (lstSongsInPlaylist.getSelectionModel().getSelectedItem() != null){
            try{
                List<Song> playlist = myTunesModel.getPlaylist();
                playlist.removeIf(s -> s.toString().equals(lstSongsInPlaylist.getSelectionModel().getSelectedItem().toString()));
                myTunesModel.updatePlaylist(playlist);
            }
            catch(Exception e){
                throw new RuntimeException(e); // TODO: vis den til brugeren tak!
            }
        }
    }
}

