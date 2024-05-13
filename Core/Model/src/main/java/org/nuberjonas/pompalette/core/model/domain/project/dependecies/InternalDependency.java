package org.nuberjonas.pompalette.core.model.domain.project.dependecies;

import org.apache.commons.lang3.StringUtils;
import org.nuberjonas.pompalette.core.model.domain.project.MavenProject;
import org.nuberjonas.pompalette.core.model.domain.project.ProjectCoordinates;

import java.util.Objects;

public final class InternalDependency implements Dependency {

    private final MavenProject project;
    private final DependencyCoordinates coordinates;

    public InternalDependency(MavenProject project) {
        this.project = project;
        this.coordinates = new DependencyCoordinates(project.getCoordinates().groupId(), project.getCoordinates().artifactId());
    }

    public MavenProject getProject(){
        return project;
    }

    @Override
    public ProjectCoordinates getCoordinates() {
        return ProjectCoordinates.coordinatesFor(coordinates);
    }

    @Override
    public DependencyCoordinates dependencyCoordinates() {
        return coordinates;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        InternalDependency that = (InternalDependency) o;
        return Objects.equals(coordinates, that.coordinates);
    }

    @Override
    public int hashCode() {
        return Objects.hash(coordinates);
    }

    @Override
    public String toString() {
        var artifactName = coordinates.artifactId().split("\\.");

        return StringUtils.isNotEmpty(project.getName()) ? project.getName() : artifactName[artifactName.length - 1] ;
    }
}
