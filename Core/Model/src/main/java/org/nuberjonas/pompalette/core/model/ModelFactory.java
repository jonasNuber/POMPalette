package org.nuberjonas.pompalette.core.model;

import org.nuberjonas.pompalette.core.model.domain.graph.ProjectGraph;

public class ModelFactory {

    public ProjectGraph getProjectGraph(){
        return new ProjectGraph();
    }

    public ProjectGraph getDependencyGraph(){
        return new ProjectGraph();
    }
}
