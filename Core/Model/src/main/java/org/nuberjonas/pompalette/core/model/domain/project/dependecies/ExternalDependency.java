package org.nuberjonas.pompalette.core.model.domain.project.dependecies;

import org.nuberjonas.pompalette.core.model.domain.project.ProjectCoordinates;

import java.util.Objects;

public record ExternalDependency(DependencyCoordinates dependencyCoordinates) implements Dependency {

    @Override
    public ProjectCoordinates getCoordinates() {
        return ProjectCoordinates.coordinatesFor(dependencyCoordinates);
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
        var artifactName = dependencyCoordinates.artifactId().split("\\.");
        return artifactName[artifactName.length - 1];
    }
}
