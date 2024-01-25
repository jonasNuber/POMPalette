package de.nuberjonas.pompalette.core.sharedkernel.projectdtos;

import java.util.List;
import java.util.Map;

public record CiManagementDTO(
        String system,
        String url,
        List<NotifierDTO> notifiers,
        Map<Object, InputLocationDTO> locations,
        InputLocationDTO location,
        InputLocationDTO systemLocation,
        InputLocationDTO urlLocation,
        InputLocationDTO notifiersLocation
) {
}
