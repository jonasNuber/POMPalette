package de.nuberjonas.pompalette.mapping.mavenmapping.input;

import de.nuberjonas.pompalette.core.sharedkernel.projectdtos.beans.input.InputLocationDTO;
import de.nuberjonas.pompalette.mapping.mappingapi.exceptions.MappingException;
import org.apache.maven.model.InputLocation;

import java.lang.reflect.Field;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

public class InputLocationMapper {

    public static InputLocationDTO mapToDTO(InputLocation inputLocation){
         return Optional.ofNullable(inputLocation)
                .map(src -> new InputLocationDTO(
                        src.getLineNumber(),
                        src.getColumnNumber(),
                        InputSourceMapper.mapToDTO(src.getSource()),
                        mapInputLocationDTOMap(src.getLocations()),
                        mapToDTO(src.getLocation(""))))
                .orElse(null);
    }

    @SuppressWarnings("unchecked")
    public static Map<Object, InputLocationDTO> mapInputLocationDTOMap(Object object) {
        try {
            Field field = getLocationsField(object.getClass());
            field.setAccessible(true);

            return mapInputLocationDTOMap((Map<Object, InputLocation>)field.get(object));

        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new MappingException("Could not map locations for DTO generation", e);
        }
    }

    private static Field getLocationsField(Class<?> clazz) throws NoSuchFieldException {
        while (clazz != null) {
            try {
                return clazz.getDeclaredField("locations");
            } catch (NoSuchFieldException e) {
                clazz = clazz.getSuperclass();
            }
        }

        throw new NoSuchFieldException("The locations field does not exist!");
    }

    private static Map<Object, InputLocationDTO> mapInputLocationDTOMap(Map<Object, InputLocation> inputLocationMap) {
        return convertMap(inputLocationMap, InputLocationMapper::mapToDTO);
    }

    @SuppressWarnings("unchecked")
    private static <T, U, V> Map<Object, V> convertMap(Map<Object, T> inputMap, Function<T, U> mapper) {
        return (Map<Object, V>) Optional.ofNullable(inputMap)
                .map(map -> map.entrySet()
                        .stream()
                        .collect(Collectors.toMap(Map.Entry::getKey,
                                entry -> mapper.apply(entry.getValue()),
                                (existing, replacement) -> existing,
                                LinkedHashMap::new)))
                .orElse(null);
    }

    public static InputLocation mapToModel(InputLocationDTO inputLocationDTO){
        if(inputLocationDTO == null){
            return null;
        }

        var inputLocation = new InputLocation(
                inputLocationDTO.lineNumber(),
                inputLocationDTO.columnNumber(),
                InputSourceMapper.mapToModel(inputLocationDTO.source())
        );

        inputLocation.setLocations(mapInputLocationMap(inputLocationDTO.locations()));
        inputLocation.setLocation("", mapToModel(inputLocationDTO.location()));

        return inputLocation;
    }

    public static Map<Object, InputLocation> mapInputLocationMap(Map<Object, InputLocationDTO> inputLocationMap) {
        return convertMap(inputLocationMap, InputLocationMapper::mapToModel);
    }
}
