package org.nuberjonas.pompalette.application.javafx_application.projectgraph.view;

import com.brunomnsilva.smartgraph.graph.Graph;
import com.brunomnsilva.smartgraph.graph.GraphEdgeList;
import com.brunomnsilva.smartgraph.graphview.SmartGraphPanel;
import javafx.fxml.FXML;
import javafx.scene.layout.StackPane;

public class ProjectGraphViewController {
    @FXML
    private StackPane projectGraphPane;

    private SmartGraphPanel<String, String> graphView;

    public void init(StackPane projectGraphPane){
        Graph<String, String> g = new GraphEdgeList<>();
        graphView = new SmartGraphPanel<>(g);
        g.insertVertex("A");
        g.insertVertex("B");
        g.insertVertex("C");
        g.insertVertex("D");
        g.insertVertex("E");
        g.insertVertex("F");
        g.insertVertex("G");

        g.insertEdge("A", "B", "1");
        g.insertEdge("A", "C", "2");
        g.insertEdge("A", "D", "3");
        g.insertEdge("A", "E", "4");
        g.insertEdge("A", "F", "5");
        g.insertEdge("A", "G", "6");

        g.insertVertex("H");
        g.insertVertex("I");
        g.insertVertex("J");
        g.insertVertex("K");
        g.insertVertex("L");
        g.insertVertex("M");
        g.insertVertex("N");

        g.insertEdge("H", "I", "7");
        g.insertEdge("H", "J", "8");
        g.insertEdge("H", "K", "9");
        g.insertEdge("H", "L", "10");
        g.insertEdge("H", "M", "11");
        g.insertEdge("H", "N", "12");

        g.insertEdge("A", "H", "0");

        projectGraphPane.getChildren().add(graphView);
        graphView.setAutomaticLayout(true);
    }

    public void initGraph(){
        graphView.init();
    }
}
