package org.nuberjonas.pompalette.core.coreapi.graph;

public interface EntityFactory<E extends Entity<D, U>, D, U> {
    E createEntity(D entityData);
}
