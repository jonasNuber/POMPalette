package org.nuberjonas.pompalette.infrastructure.parsing.projectparsingmavenimpl;

import org.apache.maven.model.Model;
import org.apache.maven.model.io.xpp3.MavenXpp3Reader;
import org.apache.maven.model.io.xpp3.MavenXpp3Writer;
import org.nuberjonas.pompalette.core.sharedkernel.projectdtos.beans.MultiModuleProjectDTO;
import org.nuberjonas.pompalette.core.sharedkernel.projectdtos.beans.ProjectDTO;
import org.nuberjonas.pompalette.infrastructure.parsing.projectparsingapi.ProjectParsingService;
import org.nuberjonas.pompalette.infrastructure.serviceloading.ServiceDiscovery;
import org.nuberjonas.pompalette.mapping.mappingapi.mapper.MapperService;

import java.nio.file.Path;

public class MavenProjectParsingService implements ProjectParsingService {

    private final MavenXpp3Reader reader;
    private final MavenXpp3Writer writer;
    private final MapperService<Model, ProjectDTO> mapperService;

    public static void main(String[] args) {
        MavenProjectParsingService test = new MavenProjectParsingService();
    }

    @SuppressWarnings("unchecked")
    public MavenProjectParsingService() {
        reader = new MavenXpp3Reader();
        writer = new MavenXpp3Writer();
        mapperService = ServiceDiscovery.loadService(MapperService.class,
                provider -> provider instanceof MapperService &&
                ((MapperService<?, ?>) provider).sourceType().equals(Model.class) &&
                ((MapperService<?, ?>) provider).destinationType().equals(ProjectDTO.class));
    }

    @Override
    public ProjectDTO loadProject(Path projectPath) {

        return null;
    }

    @Override
    public MultiModuleProjectDTO loadMultiModuleProject(Path projectPath) {
        return null;
    }

    @Override
    public void writeProject(ProjectDTO project) {

    }

    @Override
    public void writeMultiModuleProject(MultiModuleProjectDTO multiModuleProject) {

    }
}
