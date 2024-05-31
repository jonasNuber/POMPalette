package org.nuberjonas.pompalette.core.corebaseimpl.graph;

import org.nuberjonas.pompalette.core.coreapi.graph.*;
import org.nuberjonas.pompalette.core.coreapi.graph.exceptions.EntityAlreadyExistsException;
import org.nuberjonas.pompalette.core.coreapi.graph.exceptions.EntityNotFoundException;
import org.nuberjonas.pompalette.core.coreapi.graph.exceptions.RelationshipAlreadyExistsException;
import org.nuberjonas.pompalette.core.coreapi.graph.exceptions.RelationshipNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.ParameterizedType;
import java.util.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.stream.Collectors;

@SuppressWarnings("unchecked")
public class DirectedGraph<E extends Entity<D, U>, R extends Relationship<D, U>, D, U> implements Graph<E, R, D, U> {
    private static final Logger logger = LoggerFactory.getLogger(DirectedGraph.class);

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
            logger.debug("Adding entity: {}", entity);

            if (exists(entity)) {
                logger.warn("Entity already exists: {}", entity);
                throw entityAlreadyExistsException(entity);
            }

            entities.put(entity.getData(), entity);
            logger.info("Entity added: {}", entity);
        } finally {
            writeLock.unlock();
        }
    }

    @Override
    public E addEntity(D entityData) throws EntityAlreadyExistsException {
        var entity = entityFactory.createEntity(entityData);

        logger.debug("Creating and adding entity with data: {}", entityData);
        addEntity(entity);

        return entity;
    }

    @Override
    public E getEntity(D entityData) throws EntityNotFoundException {
        readLock.lock();

        try {
            logger.debug("Fetching entity with data: {}", entityData);
            var entity = entities.get(entityData);

            if (entity == null) {
                logger.error("Entity not found with data: {}", entityData);
                throw entityNotFoundException(entityData);
            }

            logger.info("Entity fetched: {}", entity);
            return entity;
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public Set<E> getEntities() {
        readLock.lock();

        try {
            logger.debug("Fetching all entities");
            return new HashSet<>(entities.values());
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public E getRelationshipSourceOf(R relationship) throws EntityNotFoundException, RelationshipNotFoundException {
        return getRelationshipEntityOf(relationship, true);
    }

    @Override
    public E getRelationshipDestinationOf(R relationship) throws EntityNotFoundException, RelationshipNotFoundException {
        return getRelationshipEntityOf(relationship, false);
    }

    private E getRelationshipEntityOf(R relationship, boolean getSource) throws EntityNotFoundException, RelationshipNotFoundException {
        readLock.lock();

        try {
            var sourceData = relationship.source().getData();
            var destinationData = relationship.destination().getData();
            checkEntitiesData(sourceData, destinationData);

            logger.debug("Fetching relationship {} of: {}", getSource ? "source" : "destination", relationship);
            var source = entities.get(sourceData);
            var destination = entities.get(destinationData);

            if (source.containsRelationship(relationship)) {
                var result = getSource ? source : destination;
                logger.info("Relationship {} fetched: {}", getSource ? "source" : "destination", result);
                return result;
            }

            logger.error("Relationship not found: {}", relationship);
            throw relationshipNotFoundException(relationship);
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public boolean removeEntity(E entity) throws EntityNotFoundException {
        logger.debug("Removing entity: {}", entity);
        return removeEntity(entity.getData()) != null;
    }

    @Override
    public E removeEntity(D entityData) throws EntityNotFoundException {
        writeLock.lock();

        try {
            logger.debug("Removing entity with data: {}", entityData);

            if (!exists(entityData)) {
                logger.error("Entity not found: {}", entityData);
                throw entityNotFoundException(entityData);
            }

            var entity = entities.get(entityData);

            getCachedRelationshipsOf(entity).forEach(r -> {
                if (entity.equals(r.destination())) {
                    r.source().removeRelationship(r);
                }
            });

            invalidateCachedRelationshipsOf(entity);

            logger.info("Entity removed: {}", entity);
            return entities.remove(entityData);
        } finally {
            writeLock.unlock();
        }
    }

    @Override
    public boolean removeAllEntities(Collection<E> entities) throws EntityNotFoundException {
        writeLock.lock();

        try {
            logger.debug("Removing all entities in collection");

            for (var entity : entities) {
                removeEntity(entity);
            }

            logger.info("All entities in collection removed");
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
            checkEntitiesData(sourceData, destinationData);

            logger.debug("Adding relationship from {} to {} with data: {}", sourceData, destinationData, relationshipData);
            var source = entities.get(sourceData);
            var destination = entities.get(destinationData);

            var relationship = relationshipFactory.createRelationship(source, destination, relationshipData);

            if (!source.addRelationship(relationship)) {
                logger.warn("Relationship already exists from {} to {} with data: {}", source, destination, relationshipData);
                throw relationshipAlreadyExistsException(source, destination, relationshipData);
            }

            invalidateCachedRelationshipsOf(source, destination);

            logger.info("Relationship added from {} to {} with data: {}", source, destination, relationshipData);
            return relationship;
        } finally {
            writeLock.unlock();
        }
    }

    @Override
    public Set<R> getRelationships() {
        readLock.lock();

        try {
            logger.debug("Fetching all relationships");
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
        logger.debug("Fetching relationships of entity: {}", entity);

        return getCachedRelationshipsOf(entity);
    }

    @Override
    public Set<R> getRelationshipsOf(D entityData) throws EntityNotFoundException {
        logger.debug("Fetching relationships of entity with data: {}", entityData);
        checkEntitiesData(entityData);

        return getRelationshipsOf(entities.get(entityData));
    }

    @Override
    public Set<R> getRelationshipsBetween(E source, E destination) throws EntityNotFoundException {
        logger.debug("Fetching relationships between {} and {}", source, destination);
        checkEntities(source, destination);

        return (Set<R>) source.getRelationships().stream()
                .filter(relationship -> destination.equals(relationship.destination()))
                .collect(Collectors.toSet());
    }

    @Override
    public Set<R> getRelationshipsBetween(D sourceData, D destinationData) throws EntityNotFoundException {
        logger.debug("Fetching relationships between {} and {}", sourceData, destinationData);
        checkEntitiesData(sourceData, destinationData);

        return getRelationshipsBetween(entities.get(sourceData), entities.get(destinationData));
    }

    @Override
    public Set<R> getIncomingRelationshipsOf(E entity) throws EntityNotFoundException {
        logger.debug("Fetching incoming relationships of entity: {}", entity);

        return getCachedRelationshipsOf(entity).stream()
                .filter(r -> entity.equals(r.destination()))
                .collect(Collectors.toSet());
    }

    @Override
    public Set<R> getIncomingRelationshipsOf(D entityData) throws EntityNotFoundException {
        logger.debug("Fetching incoming relationships of entity with data: {}", entityData);

        return getIncomingRelationshipsOf(entities.get(entityData));
    }

    @Override
    public Set<R> getOutgoingRelationshipsOf(E entity) throws EntityNotFoundException {
        logger.debug("Fetching outgoing relationships of entity: {}", entity);
        checkEntities(entity);

        return (Set<R>) entity.getRelationships();
    }

    @Override
    public Set<R> getOutgoingRelationshipsOf(D entityData) throws EntityNotFoundException {
        logger.debug("Fetching outgoing relationships of entity with data: {}", entityData);

        return getOutgoingRelationshipsOf(entities.get(entityData));
    }

    @Override
    public boolean removeRelationship(R relationship) throws RelationshipNotFoundException, EntityNotFoundException {
        writeLock.lock();

        try {
            logger.debug("Removing relationship: {}", relationship);
            var source = (E) relationship.source();
            var destination = (E) relationship.destination();
            checkEntities(source, destination);

            for (var sourceRelationship : (Set<R>) source.getRelationships()) {
                if (relationship.equals(sourceRelationship)) {
                    source.removeRelationship(relationship);

                    invalidateCachedRelationshipsOf(source, destination);

                    logger.info("Relationship removed: {}", relationship);
                    return true;
                }
            }

            logger.error("Relationship not found: {}", relationship);
            throw relationshipNotFoundException(relationship);
        } finally {
            writeLock.unlock();
        }
    }

    @Override
    public boolean removeAllRelationships(Collection<R> relationships) throws RelationshipNotFoundException, EntityNotFoundException {
        writeLock.lock();

        try {
            logger.debug("Removing all relationships in collection");

            for (var relationship : relationships) {
                removeRelationship(relationship);
            }

            logger.info("All relationships removed in collection");
            return true;
        } finally {
            writeLock.unlock();
        }
    }

    @Override
    public Set<R> removeAllRelationshipsBetween(E source, E destination) throws EntityNotFoundException {
        writeLock.lock();

        try {
            logger.debug("Removing all relationships between {} and {}", source, destination);
            checkEntities(source, destination);

            var relationships = getRelationshipsBetween(source, destination);

            for (var relationship : relationships) {
                removeRelationship(relationship);
            }

            logger.info("All relationships removed between {} and {}", source, destination);
            return relationships;
        } finally {
            writeLock.unlock();
        }
    }

    @Override
    public Set<R> removeAllRelationshipsBetween(D sourceData, D destinationData) throws EntityNotFoundException {
        logger.debug("Removing all relationships between {} and {}", sourceData, destinationData);

        return removeAllRelationshipsBetween(entities.get(sourceData), entities.get(destinationData));
    }

    @Override
    public boolean containsEntity(E entity) {
        readLock.lock();

        try {
            logger.debug("Checking if entity exists: {}", entity);
            return entities.containsValue(entity);
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public boolean containsEntity(D entityData) {
        readLock.lock();

        try {
            logger.debug("Checking if entity exists with data: {}", entityData);
            return entities.containsKey(entityData);
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public boolean containsRelationship(R relationship) throws EntityNotFoundException {
        logger.debug("Checking if relationship exists: {}", relationship);

        return getRelationships().contains(relationship);
    }

    @Override
    public boolean containsRelationship(E source, E destination) throws EntityNotFoundException {
        logger.debug("Checking if relationship exists between {} and {}", source, destination);
        checkEntities(source, destination);

        return source.getRelationships().stream()
                .anyMatch(relationship -> destination.equals(relationship.destination()));
    }

    @Override
    public boolean containsRelationship(D sourceData, D destinationData) throws EntityNotFoundException {
        logger.debug("Checking if relationship exists between {} and {}", sourceData, destinationData);

        return containsRelationship(entities.get(sourceData), entities.get(destinationData));
    }

    @Override
    public Class<E> getEntityType() {
        logger.trace("Getting entity type");

        return (Class<E>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }

    @Override
    public Class<R> getRelationshipType() {
        logger.trace("Getting relationship type");

        return (Class<R>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[1];
    }

    private Set<R> getCachedRelationshipsOf(E entity) throws EntityNotFoundException {
        readLock.lock();

        try {
            checkEntities(entity);
            logger.debug("Fetching cached relationships of entity: {}", entity);

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
            logger.debug("Invalidating cached relationships of entity: {}", entity);
            relationshipCache.remove(entity);
        }
    }

    private void checkEntities(E... entities) throws EntityNotFoundException {
        for (var entity : entities) {
            if (!exists(entity)) {
                logger.error("Entity not found: {}", entity);
                throw entityNotFoundException(entity);
            }
        }
    }

    private void checkEntitiesData(D... entitiesData){
        for (var entityData : entitiesData) {
            if (!exists(entityData)) {
                logger.error("Entity not found: {}", entityData);
                throw entityNotFoundException(entityData);
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
        return entityNotFoundException(entity == null ? null : entity.getData());
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
