package org.nuberjonas.pompalette.application.javafx_application.controls;

import atlantafx.base.theme.PrimerLight;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import org.nuberjonas.pompalette.application.javafx_application.gui.dependencygraph.view.DependencyGraphViewController;
import org.nuberjonas.pompalette.application.javafx_application.gui.loadproject.view.LoadProjectViewController;
import org.nuberjonas.pompalette.application.javafx_application.gui.main.view.MainViewController;
import org.nuberjonas.pompalette.application.javafx_application.gui.projectgraph.view.ProjectGraphViewController;

import java.io.IOException;

public class ViewHandler {

    public enum Views{
        MAIN("MainView.fxml"),
        LOAD_PROJECT("LoadProjectView.fxml"),
        PROJECT_GRAPH("ProjectGraphView.fxml"),
        DEPENDENCY_GRAPH("DependencyGraphView.fxml");

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

        mainViewController.init(getLoadProjectViewControls(), getDependencyGraphViewControls());
        addProjectGraphViewToMainContentPane(mainViewController.getMainContent());

        var scene = new Scene((Parent) root, 1080, 720);
        scene.getStylesheets().addAll("smartgraph.css");
        Application.setUserAgentStylesheet(new PrimerLight().getUserAgentStylesheet());
        primaryStage.setScene(scene);
        primaryStage.setTitle("PomPalette");
        primaryStage.setMinWidth(1080);
        primaryStage.setMinHeight(720);
        primaryStage.show();
    }

    private Parent getLoadProjectViewControls() throws IOException {
        var loader = getFXMLLoaderFor(Views.LOAD_PROJECT);
        Parent loadProjectView = loader.load();
        LoadProjectViewController controller = loader.getController();
        controller.init(viewModelFactory.getLoadProjectViewModel(), primaryStage);

        return loadProjectView;
    }

    private Parent getDependencyGraphViewControls() throws IOException {
        var loader = getFXMLLoaderFor(Views.DEPENDENCY_GRAPH);
        Parent dependecyGraphView = loader.load();
        DependencyGraphViewController controller = loader.getController();
        controller.init(viewModelFactory.getDependencyGraphViewModel());

        return dependecyGraphView;
    }

    public void addProjectGraphViewToMainContentPane(StackPane contentPane) throws IOException {
        var loader = getFXMLLoaderFor(Views.PROJECT_GRAPH);
        Parent projectGraphView = loader.load();
        ProjectGraphViewController controller = loader.getController();
        controller.init(viewModelFactory.getProjectGraphViewModel());

        contentPane.layout();
        contentPane.getChildren().add(projectGraphView);
    }

    private FXMLLoader getFXMLLoaderFor(Views view){
        return new FXMLLoader(getClass().getClassLoader().getResource(view.path));
    }
}
