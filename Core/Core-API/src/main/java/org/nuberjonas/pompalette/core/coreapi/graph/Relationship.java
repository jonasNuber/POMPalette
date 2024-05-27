package org.nuberjonas.pompalette.core.coreapi.graph;

public interface Relationship<D, U> {
    Entity<D, U> source();
    Entity<D, U> destination();
    U data();
}
