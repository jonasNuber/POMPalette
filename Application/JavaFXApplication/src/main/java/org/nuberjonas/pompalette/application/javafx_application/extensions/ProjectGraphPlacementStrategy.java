package org.nuberjonas.pompalette.application.javafx_application.extensions;

public interface ProjectGraphPlacementStrategy {
    <V,E> void place(double width, double height, ProjectGraphPanel<V, E> smartGraphPanel);

}
