package org.nuberjonas.pompalette.core.model.domain.project.dependecies;

import org.nuberjonas.pompalette.core.model.domain.project.Project;
import org.nuberjonas.pompalette.core.model.domain.project.ProjectCoordinates;

public class ResolvedDependency implements Dependency{

    private ProjectCoordinates coordinates;
    private ManagedDependency managedDependency;
    private Project managingProject;

    public ResolvedDependency(ProjectCoordinates coordinates) {
        if(coordinates.version() == null) {
            this.coordinates = coordinates;
        } else {
            throw new IllegalArgumentException("Version Coordinate is not allowed to be set for a ResolvedDependency.");
        }
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
    public ProjectCoordinates getCoordinates() {
        return coordinates;
    }


}
