package org.nuberjonas.pompalette.core.model.application.mapper;

import org.apache.commons.lang3.StringUtils;
import org.nuberjonas.pompalette.core.model.application.exceptions.DependencyResolverException;
import org.nuberjonas.pompalette.core.model.domain.graph.ProjectGraph;
import org.nuberjonas.pompalette.core.model.domain.graph.relationship.ModuleRelationship;
import org.nuberjonas.pompalette.core.model.domain.project.MavenProject;
import org.nuberjonas.pompalette.core.model.domain.project.Project;
import org.nuberjonas.pompalette.core.model.domain.project.ProjectCoordinates;
import org.nuberjonas.pompalette.core.model.domain.project.dependecies.Dependency;
import org.nuberjonas.pompalette.core.sharedkernel.projectdtos.beans.MultiModuleProjectDTO;
import org.nuberjonas.pompalette.core.sharedkernel.projectdtos.beans.ProjectDTO;
import org.nuberjonas.pompalette.core.sharedkernel.projectdtos.beans.dependency.DependencyDTO;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProjectGraphMapper {

    private Map<Project, ProjectDTO> projectMatrix;

    public ProjectGraph mapToGraph(MultiModuleProjectDTO multiModuleProject, ProjectGraph graph){
        if(multiModuleProject == null){
            throw new IllegalArgumentException("Input cannot be empty");
        }

        projectMatrix = new HashMap<>();

        var project = mapToMavenProject(multiModuleProject);
        
        graph.insertVertex(project);
        mapModules(multiModuleProject, project, graph);
        mapDependecies(graph);

        return graph;
    }

    private void mapModules(MultiModuleProjectDTO multiModuleProjectDTO, MavenProject root, ProjectGraph graph) {
        if(multiModuleProjectDTO.getProjectBOM() != null){
            var bom = mapToMavenProject(multiModuleProjectDTO.getProjectBOM());
            graph.insertVertex(bom);
            graph.insertEdge(root, bom, ModuleRelationship.bom());
        }

        for (MultiModuleProjectDTO child : multiModuleProjectDTO.getModules()) {
            var project = mapToMavenProject(child);
            graph.insertVertex(project);
            graph.insertEdge(root, project, ModuleRelationship.module());
            mapModules(child, project, graph);
        }
    }

    private MavenProject mapToMavenProject(MultiModuleProjectDTO project){
        var projectDTO = project.get();
        var mavenProject = new MavenProject(new ProjectCoordinates(
                projectDTO.groupId(),
                projectDTO.artifactId(),
                projectDTO.version()),
                projectDTO.name());
        projectMatrix.put(mavenProject, projectDTO);

        return mavenProject;
    }

    private void mapDependecies(ProjectGraph graph){
        Map<Integer, List<Dependency>> managedDependencies = new HashMap<>();
        var rootProject = graph.rootProject();
        var internalGroupIdRoot = rootProject.element().getCoordinates().groupId() + "." + rootProject.element().getCoordinates().artifactId();
        var projectDTO = projectMatrix.get(rootProject.element());

        for(DependencyDTO dependencyDTO : projectDTO.modelBase().dependencyManagement().dependencies()){
            var dependencyCoordinates = mapDependencyCoordinates(dependencyDTO);

            if(isInternalDependency(dependencyDTO, internalGroupIdRoot)){
                if(projectMatrix.containsKey(new MavenProject(dependencyCoordinates, null))){

                } else {
                    throw new DependencyResolverException(String.format("The dependency %s is internal but cannot be resolved.", dependencyCoordinates));
                }
            }
        }
    }

    private boolean isInternalDependency(DependencyDTO dependencyDTO, String internalGroupIdRoot){
        return StringUtils.containsIgnoreCase(dependencyDTO.groupId(), internalGroupIdRoot);
    }

    private ProjectCoordinates mapDependencyCoordinates(DependencyDTO dependencyDTO){
        return new ProjectCoordinates(
                dependencyDTO.groupId(),
                dependencyDTO.artifactId(),
                dependencyDTO.version());
    }
}
