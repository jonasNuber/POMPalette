package org.nuberjonas.pompalette.core.model.domain.project.dependecies;

import org.nuberjonas.pompalette.core.model.domain.project.ProjectCoordinates;

import java.util.Objects;

public record DependencyCoordinates(String groupId, String artifactId) {

    public static DependencyCoordinates coordinatesFor(ProjectCoordinates projectCoordinates){
        return new DependencyCoordinates(projectCoordinates.groupId(), projectCoordinates.artifactId());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DependencyCoordinates that = (DependencyCoordinates) o;
        return Objects.equals(groupId, that.groupId) && Objects.equals(artifactId, that.artifactId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(groupId, artifactId);
    }

    @Override
    public String toString() {
        return groupId + ":" + artifactId;
    }
}
