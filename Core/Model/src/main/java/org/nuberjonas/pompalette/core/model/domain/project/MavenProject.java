package org.nuberjonas.pompalette.core.model.domain.project;

import org.apache.commons.lang3.StringUtils;

import java.util.Objects;

public final class MavenProject implements Project {

    private ProjectCoordinates coordinates;
    private String name;
    private ProjectInformation projectInformation;

    public MavenProject(ProjectCoordinates coordinates, String name, ProjectInformation projectInformation){
        this.coordinates = coordinates;
        this.name = name;
        this.projectInformation = projectInformation;
    }

    @Override
    public ProjectCoordinates getCoordinates() {
        return coordinates;
    }

    public String getName() {
        return name;
    }

    public ProjectInformation getProjectInformation() {
        return projectInformation;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MavenProject that = (MavenProject) o;
        return Objects.equals(coordinates, that.coordinates);
    }

    @Override
    public int hashCode() {
        return Objects.hash(coordinates);
    }

    @Override
    public String toString() {
        var artifactName = coordinates.artifactId().split("\\.");

        return StringUtils.isNotEmpty(name) ? name : artifactName[artifactName.length - 1] ;
    }
}
