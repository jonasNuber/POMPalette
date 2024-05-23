package org.nuberjonas.pompalette.core.coreapi.graph.basic;

import org.nuberjonas.pompalette.core.coreapi.graph.api.EntityFactory;

public class BasicEntityFactory<ED, RD> implements EntityFactory<BasicEntity<ED, RD>, ED, RD> {
    @Override
    public BasicEntity<ED, RD> createEntity(ED entityData) {
        return new BasicEntity<>(entityData);
    }
}
