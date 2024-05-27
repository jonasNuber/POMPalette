package org.nuberjonas.pompalette.core.corebaseimpl.graph;

import org.nuberjonas.pompalette.core.coreapi.graph.RelationshipFactory;

public class BasicRelationshipFactory<D, U> implements RelationshipFactory<BasicEntity<D, U>, BasicRelationship<D, U>, D, U> {
    @Override
    public synchronized BasicRelationship<D, U> createRelationship(BasicEntity<D, U> source, BasicEntity<D, U> destination, U relationshipData) {
        return new BasicRelationship<>(source, destination, relationshipData);
    }
}
