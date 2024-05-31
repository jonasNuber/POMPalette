package org.nuberjonas.pompalette.core.coreapi.graph;

import org.nuberjonas.pompalette.core.coreapi.graph.exceptions.EntityAlreadyExistsException;
import org.nuberjonas.pompalette.core.coreapi.graph.exceptions.EntityNotFoundException;
import org.nuberjonas.pompalette.core.coreapi.graph.exceptions.RelationshipAlreadyExistsException;
import org.nuberjonas.pompalette.core.coreapi.graph.exceptions.RelationshipNotFoundException;

import java.util.Collection;
import java.util.Set;

public interface Graph<E extends Entity<D, U>, R extends Relationship<D, U>, D, U> {
    void addEntity(E entity) throws EntityAlreadyExistsException;
    E addEntity(D entityData) throws EntityAlreadyExistsException;
    E getEntity(D entityData) throws EntityNotFoundException;
    Set<E> getEntities();
    E getRelationshipSourceOf(R relationship) throws EntityNotFoundException, RelationshipNotFoundException;
    E getRelationshipDestinationOf(R relationship) throws EntityNotFoundException, RelationshipNotFoundException;
    boolean removeEntity(E entity) throws EntityNotFoundException;
    E removeEntity(D entityData) throws EntityNotFoundException;
    boolean removeAllEntities(Collection<E> entities) throws EntityNotFoundException;
    R addRelationship(E source, E destination, U relationshipData) throws RelationshipAlreadyExistsException, EntityNotFoundException;
    R addRelationship(D sourceData, D destinationData, U relationshipData) throws RelationshipAlreadyExistsException, EntityNotFoundException;
    Set<R> getRelationships();
    Set<R> getRelationshipsOf(E entity) throws EntityNotFoundException;
    Set<R> getRelationshipsOf(D entityData) throws EntityNotFoundException;
    Set<R> getRelationshipsBetween(E source, E destination) throws EntityNotFoundException;
    Set<R> getRelationshipsBetween(D sourceData, D destinationData) throws EntityNotFoundException;
    Set<R> getIncomingRelationshipsOf(E entity) throws EntityNotFoundException;
    Set<R> getIncomingRelationshipsOf(D entityData) throws EntityNotFoundException;
    Set<R> getOutgoingRelationshipsOf(E entity) throws EntityNotFoundException;
    Set<R> getOutgoingRelationshipsOf(D entityData) throws EntityNotFoundException;
    boolean removeRelationship(R relationship) throws RelationshipNotFoundException, EntityNotFoundException;
    boolean removeAllRelationships(Collection<R> relationships) throws EntityNotFoundException;
    Set<R> removeAllRelationshipsBetween(E source, E destination) throws EntityNotFoundException;
    Set<R> removeAllRelationshipsBetween(D sourceData, D destinationData) throws RelationshipNotFoundException, EntityNotFoundException;
    boolean containsEntity(E entity);
    boolean containsEntity(D entityData);
    boolean containsRelationship(R relationship) throws EntityNotFoundException;
    boolean containsRelationship(E source, E destination) throws EntityNotFoundException;
    boolean containsRelationship(D sourceData, D destinationData) throws EntityNotFoundException;
    Class<E> getEntityType();
    Class<R> getRelationshipType();
}
