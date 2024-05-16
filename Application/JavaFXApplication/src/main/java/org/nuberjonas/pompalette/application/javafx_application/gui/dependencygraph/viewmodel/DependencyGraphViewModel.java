package org.nuberjonas.pompalette.application.javafx_application.gui.dependencygraph.viewmodel;

import org.nuberjonas.pompalette.core.model.domain.graph.ProjectGraph;
import org.nuberjonas.pompalette.core.model.domain.graph.relationship.DependencyRelationship;
import org.nuberjonas.pompalette.core.model.domain.project.dependecies.DependencyScope;
import org.nuberjonas.pompalette.infrastructure.eventbus.Observer;

import java.util.ArrayList;
import java.util.List;

public class DependencyGraphViewModel {

    private List<Observer> observers;

    private ProjectGraph dependencyGraph;
    private ProjectGraph filteredDependencyGraph;

    private boolean shouldFilterTestDependencies;

    public DependencyGraphViewModel(ProjectGraph dependencyGraph) {
        observers = new ArrayList<>();
        this.dependencyGraph = dependencyGraph;
        filteredDependencyGraph = new ProjectGraph();
        shouldFilterTestDependencies = false;
    }

    public void updateDependencyGraph(ProjectGraph dependencyGraph){
        this.dependencyGraph = dependencyGraph;

        filterTestDependencies();
    }

    private void filterTestDependencies(){
        if(shouldFilterTestDependencies){
            filterGraph();
            notifyObservers(this.filteredDependencyGraph);
        } else {
            notifyObservers(this.dependencyGraph);
        }
    }

    private void filterGraph(){
        filteredDependencyGraph = dependencyGraph.copy();
        var testEdges = filteredDependencyGraph.edges().stream().filter(relationshipProjectEdge -> ((DependencyRelationship)relationshipProjectEdge.element()).scope() == DependencyScope.TEST).toList();

        testEdges.forEach(testEdge -> {
            filteredDependencyGraph.removeEdge(testEdge);

            var outgoingEdges = filteredDependencyGraph.outboundEdges(testEdge.vertices()[1]);
            filteredDependencyGraph.removeVertex(testEdge.vertices()[1]);
            var secondOutgoingEdges = filteredDependencyGraph.outboundEdges(outgoingEdges.getFirst().vertices()[1]);

            if(!secondOutgoingEdges.isEmpty()){
                outgoingEdges.add(secondOutgoingEdges.getFirst());
            }

            outgoingEdges.forEach(outgoingEdge -> {
                filteredDependencyGraph.removeEdge(outgoingEdge);
                filteredDependencyGraph.removeVertex(outgoingEdge.vertices()[1]);
            });
        });
    }

    public void updateFilterTestDependencies(boolean shouldFilter){
        this.shouldFilterTestDependencies = shouldFilter;
        filterTestDependencies();
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

    public ProjectGraph getDependencyGraph() {
        return dependencyGraph;
    }
}
