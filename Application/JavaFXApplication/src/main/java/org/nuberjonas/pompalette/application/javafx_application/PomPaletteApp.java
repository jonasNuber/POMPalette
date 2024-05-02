package org.nuberjonas.pompalette.application.javafx_application;

import com.brunomnsilva.smartgraph.graph.Graph;
import com.brunomnsilva.smartgraph.graph.GraphEdgeList;
import com.brunomnsilva.smartgraph.graphview.SmartGraphPanel;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class PomPaletteApp extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        //stage.setTitle("Test");

        //FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("MainView.fxml"));
        //Parent root = loader.load();
        //Scene scene = new Scene(root);
        //stage.setScene(scene);
        //stage.show();


        Graph<String, String> g = new GraphEdgeList<>();

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

        SmartGraphPanel<String, String> graphView = new SmartGraphPanel<>(g);
        graphView.getStylesheets().add(getClass().getClassLoader().getResource("smartgraph.css").toExternalForm());
        graphView.setAutomaticLayout(true);

        Scene scene = new Scene(graphView, 1024, 768);

        stage.setTitle("JavaFXGraph Visualization");
        stage.setScene(scene);
        stage.show();

        graphView.init();
    }
}
