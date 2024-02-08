package de.nuberjonas.pompalette.infrastructure.parsing.projectparsingapi;

import de.nuberjonas.pompalette.core.sharedkernel.projectdtos.beans.MultiModuleProjectDTO;
import de.nuberjonas.pompalette.core.sharedkernel.projectdtos.beans.ProjectDTO;

import java.nio.file.Path;

public interface ProjectParsingService {

    ProjectDTO parseProject(Path projectPath);
    MultiModuleProjectDTO parseMultiModuleProject(Path projectPath);
    void writeProject(ProjectDTO project);
    void writeMultiModuleProject(MultiModuleProjectDTO multiModuleProject);
}
