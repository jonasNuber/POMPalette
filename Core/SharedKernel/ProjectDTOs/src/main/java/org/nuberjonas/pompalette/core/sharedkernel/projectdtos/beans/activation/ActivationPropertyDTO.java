package org.nuberjonas.pompalette.core.sharedkernel.projectdtos.beans.activation;

import org.nuberjonas.pompalette.core.sharedkernel.projectdtos.beans.input.InputLocationDTO;

import java.io.Serializable;
import java.util.Map;

public record ActivationPropertyDTO(
        String name,
        String value,
        Map<Object, InputLocationDTO> locations,
        InputLocationDTO location,
        InputLocationDTO nameLocation,
        InputLocationDTO valueLocation
) implements Serializable {
}
