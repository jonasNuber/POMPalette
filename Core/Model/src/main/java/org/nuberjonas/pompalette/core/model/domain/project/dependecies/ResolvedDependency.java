package org.nuberjonas.pompalette.core.model.domain.project.dependecies;

import com.brunomnsilva.smartgraph.graphview.SmartLabelSource;
import org.nuberjonas.pompalette.core.model.domain.project.MavenProject;
import org.nuberjonas.pompalette.core.model.domain.project.ProjectCoordinates;

import java.util.Objects;

public record ResolvedDependency(MavenProject dependentProject, ManagedDependency managedDependency) implements Dependency{

    @Override
    public ProjectCoordinates getCoordinates() {
        return managedDependency.getCoordinates();
    }

    @Override
    public DependencyCoordinates dependencyCoordinates() {
        return managedDependency.dependencyCoordinates();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ResolvedDependency that = (ResolvedDependency) o;
        return Objects.equals(dependentProject, that.dependentProject) && Objects.equals(managedDependency, that.managedDependency);
    }

    @Override
    public int hashCode() {
        return Objects.hash(dependentProject, managedDependency);
    }

    @SmartLabelSource
    public String getLabel(){
        return "";
    }

    @Override
    public String toString() {
        return managedDependency.toString();
    }
}
