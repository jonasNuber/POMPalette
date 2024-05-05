package org.nuberjonas.pompalette.application.javafx_application.gui.projectgraph.view;

import javafx.fxml.FXML;
import javafx.scene.layout.StackPane;
import org.nuberjonas.pompalette.application.javafx_application.events.LoadProjectInitializationEvent;
import org.nuberjonas.pompalette.application.javafx_application.extensions.ProjectGraphPanel;
import org.nuberjonas.pompalette.application.javafx_application.extensions.ProjectGraphZoomAndScrollPane;
import org.nuberjonas.pompalette.application.javafx_application.gui.projectgraph.viewmodel.ProjectGraphViewModel;
import org.nuberjonas.pompalette.core.model.domain.graph.EdgeType;
import org.nuberjonas.pompalette.core.model.domain.project.MavenProject;
import org.nuberjonas.pompalette.infrastructure.eventbus.EventBus;
import org.nuberjonas.pompalette.infrastructure.eventbus.Subscribable;
import org.nuberjonas.pompalette.infrastructure.eventbus.events.Event;

import java.nio.file.Path;
import java.util.Set;

public class ProjectGraphViewController implements Subscribable {

    @FXML
    private StackPane projectBasePane;

    private ProjectGraphPanel<MavenProject, EdgeType> graphView;

    private ProjectGraphViewModel viewModel;

    private boolean isGraphInitialized;

    public void init(ProjectGraphViewModel viewModel){
        this.viewModel = viewModel;

        isGraphInitialized = false;

        graphView = new ProjectGraphPanel<>(viewModel.getGraph());
        graphView.setPrefSize(5000, 5000);
        graphView.setAutomaticLayout(true);

        projectBasePane.getChildren().add(new ProjectGraphZoomAndScrollPane(graphView));

        EventBus.getInstance().subscribe(LoadProjectInitializationEvent.class, this);
    }

    @Override
    public void handleEvent(Event<?> event) {
        if(event instanceof LoadProjectInitializationEvent loadEvent){
            loadProjectGraph(loadEvent.getData());
        }
    }



    private void loadProjectGraph(Path projectPath){
        if(isGraphInitialized == false){
            graphView.init();
            isGraphInitialized = true;
        }

        viewModel.loadProjectGraph(projectPath);
        graphView.update();
    }

    @Override
    public Set<Class<?>> supports() {
        return Set.of(LoadProjectInitializationEvent.class);
    }
}
