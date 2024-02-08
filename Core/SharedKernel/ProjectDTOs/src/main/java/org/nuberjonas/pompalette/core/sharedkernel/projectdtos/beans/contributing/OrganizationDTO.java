package org.nuberjonas.pompalette.core.sharedkernel.projectdtos.beans.contributing;

import org.nuberjonas.pompalette.core.sharedkernel.projectdtos.beans.input.InputLocationDTO;

import java.io.Serializable;
import java.util.Map;

public record OrganizationDTO(
        String name,
        String url,
        Map<Object, InputLocationDTO> locations,
        InputLocationDTO location,
        InputLocationDTO nameLocation,
        InputLocationDTO urlLocation
) implements Serializable {
}
