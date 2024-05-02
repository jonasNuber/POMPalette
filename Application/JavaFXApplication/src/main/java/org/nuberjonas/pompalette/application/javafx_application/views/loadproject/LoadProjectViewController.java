package org.nuberjonas.pompalette.application.javafx_application.views.loadproject;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.util.Optional;

public class LoadProjectViewController {

    @FXML
    private TextField projectPathTextfield;
    @FXML
    private Button loadButton;

    private Stage primaryStage;
    private String projectPath;

    @FXML
    public void onLoadButtonPress(ActionEvent event){
        projectPath = projectPathTextfield.getText();
    }

    @FXML
    public void openFileChooser(ActionEvent event){
        var fileChooser = new FileChooser();
        fileChooser.setTitle("Select Maven Project Pom");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Maven Projects (pom.xml)","pom.xml"));

        Optional.ofNullable(fileChooser.showOpenDialog(primaryStage))
                .ifPresentOrElse(f -> projectPathTextfield.setText(f.getAbsolutePath()), () -> projectPathTextfield.setText(""));
    }

    public void setPrimaryStage(Stage primaryStage){
        this.primaryStage = primaryStage;
    }
}
