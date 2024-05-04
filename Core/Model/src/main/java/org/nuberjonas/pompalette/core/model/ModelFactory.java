package org.nuberjonas.pompalette.core.model;

import org.nuberjonas.pompalette.core.model.graph.EdgeType;
import org.nuberjonas.pompalette.core.model.graph.ProjectGraph;
import org.nuberjonas.pompalette.core.model.project.MavenProject;
import org.nuberjonas.pompalette.core.model.project.ProjectCoordinates;
import org.nuberjonas.pompalette.core.sharedkernel.projectdtos.beans.MultiModuleProjectDTO;
import org.nuberjonas.pompalette.infrastructure.parsing.projectparsingmavenimpl.MavenProjectParsingService;

import java.nio.file.Path;

public class ModelFactory {

    private ProjectGraph projectGraph;

    public ProjectGraph getProjectGraph(String projectPath) {
        MavenProjectParsingService parsingService = new MavenProjectParsingService();

        var multiModuleProject = parsingService.loadMultiModuleProject(Path.of(projectPath));

        ProjectGraph graph = mapToDestination(multiModuleProject);

        return graph;
    }

    public ProjectGraph mapToDestination(MultiModuleProjectDTO multiModuleProjectDTO) {
        if(multiModuleProjectDTO == null){
            throw new IllegalArgumentException("Input cannot be empty");
        }

        var graph = new ProjectGraph();
        var project = new MavenProject(new ProjectCoordinates(multiModuleProjectDTO.get().groupId(), multiModuleProjectDTO.get().artifactId(), multiModuleProjectDTO.get().version()), multiModuleProjectDTO.get().name());
        graph.insertVertex(project);
        mapToGraph(multiModuleProjectDTO, project, graph);

        return graph;
    }

    private void mapToGraph(MultiModuleProjectDTO multiModuleProjectDTO, MavenProject root, ProjectGraph graph){
        for(MultiModuleProjectDTO child : multiModuleProjectDTO.getModules()){
            var project = new MavenProject(new ProjectCoordinates(child.get().groupId(), child.get().artifactId(), child.get().version()), child.get().name());
            graph.insertVertex(project);
            graph.insertEdge(root, project, EdgeType.MODULE);
            mapToGraph(child, project, graph);
        }
    }
}
