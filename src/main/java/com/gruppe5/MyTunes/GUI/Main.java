package com.gruppe5.MyTunes.GUI;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/com/gruppe5/MyTunes/Main-window.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 880, 620);
        stage.setTitle("SoundSurf");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}