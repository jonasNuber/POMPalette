package org.nuberjonas.pompalette.core.coreapi.graph.api;

import java.util.Set;

public interface Entity<ED, RD> {
    ED changeData(ED newData);
    ED getData();
    boolean addRelationship(Relationship<ED, RD> relationship);
    boolean removeRelationship(Relationship<ED, RD> relationship);
    Set<Relationship<ED, RD>> getRelationships();
}
