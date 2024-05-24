package org.nuberjonas.pompalette.core.coreapi.graph.basic;

import org.nuberjonas.pompalette.core.coreapi.graph.api.*;
import org.nuberjonas.pompalette.core.coreapi.graph.api.exceptions.EntityAlreadyExistsException;
import org.nuberjonas.pompalette.core.coreapi.graph.api.exceptions.EntityNotFoundException;
import org.nuberjonas.pompalette.core.coreapi.graph.api.exceptions.RelationshipAlreadyExistsException;
import org.nuberjonas.pompalette.core.coreapi.graph.api.exceptions.RelationshipNotFoundException;

import java.util.Set;

public class UndirectedGraph<E extends Entity<ED, RD>, R extends Relationship<ED, RD>, ED, RD> implements Graph<E, R, ED, RD> {
    private final DirectedGraph<E, R, ED, RD> directedGraph;

    public UndirectedGraph(EntityFactory<E, ED, RD> entityFactory, RelationshipFactory<E, R, ED, RD> relationshipFactory) {
        this.directedGraph = new DirectedGraph<>(entityFactory, relationshipFactory);
    }

    @Override
    public void addEntity(E entity) throws EntityAlreadyExistsException {
        directedGraph.addEntity(entity);
    }

    @Override
    public E addEntity(ED entityData) throws EntityAlreadyExistsException {
        return directedGraph.addEntity(entityData);
    }

    @Override
    public E removeEntity(E entity) throws EntityNotFoundException {
        return directedGraph.removeEntity(entity);
    }

    @Override
    public E removeEntity(ED entityData) throws EntityNotFoundException {
        return directedGraph.removeEntity(entityData);
    }

    @Override
    public E getEntity(ED entityData) throws EntityNotFoundException {
        return directedGraph.getEntity(entityData);
    }

    @Override
    public R addRelationship(E source, E destination, RD relationshipData) throws RelationshipAlreadyExistsException, EntityNotFoundException {
        R relationship = directedGraph.addRelationship(source, destination, relationshipData);
        directedGraph.addRelationship(destination, source, relationshipData);
        return relationship;
    }

    @Override
    public R addRelationship(ED sourceData, ED destinationData, RD relationshipData) throws RelationshipAlreadyExistsException, EntityNotFoundException {
        R relationship = directedGraph.addRelationship(sourceData, destinationData, relationshipData);
        directedGraph.addRelationship(destinationData, sourceData, relationshipData);
        return relationship;
    }

    @Override
    public R removeRelationship(E source, E destination) throws RelationshipNotFoundException, EntityNotFoundException {
        R relationship = directedGraph.removeRelationship(source, destination);
        directedGraph.removeRelationship(destination, source);
        return relationship;
    }

    @Override
    public R removeRelationship(ED sourceEntity, ED destinationEntity) throws RelationshipNotFoundException, EntityNotFoundException {
        R relationship = directedGraph.removeRelationship(sourceEntity, destinationEntity);
        directedGraph.removeRelationship(destinationEntity, sourceEntity);
        return relationship;
    }

    @Override
    public Set<R> getRelationships() {
        return directedGraph.getRelationships();
    }
}
