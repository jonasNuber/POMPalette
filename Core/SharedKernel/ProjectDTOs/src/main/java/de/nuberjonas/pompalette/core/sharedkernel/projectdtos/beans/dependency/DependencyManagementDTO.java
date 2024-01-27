package de.nuberjonas.pompalette.core.sharedkernel.projectdtos.beans.dependency;

import de.nuberjonas.pompalette.core.sharedkernel.projectdtos.beans.input.InputLocationDTO;

import java.util.List;
import java.util.Map;

public record DependencyManagementDTO(
        List<DependencyDTO> dependencies,
        Map<Object, InputLocationDTO> locations,
        InputLocationDTO location,
        InputLocationDTO dependenciesLocation
) {
}
