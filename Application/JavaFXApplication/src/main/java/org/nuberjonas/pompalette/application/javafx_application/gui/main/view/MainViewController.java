package org.nuberjonas.pompalette.application.javafx_application.gui.main.view;

import javafx.fxml.FXML;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import org.nuberjonas.pompalette.application.javafx_application.events.NotificationEvent;
import org.nuberjonas.pompalette.application.javafx_application.extensions.NotificationPane;
import org.nuberjonas.pompalette.infrastructure.eventbus.EventBus;
import org.nuberjonas.pompalette.infrastructure.eventbus.Subscribable;
import org.nuberjonas.pompalette.infrastructure.eventbus.events.Event;

import java.util.Set;

public class MainViewController implements Subscribable {

    @FXML
    private SplitPane splittableScene;
    @FXML
    private StackPane mainContent;
    @FXML
    private GridPane controls;

    private NotificationPane notificationPane;

    public void init() {
        setupDividerConstraints();
        notificationPane = new NotificationPane();
        mainContent.getChildren().add(notificationPane);

        EventBus.getInstance().subscribe(NotificationEvent.class, this);
    }

    private void setupDividerConstraints(){
        splittableScene.getDividers().getFirst().setPosition(0.8);

        splittableScene.getDividers().getFirst().positionProperty().addListener((obs, oldVal, newVal) -> {
            double minPosition = 0.8;
            double maxPosition = 1;
            if (newVal.doubleValue() < minPosition) {
                splittableScene.getDividers().getFirst().setPosition(minPosition);
            } else if (newVal.doubleValue() > maxPosition) {
                splittableScene.getDividers().getFirst().setPosition(maxPosition);
            }
        });
    }

    public GridPane getControls(){
        return controls;
    }

    public StackPane getMainContent(){
        return mainContent;
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
}
