package org.nuberjonas.pompalette.core.model.application.mapper;

import com.brunomnsilva.smartgraph.graph.Vertex;
import org.nuberjonas.pompalette.core.model.domain.graph.ProjectGraph;
import org.nuberjonas.pompalette.core.model.domain.graph.relationship.DependencyRelationship;
import org.nuberjonas.pompalette.core.model.domain.graph.relationship.ModuleRelationship;
import org.nuberjonas.pompalette.core.model.domain.project.MavenProject;
import org.nuberjonas.pompalette.core.model.domain.project.Project;
import org.nuberjonas.pompalette.core.model.domain.project.ProjectCoordinates;
import org.nuberjonas.pompalette.core.model.domain.project.dependecies.*;
import org.nuberjonas.pompalette.core.sharedkernel.projectdtos.beans.MultiModuleProjectDTO;
import org.nuberjonas.pompalette.core.sharedkernel.projectdtos.beans.dependency.DependencyDTO;

import java.util.ArrayList;

public class ProjectGraphMapper {

    public ProjectGraph mapToGraph(MultiModuleProjectDTO multiModuleProject, ProjectGraph graph){
        if(multiModuleProject == null){
            throw new IllegalArgumentException("Input cannot be empty");
        }

        var environment = new ProjectEnvironment();
        var project = mapToMavenProject(multiModuleProject);
        graph.insertVertex(project);

        mapProjectBom(graph, multiModuleProject, project, environment);
        mapModules(multiModuleProject, project, graph, environment);
        mapDependencies(graph, graph.rootProject(), environment);

        return graph;
    }

    private void mapProjectBom(ProjectGraph graph, MultiModuleProjectDTO multiModuleProject, MavenProject project, ProjectEnvironment environment){
        if(multiModuleProject.getProjectBOM() != null){
            var bom = mapToMavenProject(multiModuleProject.getProjectBOM());
            graph.insertVertex(bom);
            graph.insertEdge(project, bom, ModuleRelationship.bom());

            environment.addProjectPair(bom, multiModuleProject.getProjectBOM().get(), null);
            environment.addProjectPair(project, multiModuleProject.get(), bom);
        } else {
            environment.addProjectPair(project, multiModuleProject.get(), null);
        }
    }

    private void mapModules(MultiModuleProjectDTO multiModuleProjectDTO, MavenProject root, ProjectGraph graph, ProjectEnvironment environment) {
        for (MultiModuleProjectDTO child : multiModuleProjectDTO.getModules()) {
            var project = mapToMavenProject(child);
            graph.insertVertex(project);
            graph.insertEdge(root, project, ModuleRelationship.module());

            environment.addProjectPair(project, child.get(), root);

            mapModules(child, project, graph, environment);
        }
    }

    private MavenProject mapToMavenProject(MultiModuleProjectDTO project){
        var projectDTO = project.get();

        return new MavenProject(new ProjectCoordinates(
                projectDTO.groupId(),
                projectDTO.artifactId(),
                projectDTO.version()),
                projectDTO.name());
    }

    private void mapDependencies(ProjectGraph graph, Vertex<Project> projectVertex, ProjectEnvironment environment){
        if(!environment.getProperties().contains("project.version")){
            environment.addProperty("project.version", projectVertex.element().getCoordinates().version());
        }

        mapBomDependencies(graph, projectVertex, environment);
        mapManagedDependencies(graph, projectVertex, environment);
        mapDirectDependencies(graph, projectVertex, environment);

        for(var module : graph.modules(projectVertex)){
            mapDependencies(graph, module, environment);
        }
    }

    private void mapBomDependencies(ProjectGraph graph, Vertex<Project> projectVertex, ProjectEnvironment environment){
        var bomProjectVertex = graph.bomProject(projectVertex.element());

        if(bomProjectVertex != null){
            mapManagedDependencies(graph, bomProjectVertex, environment);
        }
    }

    private void mapManagedDependencies(ProjectGraph graph, Vertex<Project> projectVertex, ProjectEnvironment environment){
        var projectCoordinates = projectVertex.element().getCoordinates();
        var projectDTO = environment.getProjectDTOFor(DependencyCoordinates.coordinatesFor(projectCoordinates));
        var project = environment.getMavenProjectFor(DependencyCoordinates.coordinatesFor(projectCoordinates));
        environment.addAllProperties(projectDTO.modelBase().properties());

        if(projectDTO.modelBase().dependencyManagement() != null){
            var dependencies = new ArrayList<ManagedDependency>();

            for(var dependencyDTO : projectDTO.modelBase().dependencyManagement().dependencies()){
                dependencies.add(mapManagedDependency(graph, dependencyDTO, project, environment));
            }

            environment.addManagedDependenciesTo(project, dependencies);
        }
    }

    private ManagedDependency mapManagedDependency(ProjectGraph graph, DependencyDTO dependencyDTO, MavenProject project, ProjectEnvironment environment){
        var dependencyCoordinates = new DependencyCoordinates(dependencyDTO.groupId(), dependencyDTO.artifactId());
        Dependency dependency;
        ManagedDependency managedDependency;

        if(environment.projectExists(dependencyCoordinates)){
            dependency = new InternalDependency(environment.getMavenProjectFor(dependencyCoordinates));
            managedDependency = new ManagedDependency(dependency, project);
        } else {
            dependency = new ExternalDependency(dependencyCoordinates);
            managedDependency = new ManagedDependency(dependency, project);
            graph.insertVertex(dependency);
        }

        var targetVertex = dependency instanceof InternalDependency ?
                ((InternalDependency) dependency).getProject() : dependency;
        var relationship = dependency instanceof InternalDependency ?
                DependencyRelationship.internal(
                        PropertiesResolver.resolve(dependencyDTO.version(), environment.getProperties()),
                        DependencyScope.fromString(dependencyDTO.scope()),
                        DependencyType.fromString(dependencyDTO.type())) :
                DependencyRelationship.external(
                        PropertiesResolver.resolve(dependencyDTO.version(), environment.getProperties()),
                        DependencyScope.fromString(dependencyDTO.scope()),
                        DependencyType.fromString(dependencyDTO.type()));


        graph.insertVertex(managedDependency);
        graph.insertEdge(project, managedDependency, DependencyRelationship.manages());
        graph.insertEdge(managedDependency, targetVertex, relationship);

        return managedDependency;
    }

    private void mapDirectDependencies(ProjectGraph graph, Vertex<Project> projectVertex, ProjectEnvironment environment){
        var projectCoordinates = projectVertex.element().getCoordinates();
        var rootProjectDTO = environment.getProjectDTOFor(DependencyCoordinates.coordinatesFor(projectCoordinates));
        var project = environment.getMavenProjectFor(DependencyCoordinates.coordinatesFor(projectCoordinates));
        environment.addAllProperties(rootProjectDTO.modelBase().properties());


        if(rootProjectDTO.modelBase().dependencies() != null){
            for (var dependencyDTO : rootProjectDTO.modelBase().dependencies()){
                mapDirectDependency(graph, dependencyDTO, project, environment);
            }
        }
    }

    private void mapDirectDependency(ProjectGraph graph, DependencyDTO dependencyDTO, MavenProject project, ProjectEnvironment environment){
        var dependencyCoordinates = new DependencyCoordinates(dependencyDTO.groupId(), dependencyDTO.artifactId());
        Dependency dependency;

        if(dependencyDTO.version() == null){
            var managedDependency = environment.findManagedDependency(project, dependencyCoordinates);
            dependency = new ResolvedDependency(project, managedDependency);
            graph.insertVertex(dependency);
            graph.insertEdge(
                    project,
                    dependency,
                    DependencyRelationship.dependsOn(
                            DependencyScope.fromString(dependencyDTO.scope()),
                            DependencyType.fromString(dependencyDTO.type())));
            graph.insertEdge(
                    dependency,
                    managedDependency,
                    DependencyRelationship.resolvedBy(
                            DependencyScope.fromString(dependencyDTO.scope()),
                            DependencyType.fromString(dependencyDTO.type())));

        } else if(environment.projectExists(dependencyCoordinates)){
            graph.insertEdge(
                    project,
                    environment.getMavenProjectFor(dependencyCoordinates),
                    DependencyRelationship.internal(
                            PropertiesResolver.resolve(dependencyDTO.version(),
                                    environment.getProperties()),
                            DependencyScope.fromString(dependencyDTO.scope()),
                            DependencyType.fromString(dependencyDTO.type())));

        } else {
            dependency = new ExternalDependency(dependencyCoordinates);
            graph.insertVertex(dependency);
            graph.insertEdge(
                    project,
                    dependency,
                    DependencyRelationship.external(
                            PropertiesResolver.resolve(dependencyDTO.version(), environment.getProperties()),
                            DependencyScope.fromString(dependencyDTO.scope()),
                            DependencyType.fromString(dependencyDTO.type())));
        }
    }
}
