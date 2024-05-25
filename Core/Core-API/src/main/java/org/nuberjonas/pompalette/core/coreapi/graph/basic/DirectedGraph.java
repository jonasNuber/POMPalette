package org.nuberjonas.pompalette.core.coreapi.graph.basic;

import org.nuberjonas.pompalette.core.coreapi.graph.api.*;
import org.nuberjonas.pompalette.core.coreapi.graph.api.exceptions.EntityAlreadyExistsException;
import org.nuberjonas.pompalette.core.coreapi.graph.api.exceptions.EntityNotFoundException;
import org.nuberjonas.pompalette.core.coreapi.graph.api.exceptions.RelationshipAlreadyExistsException;
import org.nuberjonas.pompalette.core.coreapi.graph.api.exceptions.RelationshipNotFoundException;

import java.lang.reflect.ParameterizedType;
import java.util.*;
import java.util.stream.Collectors;

@SuppressWarnings("unchecked")
public class DirectedGraph<E extends Entity<D, U>, R extends Relationship<D, U>, D, U> implements Graph<E, R, D, U> {
    private final Map<D, E> entities;
    private final Map<E, Set<R>> relationshipCache;
    private final EntityFactory<E, D, U> entityFactory;
    private final RelationshipFactory<E, R, D, U> relationshipFactory;
    
    public DirectedGraph(EntityFactory<E, D, U> entityFactory, RelationshipFactory<E, R, D, U> relationshipFactory) {
        this.entities = Collections.synchronizedMap(new HashMap<>());
        this.relationshipCache = Collections.synchronizedMap(new HashMap<>());
        this.entityFactory = entityFactory;
        this.relationshipFactory = relationshipFactory;
    }

    @Override
    public synchronized void addEntity(E entity) throws EntityAlreadyExistsException {
        if(exists(entity)){
            throw entityAlreadyExistsException(entity);
        }

        entities.put(entity.getData(), entity);
    }

    @Override
    public synchronized E addEntity(D entityData) throws EntityAlreadyExistsException {
        var entity = entityFactory.createEntity(entityData);
        addEntity(entity);

        return entity;
    }

    @Override
    public synchronized E getEntity(D entityData) throws EntityNotFoundException {
        var entity = entities.get(entityData);

        if(doesNotExist(entityData) || entity == null){
            throw entityNotFoundException(entityData);
        }

        return entity;
    }

    @Override
    public synchronized Set<E> getEntities() {
        return new HashSet<>(entities.values());
    }

    @Override
    public synchronized E getRelationshipSourceOf(R relationship) throws EntityNotFoundException, RelationshipNotFoundException {
        var source = (E) relationship.source();
        var destination = (E) relationship.destination();

        checkEntities(source, destination);

        if(source.containsRelationship(relationship)){
            return source;
        }

        throw relationshipNotFoundException(relationship);
    }

    @Override
    public synchronized E getRelationshipTargetOf(R relationship) throws EntityNotFoundException, RelationshipNotFoundException {
        var source = (E) relationship.source();
        var destination = (E) relationship.destination();

        checkEntities(source, destination);

        if(source.containsRelationship(relationship)){
            return destination;
        }

        throw relationshipNotFoundException(relationship);
    }

    @Override
    public synchronized boolean removeEntity(E entity) throws EntityNotFoundException {
        removeEntity(entity.getData());
        return !entities.containsValue(entity);
    }

    @Override
    public synchronized E removeEntity(D entityData) throws EntityNotFoundException {
        if(doesNotExist(entityData)){
            throw entityNotFoundException(entityData);
        }

        var entity = entities.get(entityData);

        relationshipCache.get(entity).forEach(r -> {
            if(entity.equals(r.destination())){
                r.source().removeRelationship(r);
            }
        });

        invalidateCachedRelationshipsOf(entity);

        return entities.remove(entityData);
    }

    @Override
    public synchronized boolean removeAllEntities(Collection<E> entities) throws EntityNotFoundException {
        for (var entity : entities){
            removeEntity(entity);
        }

        return !this.entities.values().containsAll(entities);
    }

    @Override
    public synchronized R addRelationship(E source, E destination, U relationshipData) throws RelationshipAlreadyExistsException, EntityNotFoundException {
        return addRelationship(source.getData(), destination.getData(), relationshipData);
    }

    @Override
    public synchronized R addRelationship(D sourceData, D destinationData, U relationshipData) throws RelationshipAlreadyExistsException, EntityNotFoundException {
        var source = entities.get(sourceData);
        var destination = entities.get(destinationData);

        checkEntities(source, destination);

        var relationship = relationshipFactory.createRelationship(source, destination, relationshipData);

        if(!source.addRelationship(relationship)){
            throw relationshipAlreadyExistsException(source, destination, relationshipData);
        }

        invalidateCachedRelationshipsOf(source, destination);

        return relationship;
    }

    @Override
    public synchronized Set<R> getRelationships() {
        var relationships = new HashSet<R>();

        for(var entity : entities.values()){
            relationships.addAll((Set<R>) entity.getRelationships());
        }

        return relationships;
    }

    @Override
    public synchronized Set<R> getRelationshipsOf(E entity) throws EntityNotFoundException {
        return getCachedRelationshipsOf(entity);
    }

    @Override
    public synchronized Set<R> getRelationshipsOf(D entityData) throws EntityNotFoundException {
        return getRelationshipsOf(entities.get(entityData));
    }

    @Override
    public synchronized Set<R> getRelationshipsBetween(E source, E destination) throws EntityNotFoundException {
        checkEntities(source, destination);

        return (Set<R>) source.getRelationships().stream().filter(relationship -> destination.equals(relationship.destination())).collect(Collectors.toSet());
    }

    @Override
    public synchronized Set<R> getRelationshipsBetween(D sourceData, D destinationData) throws EntityNotFoundException {
        return getRelationshipsBetween(entities.get(sourceData), entities.get(destinationData));
    }

    @Override
    public synchronized Set<R> getIncomingRelationshipsOf(E entity) throws EntityNotFoundException {
        return (Set<R>) getCachedRelationshipsOf(entity).stream().filter(r -> entity.equals(r.destination()));
    }

    @Override
    public synchronized Set<R> getIncomingRelationshipsOf(D entityData) throws EntityNotFoundException {
        return getIncomingRelationshipsOf(entities.get(entityData));
    }

    @Override
    public synchronized Set<R> getOutgoingRelationshipsOf(E entity) throws EntityNotFoundException {
        checkEntities(entity);
        return (Set<R>) entity.getRelationships();
    }

    @Override
    public synchronized Set<R> getOutgoingRelationshipsOf(D entityData) throws EntityNotFoundException {
        return getOutgoingRelationshipsOf(entities.get(entityData));
    }

    @Override
    public synchronized boolean removeRelationship(R relationship) throws RelationshipNotFoundException, EntityNotFoundException {
        var source = (E) relationship.source();
        var destination = (E) relationship.destination();

       checkEntities(source, destination);

        for(var soureRelationship : source.getRelationships()){
            if(relationship.equals(soureRelationship)){
                source.removeRelationship(relationship);
                invalidateCachedRelationshipsOf(source, destination);

                return !source.containsRelationship(relationship);
            }
        }

        throw relationshipNotFoundException(relationship);
    }

    @Override
    public synchronized boolean removeAllRelationships(Collection<R> relationships) throws RelationshipNotFoundException, EntityNotFoundException {
        relationships.forEach(this::removeRelationship);

        return !getRelationships().containsAll(relationships);
    }

    @Override
    public synchronized Set<R> removeAllRelationshipsBetween(E source, E destination) throws EntityNotFoundException {
        checkEntities(source, destination);
        var relationships = (Set<R>) source.getRelationships().stream().filter(relationship -> destination.equals(relationship.destination())).collect(Collectors.toSet());

        relationships.forEach(this::removeRelationship);

        return relationships;
    }

    @Override
    public synchronized Set<R> removeAllRelationshipsBetween(D sourceData, D destinationData) throws EntityNotFoundException {
        return removeAllRelationshipsBetween(entities.get(sourceData), entities.get(destinationData));
    }

    @Override
    public synchronized boolean containsEntity(E entity) {
        return entities.containsValue(entity);
    }

    @Override
    public synchronized boolean containsEntity(D entityData) {
        return entities.containsKey(entityData);
    }

    @Override
    public synchronized boolean containsRelationship(R relationship) throws EntityNotFoundException {
        return getRelationships().contains(relationship);
    }

    @Override
    public synchronized boolean containsRelationship(E source, E destination) throws EntityNotFoundException {
        checkEntities(source, destination);
        return source.getRelationships().stream().anyMatch(relationship -> destination.equals(relationship.destination()));
    }

    @Override
    public synchronized boolean containsRelationship(D sourceData, D destinationData) throws EntityNotFoundException {
        return containsRelationship(entities.get(sourceData), entities.get(destinationData));
    }

    @Override
    public Class<E> getEntityType() {
        return (Class<E>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0].getClass();
    }

    @Override
    public Class<R> getRelationshipType() {
        return (Class<R>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[1].getClass();
    }

    private synchronized Set<R> getCachedRelationshipsOf(E entity) throws EntityNotFoundException{
        checkEntities(entity);

        return relationshipCache.computeIfAbsent(entity, e -> getRelationships().stream().filter(r -> e.equals(r.source()) || e.equals(r.destination())).collect(Collectors.toSet()));
    }

    @SafeVarargs
    private synchronized void invalidateCachedRelationshipsOf(E... entities){
        for(var entity : entities){
            relationshipCache.remove(entity);
        }
    }

    @SafeVarargs
    private synchronized void checkEntities(E... entities){
        for(var entity : entities){
            if(doesNotExist(entity)){
                throw entityNotFoundException(entity);
            }
        }
    }

    private synchronized boolean doesNotExist(E entity){
        return doesNotExist(entity.getData());
    }

    private synchronized boolean doesNotExist(D entityData){
        return !exists(entityData);
    }

    private synchronized boolean exists(E entity){
        return exists(entity.getData());
    }

    private synchronized boolean exists(D entityData){
        return entities.containsKey(entityData);
    }

    private synchronized EntityAlreadyExistsException entityAlreadyExistsException(E entity){
        return new EntityAlreadyExistsException(String.format("The entity: '%s' already exists", entity.getData()));
    }

    private synchronized EntityNotFoundException entityNotFoundException(E entity){
        return entityNotFoundException(entity.getData());
    }

    private synchronized EntityNotFoundException entityNotFoundException(D entityData){
        return new EntityNotFoundException(String.format("The entity '%s' was not found", entityData));
    }

    private synchronized RelationshipAlreadyExistsException relationshipAlreadyExistsException(E source, E destination, U relationshipData){
        return relationshipAlreadyExistsException(source.getData(), destination.getData(), relationshipData);
    }

    private synchronized RelationshipAlreadyExistsException relationshipAlreadyExistsException(D sourceData, D destinationData, U relationshipData){
        return new RelationshipAlreadyExistsException(String.format("Relationship: '%s' from entity '%s' to entity '%s' already exists", relationshipData, sourceData, destinationData));
    }

    private synchronized RelationshipNotFoundException relationshipNotFoundException(R relationship){
        return new RelationshipNotFoundException(String.format("Relationship '%s' from entity '%s' to entity '%s' was not found", relationship.data(), relationship.source(), relationship.destination()));
    }
}
