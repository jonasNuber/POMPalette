package de.nuberjonas.pompalette.infrastructure.parsing.projectparsingapi;

import de.nuberjonas.pompalette.core.sharedkernel.projectdtos.beans.project.MultiModuleProjectDTO;
import de.nuberjonas.pompalette.core.sharedkernel.projectdtos.beans.project.ProjectDTO;

import java.nio.file.Path;

public interface ProjectParsingService {

    public ProjectDTO parseProject(Path projectPath);
    public MultiModuleProjectDTO parseMultiModuleProject(Path projectPath);
    public void writeProject(ProjectDTO project);
    public void writeMultiModuleProject(MultiModuleProjectDTO multiModuleProject);
}
