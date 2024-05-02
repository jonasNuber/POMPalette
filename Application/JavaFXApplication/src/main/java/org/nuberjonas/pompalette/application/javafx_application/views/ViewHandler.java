package org.nuberjonas.pompalette.application.javafx_application.views;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.kordamp.bootstrapfx.BootstrapFX;
import org.nuberjonas.pompalette.application.javafx_application.viewmodels.ViewModelFactory;
import org.nuberjonas.pompalette.application.javafx_application.views.loadproject.LoadProjectViewController;
import org.nuberjonas.pompalette.application.javafx_application.views.main.MainViewController;

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
        var mainLoader = getFXMLLoaderFor(Views.MAIN);
        var loadProjectViewLoader = getFXMLLoaderFor(Views.LOAD_PROJECT);

        Parent root = null;

        try {
            root = mainLoader.load();
            MainViewController mainViewController = mainLoader.getController();
            mainViewController.addControlsView(loadProjectViewLoader.load());

            LoadProjectViewController loadProjectViewController = loadProjectViewLoader.getController();
            loadProjectViewController.setPrimaryStage(primaryStage);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        var scene = new Scene(root);
        scene.getStylesheets().add(BootstrapFX.bootstrapFXStylesheet());
        primaryStage.setScene(scene);
        primaryStage.setTitle("PomPalette");
        primaryStage.setMinWidth(1080);
        primaryStage.setMinHeight(720);
        primaryStage.show();
    }

    private FXMLLoader getFXMLLoaderFor(Views view){
        return new FXMLLoader(getClass().getClassLoader().getResource(view.path));
    }
}
