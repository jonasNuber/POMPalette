package org.nuberjonas.pompalette.core.model.application.mapper;

import org.nuberjonas.pompalette.core.model.domain.project.MavenProject;
import org.nuberjonas.pompalette.core.model.domain.project.dependecies.DependencyCoordinates;
import org.nuberjonas.pompalette.core.model.domain.project.dependecies.ManagedDependency;
import org.nuberjonas.pompalette.core.sharedkernel.projectdtos.beans.ProjectDTO;

import java.util.List;
import java.util.Properties;

public final class ProjectEnvironment {

    private final ProjectDescriptor descriptor;
    private final Properties properties;

    public ProjectEnvironment() {
        this.descriptor = new ProjectDescriptor();
        this.properties = new Properties();
    }

    public void addProjectPair(MavenProject mavenProject, ProjectDTO projectDTO, MavenProject parent){
       descriptor.addProjectPair(mavenProject, projectDTO, parent);
    }

    public MavenProject getMavenProjectFor(DependencyCoordinates coordinates){
        return descriptor.getMavenProjectFor(coordinates);
    }

    public ProjectDTO getProjectDTOFor(DependencyCoordinates coordinates){
        return descriptor.getProjectDTOFor(coordinates);
    }

    public boolean projectExists(DependencyCoordinates coordinates){
        return descriptor.projectExists(coordinates);
    }

    public void addManagedDependenciesTo(MavenProject mavenProject, List<ManagedDependency> managedDependencies){
        descriptor.addManagedDependenciesTo(mavenProject, managedDependencies);
    }

    public void addManagedDependenciesTo(DependencyCoordinates coordinates, List<ManagedDependency> managedDependencies){
        descriptor.addManagedDependenciesTo(coordinates, managedDependencies);
    }

    public ManagedDependency findManagedDependency(MavenProject project, DependencyCoordinates dependencyCoordinates) {
        return descriptor.findManagedDependency(project, dependencyCoordinates);
    }

    public void addProperty(String key, String value){
        properties.put(key, value);
    }

    public void addAllProperties(Properties properties){
        this.properties.putAll(properties);
    }

    public ProjectDescriptor getDescriptor() {
        return descriptor;
    }

    public Properties getProperties() {
        return properties;
    }
}
