package org.nuberjonas.pompalette.core.coreapi.graph.basic;

import org.nuberjonas.pompalette.core.coreapi.graph.api.Entity;
import org.nuberjonas.pompalette.core.coreapi.graph.api.Relationship;

import java.util.Objects;

public record BasicRelationship<ED, RD>(Entity<ED, RD> source, Entity<ED, RD> destination, RD data) implements Relationship<ED, RD> {
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
