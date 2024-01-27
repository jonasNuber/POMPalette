package de.nuberjonas.pompalette.core.sharedkernel.projectdtos.beans.contributing;

import de.nuberjonas.pompalette.core.sharedkernel.projectdtos.beans.input.InputLocationDTO;

import java.util.Map;

public record OrganizationDTO(
        String name,
        String url,
        Map<Object, InputLocationDTO> locations,
        InputLocationDTO location,
        InputLocationDTO nameLocation,
        InputLocationDTO urlLocation
) {
}
