package org.nuberjonas.pompalette.core.coreapi.graph.api;

public interface Relationship<D, U> {
    Entity<D, U> source();
    Entity<D, U> destination();
    U data();
}
