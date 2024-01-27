package de.nuberjonas.pompalette.infrastructure.parsing.projectparsingmavenimpl.factory;

import de.nuberjonas.pompalette.core.sharedkernel.projectdtos.beans.project.ProjectDTO;
import de.nuberjonas.pompalette.core.sharedkernel.projectdtos.factory.ProjectFactory;
import de.nuberjonas.pompalette.infrastructure.parsing.projectparsingmavenimpl.mapper.MavenProjectMapper;
import de.nuberjonas.pompalette.infrastructure.parsing.projectparsingmavenimpl.mapper.MavenProjectMapperImpl;
import org.apache.maven.model.Model;

public class MavenProjectFactory implements ProjectFactory<Model> {

    private final MavenProjectMapper mapper;

    public MavenProjectFactory() {
        mapper = new MavenProjectMapperImpl(); }

    @Override
    public ProjectDTO createProjectDTO(Model project) {
        return mapper.mapToDto(project);
    }

    @Override
    public Model createProject(ProjectDTO projectDTO) {
        return mapper.mapToModel(projectDTO);
    }
}
