package de.nuberjonas.pompalette.core.sharedkernel.projectdtos;

import java.util.Map;

public record LicenseDTO(
        String name,
        String url,
        String distribution,
        String comments,
        Map<Object, InputLocationDTO> locations,
        InputLocationDTO location,
        InputLocationDTO nameLocation,
        InputLocationDTO urlLocation,
        InputLocationDTO distributionLocation,
        InputLocationDTO commentsLocation
) {
}
