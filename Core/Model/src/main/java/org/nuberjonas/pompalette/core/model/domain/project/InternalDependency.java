package org.nuberjonas.pompalette.core.model.domain.project;

import com.brunomnsilva.smartgraph.graphview.SmartShapeTypeSource;

import java.util.Objects;

public class InternalDependency implements Project{

    private MavenProject project;

    public InternalDependency(MavenProject project) {
        this.project = project;
    }

    @Override
    @SmartShapeTypeSource
    public String getShape() {
        return "triangle";
    }

    @Override
    public ProjectCoordinates getCoordinates() {
        return project.getCoordinates();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        InternalDependency that = (InternalDependency) o;
        return Objects.equals(project, that.project);
    }

    @Override
    public int hashCode() {
        return Objects.hash(project);
    }

    @Override
    public String toString() {
        return project.getName();
    }
}
