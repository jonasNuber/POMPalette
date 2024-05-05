package org.nuberjonas.pompalette.core.model.domain.graph;

import com.brunomnsilva.smartgraph.graph.Vertex;
import org.nuberjonas.pompalette.core.model.domain.project.MavenProject;
import org.nuberjonas.pompalette.core.model.domain.project.ProjectCoordinates;

import java.util.Objects;

public class ProjectVertex implements Vertex<MavenProject> {

    private MavenProject project;

    public ProjectVertex(MavenProject project) {
        this.project = project;
    }

    @Override
    public MavenProject element() {
        return project;
    }

    public void setProject(MavenProject newProject){
        project = newProject;
    }

    public boolean contains(MavenProject project){
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
