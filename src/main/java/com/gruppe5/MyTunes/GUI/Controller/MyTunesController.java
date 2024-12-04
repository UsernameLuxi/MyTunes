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
    @FXML
    private Label lblFilter;
    @FXML
    private Label lblPlaylistTableviewTitle;
    @FXML
    private Label lblSongsTableviewTitle;
    @FXML
    private Label lblPlaylistViewTitle;

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
        /*
        *
        * BASIC INFORMATION
        *
        */
        // original width 880 , height 620
        int orgWidth = 880;
        int orgHeight = 620;
        //tblPlaylists
        // no need to move right
        // TODO : move up
        // TODO : resize Y
        double tblPlaylistPercentage_width = 227.0d / orgWidth;
        double tblPlaylistPercentage_height = 450.0d / orgHeight;

        // TableView<Song> tblSongs;
        // TODO : move right
        // TODO : move up
        // TODO : resize
        double tblSongsPercentage_width = 418.0d / orgWidth;
        double tblSongsPercentage_height = 426.0d / orgHeight;
        double tblSongsSpacing = calculateSpaceing(tblSongs, btnTransferSongs);

        // ListView<Song> lstSongsInPlaylist;
        // TODO : move right
        // TODO : move up
        // TODO : resize
        double lstSongsInPlaylistPercentage_width = 140.0d / orgWidth;
        double lstSongsInPlaylistPercentage_height = 430.0d / orgHeight;
        double lstSongsInPlaylistSpacing = calculateSpaceing(lstSongsInPlaylist, tblPlaylists);

        // Button btnBack;
        // TODO : move right
        // TODO : move up
        // TODO : resize
        double btnBackPercentage_width = 26.4d / orgWidth;
        double btnBackPercentage_height = 25.6d / orgHeight;
        double btnBackSpacing = calculateSpaceing(btnBack, lblSoundPicture);

        // Button btnPlay;
        // TODO : move right
        // TODO : move up
        // TODO : resize
        double btnPlayPercentage_width = 37.0d / orgWidth;
        double btnPlayPercentage_height = 30.0d / orgHeight;
        double btnPlaySpacing = calculateSpaceing(btnPlay, btnBack);

        // Button btnSkip;
        // TODO : move right
        // TODO : move up
        // TODO : resize
        double btnSkipPercentage_width = 26.4d / orgWidth;
        double btnSkipPercentage_height = 25.6d / orgHeight;
        double btnSkipSpacing = calculateSpaceing(btnSkip, btnPlay);

        // Button btnFilter;
        // TODO : move right
        // TODO : move up
        // TODO : resize
        double btnFilterPercentage_width = 28d / orgWidth;
        double btnFilterPercentage_height = 25.6d / orgHeight;
        double btnFilterSpacing = calculateSpaceing(btnFilter, txtFilter);

        // Button btnTransferSongs;
        double btnTransferSongsPercentage_width = 48.0d / orgWidth;
        double btnTransferSongsPercentage_height = 26.0d / orgHeight;
        double spacing = calculateSpaceing(btnTransferSongs, lstSongsInPlaylist);

        // Button btnPlaylistNew;
        // no need to move right
        // TODO : move up
        // TODO : resize Y
        double btnPlaylistNewPercentage_width = 48d / orgWidth;
        double btnPlaylistNewPercentage_height = 25.6d / orgHeight;

        // Button btnPlaylistEdit;
        // TODO : move right
        // TODO : move up
        // TODO : resize
        double btnPlaylistsEditPercentage_width = 36.8d / orgWidth;
        double btnPlaylistsEditPercentage_height = 25.6d / orgHeight;
        double btnPlaylistEditSpacing = calculateSpaceing(btnPlaylistEdit, btnPlaylistNew);

        // Button btnPlaylistDel;
        // TODO : move right
        // TODO : move up
        // TODO : resize
        double btnPlaylistsDelPercentage_width = 50.4d / orgWidth;
        double btnPlaylistsDelPercentage_height = 25.6d / orgHeight;
        double btnPlaylistDelSpacing = calculateSpaceing(btnPlaylistDel, btnPlaylistEdit);

        // Button btnSongInPlaylistUp;
        // TODO : move right
        // TODO : move up
        // TODO : resize
        double btnSongInPlaylistUpPercentage_width = 22.4d / orgWidth;
        double btnSongInPlaylistUpPercentage_height = 25.6d / orgHeight;
        double btnSongInPlaylistUpSpacing = calculateSpaceing(btnSongInPlaylistUp, tblPlaylists);

        // Button btnSongInPlaylistDown;
        // TODO : move right
        // TODO : move up
        // TODO : resize
        double btnSongInPlaylistDownPercentage_width = 22.4d / orgWidth;
        double btnSongInPlaylistDownPercentage_height = 25.6d / orgHeight;
        double btnSongInPlaylistDownSpacing = calculateSpaceing(btnSongInPlaylistDown, btnSongInPlaylistUp);

        // Button btnSongInPlaylistDel;
        // TODO : move right
        // TODO : move up
        // TODO : resize
        double btnSongInPlaylistDelPercentage_width = 50.4d / orgWidth;
        double btnSongInPlaylistDelPercentage_height = 25.6d / orgHeight;
        double btnSongInPlaylistDelSpacing = calculateSpaceing(btnSongInPlaylistDel, btnSongInPlaylistDown);

        // Button btnSongsNew;
        // TODO : move right
        // TODO : move up
        // TODO : resize
        double btnSongsNewPercentage_width = 48d / orgWidth;
        double btnSongsNewPercentage_height = 25.6d / orgHeight;
        double btnSongsNewSpacing = calculateSpaceing(btnSongsNew, btnSongInPlaylistDel);

        // Button btnSongsEdit;
        // TODO : move right
        // TODO : move up
        // TODO : resize
        double btnSongsEditPercentage_width = 36.8d / orgWidth;
        double btnSongsEditPercentage_height = 25.6d / orgHeight;
        double btnSongsEditSpacing = calculateSpaceing(btnSongsEdit, btnSongsNew);

        // Button btnSongsDel;
        // TODO : move right
        // TODO : move up
        // TODO : resize
        double btnSongsDelPercentage_width = 50.4d / orgWidth;
        double btnSongsDelPercentage_height = 25.6d / orgHeight;
        double btnSongsDelSpacing = calculateSpaceing(btnSongsDel, btnSongsEdit);

        // Button btnClose;
        // TODO : move right
        // TODO : move up
        // TODO : resize
        double btnClosePercentage_width = 44.8d / orgWidth;
        double btnClosePercentage_height = 25.6d / orgHeight;
        double btnCloseSpacing = calculateSpaceing(btnClose, btnSongsDel);

        // Slider sliderVol;
        // TODO : move right
        // TODO : move up
        // TODO : resize
        double sliderVolPercentage_width = 150d / orgWidth;
        double sliderVolPercentage_height = 18d / orgHeight;
        double sliderVolSpacing = calculateSpaceing(sliderVol, lblSoundPicture);

        // Label lblCurrentSong;
        // TODO : move right
        // TODO : move up
        // TODO : resize
        double lblCurrentSongPercentage_width = 324d / orgWidth;
        double lblCurrentSongPercentage_height = 30d / orgHeight;
        double lblCurrentSongSpacing = calculateSpaceing(lblCurrentSong, btnSkip);

        // TextField txtFilter;
        // TODO : move right
        // TODO : move up
        // TODO : resize
        double txtFilterPercentage_width = 149.6d / orgWidth;
        double txtFilterPercentage_height = 25.6d / orgHeight;
        double txtFilterSpacing = calculateSpaceing(txtFilter, lblFilter);

        // Label lblSoundPicture
        // no need to move right
        // TODO : move up
        // TODO : resize Y
        double lblSoundPicturePercentage_width = 28d / orgWidth;
        double lblSoundPicturePercentage_height = 18d / orgHeight;

        // Label lblPlaylistTableviewTitle
        // no need to move right
        // TODO : move up
        // TODO : resize Y
        double lblPlaylistTableviewTitlePercentage_width = 42.4d / orgWidth;
        double lblPlaylistTableviewTitlePercentage_height = 17.6d / orgHeight;

        // Label lblFilter
        // TODO : move right
        // TODO : move up
        // TODO : resize
        double lblFilterPercentage_width = 28.8d / orgWidth;
        double lblFilterPercentage_height = 17.6d / orgHeight;
        double lblFilterSpacing = calculateSpaceing(lblFilter, lblCurrentSong);

        // Label lblSongsTableviewTitle
        // TODO : move right
        // TODO : move up
        // TODO : resize
        double lblSongsTableviewTitlePercentage_width = 32.8d / orgWidth;
        double lblSongsTableviewTitlePercentage_height = 17.6d / orgHeight;
        double lblSongsTableviewTitleSpacing = calculateSpaceing(lblSongsTableviewTitle, btnTransferSongs);

        // Label sliderVolLabel
        // TODO : move right
        // TODO : move up
        // TODO : resize
        double sliderVolLabelPercentage_width = 32.8d / orgWidth;
        double sliderVolLabelPercentage_height = 17.6d / orgHeight;
        double sliderVolLabelSpacing = calculateSpaceing(sliderVolLabel, sliderVol);

        // Label lblPlaylistViewTitle
        // no need to move right
        // TODO : MOVE RIGHT
        // TODO : move up
        // TODO : resize
        double lblPlaylistViewTitlePercentage_width = 85.6f / orgWidth;
        double lblPlaylistViewTitlePercentage_height = 17.6f / orgHeight;
        double lblPlaylistViewTitleSpacing = calculateSpaceing(lblPlaylistViewTitle, tblPlaylists);


        /*
        *
        * RESIZE EVERYTHING - MOVE RIGHT
        *
        */
        // resize things only relative til the left wall
        setWidth(tblPlaylists, tblPlaylistPercentage_width, width);
        setWidth(btnPlaylistNew, btnPlaylistNewPercentage_width, width);
        setWidth(lblSoundPicture, lblSoundPicturePercentage_width, width);
        setWidth(lblPlaylistTableviewTitle, lblPlaylistTableviewTitlePercentage_width, width);

        // resize and move things
        scaleX(btnBackPercentage_width, width, btnBackSpacing, btnBack, lblSoundPicture);
        scaleX(btnPlayPercentage_width, width, btnPlaySpacing, btnPlay, btnBack);
        scaleX(btnSkipPercentage_width, width, btnSkipSpacing, btnSkip, btnPlay);
        scaleX(sliderVolPercentage_width, width, sliderVolSpacing, sliderVol, lblSoundPicture);
        scaleX(sliderVolLabelPercentage_width, width, sliderVolLabelSpacing, sliderVolLabel, sliderVol);
        scaleX(lblCurrentSongPercentage_width, width, lblCurrentSongSpacing, lblCurrentSong, btnSkip);
        scaleX(lblFilterPercentage_width, width, lblFilterSpacing, lblFilter, lblCurrentSong);
        scaleX(txtFilterPercentage_width, width, txtFilterSpacing, txtFilter, lblFilter);
        scaleX(btnFilterPercentage_width, width, btnFilterSpacing, btnFilter, txtFilter);
        scaleX(btnPlaylistsEditPercentage_width, width, btnPlaylistEditSpacing, btnPlaylistEdit, btnPlaylistNew);
        scaleX(btnPlaylistsDelPercentage_width, width, btnPlaylistDelSpacing, btnPlaylistDel, btnPlaylistEdit);
        scaleX(lstSongsInPlaylistPercentage_width, width, lstSongsInPlaylistSpacing, lstSongsInPlaylist, tblPlaylists);
        scaleX(lblPlaylistViewTitlePercentage_width, width, lblPlaylistViewTitleSpacing, lblPlaylistViewTitle, tblPlaylists);
        scaleX(btnSongInPlaylistUpPercentage_width, width, btnSongInPlaylistUpSpacing, btnSongInPlaylistUp, tblPlaylists);
        scaleX(btnSongInPlaylistDownPercentage_width, width, btnSongInPlaylistDownSpacing, btnSongInPlaylistDown, btnSongInPlaylistUp);
        scaleX(btnSongInPlaylistDelPercentage_width, width, btnSongInPlaylistDelSpacing, btnSongInPlaylistDel, btnSongInPlaylistDown);
        scaleX(btnTransferSongsPercentage_width, width, spacing, btnTransferSongs, lstSongsInPlaylist);
        scaleX(lblSongsTableviewTitlePercentage_width, width, lblSongsTableviewTitleSpacing, lblPlaylistTableviewTitle, btnTransferSongs);
        scaleX(tblSongsPercentage_width, width, tblSongsSpacing, tblSongs, btnTransferSongs);
        scaleX(btnSongsNewPercentage_width, width, btnSongsNewSpacing, btnSongsNew, btnSongInPlaylistDel);
        scaleX(btnSongsEditPercentage_width, width, btnSongsEditSpacing, btnSongsEdit, btnSongsNew);
        scaleX(btnSongsDelPercentage_width, width, btnSongsDelSpacing, btnSongsDel, btnSongsEdit);
        scaleX(btnClosePercentage_width, width, btnCloseSpacing, btnClose, btnSongsDel);
    }

    private void scaleX(double percentage_width, double window_width, double spacing, Control object, Control relative) {
        setWidth(object, percentage_width, window_width);
        // formel: X(traget) = width(relative) + X(relative) + spacing
        // -> kommer af Spaceing = X(target) - (Width(Rekative) + X(relative))
        double x = (relative.getWidth() + relative.getLayoutX()) + spacing;
        object.setLayoutX(x);
    }

    private void setWidth(Control object, double percentageWidth, double windowWidth){
        object.setPrefWidth(windowWidth * percentageWidth);
    }

    private double calculateSpaceing(Control target, Control relative){
        return Math.abs(target.getLayoutX() - (relative.getWidth() + relative.getLayoutX()));
    }
}

