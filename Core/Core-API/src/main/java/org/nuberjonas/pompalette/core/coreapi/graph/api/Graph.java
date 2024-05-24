package org.nuberjonas.pompalette.core.coreapi.graph.api;

import org.nuberjonas.pompalette.core.coreapi.graph.api.exceptions.EntityAlreadyExistsException;
import org.nuberjonas.pompalette.core.coreapi.graph.api.exceptions.EntityNotFoundException;
import org.nuberjonas.pompalette.core.coreapi.graph.api.exceptions.RelationshipAlreadyExistsException;
import org.nuberjonas.pompalette.core.coreapi.graph.api.exceptions.RelationshipNotFoundException;

import java.util.Set;

public interface Graph<E extends Entity<ED, RD>, R extends Relationship<ED, RD>, ED, RD> {
    void addEntity(E entity) throws EntityAlreadyExistsException;
    E addEntity(ED entityData) throws EntityAlreadyExistsException;
    E removeEntity(E entity) throws EntityNotFoundException;
    E removeEntity(ED entityData) throws EntityNotFoundException;
    E getEntity(ED entityData) throws EntityNotFoundException;
    R addRelationship(E source, E destination, RD relationshipData) throws RelationshipAlreadyExistsException, EntityNotFoundException;
    R addRelationship(ED sourceData, ED destinationData, RD relationshipData) throws RelationshipAlreadyExistsException, EntityNotFoundException;
    R removeRelationship(E source, E destination) throws RelationshipNotFoundException, EntityNotFoundException;
    R removeRelationship(ED sourceEntity, ED destinationEntity) throws RelationshipNotFoundException, EntityNotFoundException;
    Set<R> getRelationships();
}
