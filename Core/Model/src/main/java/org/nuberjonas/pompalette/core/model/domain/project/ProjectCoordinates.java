package org.nuberjonas.pompalette.core.model.domain.project;

import org.nuberjonas.pompalette.core.model.domain.project.dependecies.DependencyCoordinates;

import java.util.Objects;

public record ProjectCoordinates(String groupId, String artifactId, String version) {

    public static ProjectCoordinates coordinatesFor(DependencyCoordinates dependencyCoordinates){
        return new ProjectCoordinates(dependencyCoordinates.groupId(), dependencyCoordinates.artifactId(), null);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProjectCoordinates that = (ProjectCoordinates) o;
        return Objects.equals(groupId, that.groupId) && Objects.equals(artifactId, that.artifactId) && Objects.equals(version, that.version);
    }

    @Override
    public int hashCode() {
        return Objects.hash(groupId, artifactId, version);
    }

    @Override
    public String toString() {
        return groupId + ":" + artifactId + ":" + version;
    }
}
