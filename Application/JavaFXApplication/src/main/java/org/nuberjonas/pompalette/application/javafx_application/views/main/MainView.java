package org.nuberjonas.pompalette.application.javafx_application.views.main;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainView implements Initializable {

    @FXML
    private BorderPane content;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getClassLoader().getResource("LoadProjectView.fxml"));
        } catch (IOException e) {

        }
        //content.setCenter(root);
    }
}
