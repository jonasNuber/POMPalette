package org.nuberjonas.pompalette.core.model.domain.graph;

import com.brunomnsilva.smartgraph.graph.Edge;
import org.nuberjonas.pompalette.core.model.domain.graph.relationship.Relationship;
import org.nuberjonas.pompalette.core.model.domain.project.Project;
import org.nuberjonas.pompalette.core.model.domain.project.ProjectCoordinates;

public class RelationshipEdge implements Edge<Relationship, Project> {

    private Relationship edge;
    private ProjectVertex projectOutbound;
    private ProjectVertex projectInbound;

    public RelationshipEdge(Relationship edge, ProjectVertex projectOutbound, ProjectVertex projectInbound) {
        this.edge = edge;
        this.projectOutbound = projectOutbound;
        this.projectInbound = projectInbound;
    }

    @Override
    public Relationship element() {
        return edge;
    }

    @Override
    public ProjectVertex[] vertices() {
        return new ProjectVertex[]{this.projectOutbound, this.projectInbound};
    }

    public boolean containsOutboundVertex(ProjectVertex projectVertex){
        return projectVertex.equals(projectOutbound);
    }

    public boolean containsInboundVertex(ProjectVertex projectVertex){
        return projectVertex.equals(projectInbound);
    }

    public boolean contains(ProjectVertex project){
        return project.equals(projectOutbound) || project.equals(projectInbound);
    }

    public boolean contains(Project project){
        return project.equals(projectOutbound.element()) || project.equals(projectInbound.element());
    }

    public boolean contains(ProjectCoordinates project){
        return project.equals(projectOutbound.element().getCoordinates()) || project.equals(projectInbound.element().getCoordinates());
    }

    public boolean contains(ProjectVertex projectOutbound, ProjectVertex projectInbound, Relationship relationship){
        return projectInbound.equals(this.projectInbound) && projectOutbound.equals(this.projectOutbound) && relationship.equals(edge);
    }
}
