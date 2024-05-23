package org.nuberjonas.pompalette.core.coreapi.graph.api;

public interface EntityFactory<E extends Entity<ED, RD>, ED, RD> {
    E createEntity(ED entityData);
}
