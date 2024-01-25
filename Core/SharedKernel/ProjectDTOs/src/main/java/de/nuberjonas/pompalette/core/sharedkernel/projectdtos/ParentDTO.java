package de.nuberjonas.pompalette.core.sharedkernel.projectdtos;

import java.util.Map;

public record ParentDTO(
        String groupId,
        String artifactId,
        String version,
        String relativePath,
        Map<Object, InputLocationDTO> locations,
        InputLocationDTO location,
        InputLocationDTO groupIdLocation,
        InputLocationDTO artifactIdLocation,
        InputLocationDTO versionLocation,
        InputLocationDTO relativePathLocation
) {
}
