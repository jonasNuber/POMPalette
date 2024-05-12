package org.nuberjonas.pompalette.application.javafx_application.gui.projectgraph.viewmodel;

import com.brunomnsilva.smartgraph.graph.Vertex;
import javafx.application.Platform;
import org.nuberjonas.pompalette.core.model.application.ProjectGraphService;
import org.nuberjonas.pompalette.core.model.domain.graph.ProjectGraph;
import org.nuberjonas.pompalette.core.model.domain.project.Project;
import org.nuberjonas.pompalette.infrastructure.eventbus.Observer;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class ProjectGraphViewModel {

    private List<Observer> observers;

    private ProjectGraph fullGraph;
    private ProjectGraph projectGraph;
    private ProjectGraphService service;

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

    public void loadProjectGraph(Path projectGraph){
        service.loadProjectAsync(projectGraph, fullGraph).thenAccept(
                graph -> {
                    Platform.runLater(() -> fullGraph = graph);

                    loadProjectModelSubgraph(graph);
                }).exceptionally(ex -> {
            Platform.runLater(() -> {
                throw new RuntimeException(ex);
            });
            return null;
        });
    }

    private void loadProjectModelSubgraph(ProjectGraph graph){
        service.loadProjectSubgraphAsync(graph).thenAccept(subGraph -> Platform.runLater(() -> {
            projectGraph = subGraph;
            notifyObservers(subGraph);
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
