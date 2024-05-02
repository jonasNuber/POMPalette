package org.nuberjonas.pompalette.application.javafx_application.views;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.nuberjonas.pompalette.application.javafx_application.viewmodels.ViewModelFactory;

import java.io.IOException;

public class ViewHandler {

    public enum Views{
        MAIN("MainView.fxml"),
        LOAD_PROJECT("LoadProjectView.fxml");

        private final String path;

        Views(String path) {
            this.path = path;
        }

        public String path(){
            return path;
        }
    }

    private Stage primaryStage;
    private ViewModelFactory viewModelFactory;

    public ViewHandler(Stage primaryStage, ViewModelFactory viewModelFactory) {
        this.primaryStage = primaryStage;
        this.viewModelFactory = viewModelFactory;
    }

    public void start(){
        Parent root = loadView(Views.MAIN);
        primaryStage.setScene(new Scene(root));
        primaryStage.setTitle("PomPalette");
        primaryStage.setMinWidth(1080);
        primaryStage.setMinHeight(720);
        primaryStage.show();
    }

    private Parent loadView(Views view){
        try {
            return FXMLLoader.load(getClass().getClassLoader().getResource(view.path));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
