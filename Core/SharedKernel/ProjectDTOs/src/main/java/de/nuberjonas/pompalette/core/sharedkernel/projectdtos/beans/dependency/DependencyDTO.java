package de.nuberjonas.pompalette.core.sharedkernel.projectdtos.beans.dependency;

import de.nuberjonas.pompalette.core.sharedkernel.projectdtos.beans.input.InputLocationDTO;

import java.util.List;
import java.util.Map;

public record DependencyDTO(
        String groupId,
        String artifactId,
        String version,
        String type,
        String classifier,
        String scope,
        String systemPath,
        List<ExclusionDTO> exclusions,
        String optional,
        Map<Object, InputLocationDTO> locations,
        InputLocationDTO location,
        InputLocationDTO groupIdLocation,
        InputLocationDTO artifactIdLocation,
        InputLocationDTO versionLocation,
        InputLocationDTO typeLocation,
        InputLocationDTO classifierLocation,
        InputLocationDTO scopeLocation,
        InputLocationDTO systemPathLocation,
        InputLocationDTO exclusionsLocation,
        InputLocationDTO optionalLocation
) {
}
