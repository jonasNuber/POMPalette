package org.nuberjonas.pompalette.application.javafx_application.events;

import org.nuberjonas.pompalette.infrastructure.eventbus.events.Event;

import java.nio.file.Path;

public class LoadProjectInitializationEvent extends Event<Path> {
    public LoadProjectInitializationEvent(Path projectPath) {
        super(projectPath);
    }
}
