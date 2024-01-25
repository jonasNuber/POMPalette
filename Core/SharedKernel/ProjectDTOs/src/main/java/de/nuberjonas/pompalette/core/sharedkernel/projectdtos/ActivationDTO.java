package de.nuberjonas.pompalette.core.sharedkernel.projectdtos;

import java.util.Map;

public record ActivationDTO(
        boolean activeByDefault,
        String jdk,
        ActivationOS_DTO os,
        ActivationPropertyDTO property,
        ActivationFileDTO file,
        Map<Object, InputLocationDTO> locations,
        InputLocationDTO location,
        InputLocationDTO activeByDefaultLocation,
        InputLocationDTO jdkLocation,
        InputLocationDTO osLocation,
        InputLocationDTO propertyLocation,
        InputLocationDTO fileLocation
) {
}
