package org.nuberjonas.pompalette.core.model.domain.graph;

import com.brunomnsilva.smartgraph.graph.*;
import org.nuberjonas.pompalette.core.model.domain.project.Project;

import java.util.*;
import java.util.stream.Collectors;

public class ProjectGraph implements Digraph<Project, EdgeType> {

    private final Map<Project, ProjectVertex> vertices = new HashMap<>();
    private final Set<ProjectRelationship> edges = new HashSet<>();

    private final Map<Project, ProjectVertex> invisibleVertices = new HashMap<>();
    private final Set<ProjectRelationship> invisibleEdges = new HashSet<>();

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

    @Override
    public List<Edge<EdgeType, Project>> edges() {
        return new ArrayList<>(edges);
    }

    @Override
    public List<Edge<EdgeType, Project>> incidentEdges(Vertex<Project> projectVertex) throws InvalidVertexException {
        checkVertex(projectVertex);

        return this.edges.stream()
                .filter(relationship -> relationship.containsInboundVertex((ProjectVertex) projectVertex))
                .collect(Collectors.toList());
    }

    @Override
    public Collection<Edge<EdgeType, Project>> outboundEdges(Vertex<Project> vertex) throws InvalidVertexException {
        checkVertex(vertex);

        return this.edges.stream()
                .filter(relationship -> relationship.containsOutboundVertex((ProjectVertex) vertex))
                .collect(Collectors.toList());
    }

    @Override
    public Vertex<Project> opposite(Vertex<Project> projectVertex, Edge<EdgeType, Project> projectEdge) throws InvalidVertexException, InvalidEdgeException {
        checkVertex(projectVertex);
        checkEdge(projectEdge);

        if (!((ProjectRelationship)projectEdge).contains((ProjectVertex) projectVertex)) {
            return null;
        } else {
            return projectEdge.vertices()[0] == projectVertex ? projectEdge.vertices()[1] : projectEdge.vertices()[0];
        }
    }

    @Override
    public synchronized boolean areAdjacent(Vertex<Project> projectVertex, Vertex<Project> otherProjectVertex) throws InvalidVertexException {
        checkVertex(projectVertex);
        checkVertex(otherProjectVertex);

        for(ProjectRelationship rel : edges){
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

    public synchronized Vertex<Project> insertInvisibleVertex(Project project) {
        if(invisibleVertices.containsKey(project)){
            return invisibleVertices.get(project);
        }

        return invisibleVertices.put(project, new ProjectVertex(project));
    }

    @Override
    public Edge<EdgeType, Project> insertEdge(Vertex<Project> projectOutbound, Vertex<Project> projectInbound, EdgeType relationship) throws InvalidVertexException, InvalidEdgeException {
        if(edgeExists(projectOutbound, projectInbound, relationship)){
            throw new InvalidEdgeException("There's already an edge with these projects and relationship.");
        }

        var projectRelationship = new ProjectRelationship(relationship, (ProjectVertex) projectOutbound, (ProjectVertex) projectInbound);
        edges.add(projectRelationship);

        return projectRelationship;
    }

    @Override
    public Edge<EdgeType, Project> insertEdge(Project projectOutbound, Project projectInbound, EdgeType relationship) throws InvalidVertexException, InvalidEdgeException {
        var projectVertexOutbound = new ProjectVertex(projectOutbound);
        var projectVertexInbound = new ProjectVertex(projectInbound);

        if(edgeExists(projectVertexOutbound, projectVertexInbound, relationship)){
            throw new InvalidEdgeException("There's already an edge with these projects and relationship.");
        }

        ProjectRelationship projectRelationship = new ProjectRelationship(relationship, projectVertexOutbound, projectVertexInbound);
        edges.add(projectRelationship);

        return projectRelationship;
    }

    private boolean edgeExists(Vertex<Project> projectOutbound, Vertex<Project> projectInbound, EdgeType relationship){
        checkVertex(projectOutbound);
        checkVertex(projectInbound);

        for(ProjectRelationship rel : edges){
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
    public EdgeType removeEdge(Edge<EdgeType, Project> edge) throws InvalidEdgeException {
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
    public EdgeType replace(Edge<EdgeType, Project> edge, EdgeType edgeType) throws InvalidEdgeException {
        var newEdge = new ProjectRelationship(edgeType, (ProjectVertex) edge.vertices()[0], (ProjectVertex) edge.vertices()[1]);
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

    private void checkEdge(Edge<EdgeType, Project> e) throws InvalidEdgeException {
        if (e == null) {
            throw new InvalidEdgeException("Null edge.");
        } else {
            ProjectRelationship edge;
            try {
                edge = (ProjectRelationship) e;
            } catch (ClassCastException var4) {
                throw new InvalidVertexException("Not an adge.");
            }

            if (!this.edges.contains(edge)) {
                throw new InvalidEdgeException("Edge does not belong to this graph.");
            }
        }
    }
}
