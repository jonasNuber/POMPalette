package org.nuberjonas.pompalette.core.model.domain.graph;

import com.brunomnsilva.smartgraph.graph.Edge;
import org.nuberjonas.pompalette.core.model.domain.project.Project;
import org.nuberjonas.pompalette.core.model.domain.project.ProjectCoordinates;

public class ProjectRelationship implements Edge<org.nuberjonas.pompalette.core.model.domain.graph.EdgeType, Project> {

    private org.nuberjonas.pompalette.core.model.domain.graph.EdgeType edge;
    private ProjectVertex projectOutbound;
    private org.nuberjonas.pompalette.core.model.domain.graph.ProjectVertex projectInbound;

    public ProjectRelationship(org.nuberjonas.pompalette.core.model.domain.graph.EdgeType edge, org.nuberjonas.pompalette.core.model.domain.graph.ProjectVertex projectOutbound, org.nuberjonas.pompalette.core.model.domain.graph.ProjectVertex projectInbound) {
        this.edge = edge;
        this.projectOutbound = projectOutbound;
        this.projectInbound = projectInbound;
    }

    @Override
    public org.nuberjonas.pompalette.core.model.domain.graph.EdgeType element() {
        return edge;
    }

    @Override
    public org.nuberjonas.pompalette.core.model.domain.graph.ProjectVertex[] vertices() {
        return new org.nuberjonas.pompalette.core.model.domain.graph.ProjectVertex[]{this.projectOutbound, this.projectInbound};
    }

    public boolean containsOutboundVertex(org.nuberjonas.pompalette.core.model.domain.graph.ProjectVertex projectVertex){
        return projectVertex.equals(projectOutbound);
    }

    public boolean containsInboundVertex(ProjectVertex projectVertex){
        return projectVertex.equals(projectInbound);
    }

    public boolean contains(org.nuberjonas.pompalette.core.model.domain.graph.ProjectVertex project){
        return project.equals(projectOutbound) || project.equals(projectInbound);
    }

    public boolean contains(Project project){
        return project.equals(projectOutbound.element()) || project.equals(projectInbound.element());
    }

    public boolean contains(ProjectCoordinates project){
        return project.equals(projectOutbound.element().getCoordinates()) || project.equals(projectInbound.element().getCoordinates());
    }

    public boolean contains(org.nuberjonas.pompalette.core.model.domain.graph.ProjectVertex projectOutbound, org.nuberjonas.pompalette.core.model.domain.graph.ProjectVertex projectInbound, org.nuberjonas.pompalette.core.model.domain.graph.EdgeType relationship){
        return projectInbound.equals(this.projectInbound) && projectOutbound.equals(this.projectOutbound) && relationship.equals(edge);
    }
}
