package de.nuberjonas.pompalette.core.sharedkernel.projectdtos.beans.project;

import de.nuberjonas.pompalette.core.sharedkernel.projectdtos.beans.input.InputLocationDTO;

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