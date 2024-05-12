package org.nuberjonas.pompalette.application.javafx_application.gui.dependencygraph.viewmodel;

import org.nuberjonas.pompalette.core.model.domain.graph.ProjectGraph;
import org.nuberjonas.pompalette.infrastructure.eventbus.Observer;

import java.util.ArrayList;
import java.util.List;

public class DependencyGraphViewModel {

    private List<Observer> observers;

    private ProjectGraph dependencySubGraph;

    public DependencyGraphViewModel(ProjectGraph dependencySubGraph) {
        observers = new ArrayList<>();
        this.dependencySubGraph = dependencySubGraph;
    }

    public void updateDependencyGraph(ProjectGraph dependencySubGraph){
        this.dependencySubGraph = dependencySubGraph;
        notifyObservers(this.dependencySubGraph);
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

    public ProjectGraph getDependencySubGraph() {
        return dependencySubGraph;
    }
}
