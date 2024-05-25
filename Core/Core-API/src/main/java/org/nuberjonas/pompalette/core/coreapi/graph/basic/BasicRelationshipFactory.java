package org.nuberjonas.pompalette.core.coreapi.graph.basic;

import org.nuberjonas.pompalette.core.coreapi.graph.api.RelationshipFactory;

public class BasicRelationshipFactory<D, U> implements RelationshipFactory<BasicEntity<D, U>, BasicRelationship<D, U>, D, U> {
    @Override
    public synchronized BasicRelationship<D, U> createRelationship(BasicEntity<D, U> source, BasicEntity<D, U> destination, U relationshipData) {
        return new BasicRelationship<>(source, destination, relationshipData);
    }
}
