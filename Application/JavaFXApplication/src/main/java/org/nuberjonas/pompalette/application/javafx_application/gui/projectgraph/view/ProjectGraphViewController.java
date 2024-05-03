package org.nuberjonas.pompalette.application.javafx_application.gui.projectgraph.view;

import com.brunomnsilva.smartgraph.containers.ContentZoomScrollPane;
import com.brunomnsilva.smartgraph.graph.Graph;
import com.brunomnsilva.smartgraph.graph.GraphEdgeList;
import com.brunomnsilva.smartgraph.graphview.SmartGraphPanel;
import javafx.fxml.FXML;
import javafx.scene.layout.StackPane;
import org.nuberjonas.pompalette.application.javafx_application.events.LoadProjectInitializationEvent;
import org.nuberjonas.pompalette.infrastructure.eventbus.EventBus;
import org.nuberjonas.pompalette.infrastructure.eventbus.Subscribable;
import org.nuberjonas.pompalette.infrastructure.eventbus.events.Event;

import java.util.Set;

public class ProjectGraphViewController implements Subscribable {

    @FXML
    private StackPane projectBasePane;

    private Graph<String, String> graph;
    private SmartGraphPanel<String, String> graphView;

    private boolean isGraphInitialized;

    public void init(){
        isGraphInitialized = false;
        graph = new GraphEdgeList<>();
        graphView = new SmartGraphPanel<>(graph);
        graphView.setPrefSize(1000, 1000);
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

        graph.insertVertex("A");
        graph.insertVertex("B");
        graph.insertVertex("C");
        graph.insertVertex("D");
        graph.insertVertex("E");
        graph.insertVertex("F");
        graph.insertVertex("G");

        graph.insertEdge("A", "B", "1");
        graph.insertEdge("A", "C", "2");
        graph.insertEdge("A", "D", "3");
        graph.insertEdge("A", "E", "4");
        graph.insertEdge("A", "F", "5");
        graph.insertEdge("A", "G", "6");

        graph.insertVertex("H");
        graph.insertVertex("I");
        graph.insertVertex("J");
        graph.insertVertex("K");
        graph.insertVertex("L");
        graph.insertVertex("M");
        graph.insertVertex("N");

        graph.insertEdge("H", "I", "7");
        graph.insertEdge("H", "J", "8");
        graph.insertEdge("H", "K", "9");
        graph.insertEdge("H", "L", "10");
        graph.insertEdge("H", "M", "11");
        graph.insertEdge("H", "N", "12");

        graph.insertEdge("A", "H", "0");


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
