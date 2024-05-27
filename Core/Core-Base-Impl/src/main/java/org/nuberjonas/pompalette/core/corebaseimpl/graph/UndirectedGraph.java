package org.nuberjonas.pompalette.core.corebaseimpl.graph;

import org.nuberjonas.pompalette.core.coreapi.graph.*;
import org.nuberjonas.pompalette.core.coreapi.graph.exceptions.EntityAlreadyExistsException;
import org.nuberjonas.pompalette.core.coreapi.graph.exceptions.EntityNotFoundException;
import org.nuberjonas.pompalette.core.coreapi.graph.exceptions.RelationshipAlreadyExistsException;
import org.nuberjonas.pompalette.core.coreapi.graph.exceptions.RelationshipNotFoundException;

import java.util.Collection;
import java.util.Set;

public class UndirectedGraph<E extends Entity<D, U>, R extends Relationship<D, U>, D, U> implements Graph<E, R, D, U> {
    private final DirectedGraph<E, R, D, U> directedGraph;

    public UndirectedGraph(EntityFactory<E, D, U> entityFactory, RelationshipFactory<E, R, D, U> relationshipFactory) {
        this.directedGraph = new DirectedGraph<>(entityFactory, relationshipFactory);
    }

    @Override
    public void addEntity(E entity) throws EntityAlreadyExistsException {
        directedGraph.addEntity(entity);
    }

    @Override
    public E addEntity(D entityData) throws EntityAlreadyExistsException {
        return directedGraph.addEntity(entityData);
    }

    @Override
    public E getEntity(D entityData) throws EntityNotFoundException {
        return directedGraph.getEntity(entityData);
    }

    @Override
    public Set<E> getEntities() {
        return directedGraph.getEntities();
    }

    @Override
    public E getRelationshipSourceOf(R relationship) throws EntityNotFoundException, RelationshipNotFoundException {
        return directedGraph.getRelationshipSourceOf(relationship);
    }

    @Override
    public E getRelationshipTargetOf(R relationship) throws EntityNotFoundException, RelationshipNotFoundException {
        return directedGraph.getRelationshipTargetOf(relationship);
    }

    @Override
    public boolean removeEntity(E entity) throws EntityNotFoundException {
        return directedGraph.removeEntity(entity);
    }

    @Override
    public E removeEntity(D entityData) throws EntityNotFoundException {
        return directedGraph.removeEntity(entityData);
    }

    @Override
    public boolean removeAllEntities(Collection<E> entities) throws EntityNotFoundException {
        return directedGraph.removeAllEntities(entities);
    }

    @Override
    public R addRelationship(E source, E destination, U relationshipData) throws RelationshipAlreadyExistsException, EntityNotFoundException {
        var relationship = directedGraph.addRelationship(source, destination, relationshipData);

        directedGraph.addRelationship(destination, source, relationshipData);

        return relationship;
    }

    @Override
    public R addRelationship(D sourceData, D destinationData, U relationshipData) throws RelationshipAlreadyExistsException, EntityNotFoundException {
        var relationship = directedGraph.addRelationship(sourceData, destinationData, relationshipData);

        directedGraph.addRelationship(destinationData, sourceData, relationshipData);

        return relationship;
    }

    @Override
    public Set<R> getRelationships() {
        return directedGraph.getRelationships();
    }

    @Override
    public Set<R> getRelationshipsOf(E entity) throws EntityNotFoundException {
        return directedGraph.getRelationshipsOf(entity);
    }

    @Override
    public Set<R> getRelationshipsOf(D entityData) throws EntityNotFoundException {
        return directedGraph.getRelationshipsOf(entityData);
    }

    @Override
    public Set<R> getRelationshipsBetween(E source, E destination) throws EntityNotFoundException {
        return directedGraph.getRelationshipsBetween(source, destination);
    }

    @Override
    public Set<R> getRelationshipsBetween(D sourceData, D destinationData) throws EntityNotFoundException {
        return directedGraph.getRelationshipsBetween(sourceData, destinationData);
    }

    @Override
    public Set<R> getIncomingRelationshipsOf(E entity) throws EntityNotFoundException {
        return directedGraph.getIncomingRelationshipsOf(entity);
    }

    @Override
    public Set<R> getIncomingRelationshipsOf(D entityData) throws EntityNotFoundException {
        return directedGraph.getIncomingRelationshipsOf(entityData);
    }

    @Override
    public Set<R> getOutgoingRelationshipsOf(E entity) throws EntityNotFoundException {
        return directedGraph.getOutgoingRelationshipsOf(entity);
    }

    @Override
    public Set<R> getOutgoingRelationshipsOf(D entityData) throws EntityNotFoundException {
        return directedGraph.getOutgoingRelationshipsOf(entityData);
    }

    @Override
    public boolean removeRelationship(R relationship) throws RelationshipNotFoundException, EntityNotFoundException {
        var relationship1 = directedGraph.removeRelationship(relationship);
        var relationship2 = directedGraph.removeRelationship(relationship);

        return relationship1 && relationship2;
    }

    @Override
    public boolean removeAllRelationships(Collection<R> relationships) throws EntityNotFoundException {
        return directedGraph.removeAllRelationships(relationships);
    }

    @Override
    public Set<R> removeAllRelationshipsBetween(E source, E destination) throws EntityNotFoundException {
        return directedGraph.removeAllRelationshipsBetween(source, destination);
    }

    @Override
    public Set<R> removeAllRelationshipsBetween(D sourceData, D destinationData) throws RelationshipNotFoundException, EntityNotFoundException {
        return directedGraph.removeAllRelationshipsBetween(sourceData, destinationData);
    }

    @Override
    public boolean containsEntity(E entity) {
        return directedGraph.containsEntity(entity);
    }

    @Override
    public boolean containsEntity(D entityData) {
        return directedGraph.containsEntity(entityData);
    }

    @Override
    public boolean containsRelationship(R relationship) throws EntityNotFoundException {
        return directedGraph.containsRelationship(relationship);
    }

    @Override
    public boolean containsRelationship(E source, E destination) throws EntityNotFoundException {
        return directedGraph.containsRelationship(source, destination);
    }

    @Override
    public boolean containsRelationship(D sourceData, D destinationData) throws EntityNotFoundException {
        return directedGraph.containsRelationship(sourceData, destinationData);
    }

    @Override
    public Class<E> getEntityType() {
        return directedGraph.getEntityType();
    }

    @Override
    public Class<R> getRelationshipType() {
        return directedGraph.getRelationshipType();
    }
}
