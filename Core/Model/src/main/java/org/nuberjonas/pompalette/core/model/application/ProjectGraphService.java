package org.nuberjonas.pompalette.core.model.application;

import com.brunomnsilva.smartgraph.graph.Vertex;
import org.nuberjonas.pompalette.core.model.application.mapper.ProjectGraphMapper;
import org.nuberjonas.pompalette.core.model.domain.graph.ProjectGraph;
import org.nuberjonas.pompalette.core.model.domain.graph.relationship.ModuleRelationship;
import org.nuberjonas.pompalette.core.model.domain.project.MavenProject;
import org.nuberjonas.pompalette.core.model.domain.project.Project;
import org.nuberjonas.pompalette.infrastructure.eventbus.EventBus;
import org.nuberjonas.pompalette.infrastructure.eventbus.events.NotificationEvent;
import org.nuberjonas.pompalette.infrastructure.parsing.projectparsingapi.ProjectParsingService;
import org.nuberjonas.pompalette.infrastructure.parsing.projectparsingapi.exceptions.NotAProjectException;
import org.nuberjonas.pompalette.infrastructure.parsing.projectparsingapi.exceptions.ProjectParsingException;
import org.nuberjonas.pompalette.infrastructure.parsing.projectparsingmavenimpl.MavenProjectParsingService;

import java.nio.file.Path;
import java.util.concurrent.CompletableFuture;

public class ProjectGraphService {

    private ProjectParsingService parsingService;
    private ProjectGraphMapper mapper;

    public ProjectGraphService(ProjectParsingService parsingService) {
        this.parsingService = parsingService;
        this.mapper = new ProjectGraphMapper();
    }

    public ProjectGraphService(){
        this(new MavenProjectParsingService());
    }

    public CompletableFuture<ProjectGraph> loadProjectAsync(Path projectPath, ProjectGraph graph){
        return CompletableFuture.supplyAsync(() -> loadProject(projectPath, graph));
    }

    public ProjectGraph loadProject(Path projectPath, ProjectGraph graph){
        try {
            return mapper.mapToGraph(parsingService.loadMultiModuleProject(projectPath), graph);
        } catch(NotAProjectException e) {
            EventBus.getInstance().publish(NotificationEvent.error("Not a maven project!", e));
        } catch (ProjectParsingException e){
            EventBus.getInstance().publish(NotificationEvent.error("Project could not be loaded!", e));

        }
        return graph;
    }

    public CompletableFuture<ProjectGraph> loadProjectSubgraphAsync(ProjectGraph graph){
        return CompletableFuture.supplyAsync(() -> loadProjectSubgraph(graph));
    }

    public ProjectGraph loadProjectSubgraph(ProjectGraph graph){
        return graph.subGraph(
                projectVertex -> projectVertex.element() instanceof MavenProject,
                relationshipEdge -> relationshipEdge.element() instanceof ModuleRelationship);
    }

    public CompletableFuture<ProjectGraph> loadDependencySubgraphAsync(ProjectGraph graph, Vertex<Project> startingVertex, boolean isDependency){
        return CompletableFuture.supplyAsync(() -> loadDependencySubgraph(graph, startingVertex, isDependency));
    }

    public ProjectGraph loadDependencySubgraph(ProjectGraph graph, Vertex<Project> staringVertex, boolean isDependency){
        return graph.dependencySubGraph(staringVertex, isDependency);
    }
}
