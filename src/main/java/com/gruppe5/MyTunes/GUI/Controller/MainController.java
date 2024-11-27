package com.gruppe5.MyTunes.GUI.Controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;


public class MainController {
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

}