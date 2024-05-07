package org.nuberjonas.pompalette.core.model.domain.project.dependecies;

import org.nuberjonas.pompalette.core.model.domain.project.ProjectCoordinates;

import java.util.Objects;

public class ManagedDependency implements Dependency{

    private final Dependency dependency;
    private final DependencyScope scope;
    private final DependencyType type;

    public ManagedDependency(Dependency dependency, DependencyScope scope, DependencyType type) {
        this.dependency = dependency;
        this.scope = scope;
        this.type = type;
    }

    @Override
    public ProjectCoordinates getCoordinates() {
        return dependency.getCoordinates();
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
        ManagedDependency that = (ManagedDependency) o;
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
