package de.nuberjonas.pompalette.core.sharedkernel.projectdtos;

import java.util.Map;

public record ExtensionDTO(
        String groupId,
        String artifactId,
        String version,
        Map<Object, InputLocationDTO> locations,
        InputLocationDTO location,
        InputLocationDTO groupIdLocation,
        InputLocationDTO artifactIdLocation,
        InputLocationDTO versionLocation
) {
}
