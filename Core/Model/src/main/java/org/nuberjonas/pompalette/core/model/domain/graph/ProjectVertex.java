package org.nuberjonas.pompalette.core.model.domain.graph;

import com.brunomnsilva.smartgraph.graph.Vertex;
import org.nuberjonas.pompalette.core.model.domain.project.Project;
import org.nuberjonas.pompalette.core.model.domain.project.ProjectCoordinates;

import java.util.Objects;

public class ProjectVertex implements Vertex<Project> {

    private Project project;

    public ProjectVertex(Project project) {
        this.project = project;
    }

    @Override
    public Project element() {
        return project;
    }

    public void setProject(Project newProject){
        project = newProject;
    }

    public boolean contains(Project project){
        return project.equals(this.project);
    }

    public boolean contains(ProjectCoordinates coordinates){
        return coordinates.equals(project.getCoordinates());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProjectVertex that = (ProjectVertex) o;
        return Objects.equals(project, that.project);
    }

    @Override
    public int hashCode() {
        return Objects.hash(project);
    }
}
