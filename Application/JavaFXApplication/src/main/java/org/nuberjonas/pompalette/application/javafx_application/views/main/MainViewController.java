package org.nuberjonas.pompalette.application.javafx_application.views.main;

import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.layout.GridPane;

public class MainViewController {

    @FXML
    private GridPane controls;

    public void addControlsView(Parent controlsView){
        controls.add(controlsView, 0,0);
    }
}
