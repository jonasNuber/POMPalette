package org.nuberjonas.pompalette.core.coreapi.graph.api;

import java.util.List;

public interface Graph<E extends Entity<ED, RD>, R extends Relationship<ED, RD>, ED, RD> {
    void addEntity(E entity);
    E addEntity(ED entityData);
    E removeEntity(E entity);
    E removeEntity(ED entityData);
    E getEntity(ED entityData);
    void addRelationship(R relationship);
    R addRelationship(E source, E destination, RD relationshipData);
    R addRelationship(ED sourceData, ED destinationData, RD relationshipData);
    R removeRelationship(E source, E destination);
    R removeRelationship(ED sourceEntity, ED destinationEntity);
    List<R> getRelationships();
}
