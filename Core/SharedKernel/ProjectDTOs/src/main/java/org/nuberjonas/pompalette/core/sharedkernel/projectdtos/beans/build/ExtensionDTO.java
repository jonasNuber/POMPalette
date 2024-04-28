package org.nuberjonas.pompalette.core.sharedkernel.projectdtos.beans.build;

import org.nuberjonas.pompalette.core.sharedkernel.projectdtos.beans.input.InputLocationDTO;

import java.io.Serializable;
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
) implements Serializable {
}
