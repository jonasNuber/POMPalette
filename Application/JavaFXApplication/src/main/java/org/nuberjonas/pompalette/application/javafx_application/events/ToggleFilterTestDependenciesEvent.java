package org.nuberjonas.pompalette.application.javafx_application.events;

import org.nuberjonas.pompalette.infrastructure.eventbus.events.Event;

public class ToggleFilterTestDependenciesEvent extends Event<Boolean> {
    public ToggleFilterTestDependenciesEvent(Boolean data) {
        super(data);
    }
}
