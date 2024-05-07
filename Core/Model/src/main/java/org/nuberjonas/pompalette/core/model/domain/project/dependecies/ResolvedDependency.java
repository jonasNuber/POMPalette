package org.nuberjonas.pompalette.core.model.domain.project.dependecies;

import org.nuberjonas.pompalette.core.model.domain.project.Project;
import org.nuberjonas.pompalette.core.model.domain.project.ProjectCoordinates;

import java.util.Objects;

public class ResolvedDependency implements Dependency{

    private final ProjectCoordinates coordinates;
    private final DependencyScope scope;
    private final DependencyType type;
    private ManagedDependency managedDependency;
    private Project managingProject;

    public ResolvedDependency(ProjectCoordinates coordinates, DependencyScope scope, DependencyType type) {
        if(coordinates.version() == null) {
            this.coordinates = coordinates;
            this.scope = scope;
            this.type = type;
        } else {
            throw new IllegalArgumentException("Version Coordinate is not allowed to be set for a ResolvedDependency.");
        }
    }

    @Override
    public ProjectCoordinates getCoordinates() {
        return coordinates;
    }

    @Override
    public DependencyScope getScope() {
        return scope;
    }

    @Override
    public DependencyType getType() {
        return type;
    }

    public ManagedDependency getManagedDependency() {
        return managedDependency;
    }

    public void setManagedDependency(ManagedDependency managedDependency) {
        if(isSameDependency(managedDependency)){
            this.managedDependency = managedDependency;
        } else {
            throw new IllegalArgumentException(String.format("ManagedDependency: %s is not resolvable to: %s", managedDependency.getCoordinates(), coordinates));
        }
    }

    private boolean isSameDependency(ManagedDependency managedDependency){
        return managedDependency.getCoordinates().groupId().equals(coordinates.groupId()) &&
                managedDependency.getCoordinates().artifactId().equals(coordinates.artifactId());
    }

    public Project getManagingProject() {
        return managingProject;
    }

    public void setManagingProject(Project managingProject) {
        this.managingProject = managingProject;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ResolvedDependency that = (ResolvedDependency) o;
        return Objects.equals(coordinates, that.coordinates) && Objects.equals(managedDependency, that.managedDependency) && Objects.equals(managingProject, that.managingProject);
    }

    @Override
    public int hashCode() {
        return Objects.hash(coordinates, managedDependency, managingProject);
    }

    @Override
    public String toString() {
        var artifactName = coordinates.artifactId().split("\\.");
        return artifactName[artifactName.length -1];
    }
}
