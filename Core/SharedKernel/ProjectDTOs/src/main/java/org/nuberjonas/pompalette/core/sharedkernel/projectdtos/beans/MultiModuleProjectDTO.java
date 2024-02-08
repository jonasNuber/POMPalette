package org.nuberjonas.pompalette.core.sharedkernel.projectdtos.beans;

import java.util.ArrayList;
import java.util.List;

public class MultiModuleProjectDTO {
    private List<ProjectDTO> modules;

    public MultiModuleProjectDTO(List<ProjectDTO> modules) {
        this.modules = modules;
    }

    public MultiModuleProjectDTO() {
        modules = new ArrayList<>();
    }

    public void addModule(ProjectDTO projectDTO){
        modules.add(projectDTO);
    }

    public List<ProjectDTO> getModules() {
        return modules;
    }
}
