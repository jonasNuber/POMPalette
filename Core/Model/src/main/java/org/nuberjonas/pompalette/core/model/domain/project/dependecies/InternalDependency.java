package org.nuberjonas.pompalette.core.model.domain.project.dependecies;

import org.nuberjonas.pompalette.core.model.domain.project.MavenProject;
import org.nuberjonas.pompalette.core.model.domain.project.ProjectCoordinates;

import java.util.Objects;

public class InternalDependency implements Dependency {

    private MavenProject project;

    public InternalDependency(MavenProject project) {
        this.project = project;
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
