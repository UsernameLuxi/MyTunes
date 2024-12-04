package com.gruppe5.MyTunes.GUI.Controller;

import com.gruppe5.MyTunes.BE.Playlist;
import com.gruppe5.MyTunes.BE.Song;
import com.gruppe5.MyTunes.GUI.Model.MyTunesModel;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.sql.Time;
import java.util.List;


public class MyTunesController {
    private MyTunesModel myTunesModel;
    private String pauseSymbol = "⏸";
    private String playSymbol = "▶";

    @FXML
    private TableView<Playlist> tblPlaylists;

    @FXML
    private TableView<Song> tblSongs;

    @FXML
    private ListView<Song> lstSongsInPlaylist;

    @FXML
    private Button btnBack;

    @FXML
    private Button btnPlay;

    @FXML
    private Button btnSkip;

    @FXML
    private Button btnFilter;

    @FXML
    private Button btnTransferSongs;

    @FXML
    private Button btnPlaylistNew;

    @FXML
    private Button btnPlaylistEdit;

    @FXML
    private Button btnPlaylistDel;

    @FXML
    private Button btnSongInPlaylistUp;

    @FXML
    private Button btnSongInPlaylistDown;

    @FXML
    private Button btnSongInPlaylistDel;

    @FXML
    private Button btnSongsNew;

    @FXML
    private Button btnSongsEdit;

    @FXML
    private Button btnSongsDel;

    @FXML
    private Button btnClose;

    @FXML
    private Slider sliderVol;

    @FXML
    private Label lblCurrentSong;

    @FXML
    private TextField txtFilter;

    @FXML
    private AnchorPane anchorPane;

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
    @FXML
    private Label lblSoundPicture;

    public void initialize(MyTunesModel myTunesModel) {
        this.myTunesModel = myTunesModel;

        sliderVol.valueProperty().addListener((observable, oldValue, newValue) -> {
            sliderVolumeChanged(newValue);
        });

        anchorPane.setOnDragOver(event -> {
            if (event.getGestureSource() != anchorPane && event.getDragboard().hasFiles()) {
                event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
            }
            event.consume();
        });

        anchorPane.setOnDragDropped(event -> {
            AddSongsPopUpController controller = onNewSongButtonClick();
            String filePath = event.getDragboard().getFiles().getFirst().toString();
            controller.setUriText(filePath);
            myTunesModel.setDurationOfFile(filePath, controller);
            event.setDropCompleted(true);
            event.consume();
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


    public AddSongsPopUpController onNewSongButtonClick() {
        try{
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
            return controller;
        }
        catch (Exception e){
            displayError(e);
        }
        return null;
    }

    public void btnBackClicked(){
        try{
            myTunesModel.prevSong();
        }
        catch (Exception e){
            displayError(e);
        }
    }

    public void btnSkipClicked() {
        try{
            myTunesModel.nextSong();
        }
        catch (Exception e){
            displayError(e);
        }
    }

    @FXML
    private void onMoveSongUp(ActionEvent actionEvent) {
        try{
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
        } catch (Exception e) {
            displayError(e);
        }

    }

    @FXML
    private void onMoveSongDown(ActionEvent actionEvent) throws Exception {
        try{
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
        } catch (Exception e) {
            displayError(e);
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
                displayError(e);
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
                displayError(e);
            }

        }
    }

    @FXML
    private void searchForSong(ActionEvent actionEvent) {
        try{
            myTunesModel.getSongByName(txtFilter.getText().trim());
        }
        catch(Exception e){
            displayError(e);
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

    // move please ikke victor
    boolean isCreating = false;
    @FXML
    private void onNewPlaylistButtonClick(ActionEvent actionEvent) {
        try{
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
        catch(Exception e){
            displayError(e);
        }
    }
    @FXML
    private void onDeletePlaylistButtonClick(ActionEvent actionEvent) {
        try{
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
        catch(Exception e){
            displayError(e);
        }

    }

    public void deletePlaylistConfirm(){
        try{
            myTunesModel.deletePlaylist(tblPlaylists.getSelectionModel().getSelectedItem());
        }
        catch(Exception e){
            displayError(e);
        }

    }

    public Playlist getPlaylist() {
        //myTunesModel.getPlaylist();
        return myTunesModel.getPlaylist();
    }

    public void createPlaylist(String name){
        try{
            myTunesModel.createPlaylist(name);
        }
        catch(Exception e){
            displayError(e);
        }
    }

    public void updatePlaylist() {
        try{
            myTunesModel.updatePlaylist(myTunesModel.getPlaylist());
            tblPlaylists.refresh();
        }
        catch(Exception e){
            displayError(e);
        }
    }

    @FXML
    private void onEditPlaylistButtonClick(ActionEvent actionEvent) throws IOException {
        try{
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
        catch(Exception e){
            displayError(e);
        }
    }

    public MyTunesModel getMyTunesModel() {
        return myTunesModel;
    }

    private void displayError(Throwable t)
    {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Something went wrong");
        alert.setHeaderText(t.getMessage());
        alert.showAndWait();
    }

    @FXML
    private void onDeleteSongButtonClick(ActionEvent actionEvent) {
        try{
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/com/gruppe5/MyTunes/DeleteSongPopUp.fxml"));

            Parent scene = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(scene));
            stage.setTitle("Delete Song");

            // Get the controller reference
            DeleteSongPopUpController controller = loader.getController();

            // Send a reference to the parent to MyTunesController
            controller.setParent(this); // this refers to this MainWindowController object

            // Set the modality to Application (you must close Window1 before going to the parent window
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.show();
        }
        catch(Exception e){
            displayError(e);
        }

    }

    public void deleteSongConfirm(){
        try{
            myTunesModel.deleteSong(tblSongs.getSelectionModel().getSelectedItem());
        }
        catch(Exception e){
            displayError(e);
        }
    }

    public void setSongsText(String text){
        lblCurrentSong.setText(text);
    }


    /**
     * resizes the items in the window relative to the original relations
     */
    public void resizeItems(double width, double height){
        // original width 880 , height 620
        int orgWidth = 880;
        int orgHeight = 620;
        //tblPlaylists
        float tblPlaylistPercentage_width = 227.0f/ orgWidth;
        float tblPlaylistPercentage_height = 450.0f / orgHeight;

        // TableView<Song> tblSongs;
        float tblSongsPercentage_width = 418.0f / orgWidth;
        float tblSongsPercentage_height = 426.0f / orgHeight;

        // ListView<Song> lstSongsInPlaylist;
        float lstSongsInPlaylistPercentage_width = 140.0f / orgWidth;
        float lstSongsInPlaylistPercentage_height = 430.0f / orgHeight;

        // Button btnBack;
        float btnBackPercentage_width = 26.4f / orgWidth;
        float btnBackPercentage_height = 25.6f / orgHeight;

        // Button btnPlay;
        float btnPlayPercentage_width = 37.0f / orgWidth;
        float btnPlayPercentage_height = 30.0f / orgHeight;

        // Button btnSkip;
        float btnSkipPercentage_width = 26.4f / orgWidth;
        float btnSkipPercentage_height = 25.6f / orgHeight;

        // Button btnFilter;
        float btnFilterPercentage_width = 28f / orgWidth;
        float btnFilterPercentage_height = 25.6f / orgHeight;

        // Button btnTransferSongs;
        float btnTransferSongsPercentage_width = 48.0f / orgWidth;
        float btnTransferSongsPercentage_height = 26.0f / orgHeight;

        // Button btnPlaylistNew;
        float btnPlaylistNewPercentage_width = 48f / orgWidth;
        float btnPlaylistNewPercentage_height = 25.6f / orgHeight;

        // Button btnPlaylistEdit;
        float btnPlaylistsEditPercentage_width = 36.8f / orgWidth;
        float btnPlaylistsEditPercentage_height = 25.6f / orgHeight;

        // Button btnPlaylistDel;
        float btnPlaylistsDelPercentage_width = 50.4f / orgWidth;
        float btnPlaylistsDelPercentage_height = 25.6f / orgHeight;

        // Button btnSongInPlaylistUp;
        float btnSongInPlaylistUpPercentage_width = 22.4f / orgWidth;
        float btnSongInPlaylistUpPercentage_height = 25.6f / orgHeight;

        // Button btnSongInPlaylistDown;
        float btnSongInPlaylistDownPercentage_width = 22.4f / orgWidth;
        float btnSongInPlaylistDownPercentage_height = 25.6f / orgHeight;

        // Button btnSongInPlaylistDel;
        float btnSongInPlaylistDelPercentage_width = 50.4f / orgWidth;
        float btnSongInPlaylistDelPercentage_height = 25.6f / orgHeight;

        // Button btnSongsNew;
        float btnSongsNewPercentage_width = 48f / orgWidth;
        float btnSongsNewPercentage_height = 25.6f / orgHeight;

        // Button btnSongsEdit;
        float btnSongsEditPercentage_width = 36.8f / orgWidth;
        float btnSongsEditPercentage_height = 25.6f / orgHeight;

        // Button btnSongsDel;
        float btnSongsDelPercentage_width = 50.4f / orgWidth;
        float btnSongsDelPercentage_height = 25.6f / orgHeight;

        // Button btnClose;
        float btnClosePercentage_width = 44.8f / orgWidth;
        float btnClosePercentage_height = 25.6f / orgHeight;

        // Slider sliderVol;
        float sliderVolPercentage_width = 150f / orgWidth;
        float sliderVolPercentage_height = 18f / orgHeight;

        // Label lblCurrentSong;
        float lblCurrentSongPercentage_width = 324f / orgWidth;
        float lblCurrentSongPercentage_height = 30f / orgHeight;

        // TextField txtFilter;
        float txtFilterPercentage_width = 149.6f / orgWidth;
        float txtFilterPercentage_height = 25.6f / orgHeight;

        // Label lblSoundPicture
        // no need to move right
        float lblSoundPicturePercentage_width = 28f / orgWidth;
        float lblSoundPicturePercentage_height = 18f / orgHeight;

        // Label lblPlaylistTableviewTitle
        // no need to move right
        float lblPlaylistTableviewTitlePercentage_width = 42.4f / orgWidth;
        float lblPlaylistTableviewTitlePercentage_height = 17.6f / orgHeight;

    }
}

