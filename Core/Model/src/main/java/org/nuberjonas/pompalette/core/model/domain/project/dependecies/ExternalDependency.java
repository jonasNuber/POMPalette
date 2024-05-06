package org.nuberjonas.pompalette.core.model.domain.project.dependecies;

import org.nuberjonas.pompalette.core.model.domain.project.ProjectCoordinates;

import java.util.Objects;

public class ExternalDependency implements Dependency {

    private final ProjectCoordinates dependencyCoordinates;

    public ExternalDependency(ProjectCoordinates dependencyCoordinates) {
        this.dependencyCoordinates = dependencyCoordinates;
    }

    @Override
    public ProjectCoordinates getCoordinates() {
        return dependencyCoordinates;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ExternalDependency that = (ExternalDependency) o;
        return Objects.equals(dependencyCoordinates, that.dependencyCoordinates);
    }

    @Override
    public int hashCode() {
        return Objects.hash(dependencyCoordinates);
    }

    @Override
    public String toString() {
        var name = dependencyCoordinates.artifactId().split("\\.");
        return name[name.length -1];
    }
}
