package org.nuberjonas.pompalette.mapping.projectgraph_mapping;

import org.nuberjonas.pompalette.core.model.graph.EdgeType;
import org.nuberjonas.pompalette.core.model.graph.ProjectGraph;
import org.nuberjonas.pompalette.core.model.project.MavenProject;
import org.nuberjonas.pompalette.core.model.project.ProjectCoordinates;
import org.nuberjonas.pompalette.core.sharedkernel.projectdtos.beans.MultiModuleProjectDTO;
import org.nuberjonas.pompalette.mapping.mappingapi.mapper.MapperService;

public class ProjectGraphMapperService implements MapperService<MultiModuleProjectDTO, ProjectGraph> {
    @Override
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
            var project = new MavenProject(new ProjectCoordinates(multiModuleProjectDTO.get().groupId(), multiModuleProjectDTO.get().artifactId(), multiModuleProjectDTO.get().version()), multiModuleProjectDTO.get().name());
            graph.insertVertex(project);
            graph.insertEdge(root, project, EdgeType.MODULE);
            mapToGraph(child, project, graph);
        }
    }

    @Override
    public MultiModuleProjectDTO mapToSource(ProjectGraph toMap) {
        return null;
    }
}
