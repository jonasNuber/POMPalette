package org.nuberjonas.pompalette.mapping.mavenmapping.build.resource;

import org.apache.maven.model.PatternSet;
import org.nuberjonas.pompalette.core.sharedkernel.projectdtos.beans.build.resource.PatternSetDTO;
import org.nuberjonas.pompalette.mapping.mavenmapping.input.InputLocationMapper;

import java.util.Optional;

public class PatternSetMapper {

    private PatternSetMapper(){ throw new AssertionError("Utility class, cannot be instantiated"); }

    public static PatternSetDTO mapToDTO(PatternSet patternSet){
        return Optional.ofNullable(patternSet)
                .map(src -> new PatternSetDTO(
                        src.getIncludes(),
                        src.getExcludes(),
                        InputLocationMapper.mapToInputLocationDTOMap(src),
                        InputLocationMapper.mapToDTO(src.getLocation("")),
                        InputLocationMapper.mapToDTO(src.getLocation("includes")),
                        InputLocationMapper.mapToDTO(src.getLocation("excludes"))
                ))
                .orElse(null);
    }

    public static PatternSet mapToModel(PatternSetDTO patternSetDTO){
        if(patternSetDTO == null){
            return null;
        }

        var patternSet = new PatternSet();
        patternSet.setIncludes(patternSetDTO.includes());
        patternSet.setExcludes(patternSetDTO.excludes());

        if (patternSetDTO.locations() != null) {
            patternSetDTO.locations().forEach((key, value) ->
                    patternSet.setOtherLocation(key, InputLocationMapper.mapToModel(value))
            );
        }

        patternSet.setLocation("", InputLocationMapper.mapToModel(patternSetDTO.location()));
        patternSet.setLocation("includes", InputLocationMapper.mapToModel(patternSetDTO.includesLocation()));
        patternSet.setLocation("excludes", InputLocationMapper.mapToModel(patternSetDTO.excludesLocation()));

        return patternSet;
    }
}
