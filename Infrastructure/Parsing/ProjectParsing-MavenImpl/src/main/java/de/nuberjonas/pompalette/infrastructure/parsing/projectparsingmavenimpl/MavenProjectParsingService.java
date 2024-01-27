package de.nuberjonas.pompalette.infrastructure.parsing.projectparsingmavenimpl;

import de.nuberjonas.pompalette.core.sharedkernel.projectdtos.beans.project.MultiModuleProjectDTO;
import de.nuberjonas.pompalette.core.sharedkernel.projectdtos.beans.project.ProjectDTO;
import de.nuberjonas.pompalette.core.sharedkernel.projectdtos.factory.ProjectFactory;
import de.nuberjonas.pompalette.infrastructure.parsing.projectparsingapi.ProjectParsingService;
import de.nuberjonas.pompalette.infrastructure.parsing.projectparsingmavenimpl.factory.MavenProjectFactory;
import org.apache.maven.model.io.xpp3.MavenXpp3Reader;
import org.apache.maven.model.io.xpp3.MavenXpp3Writer;

import java.nio.file.Path;

public class MavenProjectParsingService implements ProjectParsingService {

    private final MavenXpp3Reader reader;
    private final MavenXpp3Writer writer;
    private final ProjectFactory factory;

    public MavenProjectParsingService() {
        reader = new MavenXpp3Reader();
        writer = new MavenXpp3Writer();
        factory = new MavenProjectFactory();
    }

    @Override
    public ProjectDTO parseProject(Path projectPath) {

        return null;
    }

    @Override
    public MultiModuleProjectDTO parseMultiModuleProject(Path projectPath) {
        return null;
    }

    @Override
    public void writeProject(ProjectDTO project) {

    }

    @Override
    public void writeMultiModuleProject(MultiModuleProjectDTO multiModuleProject) {

    }
}
