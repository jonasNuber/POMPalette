package org.nuberjonas.pompalette.core.model.graph;

import com.brunomnsilva.smartgraph.graph.*;
import org.nuberjonas.pompalette.core.model.project.MavenProject;

import java.util.*;
import java.util.stream.Collectors;

public class ProjectGraph implements Digraph<MavenProject, EdgeType> {

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
    public List<Edge<EdgeType, MavenProject>> incidentEdges(Vertex<MavenProject> projectVertex) throws InvalidVertexException {
        checkVertex(projectVertex);

        return this.edges.stream()
                .filter(relationship -> relationship.contains((ProjectVertex) projectVertex))
                .collect(Collectors.toList());
    }

    @Override
    public Collection<Edge<EdgeType, MavenProject>> outboundEdges(Vertex<MavenProject> vertex) throws InvalidVertexException {
        checkVertex(vertex);

        return this.edges.stream()
                .filter(relationship -> relationship.containsOutboundVertex((ProjectVertex) vertex))
                .collect(Collectors.toList());
    }

    @Override
    public Vertex<MavenProject> opposite(Vertex<MavenProject> projectVertex, Edge<EdgeType, MavenProject> projectEdge) throws InvalidVertexException, InvalidEdgeException {
        checkVertex(projectVertex);
        checkEdge(projectEdge);

        if (!((ProjectRelationship)projectEdge).contains((ProjectVertex) projectVertex)) {
            return null;
        } else {
            return projectEdge.vertices()[0] == projectVertex ? projectEdge.vertices()[1] : projectEdge.vertices()[0];
        }
    }

    @Override
    public synchronized boolean areAdjacent(Vertex<MavenProject> projectVertex, Vertex<MavenProject> otherProjectVertex) throws InvalidVertexException {
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
    public synchronized Vertex<MavenProject> insertVertex(MavenProject project) throws InvalidVertexException {
        if(vertices.containsKey(project)){
            throw new InvalidVertexException("There's already a vertex with this Project.");
        }

        ProjectVertex vertex = new ProjectVertex(project);
        vertices.put(project, vertex);

        return vertex;
    }

    @Override
    public Edge<EdgeType, MavenProject> insertEdge(Vertex<MavenProject> projectOutbound, Vertex<MavenProject> projectInbound, EdgeType relationship) throws InvalidVertexException, InvalidEdgeException {
        if(edgeExists(projectOutbound, projectInbound, relationship)){
            throw new InvalidEdgeException("There's already an edge with these projects and relationship.");
        }

        ProjectRelationship projectRelationship = new ProjectRelationship(relationship, (ProjectVertex) projectOutbound, (ProjectVertex) projectInbound);
        edges.add(projectRelationship);

        return projectRelationship;
    }

    @Override
    public Edge<EdgeType, MavenProject> insertEdge(MavenProject projectOutbound, MavenProject projectInbound, EdgeType relationship) throws InvalidVertexException, InvalidEdgeException {
        var projectVertexOutbound = new ProjectVertex(projectOutbound);
        var projectVertexInbound = new ProjectVertex(projectInbound);

        if(edgeExists(projectVertexOutbound, projectVertexInbound, relationship)){
            throw new InvalidEdgeException("There's already an edge with these projects and relationship.");
        }

        ProjectRelationship projectRelationship = new ProjectRelationship(relationship, (ProjectVertex) projectVertexOutbound, (ProjectVertex) projectVertexInbound);
        edges.add(projectRelationship);

        return projectRelationship;
    }

    private boolean edgeExists(Vertex<MavenProject> projectOutbound, Vertex<MavenProject> projectInbound, EdgeType relationship){
        checkVertex(projectOutbound);
        checkVertex(projectInbound);

        for(ProjectRelationship rel : edges){
            if(rel.contains((ProjectVertex)projectOutbound, (ProjectVertex) projectInbound, relationship)) {
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
    public EdgeType removeEdge(Edge<EdgeType, MavenProject> edge) throws InvalidEdgeException {
        checkEdge(edge);
        edges.remove(edge);

        return edge.element();
    }

    @Override
    public MavenProject replace(Vertex<MavenProject> projectVertex, MavenProject project) throws InvalidVertexException {
        checkVertex(projectVertex);
        checkVertex(new ProjectVertex(project));

        var oldProject = projectVertex.element();
        vertices.get(projectVertex.element()).setProject(project);

        return oldProject;
    }

    @Override
    public EdgeType replace(Edge<EdgeType, MavenProject> edge, EdgeType edgeType) throws InvalidEdgeException {
        var newEdge = new ProjectRelationship(edgeType, (ProjectVertex) edge.vertices()[0], (ProjectVertex) edge.vertices()[1]);
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

    private void checkEdge(Edge<EdgeType, MavenProject> e) throws InvalidEdgeException {
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
