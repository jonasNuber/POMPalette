package org.nuberjonas.pompalette.core.sharedkernel.projectdtos.beans;

import java.util.ArrayList;
import java.util.List;

public class MultiModuleProjectDTO {

    private ProjectDTO project;
    private List<ProjectDTO> modules;

    public MultiModuleProjectDTO(ProjectDTO project){
        this.project = project;
        modules = new ArrayList<>();
    }

    public void addModule(ProjectDTO projectDTO){
        modules.add(projectDTO);
    }

    public List<ProjectDTO> getModules() {
        return modules;
    }

    public ProjectDTO get(){
        return project;
    }
}
