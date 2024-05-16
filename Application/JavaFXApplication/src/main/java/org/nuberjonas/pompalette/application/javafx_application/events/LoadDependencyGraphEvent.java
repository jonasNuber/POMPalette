package org.nuberjonas.pompalette.application.javafx_application.events;

import com.brunomnsilva.smartgraph.graph.Vertex;
import org.nuberjonas.pompalette.core.model.domain.project.Project;
import org.nuberjonas.pompalette.infrastructure.eventbus.events.Event;

public class LoadDependencyGraphEvent extends Event<Vertex<Project>> {
    public LoadDependencyGraphEvent(Vertex<Project> data) {
        super(data);
    }
}
