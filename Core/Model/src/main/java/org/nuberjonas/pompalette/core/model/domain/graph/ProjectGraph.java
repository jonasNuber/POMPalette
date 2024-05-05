package org.nuberjonas.pompalette.core.model.domain.graph;

import com.brunomnsilva.smartgraph.graph.*;
import org.nuberjonas.pompalette.core.model.domain.project.MavenProject;

import java.util.*;
import java.util.stream.Collectors;

public class ProjectGraph implements Digraph<MavenProject, org.nuberjonas.pompalette.core.model.domain.graph.EdgeType> {

    private final Map<MavenProject, ProjectVertex> vertices = new HashMap();
    private final Set<ProjectRelationship> edges = new HashSet();

    @Override
    public int numVertices() {
        return vertices.size();
    }

    @Override
    public int numEdges() {
        return edges.size();
    }

    @Override
    public List<Vertex<MavenProject>> vertices() {
        return new ArrayList<>(vertices.values());
    }

    @Override
    public List<Edge<EdgeType, MavenProject>> edges() {
        return new ArrayList<>(edges);
    }

    @Override
    public List<Edge<org.nuberjonas.pompalette.core.model.domain.graph.EdgeType, MavenProject>> incidentEdges(Vertex<MavenProject> projectVertex) throws InvalidVertexException {
        checkVertex(projectVertex);

        return this.edges.stream()
                .filter(relationship -> relationship.containsInboundVertex((org.nuberjonas.pompalette.core.model.domain.graph.ProjectVertex) projectVertex))
                .collect(Collectors.toList());
    }

    @Override
    public Collection<Edge<org.nuberjonas.pompalette.core.model.domain.graph.EdgeType, MavenProject>> outboundEdges(Vertex<MavenProject> vertex) throws InvalidVertexException {
        checkVertex(vertex);

        return this.edges.stream()
                .filter(relationship -> relationship.containsOutboundVertex((org.nuberjonas.pompalette.core.model.domain.graph.ProjectVertex) vertex))
                .collect(Collectors.toList());
    }

    @Override
    public Vertex<MavenProject> opposite(Vertex<MavenProject> projectVertex, Edge<org.nuberjonas.pompalette.core.model.domain.graph.EdgeType, MavenProject> projectEdge) throws InvalidVertexException, InvalidEdgeException {
        checkVertex(projectVertex);
        checkEdge(projectEdge);

        if (!((org.nuberjonas.pompalette.core.model.domain.graph.ProjectRelationship)projectEdge).contains((org.nuberjonas.pompalette.core.model.domain.graph.ProjectVertex) projectVertex)) {
            return null;
        } else {
            return projectEdge.vertices()[0] == projectVertex ? projectEdge.vertices()[1] : projectEdge.vertices()[0];
        }
    }

    @Override
    public synchronized boolean areAdjacent(Vertex<MavenProject> projectVertex, Vertex<MavenProject> otherProjectVertex) throws InvalidVertexException {
        checkVertex(projectVertex);
        checkVertex(otherProjectVertex);

        for(org.nuberjonas.pompalette.core.model.domain.graph.ProjectRelationship rel : edges){
            if(rel.contains((org.nuberjonas.pompalette.core.model.domain.graph.ProjectVertex)projectVertex) && rel.contains((org.nuberjonas.pompalette.core.model.domain.graph.ProjectVertex) otherProjectVertex)) {
                return true;
            }
        }

        return false;
    }

    @Override
    public synchronized Vertex<MavenProject> insertVertex(MavenProject project) throws InvalidVertexException {
        if(vertices.containsKey(project)){
            throw new InvalidVertexException("There's already a vertex with this Project.");
        }

        org.nuberjonas.pompalette.core.model.domain.graph.ProjectVertex vertex = new org.nuberjonas.pompalette.core.model.domain.graph.ProjectVertex(project);
        vertices.put(project, vertex);

        return vertex;
    }

    @Override
    public Edge<org.nuberjonas.pompalette.core.model.domain.graph.EdgeType, MavenProject> insertEdge(Vertex<MavenProject> projectOutbound, Vertex<MavenProject> projectInbound, org.nuberjonas.pompalette.core.model.domain.graph.EdgeType relationship) throws InvalidVertexException, InvalidEdgeException {
        if(edgeExists(projectOutbound, projectInbound, relationship)){
            throw new InvalidEdgeException("There's already an edge with these projects and relationship.");
        }

        org.nuberjonas.pompalette.core.model.domain.graph.ProjectRelationship projectRelationship = new org.nuberjonas.pompalette.core.model.domain.graph.ProjectRelationship(relationship, (org.nuberjonas.pompalette.core.model.domain.graph.ProjectVertex) projectOutbound, (org.nuberjonas.pompalette.core.model.domain.graph.ProjectVertex) projectInbound);
        edges.add(projectRelationship);

        return projectRelationship;
    }

    @Override
    public Edge<org.nuberjonas.pompalette.core.model.domain.graph.EdgeType, MavenProject> insertEdge(MavenProject projectOutbound, MavenProject projectInbound, org.nuberjonas.pompalette.core.model.domain.graph.EdgeType relationship) throws InvalidVertexException, InvalidEdgeException {
        var projectVertexOutbound = new org.nuberjonas.pompalette.core.model.domain.graph.ProjectVertex(projectOutbound);
        var projectVertexInbound = new org.nuberjonas.pompalette.core.model.domain.graph.ProjectVertex(projectInbound);

        if(edgeExists(projectVertexOutbound, projectVertexInbound, relationship)){
            throw new InvalidEdgeException("There's already an edge with these projects and relationship.");
        }

        org.nuberjonas.pompalette.core.model.domain.graph.ProjectRelationship projectRelationship = new org.nuberjonas.pompalette.core.model.domain.graph.ProjectRelationship(relationship, (org.nuberjonas.pompalette.core.model.domain.graph.ProjectVertex) projectVertexOutbound, (org.nuberjonas.pompalette.core.model.domain.graph.ProjectVertex) projectVertexInbound);
        edges.add(projectRelationship);

        return projectRelationship;
    }

    private boolean edgeExists(Vertex<MavenProject> projectOutbound, Vertex<MavenProject> projectInbound, org.nuberjonas.pompalette.core.model.domain.graph.EdgeType relationship){
        checkVertex(projectOutbound);
        checkVertex(projectInbound);

        for(org.nuberjonas.pompalette.core.model.domain.graph.ProjectRelationship rel : edges){
            if(rel.contains((org.nuberjonas.pompalette.core.model.domain.graph.ProjectVertex)projectOutbound, (org.nuberjonas.pompalette.core.model.domain.graph.ProjectVertex) projectInbound, relationship)) {
                return true;
            }
        }

        return false;
    }

    @Override
    public MavenProject removeVertex(Vertex<MavenProject> project) throws InvalidVertexException {
        checkVertex(project);
        incidentEdges(project).forEach(edges::remove);

        return vertices.remove(project.element()).element();
    }

    @Override
    public org.nuberjonas.pompalette.core.model.domain.graph.EdgeType removeEdge(Edge<org.nuberjonas.pompalette.core.model.domain.graph.EdgeType, MavenProject> edge) throws InvalidEdgeException {
        checkEdge(edge);
        edges.remove(edge);

        return edge.element();
    }

    @Override
    public MavenProject replace(Vertex<MavenProject> projectVertex, MavenProject project) throws InvalidVertexException {
        checkVertex(projectVertex);
        checkVertex(new org.nuberjonas.pompalette.core.model.domain.graph.ProjectVertex(project));

        var oldProject = projectVertex.element();
        vertices.get(projectVertex.element()).setProject(project);

        return oldProject;
    }

    @Override
    public org.nuberjonas.pompalette.core.model.domain.graph.EdgeType replace(Edge<org.nuberjonas.pompalette.core.model.domain.graph.EdgeType, MavenProject> edge, org.nuberjonas.pompalette.core.model.domain.graph.EdgeType edgeType) throws InvalidEdgeException {
        var newEdge = new org.nuberjonas.pompalette.core.model.domain.graph.ProjectRelationship(edgeType, (org.nuberjonas.pompalette.core.model.domain.graph.ProjectVertex) edge.vertices()[0], (org.nuberjonas.pompalette.core.model.domain.graph.ProjectVertex) edge.vertices()[1]);
        checkEdge(edge);
        checkEdge(newEdge);

        var oldType = edge.element();

        edges.remove(edge);
        edges.add(newEdge);

        return oldType;
    }

    private void checkVertex(Vertex<MavenProject> v) throws InvalidVertexException {
        if (v == null) {
            throw new InvalidVertexException("Null vertex.");
        } else {
            org.nuberjonas.pompalette.core.model.domain.graph.ProjectVertex vertex;
            try {
                vertex = (org.nuberjonas.pompalette.core.model.domain.graph.ProjectVertex) v;
            } catch (ClassCastException var4) {
                throw new InvalidVertexException("Not a vertex.");
            }

            if (!this.vertices.containsKey(vertex.element())) {
                throw new InvalidVertexException("Vertex does not belong to this graph.");
            }
        }
    }

    private void checkEdge(Edge<org.nuberjonas.pompalette.core.model.domain.graph.EdgeType, MavenProject> e) throws InvalidEdgeException {
        if (e == null) {
            throw new InvalidEdgeException("Null edge.");
        } else {
            org.nuberjonas.pompalette.core.model.domain.graph.ProjectRelationship edge;
            try {
                edge = (org.nuberjonas.pompalette.core.model.domain.graph.ProjectRelationship) e;
            } catch (ClassCastException var4) {
                throw new InvalidVertexException("Not an adge.");
            }

            if (!this.edges.contains(edge)) {
                throw new InvalidEdgeException("Edge does not belong to this graph.");
            }
        }
    }
}
