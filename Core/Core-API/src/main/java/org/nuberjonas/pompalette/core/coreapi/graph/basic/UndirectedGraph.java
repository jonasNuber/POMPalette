package org.nuberjonas.pompalette.core.coreapi.graph.basic;

import org.nuberjonas.pompalette.core.coreapi.graph.api.*;
import org.nuberjonas.pompalette.core.coreapi.graph.api.exceptions.EntityAlreadyExistsException;
import org.nuberjonas.pompalette.core.coreapi.graph.api.exceptions.EntityNotFoundException;
import org.nuberjonas.pompalette.core.coreapi.graph.api.exceptions.RelationshipAlreadyExistsException;
import org.nuberjonas.pompalette.core.coreapi.graph.api.exceptions.RelationshipNotFoundException;

import java.util.Collection;
import java.util.Set;

public class UndirectedGraph<E extends Entity<D, U>, R extends Relationship<D, U>, D, U> implements Graph<E, R, D, U> {
    private final DirectedGraph<E, R, D, U> directedGraph;

    public UndirectedGraph(EntityFactory<E, D, U> entityFactory, RelationshipFactory<E, R, D, U> relationshipFactory) {
        this.directedGraph = new DirectedGraph<>(entityFactory, relationshipFactory);
    }


    @Override
    public synchronized void addEntity(E entity) throws EntityAlreadyExistsException {
        directedGraph.addEntity(entity);
    }

    @Override
    public synchronized E addEntity(D entityData) throws EntityAlreadyExistsException {
        return directedGraph.addEntity(entityData);
    }

    @Override
    public synchronized E getEntity(D entityData) throws EntityNotFoundException {
        return directedGraph.getEntity(entityData);
    }

    @Override
    public synchronized Set<E> getEntities() {
        return directedGraph.getEntities();
    }

    @Override
    public synchronized E getRelationshipSourceOf(R relationship) throws EntityNotFoundException, RelationshipNotFoundException {
        return directedGraph.getRelationshipSourceOf(relationship);
    }

    @Override
    public synchronized E getRelationshipTargetOf(R relationship) throws EntityNotFoundException, RelationshipNotFoundException {
        return directedGraph.getRelationshipTargetOf(relationship);
    }

    @Override
    public synchronized boolean removeEntity(E entity) throws EntityNotFoundException {
        return directedGraph.removeEntity(entity);
    }

    @Override
    public synchronized E removeEntity(D entityData) throws EntityNotFoundException {
        return directedGraph.removeEntity(entityData);
    }

    @Override
    public synchronized boolean removeAllEntities(Collection<E> entities) throws EntityNotFoundException {
        return directedGraph.removeAllEntities(entities);
    }

    @Override
    public synchronized R addRelationship(E source, E destination, U relationshipData) throws RelationshipAlreadyExistsException, EntityNotFoundException {
        R relationship = directedGraph.addRelationship(source, destination, relationshipData);
        directedGraph.addRelationship(destination, source, relationshipData);
        return relationship;
    }

    @Override
    public synchronized R addRelationship(D sourceData, D destinationData, U relationshipData) throws RelationshipAlreadyExistsException, EntityNotFoundException {
        R relationship = directedGraph.addRelationship(sourceData, destinationData, relationshipData);
        directedGraph.addRelationship(destinationData, sourceData, relationshipData);
        return relationship;
    }

    @Override
    public synchronized Set<R> getRelationships() {
        return directedGraph.getRelationships();
    }

    @Override
    public synchronized Set<R> getRelationshipsOf(E entity) throws EntityNotFoundException {
        return directedGraph.getRelationshipsOf(entity);
    }

    @Override
    public synchronized Set<R> getRelationshipsOf(D entityData) throws EntityNotFoundException {
        return directedGraph.getRelationshipsOf(entityData);
    }

    @Override
    public synchronized Set<R> getRelationshipsBetween(E source, E destination) throws EntityNotFoundException {
        return directedGraph.getRelationshipsBetween(source, destination);
    }

    @Override
    public synchronized Set<R> getRelationshipsBetween(D sourceData, D destinationData) throws EntityNotFoundException {
        return directedGraph.getRelationshipsBetween(sourceData, destinationData);
    }

    @Override
    public synchronized Set<R> getIncomingRelationshipsOf(E entity) throws EntityNotFoundException {
        return directedGraph.getIncomingRelationshipsOf(entity);
    }

    @Override
    public synchronized Set<R> getIncomingRelationshipsOf(D entityData) throws EntityNotFoundException {
        return directedGraph.getIncomingRelationshipsOf(entityData);
    }

    @Override
    public synchronized Set<R> getOutgoingRelationshipsOf(E entity) throws EntityNotFoundException {
        return directedGraph.getOutgoingRelationshipsOf(entity);
    }

    @Override
    public synchronized Set<R> getOutgoingRelationshipsOf(D entityData) throws EntityNotFoundException {
        return directedGraph.getOutgoingRelationshipsOf(entityData);
    }

    @Override
    public synchronized boolean removeRelationship(R relationship) throws RelationshipNotFoundException, EntityNotFoundException {
        var relationship1 = directedGraph.removeRelationship(relationship);
        var relationship2 = directedGraph.removeRelationship(relationship);
        return relationship1 && relationship2;
    }

    @Override
    public synchronized boolean removeAllRelationships(Collection<R> relationships) throws EntityNotFoundException {
        return directedGraph.removeAllRelationships(relationships);
    }

    @Override
    public synchronized Set<R> removeAllRelationshipsBetween(E source, E destination) throws EntityNotFoundException {
        return directedGraph.removeAllRelationshipsBetween(source, destination);
    }

    @Override
    public synchronized Set<R> removeAllRelationshipsBetween(D sourceData, D destinationData) throws RelationshipNotFoundException, EntityNotFoundException {
        return directedGraph.removeAllRelationshipsBetween(sourceData, destinationData);
    }

    @Override
    public synchronized boolean containsEntity(E entity) {
        return directedGraph.containsEntity(entity);
    }

    @Override
    public synchronized boolean containsEntity(D entityData) {
        return directedGraph.containsEntity(entityData);
    }

    @Override
    public synchronized boolean containsRelationship(R relationship) throws EntityNotFoundException {
        return directedGraph.containsRelationship(relationship);
    }

    @Override
    public synchronized boolean containsRelationship(E source, E destination) throws EntityNotFoundException {
        return directedGraph.containsRelationship(source, destination);
    }

    @Override
    public synchronized boolean containsRelationship(D sourceData, D destinationData) throws EntityNotFoundException {
        return directedGraph.containsRelationship(sourceData, destinationData);
    }

    @Override
    public Class<E> getEntityType() {
        return null;
    }

    @Override
    public Class<R> getRelationshipType() {
        return null;
    }


}
