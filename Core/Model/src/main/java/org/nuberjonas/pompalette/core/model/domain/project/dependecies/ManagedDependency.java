package org.nuberjonas.pompalette.core.model.domain.project.dependecies;

import org.nuberjonas.pompalette.core.model.domain.project.MavenProject;
import org.nuberjonas.pompalette.core.model.domain.project.ProjectCoordinates;

import java.util.Objects;

public final class ManagedDependency implements Dependency{

    private final Dependency dependency;
    private final MavenProject managingProject;

    public ManagedDependency(Dependency dependency, MavenProject managingProject) {
        if(dependency instanceof ManagedDependency){
            throw new IllegalArgumentException(String.format("Dependency %s is already a managed dependency", dependency));
        }

        this.dependency = dependency;
        this.managingProject = managingProject;
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
        ManagedDependency that = (ManagedDependency) o;
        return Objects.equals(dependency, that.dependency) && Objects.equals(managingProject, that.managingProject);
    }

    @Override
    public int hashCode() {
        return Objects.hash(dependency, managingProject);
    }

    @Override
    public String toString() {
        return "";
    }
}
