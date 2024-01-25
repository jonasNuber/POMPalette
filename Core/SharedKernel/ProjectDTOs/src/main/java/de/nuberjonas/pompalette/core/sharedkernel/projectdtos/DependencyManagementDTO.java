package de.nuberjonas.pompalette.core.sharedkernel.projectdtos;

import java.util.List;
import java.util.Map;

public record DependencyManagementDTO(
        List<DependencyDTO> dependencies,
        Map<Object, InputLocationDTO> locations,
        InputLocationDTO location,
        InputLocationDTO dependenciesLocation
) {
}
