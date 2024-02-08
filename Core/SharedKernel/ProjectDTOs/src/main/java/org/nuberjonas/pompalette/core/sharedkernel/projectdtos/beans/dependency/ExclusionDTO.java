package org.nuberjonas.pompalette.core.sharedkernel.projectdtos.beans.dependency;

import org.nuberjonas.pompalette.core.sharedkernel.projectdtos.beans.input.InputLocationDTO;

import java.io.Serializable;
import java.util.Map;

public record ExclusionDTO(
        String groupId,
        String artifactId,
        Map<Object, InputLocationDTO> locations,
        InputLocationDTO location,
        InputLocationDTO groupIdLocation,
        InputLocationDTO artifactIdLocation
) implements Serializable {
}
