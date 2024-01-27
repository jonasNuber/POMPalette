package de.nuberjonas.pompalette.core.sharedkernel.projectdtos.beans.activation;

import de.nuberjonas.pompalette.core.sharedkernel.projectdtos.beans.input.InputLocationDTO;

import java.util.Map;

public record ActivationOS_DTO(
        String name,
        String family,
        String arch,
        String version,
        Map<Object, InputLocationDTO> locations,
        InputLocationDTO location,
        InputLocationDTO nameLocation,
        InputLocationDTO familyLocation,
        InputLocationDTO archLocation,
        InputLocationDTO versionLocation
) {
}
