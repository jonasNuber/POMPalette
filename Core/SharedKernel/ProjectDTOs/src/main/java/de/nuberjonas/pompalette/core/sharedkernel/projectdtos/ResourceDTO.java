package de.nuberjonas.pompalette.core.sharedkernel.projectdtos;

import java.util.List;
import java.util.Map;

public record ResourceDTO(
        //PatternSet
        List<String> includes,
        List<String> excludes,
        Map<Object, InputLocationDTO> locations,
        InputLocationDTO location,
        InputLocationDTO includesLocation,
        InputLocationDTO excludesLocation,

        //FileSet
        String directory,

        //Resource
        String targetPath,
        String filtering,
        String mergeId
) {
}
