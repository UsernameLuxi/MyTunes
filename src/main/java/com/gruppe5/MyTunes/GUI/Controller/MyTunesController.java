package com.gruppe5.MyTunes.GUI.Controller;

import com.gruppe5.MyTunes.BLL.MyTunesLogic;
import com.gruppe5.MyTunes.GUI.Model.MyTunesModel;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.awt.event.ActionEvent;
import java.io.IOException;


public class MyTunesController {
    private MyTunesLogic myTunesLogic;

    @FXML
    public TableView tblPlaylists;

    @FXML
    public TableView tblSongs;

    @FXML
    public ListView lstSongsInPlaylist;

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

    @FXML
    private void onNewSongButtonClick(ActionEvent actionEvent){}

    public void initialize(MyTunesLogic myTunesLogic) {
        this.myTunesLogic = myTunesLogic;

        sliderVol.valueProperty().addListener((observable, oldValue, newValue) -> {
            myTunesLogic.setVolume(newValue.intValue());
            sliderVolLabel.setText(String.valueOf(newValue.intValue()));
        });
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
}

