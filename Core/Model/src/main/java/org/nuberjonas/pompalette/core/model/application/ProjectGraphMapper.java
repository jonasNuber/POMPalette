package org.nuberjonas.pompalette.core.model.application;

import org.nuberjonas.pompalette.core.model.domain.graph.EdgeType;
import org.nuberjonas.pompalette.core.model.domain.graph.ProjectGraph;
import org.nuberjonas.pompalette.core.model.domain.project.MavenProject;
import org.nuberjonas.pompalette.core.model.domain.project.ProjectCoordinates;
import org.nuberjonas.pompalette.core.model.domain.project.dependecies.ExternalDependency;
import org.nuberjonas.pompalette.core.sharedkernel.projectdtos.beans.MultiModuleProjectDTO;
import org.nuberjonas.pompalette.core.sharedkernel.projectdtos.beans.dependency.DependencyDTO;

public class ProjectGraphMapper {

    public ProjectGraph mapToGraph(MultiModuleProjectDTO multiModuleProject, ProjectGraph graph){
        if(multiModuleProject == null){
            throw new IllegalArgumentException("Input cannot be empty");
        }

        var project = mapToMavenProject(multiModuleProject);
        
        graph.insertVertex(project);
        mapDepdendencies(multiModuleProject, project, graph);
        mapModules(multiModuleProject, project, graph);

        return graph;
    }

    private void mapModules(MultiModuleProjectDTO multiModuleProjectDTO, MavenProject root, ProjectGraph graph) {
        if(multiModuleProjectDTO.getProjectBOM() != null){
            var bom = mapToMavenProject(multiModuleProjectDTO.getProjectBOM());
            graph.insertVertex(bom);
            graph.insertEdge(root, bom, EdgeType.BOM);
        }

        for (MultiModuleProjectDTO child : multiModuleProjectDTO.getModules()) {
            var project = new MavenProject(new ProjectCoordinates(child.get().groupId(), child.get().artifactId(), child.get().version()), child.get().name());
            graph.insertVertex(project);
            graph.insertEdge(root, project, EdgeType.MODULE);
            mapDepdendencies(child, project, graph);
            mapModules(child, project, graph);
        }
    }

    private void mapDepdendencies(MultiModuleProjectDTO multiModuleProjectDTO, MavenProject root, ProjectGraph graph){
        for (DependencyDTO dependencyDTO : multiModuleProjectDTO.get().modelBase().dependencies()){
            var dependency = mapToMavenDependency(dependencyDTO);
            graph.insertVertex(dependency);
            graph.insertEdge(root, dependency, EdgeType.THIRD_PARTY_DEPENDENCY);
        }
    }

    private MavenProject mapToMavenProject(MultiModuleProjectDTO project){
        return new MavenProject(new ProjectCoordinates(
                project.get().groupId(),
                project.get().artifactId(),
                project.get().version()),
                project.get().name());
    }

    private ExternalDependency mapToMavenDependency(DependencyDTO dependency){
        return new ExternalDependency(new ProjectCoordinates(
                dependency.groupId(),
                dependency.artifactId(),
                dependency.version()
        ));
    }
}
