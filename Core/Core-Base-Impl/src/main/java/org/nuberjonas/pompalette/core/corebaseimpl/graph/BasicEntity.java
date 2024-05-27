package org.nuberjonas.pompalette.core.corebaseimpl.graph;

import org.nuberjonas.pompalette.core.coreapi.graph.Entity;
import org.nuberjonas.pompalette.core.coreapi.graph.Relationship;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class BasicEntity<D, U> implements Entity<D, U> {
    private final D data;
    private final Set<Relationship<D, U>> relationships;

    public BasicEntity(D data) {
        this.data = data;
        relationships = new HashSet<>();
    }

    @Override
    public D getData() {
        return data;
    }

    @Override
    public synchronized boolean addRelationship(Relationship<D, U> relationship) {
        return relationships.add(relationship);
    }

    @Override
    public synchronized boolean removeRelationship(Relationship<D, U> relationship) {
        return relationships.remove(relationship);
    }

    @Override
    public boolean containsRelationship(Relationship<D, U> relationship) {
        return relationships.contains(relationship);
    }

    @Override
    public synchronized Set<Relationship<D, U>> getRelationships() {
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

    @Override
    public String toString() {
        return data.toString();
    }
}
