package org.nuberjonas.pompalette.core.model.domain.graph;

import com.brunomnsilva.smartgraph.graph.*;
import org.apache.commons.lang3.StringUtils;
import org.nuberjonas.pompalette.core.model.domain.graph.relationship.DependencyRelationship;
import org.nuberjonas.pompalette.core.model.domain.graph.relationship.ModuleRelationship;
import org.nuberjonas.pompalette.core.model.domain.graph.relationship.Relationship;
import org.nuberjonas.pompalette.core.model.domain.project.MavenProject;
import org.nuberjonas.pompalette.core.model.domain.project.Project;
import org.nuberjonas.pompalette.core.model.domain.project.dependecies.ExternalDependency;
import org.nuberjonas.pompalette.core.model.domain.project.dependecies.ManagedDependency;
import org.nuberjonas.pompalette.core.model.domain.project.dependecies.ResolvedDependency;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class ProjectGraph implements Digraph<Project, Relationship> {

    private final Map<Project, ProjectVertex> vertices;
    private final Set<RelationshipEdge> edges;

    private Vertex<Project> rootProject;

    public ProjectGraph(){
        this(new HashMap<>(), new HashSet<>());
    }

    private ProjectGraph(Map<Project, ProjectVertex> vertices, Set<RelationshipEdge> edges){
        this.vertices = vertices;
        this.edges = edges;
    }

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

    public Vertex<Project> rootProject() {
        if (rootProject == null) {
            for (Vertex<Project> vertex : vertices()) {
                if (incidentEdges(vertex).isEmpty()) {
                    rootProject = vertex;
                    return rootProject;
                }
            }
        }

        return rootProject;
    }

    public Vertex<Project> bomProject(Project project){
        if(vertices.containsKey(project)){
            for(var outboundEdge : outboundEdges(vertices.get(project))){
                if(StringUtils.equals(ModuleRelationship.BOM, outboundEdge.element().denominator())){
                    return outboundEdge.vertices()[1];
                }
            }
        }
        return null;
    }

    public List<Vertex<Project>> modules(Vertex<Project> project){
        checkVertex(project);

        return outboundEdges(project)
                .stream()
                .filter(relationship -> StringUtils.equals(ModuleRelationship.MODULE, relationship.element().denominator()))
                .map(relationship -> relationship.vertices()[1])
                .collect(Collectors.toList());
    }

    public ProjectGraph subGraph(Predicate<Vertex<Project>> vertexFilter, Predicate<RelationshipEdge> edgeFilter){
        var subGraph = new ProjectGraph();

        vertices().stream()
                .filter(vertexFilter)
                .forEach(vertex -> subGraph.insertVertex(vertex.element()));

        edges.stream()
                .filter(edgeFilter)
                .forEach(relationshipEdge -> subGraph.insertEdge(relationshipEdge.vertices()[0], relationshipEdge.vertices()[1], relationshipEdge.element()));

        return subGraph;
    }

    public ProjectGraph dependencySubGraph(Vertex<Project> startingVertex, boolean isDependency){
        var dependencyGraph = new ProjectGraph();
        dependencyGraph.insertVertex(startingVertex.element());

        if(startingVertex.element() instanceof MavenProject && !isDependency){
            var outGoingDependencyEdges = outboundEdges(startingVertex).stream().filter(relationshipProjectEdge -> relationshipProjectEdge.element() instanceof DependencyRelationship);
            outGoingDependencyEdges.forEach(dependencyEdge -> {
                var dependency = dependencyEdge.vertices()[1].element();
                dependencyGraph.insertVertex(dependency);
                dependencyGraph.insertEdge(dependencyEdge.vertices()[0], dependencyEdge.vertices()[1], dependencyEdge.element());

                if(dependency instanceof ResolvedDependency resolvedDependency){
                    var managedDependencyEdge = outboundEdges(dependencyEdge.vertices()[1]).getFirst();
                    dependencyGraph.insertVertex(managedDependencyEdge.vertices()[1].element());
                    dependencyGraph.insertEdge(dependency, managedDependencyEdge.vertices()[1].element(), managedDependencyEdge.element());

                    var directDependencyEdge = outboundEdges(managedDependencyEdge.vertices()[1]).getFirst();

                    dependencyGraph.insertVertex(directDependencyEdge.vertices()[1].element());
                    dependencyGraph.insertEdge(managedDependencyEdge.vertices()[1].element(), directDependencyEdge.vertices()[1].element(), directDependencyEdge.element());
                } else if (dependency instanceof ManagedDependency managedDependency){
                    var directDependencyEdge = outboundEdges(dependencyEdge.vertices()[1]).getFirst();

                    dependencyGraph.insertVertex(directDependencyEdge.vertices()[1].element());
                    dependencyGraph.insertEdge(dependencyEdge.vertices()[1].element(), directDependencyEdge.vertices()[1].element(), directDependencyEdge.element());
                }
            });
        } else if(startingVertex.element() instanceof ManagedDependency){
            var dependencyEdge = outboundEdges(startingVertex).getFirst();
            dependencyGraph.insertVertex(startingVertex.element());
            dependencyGraph.insertVertex(dependencyEdge.vertices()[1].element());
            dependencyGraph.insertEdge(startingVertex, dependencyEdge.vertices()[1], dependencyEdge.element());

            var resolvedEdges = incidentEdges(startingVertex);

            resolvedEdges.forEach(resolvedEdge -> {
                var resolvedVertex = resolvedEdge.vertices()[0];
                dependencyGraph.insertVertex(resolvedVertex.element());
                dependencyGraph.insertEdge(resolvedVertex, startingVertex, resolvedEdge.element());

                var dependsOnEdge = incidentEdges(resolvedVertex).getFirst();

                if(dependsOnEdge.element() instanceof DependencyRelationship){
                    dependencyGraph.insertVertex(dependsOnEdge.vertices()[0].element());
                    dependencyGraph.insertEdge(dependsOnEdge.vertices()[0], resolvedVertex, dependsOnEdge.element());
                }

            });
        } else if((startingVertex.element() instanceof MavenProject && isDependency) || startingVertex.element() instanceof ExternalDependency){
            var dependencyEdges = incidentEdges(startingVertex).stream().filter(edge -> edge.element() instanceof DependencyRelationship).toList();
            dependencyEdges.forEach(dependencyEdge -> {
                var dependencyVertex = dependencyEdge.vertices()[0];
                dependencyGraph.insertVertex(dependencyVertex.element());
                dependencyGraph.insertEdge(dependencyVertex, startingVertex, dependencyEdge.element());

                var managedEdge = incidentEdges(dependencyVertex).stream().filter(edge -> edge.vertices()[0].element() instanceof MavenProject).findFirst();
                managedEdge.ifPresent(edge -> {
                    dependencyGraph.insertVertex(edge.vertices()[0].element());
                    dependencyGraph.insertEdge(edge.vertices()[0], dependencyVertex, edge.element());
                });
            });
        }

        return dependencyGraph;
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

    @Override
    public List<Edge<Relationship, Project>> outboundEdges(Vertex<Project> vertex) throws InvalidVertexException {
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

        var projectVertex = new ProjectVertex(project);
        vertices.put(project, projectVertex);

        return projectVertex;
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
                throw new InvalidVertexException("Not an edge.");
            }

            if (!this.edges.contains(edge)) {
                throw new InvalidEdgeException("Edge does not belong to this graph.");
            }
        }
    }
}
