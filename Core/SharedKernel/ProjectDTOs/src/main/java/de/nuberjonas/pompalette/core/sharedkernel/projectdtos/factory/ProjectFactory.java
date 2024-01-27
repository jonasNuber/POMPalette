package de.nuberjonas.pompalette.core.sharedkernel.projectdtos.factory;

import de.nuberjonas.pompalette.core.sharedkernel.projectdtos.beans.project.ProjectDTO;

public interface ProjectFactory<T> {
    public ProjectDTO createProjectDTO(T project);
    public T createProject(ProjectDTO projectDTO);
}
