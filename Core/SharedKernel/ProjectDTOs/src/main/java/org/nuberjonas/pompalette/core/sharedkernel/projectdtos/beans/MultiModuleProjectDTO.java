package org.nuberjonas.pompalette.core.sharedkernel.projectdtos.beans;

import java.util.ArrayList;
import java.util.List;

public class MultiModuleProjectDTO {

    private ProjectDTO project;
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

    public ProjectDTO get(){
        return project;
    }
}
