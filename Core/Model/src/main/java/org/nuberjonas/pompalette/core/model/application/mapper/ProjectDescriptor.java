package org.nuberjonas.pompalette.core.model.application.mapper;

import org.nuberjonas.pompalette.core.model.application.exceptions.DependencyResolverException;
import org.nuberjonas.pompalette.core.model.domain.project.MavenProject;
import org.nuberjonas.pompalette.core.model.domain.project.dependecies.DependencyCoordinates;
import org.nuberjonas.pompalette.core.model.domain.project.dependecies.ManagedDependency;
import org.nuberjonas.pompalette.core.sharedkernel.projectdtos.beans.ProjectDTO;

import java.util.*;

public class ProjectDescriptor {

    private Map<DependencyCoordinates, MavenProject> mavenProjects;
    private Map<MavenProject, MavenProject> mavenProjectParents;
    private Map<DependencyCoordinates, ProjectDTO> projectDTOs;
    private Map<MavenProject, List<ManagedDependency>> projectManagedDependencies;

    public ProjectDescriptor() {
        mavenProjects = new HashMap<>();
        mavenProjectParents = new HashMap<>();
        projectDTOs = new HashMap<>();
        projectManagedDependencies = new HashMap<>();
    }

    public void addProjectPair(MavenProject mavenProject, ProjectDTO projectDTO, MavenProject parent){
        mavenProjects.put(DependencyCoordinates.coordinatesFor(mavenProject.getCoordinates()), mavenProject);
        mavenProjectParents.put(mavenProject, parent);
        projectDTOs.put(DependencyCoordinates.coordinatesFor(mavenProject.getCoordinates()), projectDTO);
    }

    public MavenProject getMavenProjectFor(DependencyCoordinates coordinates){
        return mavenProjects.get(coordinates);
    }

    public ProjectDTO getProjectDTOFor(DependencyCoordinates coordinates){
        return projectDTOs.get(coordinates);
    }

    public boolean projectExists(DependencyCoordinates coordinates){
        return mavenProjects.containsKey(coordinates);
    }

    public void addManagedDependenciesTo(MavenProject mavenProject, List<ManagedDependency> managedDependencies){
        if(mavenProjects.containsKey(DependencyCoordinates.coordinatesFor(mavenProject.getCoordinates()))){
            projectManagedDependencies.put(mavenProject, managedDependencies);
        } else {
            throw new NoSuchElementException(String.format("Project or Module with the id %s does not exist in the Descriptor", mavenProject));
        }
    }

    public void addManagedDependenciesTo(DependencyCoordinates coordinates, List<ManagedDependency> managedDependencies){
        var project = mavenProjects.get(coordinates);

        if(project != null){
            projectManagedDependencies.put(project, managedDependencies);
        } else {
            throw new NoSuchElementException(String.format("Project or Module with the id %s does not exist in the Descriptor", coordinates));
        }
    }

    public ManagedDependency findManagedDependency(MavenProject project, DependencyCoordinates dependencyCoordinates) {
        var optionalManagedDependencies = Optional.ofNullable(projectManagedDependencies.get(project));

        return optionalManagedDependencies.flatMap(managedDependencies ->
                managedDependencies.stream()
                        .filter(managedDependency -> dependencyCoordinates.equals(managedDependency.dependencyCoordinates()))
                        .findFirst()
        ).orElseGet(() -> {
            var parent = mavenProjectParents.get(project);

            if (parent != null) {
                return findManagedDependency(parent, dependencyCoordinates);
            } else {
                throw new DependencyResolverException(String.format("Managed dependency for %s could not be resolved because no parent in the graph had this dependency managed", dependencyCoordinates));
            }
        });
    }
}
