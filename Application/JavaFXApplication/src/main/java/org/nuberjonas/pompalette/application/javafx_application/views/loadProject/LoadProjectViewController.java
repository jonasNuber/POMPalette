package org.nuberjonas.pompalette.application.javafx_application.views.loadProject;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class LoadProjectViewController {

    @FXML
    private TextField projectPathTextfield;
    @FXML
    private Button loadButton;

    private String projectPath;

    @FXML
    public void onLoadButtonPress(ActionEvent event){
        projectPath = projectPathTextfield.getText();
    }
}
