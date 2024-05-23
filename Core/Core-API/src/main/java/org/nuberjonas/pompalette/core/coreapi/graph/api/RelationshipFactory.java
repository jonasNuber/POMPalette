package org.nuberjonas.pompalette.core.coreapi.graph.api;

public interface RelationshipFactory<E extends Entity<ED, RD>, R extends Relationship<ED, RD>, ED, RD>{
    R createRelationship(E source, E destination, RD relationshipData);
}
