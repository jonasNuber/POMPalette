package org.nuberjonas.pompalette.application.javafx_application.gui.main.view;

import atlantafx.base.theme.PrimerDark;
import atlantafx.base.theme.PrimerLight;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.StackPane;
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

    private enum Controls {
        LOAD_PROJECT;
    }

    @FXML
    private ToggleButton darkmodeToggle;

    @FXML
    private StackPane parentPane;
    @FXML
    private StackPane mainContent;

    private NotificationPane notificationPane;
    private Map<Controls, DraggablePane> controlPanes;

    public void init(Parent loadProjectView) {
        controlPanes = new HashMap<>();
        notificationPane = new NotificationPane();
        mainContent.getChildren().add(notificationPane);

        setupLoadProjectPane(loadProjectView);
        EventBus.getInstance().subscribe(NotificationEvent.class, this);
    }

    private void setupLoadProjectPane(Parent loadProjectView){
        var loadProjectPane = new DraggablePane(400, 200, 600,0, parentPane);
        loadProjectPane.getChildren().add(loadProjectView);
        parentPane.getChildren().add(loadProjectPane);
        controlPanes.put(Controls.LOAD_PROJECT, loadProjectPane);
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
        var loadProjectView = controlPanes.get(Controls.LOAD_PROJECT);
        if(!loadProjectView.isVisible()){
            loadProjectView.setVisible(true);
        }
    }

    @Override
    public void handleEvent(Event<?> event) {
        if(event instanceof NotificationEvent notificationEvent){
            var notificationType = notificationEvent.getData().getEventType();
            var notificationMessage = notificationEvent.getData().getMessage();

            switch (notificationType){
                case INFO -> notificationPane.showInfo(notificationMessage);
                case ERROR -> notificationPane.showError(notificationMessage);
                case null, default -> notificationPane.showWarning(notificationMessage);
            }
        }
    }

    @Override
    public Set<Class<?>> supports() {
        return Set.of(NotificationEvent.class);
    }

    public StackPane getMainContent() {
        return mainContent;
    }
}
