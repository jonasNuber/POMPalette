package org.nuberjonas.pompalette.application.javafx_application.gui.projectgraph.viewmodel;

import org.nuberjonas.pompalette.core.model.application.ProjectGraphService;
import org.nuberjonas.pompalette.core.model.domain.graph.ProjectGraph;

import java.nio.file.Path;

public class ProjectGraphViewModel {

    private ProjectGraph graph;
    private ProjectGraphService service;

    public ProjectGraphViewModel(ProjectGraph graph, ProjectGraphService service) {
        this.graph = graph;
        this.service = service;
    }

    public void loadProjectGraph(Path projectGraph){
        service.loadProject(projectGraph, graph);
    }

    public ProjectGraph getGraph() {
        return graph;
    }
}
