package org.nuberjonas.pompalette.core.model.project;

import java.util.Objects;

public class MavenProject {

    private ProjectCoordinates coordinates;
    private String name;

    public MavenProject(ProjectCoordinates coordinates, String name){
        this.coordinates = coordinates;
        this.name = name;
    }

    public ProjectCoordinates getCoordinates() {
        return coordinates;
    }

    public String getName() {
        return name;
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
        return name;
    }
}
