package de.nuberjonas.pompalette.mapping.mappingapi.mapper;

import java.util.List;
import java.util.stream.Collectors;

public class ListMapper {

    private ListMapper(){}

    public static <I, O> List<O> mapList(List<I> items, FunctionalMapper<I, O> functionalMapper) {
        return items.stream()
                .map(functionalMapper::map)
                .collect(Collectors.toList());
    }
}
