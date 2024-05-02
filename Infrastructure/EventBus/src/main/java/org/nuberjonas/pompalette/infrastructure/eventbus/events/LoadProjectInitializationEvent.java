package org.nuberjonas.pompalette.infrastructure.eventbus.events;

public class LoadProjectInitializationEvent extends Event<String>{
    public LoadProjectInitializationEvent(String projectPath) {
        super(projectPath);
    }
}
