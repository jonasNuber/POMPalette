package org.nuberjonas.pompalette.application.javafx_application.gui.dependencygraph.view;

import com.brunomnsilva.smartgraph.graphview.SmartGraphVertex;
import javafx.fxml.FXML;
import javafx.scene.layout.StackPane;
import org.nuberjonas.pompalette.application.javafx_application.events.*;
import org.nuberjonas.pompalette.application.javafx_application.extensions.ProjectGraphPanel;
import org.nuberjonas.pompalette.application.javafx_application.extensions.ProjectGraphZoomAndScrollPane;
import org.nuberjonas.pompalette.application.javafx_application.gui.dependencygraph.viewmodel.DependencyGraphViewModel;
import org.nuberjonas.pompalette.core.model.domain.graph.ProjectGraph;
import org.nuberjonas.pompalette.core.model.domain.graph.relationship.Relationship;
import org.nuberjonas.pompalette.core.model.domain.project.Project;
import org.nuberjonas.pompalette.infrastructure.eventbus.EventBus;
import org.nuberjonas.pompalette.infrastructure.eventbus.Observer;
import org.nuberjonas.pompalette.infrastructure.eventbus.Subscribable;
import org.nuberjonas.pompalette.infrastructure.eventbus.events.Event;

import java.util.Set;

public class DependencyGraphViewController implements Subscribable, Observer {

    @FXML
    private StackPane dependencyGraphViewRoot;

    private ProjectGraphPanel<Project, Relationship> graphView;

    private DependencyGraphViewModel viewModel;

    private boolean isGraphInitialized;

    public void init(DependencyGraphViewModel viewModel){
        this.viewModel = viewModel;

        isGraphInitialized = false;

        graphView = new ProjectGraphPanel<>(viewModel.getDependencyGraph());
        graphView.setPrefSize(5000, 5000);
        graphView.setAutomaticLayout(true);

        graphView.setVertexDoubleClickAction(this::loadDependencyGraph);

        dependencyGraphViewRoot.getChildren().add(new ProjectGraphZoomAndScrollPane(graphView));

        EventBus.getInstance().subscribe(ShowDependencyGraphEvent.class, this);
        EventBus.getInstance().subscribe(ToggleAutomaticGraphLayoutEvent.class, this);
        EventBus.getInstance().subscribe(ToggleFilterTestDependenciesEvent.class, this);
        viewModel.addObserver(this);
    }

    @Override
    public void update(Object data) {
        if(data instanceof ProjectGraph projectGraph){
            graphView.setGraph(new ProjectGraph());
            graphView.update();
            graphView.setGraph(projectGraph);
            graphView.update();
        }
    }

    @Override
    public void handleEvent(Event<?> event) {
        if(event instanceof ShowDependencyGraphEvent showDependencyGraphEvent){
            showDependencyGraph(showDependencyGraphEvent.getData());
            EventBus.getInstance().publish(ShowControlsEvent.showDependencyGraphControls(true));
        } else if (event instanceof ToggleAutomaticGraphLayoutEvent toggleAutomaticGraphLayoutEvent){
            initializeGraph();
            graphView.setAutomaticLayout(toggleAutomaticGraphLayoutEvent.getData());
            graphView.update();
        } else if(event instanceof ToggleFilterTestDependenciesEvent toggleFilterTestDependenciesEvent){
            initializeGraph();
            viewModel.updateFilterTestDependencies(toggleFilterTestDependenciesEvent.getData());
        }
    }

    @Override
    public Set<Class<?>> supports() {
        return Set.of(ShowDependencyGraphEvent.class, ToggleAutomaticGraphLayoutEvent.class, ToggleFilterTestDependenciesEvent.class);
    }

    private void showDependencyGraph(ProjectGraph dependencyGraph){
        initializeGraph();

        viewModel.updateDependencyGraph(dependencyGraph);
    }

    private void initializeGraph(){
        if(!isGraphInitialized){
            graphView.init();
            isGraphInitialized = true;
        }
    }

    private void loadDependencyGraph(SmartGraphVertex<Project> projectSmartGraphVertex) {
        EventBus.getInstance().publish(new LoadDependencyGraphEvent(projectSmartGraphVertex.getUnderlyingVertex()));
    }
}
