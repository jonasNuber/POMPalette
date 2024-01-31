package de.nuberjonas.pompalette.core.sharedkernel.projectdtos.beans.build.resource;

import de.nuberjonas.pompalette.core.sharedkernel.projectdtos.beans.input.InputLocationDTO;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public record PatternSetDTO(
        List<String> includes,
        List<String> excludes,
        Map<Object, InputLocationDTO> locations,
        InputLocationDTO location,
        InputLocationDTO includesLocation,
        InputLocationDTO excludesLocation
) implements Serializable {
}
