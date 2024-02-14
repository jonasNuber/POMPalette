package org.nuberjonas.pompalette.mapping.mappingapi.mapper.utilities;

@FunctionalInterface
public interface FunctionalMapper<S, D> {
    D map(S input);
}
