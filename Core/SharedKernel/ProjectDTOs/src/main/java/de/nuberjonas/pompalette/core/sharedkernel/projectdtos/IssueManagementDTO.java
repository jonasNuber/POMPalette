package de.nuberjonas.pompalette.core.sharedkernel.projectdtos;

import java.util.Map;

public record IssueManagementDTO(
        String system,
        String url,
        Map<Object, InputLocationDTO> locations,
        InputLocationDTO location,
        InputLocationDTO systemLocation,
        InputLocationDTO urlLocation
) {
}
