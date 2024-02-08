package org.nuberjonas.pompalette.core.sharedkernel.projectdtos.beans.activation;

import org.nuberjonas.pompalette.core.sharedkernel.projectdtos.beans.input.InputLocationDTO;

import java.io.Serializable;
import java.util.Map;

public record ActivationDTO (
        boolean activeByDefault,
        String jdk,
        ActivationOsDTO os,
        ActivationPropertyDTO property,
        ActivationFileDTO file,
        Map<Object, InputLocationDTO> locations,
        InputLocationDTO location,
        InputLocationDTO activeByDefaultLocation,
        InputLocationDTO jdkLocation,
        InputLocationDTO osLocation,
        InputLocationDTO propertyLocation,
        InputLocationDTO fileLocation
) implements Serializable {
}
