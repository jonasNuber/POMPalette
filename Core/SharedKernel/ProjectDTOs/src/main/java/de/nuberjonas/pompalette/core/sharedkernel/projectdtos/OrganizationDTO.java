package de.nuberjonas.pompalette.core.sharedkernel.projectdtos;

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
