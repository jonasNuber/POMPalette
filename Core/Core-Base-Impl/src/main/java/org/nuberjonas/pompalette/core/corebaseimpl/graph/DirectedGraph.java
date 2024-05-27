package org.nuberjonas.pompalette.core.corebaseimpl.graph;

import org.nuberjonas.pompalette.core.coreapi.graph.*;
import org.nuberjonas.pompalette.core.coreapi.graph.exceptions.EntityAlreadyExistsException;
import org.nuberjonas.pompalette.core.coreapi.graph.exceptions.EntityNotFoundException;
import org.nuberjonas.pompalette.core.coreapi.graph.exceptions.RelationshipAlreadyExistsException;
import org.nuberjonas.pompalette.core.coreapi.graph.exceptions.RelationshipNotFoundException;

import java.lang.reflect.ParameterizedType;
import java.util.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.stream.Collectors;

@SuppressWarnings("unchecked")
public class DirectedGraph<E extends Entity<D, U>, R extends Relationship<D, U>, D, U> implements Graph<E, R, D, U> {
    private final Map<D, E> entities;
    private final Map<E, Set<R>> relationshipCache;
    private final EntityFactory<E, D, U> entityFactory;
    private final RelationshipFactory<E, R, D, U> relationshipFactory;

    private final ReentrantReadWriteLock rwLock = new ReentrantReadWriteLock();
    private final Lock readLock = rwLock.readLock();
    private final Lock writeLock = rwLock.writeLock();

    public DirectedGraph(EntityFactory<E, D, U> entityFactory, RelationshipFactory<E, R, D, U> relationshipFactory) {
        this.entities = new HashMap<>();
        this.relationshipCache = new HashMap<>();
        this.entityFactory = entityFactory;
        this.relationshipFactory = relationshipFactory;
    }

    @Override
    public void addEntity(E entity) throws EntityAlreadyExistsException {
        writeLock.lock();

        try {
            if (exists(entity)) {
                throw entityAlreadyExistsException(entity);
            }

            entities.put(entity.getData(), entity);
        } finally {
            writeLock.unlock();
        }
    }

    @Override
    public E addEntity(D entityData) throws EntityAlreadyExistsException {
        var entity = entityFactory.createEntity(entityData);
        addEntity(entity);
        return entity;
    }

    @Override
    public E getEntity(D entityData) throws EntityNotFoundException {
        readLock.lock();

        try {
            var entity = entities.get(entityData);

            if (entity == null) {
                throw entityNotFoundException(entityData);
            }

            return entity;
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public Set<E> getEntities() {
        readLock.lock();

        try {
            return new HashSet<>(entities.values());
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public E getRelationshipSourceOf(R relationship) throws EntityNotFoundException, RelationshipNotFoundException {
        readLock.lock();

        try {
            var source = (E) relationship.source();
            var destination = (E) relationship.destination();
            checkEntities(source, destination);

            if (source.containsRelationship(relationship)) {
                return source;
            }

            throw relationshipNotFoundException(relationship);
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public E getRelationshipTargetOf(R relationship) throws EntityNotFoundException, RelationshipNotFoundException {
        readLock.lock();
        try {
            var source = (E) relationship.source();
            var destination = (E) relationship.destination();

            checkEntities(source, destination);

            if (source.containsRelationship(relationship)) {
                return destination;
            }

            throw relationshipNotFoundException(relationship);
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public boolean removeEntity(E entity) throws EntityNotFoundException {
        return removeEntity(entity.getData()) != null;
    }

    @Override
    public E removeEntity(D entityData) throws EntityNotFoundException {
        writeLock.lock();

        try {
            if (!exists(entityData)) {
                throw entityNotFoundException(entityData);
            }

            var entity = entities.remove(entityData);

            getCachedRelationshipsOf(entity).forEach(r -> {
                if (entity.equals(r.destination())) {
                    r.source().removeRelationship(r);
                }
            });

            invalidateCachedRelationshipsOf(entity);

            return entity;
        } finally {
            writeLock.unlock();
        }
    }

    @Override
    public boolean removeAllEntities(Collection<E> entities) throws EntityNotFoundException {
        writeLock.lock();

        try {
            for (var entity : entities) {
                removeEntity(entity);
            }

            return true;
        } finally {
            writeLock.unlock();
        }
    }

    @Override
    public R addRelationship(E source, E destination, U relationshipData) throws RelationshipAlreadyExistsException, EntityNotFoundException {
        return addRelationship(source.getData(), destination.getData(), relationshipData);
    }

    @Override
    public R addRelationship(D sourceData, D destinationData, U relationshipData) throws RelationshipAlreadyExistsException, EntityNotFoundException {
        writeLock.lock();
        try {
            var source = entities.get(sourceData);
            var destination = entities.get(destinationData);
            checkEntities(source, destination);

            var relationship = relationshipFactory.createRelationship(source, destination, relationshipData);

            if (!source.addRelationship(relationship)) {
                throw relationshipAlreadyExistsException(source, destination, relationshipData);
            }

            invalidateCachedRelationshipsOf(source, destination);

            return relationship;
        } finally {
            writeLock.unlock();
        }
    }

    @Override
    public Set<R> getRelationships() {
        readLock.lock();

        try {
            var relationships = new HashSet<R>();

            for (var entity : entities.values()) {
                relationships.addAll((Set<R>) entity.getRelationships());
            }

            return relationships;
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public Set<R> getRelationshipsOf(E entity) throws EntityNotFoundException {
        return getCachedRelationshipsOf(entity);
    }

    @Override
    public Set<R> getRelationshipsOf(D entityData) throws EntityNotFoundException {
        return getRelationshipsOf(entities.get(entityData));
    }

    @Override
    public Set<R> getRelationshipsBetween(E source, E destination) throws EntityNotFoundException {
        checkEntities(source, destination);

        return (Set<R>) source.getRelationships().stream()
                .filter(relationship -> destination.equals(relationship.destination()))
                .collect(Collectors.toSet());
    }

    @Override
    public Set<R> getRelationshipsBetween(D sourceData, D destinationData) throws EntityNotFoundException {
        return getRelationshipsBetween(entities.get(sourceData), entities.get(destinationData));
    }

    @Override
    public Set<R> getIncomingRelationshipsOf(E entity) throws EntityNotFoundException {
        return getCachedRelationshipsOf(entity).stream()
                .filter(r -> entity.equals(r.destination()))
                .collect(Collectors.toSet());
    }

    @Override
    public Set<R> getIncomingRelationshipsOf(D entityData) throws EntityNotFoundException {
        return getIncomingRelationshipsOf(entities.get(entityData));
    }

    @Override
    public Set<R> getOutgoingRelationshipsOf(E entity) throws EntityNotFoundException {
        checkEntities(entity);

        return (Set<R>) entity.getRelationships();
    }

    @Override
    public Set<R> getOutgoingRelationshipsOf(D entityData) throws EntityNotFoundException {
        return getOutgoingRelationshipsOf(entities.get(entityData));
    }

    @Override
    public boolean removeRelationship(R relationship) throws RelationshipNotFoundException, EntityNotFoundException {
        writeLock.lock();
        try {
            var source = (E) relationship.source();
            var destination = (E) relationship.destination();
            checkEntities(source, destination);

            for (var sourceRelationship : (Set<R>) source.getRelationships()) {
                if (relationship.equals(sourceRelationship)) {
                    source.removeRelationship(relationship);

                    invalidateCachedRelationshipsOf(source, destination);

                    return true;
                }
            }
            throw relationshipNotFoundException(relationship);
        } finally {
            writeLock.unlock();
        }
    }

    @Override
    public boolean removeAllRelationships(Collection<R> relationships) throws RelationshipNotFoundException, EntityNotFoundException {
        writeLock.lock();

        try {
            for (var relationship : relationships) {
                removeRelationship(relationship);
            }

            return true;
        } finally {
            writeLock.unlock();
        }
    }

    @Override
    public Set<R> removeAllRelationshipsBetween(E source, E destination) throws EntityNotFoundException {
        writeLock.lock();

        try {
            checkEntities(source, destination);

            var relationships = getRelationshipsBetween(source, destination);

            for (var relationship : relationships) {
                removeRelationship(relationship);
            }

            return relationships;
        } finally {
            writeLock.unlock();
        }
    }

    @Override
    public Set<R> removeAllRelationshipsBetween(D sourceData, D destinationData) throws EntityNotFoundException {
        return removeAllRelationshipsBetween(entities.get(sourceData), entities.get(destinationData));
    }

    @Override
    public boolean containsEntity(E entity) {
        readLock.lock();

        try {
            return entities.containsValue(entity);
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public boolean containsEntity(D entityData) {
        readLock.lock();

        try {
            return entities.containsKey(entityData);
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public boolean containsRelationship(R relationship) throws EntityNotFoundException {
        return getRelationships().contains(relationship);
    }

    @Override
    public boolean containsRelationship(E source, E destination) throws EntityNotFoundException {
        checkEntities(source, destination);

        return source.getRelationships().stream()
                .anyMatch(relationship -> destination.equals(relationship.destination()));
    }

    @Override
    public boolean containsRelationship(D sourceData, D destinationData) throws EntityNotFoundException {
        return containsRelationship(entities.get(sourceData), entities.get(destinationData));
    }

    @Override
    public Class<E> getEntityType() {
        return (Class<E>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }

    @Override
    public Class<R> getRelationshipType() {
        return (Class<R>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[1];
    }

    private Set<R> getCachedRelationshipsOf(E entity) throws EntityNotFoundException {
        checkEntities(entity);

        readLock.lock();

        try {
            return relationshipCache.computeIfAbsent(entity, e ->
                    getRelationships().stream()
                            .filter(r -> e.equals(r.source()) || e.equals(r.destination()))
                            .collect(Collectors.toSet()));
        } finally {
            readLock.unlock();
        }
    }

    private void invalidateCachedRelationshipsOf(E... entities) {
        for (var entity : entities) {
            relationshipCache.remove(entity);
        }
    }

    private void checkEntities(E... entities) throws EntityNotFoundException {
        for (var entity : entities) {

            if (!exists(entity)) {
                throw entityNotFoundException(entity);
            }
        }
    }

    private boolean exists(E entity) {
        return entities.containsValue(entity);
    }

    private boolean exists(D entityData) {
        return entities.containsKey(entityData);
    }

    private EntityAlreadyExistsException entityAlreadyExistsException(E entity) {
        return new EntityAlreadyExistsException(String.format("The entity: '%s' already exists", entity));
    }

    private EntityNotFoundException entityNotFoundException(E entity) {
        return entityNotFoundException(entity.getData());
    }

    private EntityNotFoundException entityNotFoundException(D entityData) {
        return new EntityNotFoundException(String.format("The entity '%s' was not found", entityData));
    }

    private RelationshipAlreadyExistsException relationshipAlreadyExistsException(E source, E destination, U relationshipData) {
        return new RelationshipAlreadyExistsException(String.format("Relationship: '%s' from entity '%s' to entity '%s' already exists", relationshipData, source, destination));
    }

    private RelationshipNotFoundException relationshipNotFoundException(R relationship) {
        return new RelationshipNotFoundException(String.format("Relationship '%s' from entity '%s' to entity '%s' was not found", relationship, relationship.source(), relationship.destination()));
    }
}
