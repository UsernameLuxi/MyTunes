package com.gruppe5.MyTunes.DAL;

import com.gruppe5.MyTunes.BE.Playlist;
import com.gruppe5.MyTunes.BE.Song;
import javafx.scene.control.Control;

import java.util.List;

public class MOCK_DB implements IDataAccess {

    @Override
    public List<Song> getAllSongs() {
        return List.of();
    }

    @Override
    public Song getSong(int id) {
        return null;
    }

    @Override
    public List<Song> getSongByName(String name) {
        return List.of();
    }

    @Override
    public List<Song> getSongByArtist(String artist) {
        return List.of();
    }

    @Override
    public List<Song> getSongByArtist(int artistID) {
        return List.of();
    }

    @Override
    public Song addSong(Song song) {
        return null;
    }

    @Override
    public Song updateSong(Song song) {
        return null;
    }

    @Override
    public void deleteSong(Song song) {

    }

    @Override
    public List<Playlist> getAllPlaylists() {
        return List.of();
    }

    @Override
    public Playlist getPlaylist(int id) {
        return null;
    }

    @Override
    public Playlist getPlaylist(String name) {
        return null;
    }

    @Override
    public Playlist addPlaylist(Playlist playlist) {
        return null;
    }

    @Override
    public Playlist updatePlaylist(Playlist playlist) {
        return null;
    }

    @Override
    public void deletePlaylist(Playlist playlist) {

    }

    @Override
    public List<String> getGenres() throws Exception {
        return List.of();
    }



    // trash can
    /*

    /**
     * resizes the items in the window relative to the original relations
     */
    /*
    public void resizeItems(double width, double height){

        width -= 15;
        height -= 15;
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
        // TODO : move up
        // TODO : resize Y
        double tblSongsPercentage_width = 418.0d / orgWidth;
        double tblSongsPercentage_height = 426.0d / orgHeight;
        //double tblSongsSpacing = calculateSpaceing(tblSongs, btnTransferSongs);
        double tblSongsSpacing = calculateSpaceing(tblSongs, btnTransferSongs);

        // ListView<Song> lstSongsInPlaylist;
        // TODO : move up
        // TODO : resize Y
        double lstSongsInPlaylistPercentage_width = 140.0d / orgWidth;
        double lstSongsInPlaylistPercentage_height = 430.0d / orgHeight;
        double lstSongsInPlaylistSpacing = calculateSpaceing(lstSongsInPlaylist, tblPlaylists);

        // Button btnBack;
        // TODO : move up
        // TODO : resize Y
        double btnBackPercentage_width = 26.4d / orgWidth;
        double btnBackPercentage_height = 25.6d / orgHeight;
        double btnBackSpacing = calculateSpaceing(btnBack, lblSoundPicture);

        // Button btnPlay;
        // TODO : move up
        // TODO : resize Y
        double btnPlayPercentage_width = 37.0d / orgWidth;
        double btnPlayPercentage_height = 30.0d / orgHeight;
        double btnPlaySpacing = calculateSpaceing(btnPlay, btnBack);

        // Button btnSkip;
        // TODO : move up
        // TODO : resize Y
        double btnSkipPercentage_width = 26.4d / orgWidth;
        double btnSkipPercentage_height = 25.6d / orgHeight;
        double btnSkipSpacing = calculateSpaceing(btnSkip, btnPlay);

        // Button btnFilter;
        // TODO : move up
        // TODO : resize Y
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
        // TODO : move up
        // TODO : resize Y
        double btnPlaylistsEditPercentage_width = 36.8d / orgWidth;
        double btnPlaylistsEditPercentage_height = 25.6d / orgHeight;
        double btnPlaylistEditSpacing = calculateSpaceing(btnPlaylistEdit, btnPlaylistNew);

        // Button btnPlaylistDel;
        // TODO : move up
        // TODO : resize Y
        double btnPlaylistsDelPercentage_width = 50.4d / orgWidth;
        double btnPlaylistsDelPercentage_height = 25.6d / orgHeight;
        double btnPlaylistDelSpacing = calculateSpaceing(btnPlaylistDel, btnPlaylistEdit);

        // Button btnSongInPlaylistUp;
        // TODO : move up
        // TODO : resize Y
        double btnSongInPlaylistUpPercentage_width = 22.4d / orgWidth;
        double btnSongInPlaylistUpPercentage_height = 25.6d / orgHeight;
        double btnSongInPlaylistUpSpacing = calculateSpaceing(btnSongInPlaylistUp, tblPlaylists);

        // Button btnSongInPlaylistDown;
        // TODO : move up
        // TODO : resize Y
        double btnSongInPlaylistDownPercentage_width = 22.4d / orgWidth;
        double btnSongInPlaylistDownPercentage_height = 25.6d / orgHeight;
        double btnSongInPlaylistDownSpacing = calculateSpaceing(btnSongInPlaylistDown, btnSongInPlaylistUp);

        // Button btnSongInPlaylistDel;
        // TODO : move up
        // TODO : resize Y
        double btnSongInPlaylistDelPercentage_width = 50.4d / orgWidth;
        double btnSongInPlaylistDelPercentage_height = 25.6d / orgHeight;
        double btnSongInPlaylistDelSpacing = calculateSpaceing(btnSongInPlaylistDel, btnSongInPlaylistDown);

        // Button btnSongsNew;
        // TODO : move up
        // TODO : resize Y
        double btnSongsNewPercentage_width = 48d / orgWidth;
        double btnSongsNewPercentage_height = 25.6d / orgHeight;
        double btnSongsNewSpacing = calculateSpaceing(btnSongsNew, btnSongInPlaylistDel);

        // Button btnSongsEdit;
        // TODO : move up
        // TODO : resize Y
        double btnSongsEditPercentage_width = 36.8d / orgWidth;
        double btnSongsEditPercentage_height = 25.6d / orgHeight;
        double btnSongsEditSpacing = calculateSpaceing(btnSongsEdit, btnSongsNew);

        // Button btnSongsDel;
        // TODO : move up
        // TODO : resize Y
        double btnSongsDelPercentage_width = 50.4d / orgWidth;
        double btnSongsDelPercentage_height = 25.6d / orgHeight;
        double btnSongsDelSpacing = calculateSpaceing(btnSongsDel, btnSongsEdit);

        // Button btnClose;
        // TODO : move up
        // TODO : resize Y
        double btnClosePercentage_width = 44.8d / orgWidth;
        double btnClosePercentage_height = 25.6d / orgHeight;
        double btnCloseSpacing = calculateSpaceing(btnClose, btnSongsDel);

        // Slider sliderVol;
        // TODO : move up
        // TODO : resize Y
        double sliderVolPercentage_width = 150d / orgWidth;
        double sliderVolPercentage_height = 18d / orgHeight;
        double sliderVolSpacing = calculateSpaceing(sliderVol, lblSoundPicture);

        // Label lblCurrentSong;
        // TODO : move up
        // TODO : resize Y
        double lblCurrentSongPercentage_width = 324d / orgWidth;
        double lblCurrentSongPercentage_height = 30d / orgHeight;
        double lblCurrentSongSpacing = calculateSpaceing(lblCurrentSong, btnSkip);

        // TextField txtFilter;
        // TODO : move up
        // TODO : resize Y
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
        // TODO : move up
        // TODO : resize Y
        double lblFilterPercentage_width = 28.8d / orgWidth;
        double lblFilterPercentage_height = 17.6d / orgHeight;
        double lblFilterSpacing = calculateSpaceing(lblFilter, lblCurrentSong);

        // Label lblSongsTableviewTitle
        // TODO : move up
        // TODO : resize Y
        double lblSongsTableviewTitlePercentage_width = 32.8d / orgWidth;
        double lblSongsTableviewTitlePercentage_height = 17.6d / orgHeight;
        double lblSongsTableviewTitleSpacing = calculateSpaceing(lblSongsTableviewTitle, btnTransferSongs);

        // Label sliderVolLabel
        // TODO : move up
        // TODO : resize Y
        double sliderVolLabelPercentage_width = 32.8d / orgWidth;
        double sliderVolLabelPercentage_height = 17.6d / orgHeight;
        double sliderVolLabelSpacing = calculateSpaceing(sliderVolLabel, sliderVol);

        // Label lblPlaylistViewTitle
        // no need to move right
        // TODO : move up
        // TODO : resize Y
        double lblPlaylistViewTitlePercentage_width = 85.6f / orgWidth;
        double lblPlaylistViewTitlePercentage_height = 17.6f / orgHeight;
        double lblPlaylistViewTitleSpacing = calculateSpaceing(lblPlaylistViewTitle, tblPlaylists);


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
        scaleX(lblSongsTableviewTitlePercentage_width, width, lblSongsTableviewTitleSpacing, lblSongsTableviewTitle, btnTransferSongs);
        scaleX(tblSongsPercentage_width, width, tblSongsSpacing, tblSongs, btnTransferSongs);
        scaleX(btnSongsNewPercentage_width, width, btnSongsNewSpacing, btnSongsNew, btnSongInPlaylistDel);
        scaleX(btnSongsEditPercentage_width, width, btnSongsEditSpacing, btnSongsEdit, btnSongsNew);
        scaleX(btnSongsDelPercentage_width, width, btnSongsDelSpacing, btnSongsDel, btnSongsEdit);
        scaleX(btnClosePercentage_width, width, btnCloseSpacing, btnClose, btnSongsDel);
    }
    */
/*
    private void scaleX(double percentage_width, double window_width, double spacing, Control object, Control relative) {
        setWidth(object, percentage_width, window_width);
        // formel: X(traget) = width(relative) + X(relative) + spacing
        // -> kommer af Spaceing = X(target) - (Width(Rekative) + X(relative))
        double x = (relative.getWidth() + relative.getLayoutX()) + spacing;
        System.out.println(spacing);
        object.setLayoutX(x);
    }
*/
    /*
    private void setWidth(Control object, double percentageWidth, double windowWidth){
        object.setPrefWidth(windowWidth * percentageWidth);
    }
/*
    private double calculateSpaceing(Control target, Control relative){
        return Math.abs(target.getLayoutX() - (relative.getWidth() + relative.getLayoutX()));
    }
     */
}
