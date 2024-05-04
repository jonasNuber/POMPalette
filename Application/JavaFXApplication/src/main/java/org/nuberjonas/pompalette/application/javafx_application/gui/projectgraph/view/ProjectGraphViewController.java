package org.nuberjonas.pompalette.application.javafx_application.gui.projectgraph.view;

import com.brunomnsilva.smartgraph.containers.ContentZoomScrollPane;
import com.brunomnsilva.smartgraph.graph.Edge;
import com.brunomnsilva.smartgraph.graph.Vertex;
import com.brunomnsilva.smartgraph.graphview.SmartGraphPanel;
import javafx.fxml.FXML;
import javafx.scene.layout.StackPane;
import org.nuberjonas.pompalette.application.javafx_application.events.LoadProjectInitializationEvent;
import org.nuberjonas.pompalette.core.model.ModelFactory;
import org.nuberjonas.pompalette.core.model.graph.EdgeType;
import org.nuberjonas.pompalette.core.model.graph.ProjectGraph;
import org.nuberjonas.pompalette.core.model.project.MavenProject;
import org.nuberjonas.pompalette.infrastructure.eventbus.EventBus;
import org.nuberjonas.pompalette.infrastructure.eventbus.Subscribable;
import org.nuberjonas.pompalette.infrastructure.eventbus.events.Event;

import java.util.Set;

public class ProjectGraphViewController implements Subscribable {

    @FXML
    private StackPane projectBasePane;

    private ProjectGraph graph;
    private SmartGraphPanel<MavenProject, EdgeType> graphView;

    private boolean isGraphInitialized;

    public void init(){
        isGraphInitialized = false;
        graph = new ProjectGraph();
        graphView = new SmartGraphPanel<>(graph);
        graphView.setPrefSize(2000, 2000);
        graphView.setAutomaticLayout(true);

        var zoomScrollPane = new ContentZoomScrollPane(graphView);

        projectBasePane.getChildren().add(zoomScrollPane);

        EventBus.getInstance().subscribe(LoadProjectInitializationEvent.class, this);
    }

    private void loadProjectGraph(String projectPath){
        if(isGraphInitialized == false){
            graphView.init();
            isGraphInitialized = true;
        }

        ModelFactory factory = new ModelFactory();
        var newGraph = factory.getProjectGraph(projectPath);

        for(Vertex<MavenProject> vertex: newGraph.vertices()){
            graph.insertVertex(vertex.element());
        }

        for(Edge<EdgeType, MavenProject> rel : newGraph.edges()){
            graph.insertEdge(rel.vertices()[0], rel.vertices()[1], rel.element());
        }

        graphView.update();
    }

    @Override
    public void handleEvent(Event<?> event) {
        if(event instanceof LoadProjectInitializationEvent loadEvent){
            loadProjectGraph(loadEvent.getData());
        }
    }

    @Override
    public Set<Class<?>> supports() {
        return Set.of(LoadProjectInitializationEvent.class);
    }
}
