package de.nuberjonas.pompalette.core.sharedkernel.projectdtos.factory;

import de.nuberjonas.pompalette.core.sharedkernel.projectdtos.beans.project.ProjectDTO;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public interface ProjectFactory<T> {
    public Optional<ProjectDTO> createProjectDTO(T project);
    public Optional<T> createProject(ProjectDTO projectDTO);

    default <T, U> List<U> createList(List<T> items, MapperFunction<T, U> mapperFunction) {
        return items.stream()
                .map(mapperFunction::map)
                .collect(Collectors.toList());
    }
}
