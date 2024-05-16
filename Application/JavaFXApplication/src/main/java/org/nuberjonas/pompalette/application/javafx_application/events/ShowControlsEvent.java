package org.nuberjonas.pompalette.application.javafx_application.events;

import org.nuberjonas.pompalette.application.javafx_application.gui.main.view.ControlPanels;
import org.nuberjonas.pompalette.infrastructure.eventbus.events.Event;

public class ShowControlsEvent extends Event<ShowControlsEvent.ShowControlsPayload> {

    private ShowControlsEvent(ShowControlsPayload data) {
        super(data);
    }

    public static ShowControlsEvent showLoadProjectControls(boolean shouldShow){
        return new ShowControlsEvent(new ShowControlsPayload(ControlPanels.LOAD_PROJECT, shouldShow));
    }

    public static ShowControlsEvent showDependencyGraphControls(boolean shouldShow){
        return new ShowControlsEvent(new ShowControlsPayload(ControlPanels.DEPENDENCY_GRAPH, shouldShow));
    }

    public record ShowControlsPayload(ControlPanels controlPanels, Boolean shouldShow){ }
}
