package org.nuberjonas.pompalette.application.javafx_application.events;

import org.nuberjonas.pompalette.infrastructure.eventbus.events.Event;

public class LoadProjectInitializationEvent extends Event<String> {
    public LoadProjectInitializationEvent(String projectPath) {
        super(projectPath);
    }
}
