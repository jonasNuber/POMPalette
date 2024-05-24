package org.nuberjonas.pompalette.core.coreapi.graph.basic;

import org.nuberjonas.pompalette.core.coreapi.graph.api.Entity;
import org.nuberjonas.pompalette.core.coreapi.graph.api.Relationship;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class BasicEntity<ED, RD> implements Entity<ED, RD> {
    private ED data;
    private final Set<Relationship<ED, RD>> relationships;

    public BasicEntity(ED data) {
        this.data = data;
        relationships = new HashSet<>();
    }

    @Override
    public synchronized ED changeData(ED newData) {
        var oldData = data;
        data = newData;
        return oldData;
    }

    @Override
    public ED getData() {
        return data;
    }

    @Override
    public synchronized boolean addRelationship(Relationship<ED, RD> relationship) {
        return relationships.add(relationship);
    }

    @Override
    public synchronized boolean removeRelationship(Relationship<ED, RD> relationship) {
        return relationships.remove(relationship);
    }

    @Override
    public synchronized Set<Relationship<ED, RD>> getRelationships() {
        return relationships;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BasicEntity<?, ?> that = (BasicEntity<?, ?>) o;
        return Objects.equals(data, that.data);
    }

    @Override
    public int hashCode() {
        return Objects.hash(data);
    }
}
