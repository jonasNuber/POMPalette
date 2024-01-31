package de.nuberjonas.pompalette.core.sharedkernel.projectdtos.beans.activation;

import de.nuberjonas.pompalette.core.sharedkernel.projectdtos.beans.input.InputLocationDTO;

import java.io.Serializable;
import java.util.Map;

public record ActivationFileDTO(
        String missing,
        String exists,
        Map<Object, InputLocationDTO> locations,
        InputLocationDTO location,
        InputLocationDTO missingLocation,
        InputLocationDTO existsLocation
) implements Serializable {
}
