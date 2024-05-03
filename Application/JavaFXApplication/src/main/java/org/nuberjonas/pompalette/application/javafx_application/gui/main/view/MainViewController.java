package org.nuberjonas.pompalette.application.javafx_application.gui.main.view;

import javafx.fxml.FXML;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;

import java.io.IOException;

public class MainViewController {

    @FXML
    private SplitPane splitableScene;
    @FXML
    private StackPane mainContent;
    @FXML
    private GridPane controls;

    public void init() throws IOException {
        setupDividerConstraints();
    }

    private void setupDividerConstraints(){
        splitableScene.getDividers().getFirst().setPosition(0.8);

        splitableScene.getDividers().getFirst().positionProperty().addListener((obs, oldVal, newVal) -> {
            double minPosition = 0.8;
            double maxPosition = 1;
            if (newVal.doubleValue() < minPosition) {
                splitableScene.getDividers().getFirst().setPosition(minPosition);
            } else if (newVal.doubleValue() > maxPosition) {
                splitableScene.getDividers().getFirst().setPosition(maxPosition);
            }
        });
    }

    public GridPane getControls(){
        return controls;
    }

    public StackPane getMainContent(){
        return mainContent;
    }
}
