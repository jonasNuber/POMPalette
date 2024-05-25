package org.nuberjonas.pompalette.core.coreapi.graph.basic;

import org.nuberjonas.pompalette.core.coreapi.graph.api.*;
import org.nuberjonas.pompalette.core.coreapi.graph.api.exceptions.EntityAlreadyExistsException;
import org.nuberjonas.pompalette.core.coreapi.graph.api.exceptions.EntityNotFoundException;
import org.nuberjonas.pompalette.core.coreapi.graph.api.exceptions.RelationshipAlreadyExistsException;
import org.nuberjonas.pompalette.core.coreapi.graph.api.exceptions.RelationshipNotFoundException;

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
    public synchronized E removeEntity(E entity) throws EntityNotFoundException {
        return directedGraph.removeEntity(entity);
    }

    @Override
    public synchronized E removeEntity(D entityData) throws EntityNotFoundException {
        return directedGraph.removeEntity(entityData);
    }

    @Override
    public synchronized E getEntity(D entityData) throws EntityNotFoundException {
        return directedGraph.getEntity(entityData);
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
    public synchronized R removeRelationship(E source, E destination) throws RelationshipNotFoundException, EntityNotFoundException {
        R relationship = directedGraph.removeRelationship(source, destination);
        directedGraph.removeRelationship(destination, source);
        return relationship;
    }

    @Override
    public synchronized R removeRelationship(D sourceEntity, D destinationEntity) throws RelationshipNotFoundException, EntityNotFoundException {
        R relationship = directedGraph.removeRelationship(sourceEntity, destinationEntity);
        directedGraph.removeRelationship(destinationEntity, sourceEntity);
        return relationship;
    }

    @Override
    public synchronized Set<R> getRelationships() {
        return directedGraph.getRelationships();
    }
}
