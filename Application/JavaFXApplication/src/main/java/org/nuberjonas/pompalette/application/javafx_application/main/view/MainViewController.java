package org.nuberjonas.pompalette.application.javafx_application.main.view;

import javafx.fxml.FXML;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import org.nuberjonas.pompalette.application.javafx_application.main.ViewHandler;
import org.nuberjonas.pompalette.infrastructure.eventbus.EventBus;
import org.nuberjonas.pompalette.infrastructure.eventbus.Subscribable;
import org.nuberjonas.pompalette.infrastructure.eventbus.events.Event;
import org.nuberjonas.pompalette.infrastructure.eventbus.events.LoadProjectInitializationEvent;

import java.io.IOException;
import java.util.Set;

public class MainViewController implements Subscribable {

    @FXML
    private StackPane contentPane;
    @FXML
    private GridPane controls;

    private ViewHandler viewHandler;

    public void init(ViewHandler viewHandler) throws IOException {
        this.viewHandler = viewHandler;
        viewHandler.addLoadProjectViewToControls(controls);

        EventBus.getInstance().subscribe(LoadProjectInitializationEvent.class, this);
    }

    @Override
    public void handleEvent(Event<?> event) {
        if(event instanceof LoadProjectInitializationEvent loadProjectInitializationEvent){
            try {
                viewHandler.addProjectGraphViewToContentPane(contentPane);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public Set<Class<?>> supports() {
        return Set.of(LoadProjectInitializationEvent.class);
    }
}
