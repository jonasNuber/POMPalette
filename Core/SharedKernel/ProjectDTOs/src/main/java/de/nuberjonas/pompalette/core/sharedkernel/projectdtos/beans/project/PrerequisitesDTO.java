package de.nuberjonas.pompalette.core.sharedkernel.projectdtos.beans.project;

import de.nuberjonas.pompalette.core.sharedkernel.projectdtos.beans.input.InputLocationDTO;

import java.util.Map;

public record PrerequisitesDTO(
        String maven,
        Map<Object, InputLocationDTO> locations,
        InputLocationDTO location,
        InputLocationDTO mavenLocation
) {
}
