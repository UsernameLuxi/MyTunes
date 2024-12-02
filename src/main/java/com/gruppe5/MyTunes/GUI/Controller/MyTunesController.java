package com.gruppe5.MyTunes.GUI.Controller;

import com.gruppe5.MyTunes.BE.Playlist;
import com.gruppe5.MyTunes.BE.Song;
import com.gruppe5.MyTunes.GUI.Model.MyTunesModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Time;
import java.util.List;


public class MyTunesController {
    private MyTunesModel myTunesModel;
    private String pauseSymbol = "⏸";
    private String playSymbol = "▶";

    @FXML
    public TableView<Playlist> tblPlaylists;

    @FXML
    public TableView<Song> tblSongs;

    @FXML
    public ListView<Song> lstSongsInPlaylist;

    @FXML
    public Button btnBack;

    @FXML
    public Button btnPlay;

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
    private TableColumn<Playlist, String> colPlaylistName;
    @FXML
    private TableColumn<Playlist, Integer> colPlaylistAmtSongs;
    @FXML
    private TableColumn<Playlist, Time> colPlaylistTotDur;
    @FXML
    private TableColumn<Song, String> colSongTitle;
    @FXML
    private TableColumn<Song, String> colArtist;
    @FXML
    private TableColumn<Song, String> colGenre;
    @FXML
    private TableColumn<Song, Time> colSongDur;

    public void initialize(MyTunesModel myTunesModel) {
        this.myTunesModel = myTunesModel;

        sliderVol.valueProperty().addListener((observable, oldValue, newValue) -> {
            sliderVolumeChanged(newValue);
        });


        sliderVolumeChanged(50);
        colPlaylistName.setCellValueFactory(new PropertyValueFactory<Playlist, String>("name"));
        colPlaylistTotDur.setCellValueFactory(new PropertyValueFactory<Playlist, Time>("totalDuration"));
        colPlaylistAmtSongs.setCellValueFactory(new PropertyValueFactory<Playlist, Integer>("size"));
        tblPlaylists.setItems(myTunesModel.getPlaylists());

        tblPlaylists.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                myTunesModel.setPlaylist(newValue);
                lstSongsInPlaylist.setItems(myTunesModel.getCurrentPlaylistSongs());
            }
        });

        tblSongs.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null && !btnPlay.getText().equals(playSymbol)) {
                btnPlay.setText(playSymbol);
            }

        });

        lstSongsInPlaylist.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                btnPlay.setText(playSymbol);
            }
        });

        colSongTitle.setCellValueFactory(new PropertyValueFactory<Song, String>("title"));
        colArtist.setCellValueFactory(new PropertyValueFactory<Song, String>("artist"));
        colGenre.setCellValueFactory(new PropertyValueFactory<Song, String>("genre"));
        colSongDur.setCellValueFactory(new PropertyValueFactory<Song, Time>("time"));
        tblSongs.setItems(myTunesModel.getSongs());
    }

    /**
     * Sets the volume in the BLL and changes the volume slider label text.
     * @param newValue the value from the volume slider, 0-100
     */
    private void sliderVolumeChanged(Number newValue) {
        myTunesModel.setVolume(newValue.intValue());
        sliderVolLabel.setText(String.valueOf(newValue.intValue()));
    }

    public void onNewSongButtonClick() throws Exception {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/com/gruppe5/MyTunes/AddSongsPopUp.fxml"));

        Parent scene = loader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(scene));
        stage.setTitle("Add/Edit Song");

        // Get the controller reference
        AddSongsPopUpController controller = loader.getController();

        // Send a reference to the parent to MyTunesController
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
            int selectedIndex = lstSongsInPlaylist.getSelectionModel().getSelectedIndex();
            Playlist p = myTunesModel.getPlaylist();
            Song selectedSong = lstSongsInPlaylist.getSelectionModel().getSelectedItem();
            List<Song> playlist = p.getSongs();
            if (selectedIndex != 0) {
                Song down = playlist.get(lstSongsInPlaylist.getSelectionModel().getSelectedIndex() - 1);
                playlist.set(lstSongsInPlaylist.getSelectionModel().getSelectedIndex(), down);
                playlist.set(lstSongsInPlaylist.getSelectionModel().getSelectedIndex() - 1, selectedSong);
                myTunesModel.updatePlaylist(p);
            }
        }

    }

    @FXML
    private void onMoveSongDown(ActionEvent actionEvent) throws Exception {
        if (lstSongsInPlaylist.getSelectionModel().getSelectedItem() != null) {
            int selectedIndex = lstSongsInPlaylist.getSelectionModel().getSelectedIndex();
            Playlist p = myTunesModel.getPlaylist();
            Song selectedSong = lstSongsInPlaylist.getSelectionModel().getSelectedItem();
            List<Song> playlist = p.getSongs();
            if (selectedIndex != playlist.size() - 1) {
                Song up = playlist.get(lstSongsInPlaylist.getSelectionModel().getSelectedIndex() + 1);
                playlist.set(lstSongsInPlaylist.getSelectionModel().getSelectedIndex(), up);
                playlist.set(lstSongsInPlaylist.getSelectionModel().getSelectedIndex() + 1, selectedSong);
                myTunesModel.updatePlaylist(p);
            }

        }
    }

    @FXML
    private void onSongDeleteInPlaylist(ActionEvent actionEvent) {
        if (lstSongsInPlaylist.getSelectionModel().getSelectedItem() != null){
            try{
                Playlist playlist = myTunesModel.getPlaylist();
                List<Song> songs = playlist.getSongs();
                songs.remove(lstSongsInPlaylist.getSelectionModel().getSelectedIndex());
                playlist.setSongs(songs);
                myTunesModel.updatePlaylist(playlist);
                tblPlaylists.refresh();
            }
            catch(Exception e){
                throw new RuntimeException(e); // TODO: vis den til brugeren tak!
            }
        }
    }

    @FXML
    private void addNewSongToPlaylist(ActionEvent actionEvent) {
        if (tblSongs.getSelectionModel().getSelectedItem() != null) {
            Playlist p = myTunesModel.getPlaylist();
            List<Song> playlist = p.getSongs();
            playlist.add(tblSongs.getSelectionModel().getSelectedItem());
            p.setSongs(playlist);
            try{
                myTunesModel.updatePlaylist(p);
                tblPlaylists.refresh();
            }
            catch(Exception e){
                throw new RuntimeException(e); // TODO : vis den til brugeren hvis det er
            }

        }
    }

    @FXML
    private void searchForSong(ActionEvent actionEvent) {
        try{
            myTunesModel.getSongByName(txtFilter.getText().trim());
        }
        catch(Exception e){
            throw new RuntimeException(e); // TODO : vis det til brugeren
        }
    }
    @FXML
    private void onPlayButtonClick(ActionEvent actionEvent) {
        if (lstSongsInPlaylist.getSelectionModel().getSelectedItem() != null) {
            int index = lstSongsInPlaylist.getSelectionModel().getSelectedIndex();
            myTunesModel.playFromNewPlace(index, myTunesModel.getCurrentPlaylistSongs());
            btnPlay.setText(pauseSymbol);
        } else if (tblSongs.getSelectionModel().getSelectedItem() != null) {
            int index = tblSongs.getSelectionModel().getSelectedIndex();
            myTunesModel.playFromNewPlace(index, myTunesModel.getSongs());
            btnPlay.setText(pauseSymbol);
        }
        else{
            if (btnPlay.getText().equals(pauseSymbol)){
                btnPlay.setText(playSymbol);
                // pause
                myTunesModel.pauseSong();
            }
            else{
                btnPlay.setText(pauseSymbol);
                // resume
                myTunesModel.resumeSong();
            }


        }

        tblPlaylists.getSelectionModel().clearSelection();
        tblSongs.getSelectionModel().clearSelection();
        lstSongsInPlaylist.getSelectionModel().clearSelection();
    }
    boolean isCreating = false;
    @FXML
    private void onNewPlaylistButtonClick(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/com/gruppe5/MyTunes/AddPlaylistPopUp.fxml"));

        Parent scene = loader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(scene));
        stage.setTitle("Add/Edit Playlist");

        // Get the controller reference
        AddPlaylistPopUpController controller = loader.getController();

        // Send a reference to the parent to MyTunesController
        controller.setParent(this); // this refers to this MainWindowController object

        isCreating = true;

        // Set the modality to Application (you must close Window1 before going to the parent window
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();
    }
    @FXML
    private void onDeletePlaylistButtonClick(ActionEvent actionEvent) throws Exception {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/com/gruppe5/MyTunes/DeletePlaylistPopUp.fxml"));

        Parent scene = loader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(scene));
        stage.setTitle("Delete Playlist");

        // Get the controller reference
        DeletePlaylistPopUpController controller = loader.getController();

        // Send a reference to the parent to MyTunesController
        controller.setParent(this); // this refers to this MainWindowController object

        // Set the modality to Application (you must close Window1 before going to the parent window
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();
    }
    public void deletePlaylistConfirm() throws Exception {
        System.out.println("deletePlaylistConfirm test");
        myTunesModel.deletePlaylist(tblPlaylists.getSelectionModel().getSelectedItem());
    }
    public Playlist getPlaylist() {
        myTunesModel.getPlaylist();
        return myTunesModel.getPlaylist();
    }
    public void createPlaylist(String name) throws Exception {
        myTunesModel.createPlaylist(name);
    }
    public void updatePlaylist() throws Exception {
        myTunesModel.updatePlaylist(myTunesModel.getPlaylist());
        tblPlaylists.refresh();
    }
    @FXML
    private void onEditPlaylistButtonClick(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/com/gruppe5/MyTunes/AddPlaylistPopUp.fxml"));

        Parent scene = loader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(scene));
        stage.setTitle("Add/Edit Playlist");

        // Get the controller reference
        AddPlaylistPopUpController controller = loader.getController();

        // Send a reference to the parent to MyTunesController
        controller.setParent(this); // this refers to this MainWindowController object

        isCreating = false;

        // Set the modality to Application (you must close Window1 before going to the parent window
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();
    }

    public MyTunesModel getMyTunesModel() {
        return myTunesModel;
    }
}

