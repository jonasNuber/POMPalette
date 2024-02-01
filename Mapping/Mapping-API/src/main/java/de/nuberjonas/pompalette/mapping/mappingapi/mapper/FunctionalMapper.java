package de.nuberjonas.pompalette.mapping.mappingapi.mapper;

@FunctionalInterface
public interface FunctionalMapper<S, D> {
    D map(S input);
}
