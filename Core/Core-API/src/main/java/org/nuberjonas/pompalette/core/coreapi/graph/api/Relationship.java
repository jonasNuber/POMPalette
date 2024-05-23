package org.nuberjonas.pompalette.core.coreapi.graph.api;

public interface Relationship<ED, RD> {
    Entity<ED, RD> source();
    Entity<ED, RD> destination();
    RD data();
}
