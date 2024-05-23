package org.nuberjonas.pompalette.core.coreapi.graph.basic;

import org.nuberjonas.pompalette.core.coreapi.graph.api.RelationshipFactory;

public class BasicRelationshipFactory<ED, RD> implements RelationshipFactory<BasicEntity<ED, RD>, BasicRelationship<ED, RD>, ED, RD> {
    @Override
    public BasicRelationship<ED, RD> createRelationship(BasicEntity<ED, RD> source, BasicEntity<ED, RD> destination, RD relationshipData) {
        return new BasicRelationship<>(source, destination, relationshipData);
    }
}
