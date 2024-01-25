package de.nuberjonas.pompalette.core.sharedkernel.projectdtos;

import java.util.Map;

public record ActivationFileDTO(
        String missing,
        String exists,
        Map<Object, InputLocationDTO> locations,
        InputLocationDTO location,
        InputLocationDTO missingLocation,
        InputLocationDTO existsLocation
) {
}
