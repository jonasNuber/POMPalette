package org.nuberjonas.pompalette.core.model.graph;

import com.brunomnsilva.smartgraph.graph.Edge;
import org.nuberjonas.pompalette.core.model.project.MavenProject;
import org.nuberjonas.pompalette.core.model.project.ProjectCoordinates;

public class ProjectRelationship implements Edge<EdgeType, MavenProject> {

    private EdgeType edge;
    private ProjectVertex projectOutbound;
    private ProjectVertex projectInbound;

    public ProjectRelationship(EdgeType edge, ProjectVertex projectOutbound, ProjectVertex projectInbound) {
        this.edge = edge;
        this.projectOutbound = projectOutbound;
        this.projectInbound = projectInbound;
    }

    @Override
    public EdgeType element() {
        return edge;
    }

    @Override
    public ProjectVertex[] vertices() {
        return new ProjectVertex[]{this.projectOutbound, this.projectInbound};
    }

    public boolean containsOutboundVertex(ProjectVertex projectVertex){
        return projectVertex.equals(projectOutbound);
    }

    public boolean contains(ProjectVertex project){
        return project.equals(projectOutbound) || project.equals(projectInbound);
    }

    public boolean contains(MavenProject project){
        return project.equals(projectOutbound.element()) || project.equals(projectInbound.element());
    }

    public boolean contains(ProjectCoordinates project){
        return project.equals(projectOutbound.element().getCoordinates()) || project.equals(projectInbound.element().getCoordinates());
    }

    public boolean contains(ProjectVertex projectOutbound, ProjectVertex projectInbound, EdgeType relationship){
        return projectInbound.equals(this.projectInbound) && projectOutbound.equals(this.projectOutbound) && relationship.equals(edge);
    }
}
