package de.nuberjonas.pompalette.infrastructure.parsing.projectparsingmavenimpl.mapper;

import de.nuberjonas.pompalette.core.sharedkernel.projectdtos.beans.project.ProjectDTO;
import org.apache.maven.model.Model;
import org.mapstruct.Mapper;

@Mapper
public interface MavenProjectMapper {
    ProjectDTO mapToDto(Model model);
    Model mapToModel(ProjectDTO projectDTO);
}
