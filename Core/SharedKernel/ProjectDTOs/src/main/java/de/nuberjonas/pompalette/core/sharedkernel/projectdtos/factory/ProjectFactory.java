package de.nuberjonas.pompalette.core.sharedkernel.projectdtos.factory;

import de.nuberjonas.pompalette.core.sharedkernel.projectdtos.beans.project.ProjectDTO;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public interface ProjectFactory<T> {
    Optional<ProjectDTO> createProjectDTO(T project);
    Optional<T> createProject(ProjectDTO projectDTO);

    default <I, U> List<U> createList(List<I> items, MapperFunction<I, U> mapperFunction) {
        return items.stream()
                .map(mapperFunction::map)
                .collect(Collectors.toList());
    }
}
