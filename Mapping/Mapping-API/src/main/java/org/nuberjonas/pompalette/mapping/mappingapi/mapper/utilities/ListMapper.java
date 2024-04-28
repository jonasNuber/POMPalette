package org.nuberjonas.pompalette.mapping.mappingapi.mapper.utilities;

import org.nuberjonas.pompalette.mapping.mappingapi.exceptions.MappingException;

import java.util.List;

public class ListMapper {

    private ListMapper(){
        throw new AssertionError("Utility class, cannot be instantiated");
    }

    public static <I, O> List<O> mapList(List<I> items, FunctionalMapper<I, O> functionalMapper) {
        if(functionalMapper == null){
            throw new MappingException("No mapping function specified");
        }

        return (items != null) ? items.stream()
                .map(functionalMapper::map)
                .toList() : null;
    }
}
