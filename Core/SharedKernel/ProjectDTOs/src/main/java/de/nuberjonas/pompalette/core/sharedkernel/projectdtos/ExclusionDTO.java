package de.nuberjonas.pompalette.core.sharedkernel.projectdtos;

import java.util.Map;

public record ExclusionDTO(
        String groupId,
        String artifactId,
        Map<Object, InputLocationDTO> locations,
        InputLocationDTO location,
        InputLocationDTO groupIdLocation,
        InputLocationDTO artifactIdLocation
) {
}
