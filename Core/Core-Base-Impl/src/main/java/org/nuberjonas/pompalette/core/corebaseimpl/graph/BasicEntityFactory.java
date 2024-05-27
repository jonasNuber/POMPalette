package org.nuberjonas.pompalette.core.corebaseimpl.graph;

import org.nuberjonas.pompalette.core.coreapi.graph.EntityFactory;

public class BasicEntityFactory<D, U> implements EntityFactory<BasicEntity<D, U>, D, U> {
    @Override
    public synchronized BasicEntity<D, U> createEntity(D entityData) {
        return new BasicEntity<>(entityData);
    }
}
