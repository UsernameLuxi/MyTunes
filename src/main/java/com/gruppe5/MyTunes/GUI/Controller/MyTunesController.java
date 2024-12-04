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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


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

        /*
        anchorPane.setOnDragOver(event -> {
            if (event.getGestureSource() != anchorPane && event.getDragboard().hasFiles()) {
                event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
            }
            event.consume();
        });
        */
        /*

        anchorPane.setOnDragDropped(event -> {
            AddSongsPopUpController controller = onNewSongButtonClick();
            String filePath = event.getDragboard().getFiles().getFirst().toString();
            controller.setUriText(filePath);
            myTunesModel.setDurationOfFile(filePath, controller);
            event.setDropCompleted(true);
            event.consume();
        });

         */

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


    Map<Control, List<Double>> map; // lstDouble percentage - width - height - x - y
    void inti(){
        map = new HashMap<Control, List<Double>>();
        int orgWidth = 880;
        int orgHeight = 620;

        map.put(tblPlaylists, new ArrayList<>(){{add(tblPlaylists.getWidth() / orgWidth);add(tblPlaylists.getHeight()/orgHeight);add(tblPlaylists.getLayoutX()/orgWidth);add(tblPlaylists.getLayoutY()/orgHeight);}});
        map.put(btnPlaylistNew, new ArrayList<>(){{add(btnPlaylistNew.getWidth() / orgWidth);add(btnPlaylistNew.getHeight()/orgHeight);add(btnPlaylistNew.getLayoutX()/orgWidth);add(btnPlaylistNew.getLayoutY()/orgHeight);}});
        map.put(lblSoundPicture, new ArrayList<>(){{add(lblSoundPicture.getWidth() / orgWidth);add(lblSoundPicture.getHeight()/orgHeight);add(lblSoundPicture.getLayoutX()/orgWidth);add(lblSoundPicture.getLayoutY()/orgHeight);}});
        map.put(lblPlaylistTableviewTitle, new ArrayList<>(){{add(lblPlaylistTableviewTitle.getWidth() / orgWidth);add(lblPlaylistTableviewTitle.getHeight()/orgHeight);add(lblPlaylistTableviewTitle.getLayoutX()/orgWidth);add(lblPlaylistTableviewTitle.getLayoutY()/orgHeight);}});



        map.put(btnBack, new ArrayList<>(){{add(btnBack.getWidth() / orgWidth);add(btnBack.getHeight()/orgHeight);add(btnBack.getLayoutX()/orgWidth);add(btnBack.getLayoutY()/orgHeight);}});
        map.put(btnPlay, new ArrayList<>(){{add(btnPlay.getWidth() / orgWidth);add(btnPlay.getHeight()/orgHeight);add(btnPlay.getLayoutX()/orgWidth);add(btnPlay.getLayoutY()/orgHeight);}});
        map.put(btnSkip, new ArrayList<>(){{add(btnSkip.getWidth() / orgWidth);add(btnSkip.getHeight()/orgHeight);add(btnSkip.getLayoutX()/orgWidth);add(btnSkip.getLayoutY()/orgHeight);}});
        map.put(sliderVol, new ArrayList<>(){{add(sliderVol.getWidth() / orgWidth);add(sliderVol.getHeight()/orgHeight);add(sliderVol.getLayoutX()/orgWidth);add(sliderVol.getLayoutY()/orgHeight);}});
        map.put(sliderVolLabel, new ArrayList<>(){{add(sliderVolLabel.getWidth() / orgWidth);add(sliderVolLabel.getHeight()/orgHeight);add(sliderVolLabel.getLayoutX()/orgWidth);add(sliderVolLabel.getLayoutY()/orgHeight);}});
        map.put(lblCurrentSong, new ArrayList<>(){{add(lblCurrentSong.getWidth() / orgWidth);add(lblCurrentSong.getHeight()/orgHeight);add(lblCurrentSong.getLayoutX()/orgWidth);add(lblCurrentSong.getLayoutY()/orgHeight);}});
        map.put(lblFilter, new ArrayList<>(){{add(lblFilter.getWidth() / orgWidth);add(lblFilter.getHeight()/orgHeight);add(lblFilter.getLayoutX()/orgWidth);add(lblFilter.getLayoutY()/orgHeight);}});
        map.put(txtFilter, new ArrayList<>(){{add(txtFilter.getWidth() / orgWidth);add(txtFilter.getHeight()/orgHeight);add(txtFilter.getLayoutX()/orgWidth);add(txtFilter.getLayoutY()/orgHeight);}});
        map.put(btnFilter, new ArrayList<>(){{add(btnFilter.getWidth() / orgWidth);add(btnFilter.getHeight()/orgHeight);add(btnFilter.getLayoutX()/orgWidth);add(btnFilter.getLayoutY()/orgHeight);}});
        map.put(btnPlaylistEdit, new ArrayList<>(){{add(btnPlaylistEdit.getWidth() / orgWidth);add(btnPlaylistEdit.getHeight()/orgHeight);add(btnPlaylistEdit.getLayoutX()/orgWidth);add(btnPlaylistEdit.getLayoutY()/orgHeight);}});
        map.put(btnPlaylistDel, new ArrayList<>(){{add(btnPlaylistDel.getWidth() / orgWidth);add(btnPlaylistDel.getHeight()/orgHeight);add(btnPlaylistDel.getLayoutX()/orgWidth);add(btnPlaylistDel.getLayoutY()/orgHeight);}});
        map.put(lstSongsInPlaylist, new ArrayList<>(){{add(lstSongsInPlaylist.getWidth() / orgWidth);add(lstSongsInPlaylist.getHeight()/orgHeight);add(lstSongsInPlaylist.getLayoutX()/orgWidth);add(lstSongsInPlaylist.getLayoutY()/orgHeight);}});
        map.put(lblPlaylistViewTitle, new ArrayList<>(){{add(lblPlaylistViewTitle.getWidth() / orgWidth);add(lblPlaylistViewTitle.getHeight()/orgHeight);add(lblPlaylistViewTitle.getLayoutX()/orgWidth);add(lblPlaylistViewTitle.getLayoutY()/orgHeight);}});
        map.put(btnSongInPlaylistUp, new ArrayList<>(){{add(btnSongInPlaylistUp.getWidth() / orgWidth);add(btnSongInPlaylistUp.getHeight()/orgHeight);add(btnSongInPlaylistUp.getLayoutX()/orgWidth);add(btnSongInPlaylistUp.getLayoutY()/orgHeight);}});
        map.put(btnSongInPlaylistDown, new ArrayList<>(){{add(btnSongInPlaylistDown.getWidth() / orgWidth);add(btnSongInPlaylistDown.getHeight()/orgHeight);add(btnSongInPlaylistDown.getLayoutX()/orgWidth);add(btnSongInPlaylistDown.getLayoutY()/orgHeight);}});
        map.put(btnSongInPlaylistDel, new ArrayList<>(){{add(btnSongInPlaylistDel.getWidth() / orgWidth);add(btnSongInPlaylistDel.getHeight()/orgHeight);add(btnSongInPlaylistDel.getLayoutX()/orgWidth);add(btnSongInPlaylistDel.getLayoutY()/orgHeight);}});
        map.put(btnTransferSongs, new ArrayList<>(){{add(btnTransferSongs.getWidth() / orgWidth);add(btnTransferSongs.getHeight()/orgHeight);add(btnTransferSongs.getLayoutX()/orgWidth);add(btnTransferSongs.getLayoutY()/orgHeight);}});
        map.put(lblSongsTableviewTitle, new ArrayList<>(){{add(lblSongsTableviewTitle.getWidth() / orgWidth);add(lblSongsTableviewTitle.getHeight()/orgHeight);add(lblSongsTableviewTitle.getLayoutX()/orgWidth);add(lblSongsTableviewTitle.getLayoutY()/orgHeight);}});
        map.put(tblSongs, new ArrayList<>(){{add(tblSongs.getWidth() / orgWidth);add(tblSongs.getHeight()/orgHeight);add(tblSongs.getLayoutX()/orgWidth);add(tblSongs.getLayoutY()/orgHeight);}});
        map.put(btnSongsNew, new ArrayList<>(){{add(btnSongsNew.getWidth() / orgWidth);add(btnSongsNew.getHeight()/orgHeight);add(btnSongsNew.getLayoutX()/orgWidth);add(btnSongsNew.getLayoutY()/orgHeight);}});
        map.put(btnSongsEdit, new ArrayList<>(){{add(btnSongsEdit.getWidth() / orgWidth);add(btnSongsEdit.getHeight()/orgHeight);add(btnSongsEdit.getLayoutX()/orgWidth);add(btnSongsEdit.getLayoutY()/orgHeight);}});
        map.put(btnSongsDel, new ArrayList<>(){{add(btnSongsDel.getWidth() / orgWidth);add(btnSongsDel.getHeight()/orgHeight);add(btnSongsDel.getLayoutX()/orgWidth);add(btnSongsDel.getLayoutY()/orgHeight);}});
        map.put(btnClose, new ArrayList<>(){{add(btnClose.getWidth() / orgWidth);add(btnClose.getHeight()/orgHeight);add(btnClose.getLayoutX()/orgWidth);add(btnClose.getLayoutY()/orgHeight);}});

        /*
        double tblPlaylistPercentage_width = 227.0d / orgWidth;
        double tblPlaylistPercentage_height = 450.0d / orgHeight;
        double tblPlaylistLeft = 12d / orgWidth;
        List<Double> list = new ArrayList<Double>(){{add(tblPlaylistPercentage_width);add(tblPlaylistPercentage_height);add(tblPlaylistLeft);}};
        map.put(tblPlaylists, list);

        double tblSongsPercentage_width = 417.6d / orgWidth;
        double tblSongsPercentage_height = 426.4d / orgHeight;
        double tblSongsLeft = tblSongs.getLayoutX() - (btnTransferSongs.getLayoutX() + btnTransferSongs.getWidth());
        list = new ArrayList<>();
        double tblSongsSpacing = calculateSpaceing(tblSongs, btnTransferSongs);
        */


    }

}

