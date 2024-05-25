package org.nuberjonas.pompalette.core.coreapi.graph.api;

import org.nuberjonas.pompalette.core.coreapi.graph.api.exceptions.EntityAlreadyExistsException;
import org.nuberjonas.pompalette.core.coreapi.graph.api.exceptions.EntityNotFoundException;
import org.nuberjonas.pompalette.core.coreapi.graph.api.exceptions.RelationshipAlreadyExistsException;
import org.nuberjonas.pompalette.core.coreapi.graph.api.exceptions.RelationshipNotFoundException;

import java.util.Set;

public interface Graph<E extends Entity<D, U>, R extends Relationship<D, U>, D, U> {
    void addEntity(E entity) throws EntityAlreadyExistsException;
    E addEntity(D entityData) throws EntityAlreadyExistsException;
    E removeEntity(E entity) throws EntityNotFoundException;
    E removeEntity(D entityData) throws EntityNotFoundException;
    E getEntity(D entityData) throws EntityNotFoundException;
    R addRelationship(E source, E destination, U relationshipData) throws RelationshipAlreadyExistsException, EntityNotFoundException;
    R addRelationship(D sourceData, D destinationData, U relationshipData) throws RelationshipAlreadyExistsException, EntityNotFoundException;
    R removeRelationship(E source, E destination) throws RelationshipNotFoundException, EntityNotFoundException;
    R removeRelationship(D sourceEntity, D destinationEntity) throws RelationshipNotFoundException, EntityNotFoundException;
    Set<R> getRelationships();
}
