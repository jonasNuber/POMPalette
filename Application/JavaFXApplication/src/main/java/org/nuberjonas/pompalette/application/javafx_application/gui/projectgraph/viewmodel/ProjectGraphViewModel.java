package org.nuberjonas.pompalette.application.javafx_application.gui.projectgraph.viewmodel;

import com.brunomnsilva.smartgraph.graph.Vertex;
import javafx.application.Platform;
import org.nuberjonas.pompalette.application.javafx_application.events.SetProjectSearchListEvent;
import org.nuberjonas.pompalette.core.model.application.ProjectGraphService;
import org.nuberjonas.pompalette.core.model.domain.graph.ProjectGraph;
import org.nuberjonas.pompalette.core.model.domain.graph.relationship.DependencyRelationship;
import org.nuberjonas.pompalette.core.model.domain.project.MavenProject;
import org.nuberjonas.pompalette.core.model.domain.project.Project;
import org.nuberjonas.pompalette.core.model.domain.project.dependecies.ExternalDependency;
import org.nuberjonas.pompalette.core.model.domain.searchlist.ProjectSearchListElement;
import org.nuberjonas.pompalette.infrastructure.eventbus.EventBus;
import org.nuberjonas.pompalette.infrastructure.eventbus.Observer;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class ProjectGraphViewModel {

    private final List<Observer> observers;
    private final ProjectGraphService service;

    private ProjectGraph fullGraph;
    private ProjectGraph projectGraph;

    public ProjectGraphViewModel(ProjectGraph graph, ProjectGraphService service) {
        observers = new ArrayList<>();
        this.fullGraph = graph;
        this.projectGraph = new ProjectGraph();
        this.service = service;
    }

    public void addObserver(Observer observer){
        observers.add(observer);
    }

    public void removeObserver(Observer observer){
        observers.remove(observer);
    }

    private void notifyObservers(ProjectGraph graph){
        for (var observer : observers){
            observer.update(graph);
        }
    }

    public void loadProjectGraph(Path projectPath){
        service.loadProjectAsync(projectPath, new ProjectGraph()).thenAccept(
                graph -> {
                    Platform.runLater(() -> {
                        fullGraph = graph;
                        EventBus.getInstance().publish(new SetProjectSearchListEvent(getSearchListElements()));
                    });

                    loadProjectModelSubgraph(graph);
                }).exceptionally(ex -> {
            Platform.runLater(() -> {
                throw new RuntimeException(ex);
            });
            return null;
        });
    }

    private List<ProjectSearchListElement> getSearchListElements(){
        var searchListElements = new ArrayList<ProjectSearchListElement>();

        fullGraph.vertices().forEach(projectVertex -> {
            if(projectVertex.element() instanceof MavenProject || projectVertex.element() instanceof ExternalDependency){
                searchListElements.add(new ProjectSearchListElement(projectVertex, fullGraph.incidentEdges(projectVertex).stream().filter(relationshipProjectEdge -> relationshipProjectEdge.element() instanceof DependencyRelationship).map(relationshipProjectEdge -> ((DependencyRelationship)relationshipProjectEdge.element())).toList()));
            }
        });

        return searchListElements;
    }

    private void loadProjectModelSubgraph(ProjectGraph graph){
        service.loadProjectSubgraphAsync(graph).thenAccept(subGraph -> Platform.runLater(() -> {
            projectGraph = subGraph;
            notifyObservers(projectGraph);
        })).exceptionally(ex -> {
            Platform.runLater(() -> {
                throw new RuntimeException(ex);
            });
            return null;
        });
    }

    public CompletableFuture<ProjectGraph> loadDependencySubGraph(Vertex<Project> startingVertex, boolean isDependency){
        return service.loadDependencySubgraphAsync(fullGraph, startingVertex, isDependency);
    }

    public ProjectGraph getFullGraph() {
        return fullGraph;
    }
}
