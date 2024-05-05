package org.nuberjonas.pompalette.core.sharedkernel.projectdtos.beans;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MultiModuleProjectDTO {

    private ProjectDTO project;
    private MultiModuleProjectDTO projectBOM;
    private Path projectPath;
    private List<MultiModuleProjectDTO> modules;

    public MultiModuleProjectDTO(ProjectDTO project){
        this.project = project;
        modules = new ArrayList<>();
    }

    public void addModule(MultiModuleProjectDTO module) { modules.add(module);}

    public void addModule(ProjectDTO module){
        modules.add(new MultiModuleProjectDTO(module));
    }

    public List<MultiModuleProjectDTO> getModules() {
        return modules;
    }

    public void addProjectBOM(MultiModuleProjectDTO projectBOM){
        this.projectBOM = projectBOM;
    }

    public void setProjectPath(Path projectPath){
        this.projectPath = projectPath;
    }

    public MultiModuleProjectDTO getProjectBOM() {
        return projectBOM;
    }

    public Path getProjectPath() {
        return projectPath;
    }

    public ProjectDTO get(){
        return project;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MultiModuleProjectDTO that = (MultiModuleProjectDTO) o;
        return Objects.equals(project, that.project) && Objects.equals(modules, that.modules);
    }

    @Override
    public int hashCode() {
        return Objects.hash(project, modules);
    }
}
