package org.nuberjonas.pompalette.core.model;

import org.nuberjonas.pompalette.core.model.domain.graph.ProjectGraph;

public class ModelFactory {

    private ProjectGraph projectGraph;

    public ProjectGraph getProjectGraph(){
        if(projectGraph == null){
            projectGraph = new ProjectGraph();
        }
        return projectGraph;
    }
}
