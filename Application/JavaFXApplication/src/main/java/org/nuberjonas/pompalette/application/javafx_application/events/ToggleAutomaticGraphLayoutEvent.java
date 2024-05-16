package org.nuberjonas.pompalette.application.javafx_application.events;

import org.nuberjonas.pompalette.infrastructure.eventbus.events.Event;

public class ToggleAutomaticGraphLayoutEvent extends Event<Boolean> {

    public ToggleAutomaticGraphLayoutEvent(Boolean data) {
        super(data);
    }
}
