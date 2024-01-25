package de.nuberjonas.pompalette.core.sharedkernel.projectdtos;

import java.util.Map;

public record PrerequisitesDTO(
        String maven,
        Map<Object, InputLocationDTO> locations,
        InputLocationDTO location,
        InputLocationDTO mavenLocation
) {
}
