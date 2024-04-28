package org.nuberjonas.pompalette.core.sharedkernel.projectdtos.beans.management;

import org.nuberjonas.pompalette.core.sharedkernel.projectdtos.beans.input.InputLocationDTO;

import java.io.Serializable;
import java.util.Map;

public record RelocationDTO(
        String groupId,
        String artifactId,
        String version,
        String message,
        Map<Object, InputLocationDTO> locations,
        InputLocationDTO location,
        InputLocationDTO groupIdLocation,
        InputLocationDTO artifactIdLocation,
        InputLocationDTO versionLocation,
        InputLocationDTO messageLocation
) implements Serializable {
}
