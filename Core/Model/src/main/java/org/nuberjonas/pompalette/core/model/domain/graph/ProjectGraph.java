package org.nuberjonas.pompalette.core.model.domain.graph;

import com.brunomnsilva.smartgraph.graph.*;
import org.nuberjonas.pompalette.core.model.domain.graph.relationship.Relationship;
import org.nuberjonas.pompalette.core.model.domain.graph.relationship.RelationshipEdge;
import org.nuberjonas.pompalette.core.model.domain.project.Project;

import java.util.*;
import java.util.stream.Collectors;

public class ProjectGraph implements Digraph<Project, Relationship> {

    private final Map<Project, ProjectVertex> vertices = new HashMap<>();
    private final Set<RelationshipEdge> edges = new HashSet<>();

    private Vertex<Project> rootProject;

    @Override
    public int numVertices() {
        return vertices.size();
    }

    @Override
    public int numEdges() {
        return edges.size();
    }

    @Override
    public List<Vertex<Project>> vertices() {
        return new ArrayList<>(vertices.values());
    }

    public Vertex<Project> rootProject(){
        if(rootProject == null){
            for (Vertex<Project> vertex : vertices()){
                if(incidentEdges(vertex).isEmpty()){
                    rootProject = vertex;
                    return rootProject;
                }
            }
        }
        return rootProject;
    }

    @Override
    public List<Edge<Relationship, Project>> edges() {
        return new ArrayList<>(edges);
    }

    @Override
    public List<Edge<Relationship, Project>> incidentEdges(Vertex<Project> projectVertex) throws InvalidVertexException {
        checkVertex(projectVertex);

        return this.edges.stream()
                .filter(relationship -> relationship.containsInboundVertex((ProjectVertex) projectVertex))
                .collect(Collectors.toList());
    }

    public List<Edge<Relationship, Project>> modules(Vertex<Project> projectVertex){
        checkVertex(projectVertex);

        return this.edges.stream()
                .filter(relationship -> relationship.containsInboundVertex((ProjectVertex) projectVertex) && "Module".equals(relationship.element().denominator()))
                .collect(Collectors.toList());
    }

    @Override
    public Collection<Edge<Relationship, Project>> outboundEdges(Vertex<Project> vertex) throws InvalidVertexException {
        checkVertex(vertex);

        return this.edges.stream()
                .filter(relationship -> relationship.containsOutboundVertex((ProjectVertex) vertex))
                .collect(Collectors.toList());
    }

    @Override
    public Vertex<Project> opposite(Vertex<Project> projectVertex, Edge<Relationship, Project> projectEdge) throws InvalidVertexException, InvalidEdgeException {
        checkVertex(projectVertex);
        checkEdge(projectEdge);

        if (!((RelationshipEdge)projectEdge).contains((ProjectVertex) projectVertex)) {
            return null;
        } else {
            return projectEdge.vertices()[0] == projectVertex ? projectEdge.vertices()[1] : projectEdge.vertices()[0];
        }
    }

    @Override
    public synchronized boolean areAdjacent(Vertex<Project> projectVertex, Vertex<Project> otherProjectVertex) throws InvalidVertexException {
        checkVertex(projectVertex);
        checkVertex(otherProjectVertex);

        for(RelationshipEdge rel : edges){
            if(rel.contains((ProjectVertex)projectVertex) && rel.contains((ProjectVertex) otherProjectVertex)) {
                return true;
            }
        }

        return false;
    }

    @Override
    public synchronized Vertex<Project> insertVertex(Project project) throws InvalidVertexException {
        if(vertices.containsKey(project)){
            return vertices.get(project);
        }

        return vertices.put(project, new ProjectVertex(project));
    }

    @Override
    public Edge<Relationship, Project> insertEdge(Vertex<Project> projectOutbound, Vertex<Project> projectInbound, Relationship relationship) throws InvalidVertexException, InvalidEdgeException {
        if(edgeExists(projectOutbound, projectInbound, relationship)){
            throw new InvalidEdgeException("There's already an edge with these projects and relationship.");
        }

        var projectRelationship = new RelationshipEdge(relationship, (ProjectVertex) projectOutbound, (ProjectVertex) projectInbound);
        edges.add(projectRelationship);

        return projectRelationship;
    }

    @Override
    public Edge<Relationship, Project> insertEdge(Project projectOutbound, Project projectInbound, Relationship relationship) throws InvalidVertexException, InvalidEdgeException {
        var projectVertexOutbound = new ProjectVertex(projectOutbound);
        var projectVertexInbound = new ProjectVertex(projectInbound);

        if(edgeExists(projectVertexOutbound, projectVertexInbound, relationship)){
            throw new InvalidEdgeException("There's already an edge with these projects and relationship.");
        }

        RelationshipEdge projectRelationship = new RelationshipEdge(relationship, projectVertexOutbound, projectVertexInbound);
        edges.add(projectRelationship);

        return projectRelationship;
    }

    private boolean edgeExists(Vertex<Project> projectOutbound, Vertex<Project> projectInbound, Relationship relationship){
        checkVertex(projectOutbound);
        checkVertex(projectInbound);

        for(RelationshipEdge rel : edges){
            if(rel.contains((ProjectVertex)projectOutbound, (org.nuberjonas.pompalette.core.model.domain.graph.ProjectVertex) projectInbound, relationship)) {
                return true;
            }
        }

        return false;
    }

    @Override
    public Project removeVertex(Vertex<Project> project) throws InvalidVertexException {
        checkVertex(project);
        incidentEdges(project).forEach(edges::remove);

        return vertices.remove(project.element()).element();
    }

    @Override
    public Relationship removeEdge(Edge<Relationship, Project> edge) throws InvalidEdgeException {
        checkEdge(edge);
        edges.remove(edge);

        return edge.element();
    }

    @Override
    public Project replace(Vertex<Project> projectVertex, Project project) throws InvalidVertexException {
        checkVertex(projectVertex);
        checkVertex(new ProjectVertex(project));

        var oldProject = projectVertex.element();
        vertices.get(projectVertex.element()).setProject(project);

        return oldProject;
    }

    @Override
    public Relationship replace(Edge<Relationship, Project> edge, Relationship Relationship) throws InvalidEdgeException {
        var newEdge = new RelationshipEdge(Relationship, (ProjectVertex) edge.vertices()[0], (ProjectVertex) edge.vertices()[1]);
        checkEdge(edge);
        checkEdge(newEdge);

        var oldType = edge.element();

        edges.remove(edge);
        edges.add(newEdge);

        return oldType;
    }

    private void checkVertex(Vertex<Project> v) throws InvalidVertexException {
        if (v == null) {
            throw new InvalidVertexException("Null vertex.");
        } else {
            ProjectVertex vertex;
            try {
                vertex = (ProjectVertex) v;
            } catch (ClassCastException var4) {
                throw new InvalidVertexException("Not a vertex.");
            }

            if (!this.vertices.containsKey(vertex.element())) {
                throw new InvalidVertexException("Vertex does not belong to this graph.");
            }
        }
    }

    private void checkEdge(Edge<Relationship, Project> e) throws InvalidEdgeException {
        if (e == null) {
            throw new InvalidEdgeException("Null edge.");
        } else {
            RelationshipEdge edge;
            try {
                edge = (RelationshipEdge) e;
            } catch (ClassCastException var4) {
                throw new InvalidVertexException("Not an adge.");
            }

            if (!this.edges.contains(edge)) {
                throw new InvalidEdgeException("Edge does not belong to this graph.");
            }
        }
    }
}
