package org.nuberjonas.pompalette.core.coreapi.graph;

import java.util.Set;

public interface Entity<D, U> {
    D getData();
    boolean addRelationship(Relationship<D, U> relationship);
    boolean removeRelationship(Relationship<D, U> relationship);
    boolean containsRelationship(Relationship<D, U> relationship);
    Set<Relationship<D, U>> getRelationships();
}
