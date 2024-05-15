package org.nuberjonas.pompalette.application.javafx_application.gui.main.view;

import atlantafx.base.theme.PrimerDark;
import atlantafx.base.theme.PrimerLight;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.StackPane;
import org.nuberjonas.pompalette.application.javafx_application.events.ShowControlsEvent;
import org.nuberjonas.pompalette.application.javafx_application.extensions.DraggablePane;
import org.nuberjonas.pompalette.application.javafx_application.extensions.NotificationPane;
import org.nuberjonas.pompalette.infrastructure.eventbus.EventBus;
import org.nuberjonas.pompalette.infrastructure.eventbus.Subscribable;
import org.nuberjonas.pompalette.infrastructure.eventbus.events.Event;
import org.nuberjonas.pompalette.infrastructure.eventbus.events.NotificationEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class MainViewController implements Subscribable {

    @FXML
    private StackPane parentPane;
    @FXML
    private StackPane mainContent;
    @FXML
    private ToggleButton darkmodeToggle;

    private NotificationPane notificationPane;
    private Map<ControlPanels, DraggablePane> controlPanes;

    public void init(Parent loadProjectView, Parent dependencyGraphView, Parent projectSearchListView) {
        controlPanes = new HashMap<>();
        notificationPane = new NotificationPane();
        mainContent.getChildren().add(notificationPane);

        setupLoadProjectPane(loadProjectView);
        setupDependencyGraphPane(dependencyGraphView);
        setupProjectSearchListPane(projectSearchListView);

        EventBus.getInstance().subscribe(NotificationEvent.class, this);
        EventBus.getInstance().subscribe(ShowControlsEvent.class, this);
    }

    private void setupLoadProjectPane(Parent loadProjectView){
        var loadProjectPane = new DraggablePane(400, 200, 600,0, parentPane);
        loadProjectPane.getChildren().add(loadProjectView);
        parentPane.getChildren().add(loadProjectPane);
        controlPanes.put(ControlPanels.LOAD_PROJECT, loadProjectPane);
    }

    private void setupDependencyGraphPane(Parent dependencyGraphView){
        var dependencyGraphPane = new DraggablePane(600, 600, 0,0, parentPane);
        dependencyGraphPane.getChildren().add(dependencyGraphView);
        parentPane.getChildren().add(dependencyGraphPane);
        controlPanes.put(ControlPanels.DEPENDENCY_GRAPH, dependencyGraphPane);
        dependencyGraphPane.setVisible(false);
    }

    private void setupProjectSearchListPane(Parent projectSearchListView){
        var projectSearchListPane = new DraggablePane(400, 550, 600,0, parentPane);
        projectSearchListPane.getChildren().add(projectSearchListView);
        parentPane.getChildren().add(projectSearchListPane);
        controlPanes.put(ControlPanels.PROJECT_SEARCH_LIST, projectSearchListPane);
        projectSearchListPane.setVisible(false);
    }

    @FXML
    private void darkModeToggleAction(){
            if(darkmodeToggle.isSelected()){
                Application.setUserAgentStylesheet(new PrimerDark().getUserAgentStylesheet());
            } else {
                Application.setUserAgentStylesheet(new PrimerLight().getUserAgentStylesheet());
            }
    }

    @FXML
    private void showLoadProjectView(){
        var loadProjectView = controlPanes.get(ControlPanels.LOAD_PROJECT);
        if(!loadProjectView.isVisible()){
            loadProjectView.setVisible(true);
        }
    }

    @FXML
    private void showDependencyGraphView(){
        var dependencyGraphView = controlPanes.get(ControlPanels.DEPENDENCY_GRAPH);
        if(!dependencyGraphView.isVisible()){
            dependencyGraphView.setVisible(true);
        }
    }

    @FXML
    private void showProjectSearchListView(){
        var projectSearchListView = controlPanes.get(ControlPanels.PROJECT_SEARCH_LIST);
        if(!projectSearchListView.isVisible()){
            projectSearchListView.setVisible(true);
        }
    }

    @Override
    public void handleEvent(Event<?> event) {

        Platform.runLater(() -> {
            if(event instanceof NotificationEvent notificationEvent){
                var notificationType = notificationEvent.getData().getEventType();
                var notificationMessage = notificationEvent.getData().getMessage();

                switch (notificationType){
                    case INFO -> notificationPane.showInfo(notificationMessage);
                    case ERROR -> notificationPane.showError(notificationMessage);
                    case null, default -> notificationPane.showWarning(notificationMessage);
                }
            } else if(event instanceof ShowControlsEvent showControlsEvent) {
                controlPanes.get(showControlsEvent.getData().controlPanels()).setVisible(showControlsEvent.getData().shouldShow());
            }
        });
    }

    @Override
    public Set<Class<?>> supports() {
        return Set.of(NotificationEvent.class, ShowControlsEvent.class);
    }

    public StackPane getMainContent() {
        return mainContent;
    }
}
