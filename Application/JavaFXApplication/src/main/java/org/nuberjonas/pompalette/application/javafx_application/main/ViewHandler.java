package org.nuberjonas.pompalette.application.javafx_application.main;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import org.kordamp.bootstrapfx.BootstrapFX;
import org.nuberjonas.pompalette.application.javafx_application.loadproject.view.LoadProjectViewController;
import org.nuberjonas.pompalette.application.javafx_application.main.view.MainViewController;
import org.nuberjonas.pompalette.application.javafx_application.projectgraph.view.ProjectGraphViewController;

import java.io.IOException;

public class ViewHandler {

    public enum Views{
        MAIN("MainView.fxml"),
        LOAD_PROJECT("LoadProjectView.fxml"),
        PROJECT_GRAPH("ProjectGraphView.fxml");

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

    public void start() throws IOException {
        var mainLoader = getFXMLLoaderFor(Views.MAIN);
        var root = mainLoader.load();

        MainViewController mainViewController = mainLoader.getController();
        mainViewController.init(this);

        var scene = new Scene((Parent) root, 1080, 720);
        scene.getStylesheets().addAll(BootstrapFX.bootstrapFXStylesheet(), "smartgraph.css ");
        primaryStage.setScene(scene);
        primaryStage.setTitle("PomPalette");
        primaryStage.setMinWidth(1080);
        primaryStage.setMinHeight(720);
        primaryStage.show();
    }

    public void addLoadProjectViewToControls(GridPane pane) throws IOException {
        var loader = getFXMLLoaderFor(Views.LOAD_PROJECT);
        Parent loadProjectView = loader.load();
        LoadProjectViewController loadProjectViewController = loader.getController();
        loadProjectViewController.init(viewModelFactory.getLoadProjectViewModel(), primaryStage);

        pane.add(loadProjectView, 0,0);
    }

    public void addProjectGraphViewToContentPane(StackPane contentPane) throws IOException {
        var loader = getFXMLLoaderFor(Views.PROJECT_GRAPH);
        Parent projectGraphView = loader.load();
        ProjectGraphViewController projectGraphViewController = loader.getController();
        projectGraphViewController.init();

        contentPane.layout();
        contentPane.getChildren().add(projectGraphView);
        projectGraphViewController.initGraph();
    }

    private FXMLLoader getFXMLLoaderFor(Views view){
        return new FXMLLoader(getClass().getClassLoader().getResource(view.path));
    }
}
