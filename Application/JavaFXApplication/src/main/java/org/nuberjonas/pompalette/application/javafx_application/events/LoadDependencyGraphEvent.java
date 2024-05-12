package org.nuberjonas.pompalette.application.javafx_application.events;

import com.brunomnsilva.smartgraph.graphview.SmartGraphVertex;
import org.nuberjonas.pompalette.core.model.domain.project.Project;
import org.nuberjonas.pompalette.infrastructure.eventbus.events.Event;

public class LoadDependencyGraphEvent extends Event<SmartGraphVertex<Project>> {
    public LoadDependencyGraphEvent(SmartGraphVertex<Project> data) {
        super(data);
    }
}
