package org.nuberjonas.pompalette.core.model.domain.project.dependecies;

import org.nuberjonas.pompalette.core.model.domain.project.ProjectCoordinates;

import java.util.Objects;

public class ExternalDependency implements Dependency {

    private final ProjectCoordinates dependencyCoordinates;
    private final DependencyScope scope;
    private final DependencyType type;

    public ExternalDependency(ProjectCoordinates dependencyCoordinates, DependencyScope scope, DependencyType type) {
        this.dependencyCoordinates = dependencyCoordinates;
        this.scope = scope;
        this.type = type;
    }

    @Override
    public ProjectCoordinates getCoordinates() {
        return dependencyCoordinates;
    }

    @Override
    public DependencyScope getScope() {
        return scope;
    }

    @Override
    public DependencyType getType() {
        return type;
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
        return artifactName[artifactName.length -1];
    }
}
