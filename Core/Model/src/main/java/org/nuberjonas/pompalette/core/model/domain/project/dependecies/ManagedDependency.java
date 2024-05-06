package org.nuberjonas.pompalette.core.model.domain.project.dependecies;

import org.nuberjonas.pompalette.core.model.domain.project.ProjectCoordinates;

import java.util.Objects;

public class ManagedDependency implements Dependency{

    private Dependency dependency;

    public ManagedDependency(Dependency dependency) {
        this.dependency = dependency;
    }

    @Override
    public ProjectCoordinates getCoordinates() {
        return dependency.getCoordinates();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ManagedDependency that = (ManagedDependency) o;
        return Objects.equals(dependency, that.dependency);
    }

    @Override
    public int hashCode() {
        return Objects.hash(dependency);
    }

    @Override
    public String toString() {
        var name = dependency.getCoordinates().artifactId().split("\\.");
        return name[name.length -1];
    }
}
