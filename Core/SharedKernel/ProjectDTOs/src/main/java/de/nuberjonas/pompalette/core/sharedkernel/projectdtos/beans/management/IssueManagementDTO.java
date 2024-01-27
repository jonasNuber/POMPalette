package de.nuberjonas.pompalette.core.sharedkernel.projectdtos.beans.management;

import de.nuberjonas.pompalette.core.sharedkernel.projectdtos.beans.input.InputLocationDTO;

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
