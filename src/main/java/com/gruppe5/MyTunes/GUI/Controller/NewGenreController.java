package com.gruppe5.MyTunes.GUI.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class NewGenreController {
    private AddSongsPopUpController parent;

    @FXML
    private TextField genreInput;

    public void setParent(AddSongsPopUpController parent){
        this.parent = parent;
    }

    @FXML
    private void onAdd(ActionEvent actionEvent) {
        if (genreInput.getText().trim().isEmpty()) {
            displayError(new Exception("Genre Name cannot be empty"));
        }
        else{
            parent.addGenre(genreInput.getText());
            onCancel(actionEvent);
        }
    }

    @FXML
    private void onCancel(ActionEvent actionEvent) {
        Stage stage = (Stage) genreInput.getScene().getWindow();
        stage.close();
    }

    private void displayError(Throwable t)
    {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Something went wrong");
        alert.setHeaderText(t.getMessage());
        alert.showAndWait();
    }
}
