package org.nuberjonas.pompalette.application.javafx_application.events;

import org.nuberjonas.pompalette.core.model.domain.graph.ProjectGraph;
import org.nuberjonas.pompalette.infrastructure.eventbus.events.Event;

public class ShowDependencyGraphEvent extends Event<ProjectGraph> {
    public ShowDependencyGraphEvent(ProjectGraph data) {
        super(data);
    }
}
