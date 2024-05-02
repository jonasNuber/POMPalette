package org.nuberjonas.pompalette.core.model.project;

import java.util.ArrayList;
import java.util.List;

public class MavenProject {

    private ProjectCoordinates coordinates;
    private String name;
    private MavenProject parent;
    private List<MavenProject> modules;

    public MavenProject(ProjectCoordinates coordinates, String name){
        this.coordinates = coordinates;
        this.name = name;
        modules = new ArrayList<>();
    }

    public ProjectCoordinates getCoordinates() {
        return coordinates;
    }

    public String getName() {
        return name;
    }

    public MavenProject getParent() {
        return parent;
    }

    public void setParent(MavenProject parent) {
        this.parent = parent;
    }

    public List<MavenProject> getModules() {
        return modules;
    }

    public void setModules(List<MavenProject> modules) {
        this.modules = modules;
    }

    public void addModule(MavenProject module){
        modules.add(module);
    }
}
