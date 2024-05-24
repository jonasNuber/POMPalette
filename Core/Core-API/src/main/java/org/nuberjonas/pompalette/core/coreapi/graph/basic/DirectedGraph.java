package org.nuberjonas.pompalette.core.coreapi.graph.basic;

import org.nuberjonas.pompalette.core.coreapi.graph.api.*;
import org.nuberjonas.pompalette.core.coreapi.graph.api.exceptions.EntityAlreadyExistsException;
import org.nuberjonas.pompalette.core.coreapi.graph.api.exceptions.EntityNotFoundException;
import org.nuberjonas.pompalette.core.coreapi.graph.api.exceptions.RelationshipAlreadyExistsException;
import org.nuberjonas.pompalette.core.coreapi.graph.api.exceptions.RelationshipNotFoundException;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class DirectedGraph<E extends Entity<ED, RD>, R extends Relationship<ED, RD>, ED, RD> implements Graph<E, R, ED, RD> {
    private final Map<ED, E> entities;
    private final EntityFactory<E, ED, RD> entityFactory;
    private final RelationshipFactory<E, R, ED, RD> relationshipFactory;

    public DirectedGraph(EntityFactory<E, ED, RD> entityFactory, RelationshipFactory<E, R, ED, RD> relationshipFactory) {
        this.entities = new HashMap<>();
        this.entityFactory = entityFactory;
        this.relationshipFactory = relationshipFactory;
    }

    @Override
    public void addEntity(E entity) throws EntityAlreadyExistsException {
        if(exists(entity)){
            throw entityAlreadyExistsException(entity);
        }

        entities.put(entity.getData(), entity);
    }

    @Override
    public E addEntity(ED entityData) throws EntityAlreadyExistsException {
        var entity = entityFactory.createEntity(entityData);
        addEntity(entity);

        return entity;
    }

    @Override
    public E removeEntity(E entity) throws EntityNotFoundException {
        return removeEntity(entity.getData());
    }

    @Override
    public E removeEntity(ED entityData) throws EntityNotFoundException {
        if(doesNotExist(entityData)){
            throw entityNotFoundException(entityData);
        }

        return entities.remove(entityData);
    }

    @Override
    public E getEntity(ED entityData) throws EntityNotFoundException {
        var entity = entities.get(entityData);

        if(doesNotExist(entityData) || entity == null){
            throw entityNotFoundException(entityData);
        }

        return entity;
    }

    @Override
    public R addRelationship(E source, E destination, RD relationshipData) throws RelationshipAlreadyExistsException, EntityNotFoundException {
        return addRelationship(source.getData(), destination.getData(), relationshipData);
    }

    @Override
    public R addRelationship(ED sourceData, ED destinationData, RD relationshipData) throws RelationshipAlreadyExistsException, EntityNotFoundException {
        var source = entities.get(sourceData);
        var destination = entities.get(destinationData);

        if(doesNotExist(source)){
            throw entityNotFoundException(source);
        }
        if(doesNotExist(destination)){
            throw entityNotFoundException(destination);
        }

        var relationship = relationshipFactory.createRelationship(source, destination, relationshipData);

        if(!source.addRelationship(relationship)){
            throw relationshipAlreadyExistsException(source, destination, relationshipData);
        }

        return relationship;
    }

    @Override
    public R removeRelationship(E source, E destination) throws RelationshipNotFoundException, EntityNotFoundException {
        return removeRelationship(source.getData(), destination.getData());
    }

    @Override
    @SuppressWarnings("unchecked")
    public R removeRelationship(ED sourceEntity, ED destinationEntity) throws RelationshipNotFoundException, EntityNotFoundException {
        var source = entities.get(sourceEntity);
        var destination = entities.get(destinationEntity);

        if(doesNotExist(source)){
            throw entityNotFoundException(source);
        }
        if(doesNotExist(destination)){
            throw entityNotFoundException(destination);
        }

        for(var relationship : source.getRelationships()){
            if(destination.equals(relationship.destination())){
                source.removeRelationship(relationship);
                return (R) relationship;
            }
        }

        throw relationshipNotFoundException(sourceEntity, destinationEntity);
    }

    @Override
    @SuppressWarnings("unchecked")
    public Set<R> getRelationships() {
        var relationships = new HashSet<R>();

        for(var entity : entities.values()){
            relationships.addAll((Set<R>) entity.getRelationships());
        }

        return relationships;
    }

    private boolean doesNotExist(E entity){
        return doesNotExist(entity.getData());
    }

    private boolean doesNotExist(ED entityData){
        return !exists(entityData);
    }

    private boolean exists(E entity){
        return exists(entity.getData());
    }

    private boolean exists(ED entityData){
        return entities.containsKey(entityData);
    }

    private EntityAlreadyExistsException entityAlreadyExistsException(E entity){
        return new EntityAlreadyExistsException(String.format("The entity: '%s' already exists", entity.getData()));
    }

    private EntityNotFoundException entityNotFoundException(E entity){
        return entityNotFoundException(entity.getData());
    }

    private EntityNotFoundException entityNotFoundException(ED entityData){
        return new EntityNotFoundException(String.format("The entity '%s' was not found", entityData));
    }

    private RelationshipAlreadyExistsException relationshipAlreadyExistsException(E source, E destination, RD relationshipData){
        return relationshipAlreadyExistsException(source.getData(), destination.getData(), relationshipData);
    }

    private RelationshipAlreadyExistsException relationshipAlreadyExistsException(ED sourceData, ED destinationData, RD relationshipData){
        return new RelationshipAlreadyExistsException(String.format("Relationship: '%s' from entity '%s' to entity '%s' already exists", relationshipData, sourceData, destinationData));
    }

    private RelationshipNotFoundException relationshipNotFoundException(ED sourceData, ED destinationData){
        return new RelationshipNotFoundException(String.format("Relationship from entity '%s' to entity '%s' was not found", sourceData, destinationData));
    }
}
