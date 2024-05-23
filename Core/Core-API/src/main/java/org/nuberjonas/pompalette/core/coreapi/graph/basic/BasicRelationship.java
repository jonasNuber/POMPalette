package org.nuberjonas.pompalette.core.coreapi.graph.basic;

import org.nuberjonas.pompalette.core.coreapi.graph.api.Entity;
import org.nuberjonas.pompalette.core.coreapi.graph.api.Relationship;

public record BasicRelationship<ED, RD>(Entity<ED, RD> source, Entity<ED, RD> destination, RD data) implements Relationship<ED, RD> {

}
