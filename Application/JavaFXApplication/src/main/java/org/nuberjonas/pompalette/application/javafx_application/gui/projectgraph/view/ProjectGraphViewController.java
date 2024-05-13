package org.nuberjonas.pompalette.application.javafx_application.gui.projectgraph.view;

import com.brunomnsilva.smartgraph.graphview.SmartGraphVertex;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.layout.StackPane;
import org.nuberjonas.pompalette.application.javafx_application.events.LoadDependencyGraphEvent;
import org.nuberjonas.pompalette.application.javafx_application.events.LoadProjectInitializationEvent;
import org.nuberjonas.pompalette.application.javafx_application.events.ShowDependencyGraphEvent;
import org.nuberjonas.pompalette.application.javafx_application.extensions.ProjectGraphPanel;
import org.nuberjonas.pompalette.application.javafx_application.extensions.ProjectGraphZoomAndScrollPane;
import org.nuberjonas.pompalette.application.javafx_application.gui.projectgraph.viewmodel.ProjectGraphViewModel;
import org.nuberjonas.pompalette.core.model.domain.graph.ProjectGraph;
import org.nuberjonas.pompalette.core.model.domain.graph.relationship.Relationship;
import org.nuberjonas.pompalette.core.model.domain.project.Project;
import org.nuberjonas.pompalette.infrastructure.eventbus.EventBus;
import org.nuberjonas.pompalette.infrastructure.eventbus.Observer;
import org.nuberjonas.pompalette.infrastructure.eventbus.Subscribable;
import org.nuberjonas.pompalette.infrastructure.eventbus.events.Event;

import java.nio.file.Path;
import java.util.Set;

public class ProjectGraphViewController implements Subscribable, Observer {

    @FXML
    private StackPane projectBasePane;

    private ProjectGraphPanel<Project, Relationship> graphView;

    private ProjectGraphViewModel viewModel;

    private boolean isGraphInitialized;

    public void init(ProjectGraphViewModel viewModel){
        this.viewModel = viewModel;

        isGraphInitialized = false;

        graphView = new ProjectGraphPanel<>(viewModel.getFullGraph());
        graphView.setPrefSize(5000, 5000);
        graphView.setAutomaticLayout(true);

        graphView.setVertexDoubleClickAction(projectSmartGraphVertex -> loadDependencyGraph(projectSmartGraphVertex, false));

        projectBasePane.getChildren().add(new ProjectGraphZoomAndScrollPane(graphView));

        viewModel.addObserver(this);
        EventBus.getInstance().subscribe(LoadProjectInitializationEvent.class, this);
        EventBus.getInstance().subscribe(LoadDependencyGraphEvent.class, this);
    }

    @Override
    public void handleEvent(Event<?> event) {
        if(event instanceof LoadProjectInitializationEvent loadEvent){
            loadProjectGraph(loadEvent.getData());
        } else if(event instanceof LoadDependencyGraphEvent loadDependencyGraphEvent) {
            loadDependencyGraph(loadDependencyGraphEvent.getData(), true);
        }
    }

    @Override
    public Set<Class<?>> supports() {
        return Set.of(LoadProjectInitializationEvent.class, LoadDependencyGraphEvent.class);
    }

    @Override
    public void update(Object data) {
        if(data instanceof ProjectGraph){
            graphView.setGraph((ProjectGraph) data);
            graphView.update();
        }
    }

    private void loadProjectGraph(Path projectPath){
        if(!isGraphInitialized){
            graphView.init();
            isGraphInitialized = true;
        }

        viewModel.loadProjectGraph(projectPath);
    }

    private void loadDependencyGraph(SmartGraphVertex<Project> projectSmartGraphVertex, boolean isDependency) {
        viewModel.loadDependencySubGraph(projectSmartGraphVertex.getUnderlyingVertex(), isDependency).thenAccept(
                dependencySubGraph ->
                        Platform.runLater(
                                () -> EventBus.getInstance().publish(new ShowDependencyGraphEvent(dependencySubGraph))
                        ));
    }
}
