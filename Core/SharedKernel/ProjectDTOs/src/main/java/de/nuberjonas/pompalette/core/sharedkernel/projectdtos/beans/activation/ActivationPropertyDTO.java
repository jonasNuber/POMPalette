package de.nuberjonas.pompalette.core.sharedkernel.projectdtos.beans.activation;

import de.nuberjonas.pompalette.core.sharedkernel.projectdtos.beans.input.InputLocationDTO;

import java.util.Map;

public record ActivationPropertyDTO(
        String name,
        String value,
        Map<Object, InputLocationDTO> locations,
        InputLocationDTO location,
        InputLocationDTO nameLocation,
        InputLocationDTO valueLocation
) {
}
