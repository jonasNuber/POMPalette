package org.nuberjonas.pompalette.core.coreapi.graph.api;

public interface RelationshipFactory<E extends Entity<D, U>, R extends Relationship<D, U>, D, U>{
    R createRelationship(E source, E destination, U relationshipData);
}
