package org.nuberjonas.pompalette.core.model.domain.project.dependecies;

import org.nuberjonas.pompalette.core.model.domain.project.ProjectCoordinates;

import java.util.Objects;

public final class TransitiveDependency implements Dependency {

    private final Dependency dependency;

    public TransitiveDependency(Dependency dependency) {
        this.dependency = dependency;
    }

    @Override
    public ProjectCoordinates getCoordinates() {
        return dependency.getCoordinates();
    }

    @Override
    public DependencyCoordinates dependencyCoordinates() {
        return dependency.dependencyCoordinates();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TransitiveDependency that = (TransitiveDependency) o;
        return Objects.equals(dependency, that.dependency);
    }

    @Override
    public int hashCode() {
        return Objects.hash(dependency);
    }

    @Override
    public String toString() {
        return dependency.toString();
    }
}
