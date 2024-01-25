package de.nuberjonas.pompalette.core.sharedkernel.projectdtos;

import java.util.Map;

public record RelocationDTO(
        String artifactId,
        String groupId,
        String version,
        String message,
        Map<Object, InputLocationDTO> locations,
        InputLocationDTO location,
        InputLocationDTO groupIdLocation,
        InputLocationDTO artifactIdLocation,
        InputLocationDTO versionLocation,
        InputLocationDTO messageLocation
) {
}
