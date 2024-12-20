package com.gruppe5.MyTunes.GUI;

import com.gruppe5.MyTunes.BLL.MyTunesLogic;
import com.gruppe5.MyTunes.GUI.Controller.MyTunesController;
import com.gruppe5.MyTunes.GUI.Model.MyTunesModel;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/com/gruppe5/MyTunes/MyTunes.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 880, 620);
        stage.setTitle("SoundSurf");
        stage.setScene(scene);
        stage.getIcons().add(new Image(String.valueOf(Main.class.getResource("/logo_ting.png"))));
        stage.show();

        MyTunesController controller = fxmlLoader.getController();
        MyTunesModel myTunesModel = new MyTunesModel(controller);
        controller.initialize(myTunesModel);

        controller.init();
        stage.widthProperty().addListener((observable, oldValue, newValue) -> {controller.resizeItems(newValue.doubleValue(), stage.getHeight());});
        stage.heightProperty().addListener((observable, oldValue, newValue) -> {controller.resizeItems(stage.getWidth(), newValue.doubleValue());});

    }

    public static void main(String[] args) {
        launch();
    }
}