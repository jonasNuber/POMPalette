package org.nuberjonas.pompalette.application.javafx_application.extensions;

import com.brunomnsilva.smartgraph.graphview.SmartGraphVertex;
import com.brunomnsilva.smartgraph.graphview.UtilitiesPoint2D;
import javafx.geometry.Point2D;

import java.util.ArrayList;
import java.util.List;

public class CircularPlacementStrategy implements ProjectGraphPlacementStrategy{
    private static final int RADIUS_PADDING = 4;

    @Override
    public <V, E> void place(double width, double height, ProjectGraphPanel<V, E> projectGraphPanel) {
        // Sort vertices by their label
        List<SmartGraphVertex<V>> vertices = new ArrayList<>(projectGraphPanel.getSmartVertices());

        vertices.sort((v1, v2) -> {
            V e1 = v1.getUnderlyingVertex().element();
            V e2 = v2.getUnderlyingVertex().element();
            return projectGraphPanel.getVertexLabelFor(e1).compareTo(projectGraphPanel.getVertexLabelFor(e2));
        });

        //place first vertex at north position, others in clockwise manner
        Point2D center = new Point2D(width / 2, height / 2);
        int N = vertices.size();
        double angleIncrement = -360f / N;
        boolean first = true;
        Point2D p = null;

        for (SmartGraphVertex<V> vertex : vertices) {

            if (first) {
                //verify the smallest width and height.
                if(width > height)
                    p = new Point2D(center.getX(),
                            center.getY() - height / 2 + vertex.getRadius() * RADIUS_PADDING);
                else
                    p = new Point2D(center.getX(),
                            center.getY() - width / 2 + vertex.getRadius() * RADIUS_PADDING);

                first = false;
            } else {
                p = UtilitiesPoint2D.rotate(p, center, angleIncrement);
            }

            vertex.setPosition(p.getX(), p.getY());
        }
    }
}
