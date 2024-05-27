package org.nuberjonas.pompalette.core.corebaseimpl.graph;

import org.nuberjonas.pompalette.core.coreapi.graph.Entity;
import org.nuberjonas.pompalette.core.coreapi.graph.Relationship;

import java.util.Objects;

public record BasicRelationship<D, U>(Entity<D, U> source, Entity<D, U> destination, U data) implements Relationship<D, U> {
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BasicRelationship<?, ?> that = (BasicRelationship<?, ?>) o;
        return Objects.equals(source, that.source) && Objects.equals(destination, that.destination) && Objects.equals(data, that.data);
    }

    @Override
    public int hashCode() {
        return Objects.hash(source, destination, data);
    }
}
