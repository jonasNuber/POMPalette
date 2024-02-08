package de.nuberjonas.pompalette.infrastructure.parsing.projectparsingmavenimpl;

import de.nuberjonas.pompalette.core.sharedkernel.projectdtos.beans.MultiModuleProjectDTO;
import de.nuberjonas.pompalette.core.sharedkernel.projectdtos.beans.ProjectDTO;
import de.nuberjonas.pompalette.infrastructure.parsing.projectparsingapi.ProjectParsingService;
import org.apache.maven.model.io.xpp3.MavenXpp3Reader;
import org.apache.maven.model.io.xpp3.MavenXpp3Writer;

import java.nio.file.Path;

public class MavenProjectParsingService implements ProjectParsingService {

    private final MavenXpp3Reader reader;
    private final MavenXpp3Writer writer;

    public MavenProjectParsingService() {
        reader = new MavenXpp3Reader();
        writer = new MavenXpp3Writer();
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
