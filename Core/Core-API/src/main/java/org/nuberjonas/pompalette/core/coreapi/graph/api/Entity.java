package org.nuberjonas.pompalette.core.coreapi.graph.api;

import java.util.Set;

public interface Entity<D, U> {
    D changeData(D newData);
    D getData();
    boolean addRelationship(Relationship<D, U> relationship);
    boolean removeRelationship(Relationship<D, U> relationship);
    Set<Relationship<D, U>> getRelationships();
}
