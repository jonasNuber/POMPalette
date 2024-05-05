package org.nuberjonas.pompalette.core.model.domain.project;

import com.brunomnsilva.smartgraph.graphview.SmartShapeTypeSource;

public class MavenDependency implements Project{

    private ProjectCoordinates dependencyCoordinates;

    public MavenDependency(ProjectCoordinates dependencyCoordinates) {
        this.dependencyCoordinates = dependencyCoordinates;
    }

    @Override
    @SmartShapeTypeSource
    public String getShape() {
        return "triangle";
    }

    @Override
    public ProjectCoordinates getCoordinates() {
        return dependencyCoordinates;
    }

//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//        MavenDependency that = (MavenDependency) o;
//        return Objects.equals(dependencyCoordinates, that.dependencyCoordinates);
//    }
//
//    @Override
//    public int hashCode() {
//        return Objects.hash(dependencyCoordinates);
//    }

    @Override
    public String toString() {
        var name = dependencyCoordinates.artifactId().split("\\.");
        return name[name.length -1];
    }
}