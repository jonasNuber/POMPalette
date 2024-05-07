package org.nuberjonas.pompalette.core.model.domain.project.dependecies;

import org.apache.commons.lang3.StringUtils;
import org.nuberjonas.pompalette.core.model.domain.project.MavenProject;
import org.nuberjonas.pompalette.core.model.domain.project.ProjectCoordinates;

import java.util.Objects;

public class InternalDependency implements Dependency {

    private final MavenProject project;
    private final DependencyScope scope;
    private final DependencyType type;

    public InternalDependency(MavenProject project, DependencyScope scope, DependencyType type) {
        this.project = project;
        this.scope = scope;
        this.type = type;
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
    public DependencyScope getScope() {
        return scope;
    }

    @Override
    public DependencyType getType() {
        return type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(project);
    }

    @Override
    public String toString() {
        var artifactName = project.getCoordinates().artifactId().split("\\.");

        return StringUtils.isNotEmpty(project.getName()) ? project.getName() : artifactName[artifactName.length - 1] ;
    }
}
