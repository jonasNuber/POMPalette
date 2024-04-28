package org.nuberjonas.pompalette.core.sharedkernel.projectdtos.beans.model;

import org.nuberjonas.pompalette.core.sharedkernel.projectdtos.beans.input.InputLocationDTO;

import java.io.Serializable;
import java.util.Map;

public record LicenseDTO(
        String name,
        String url,
        String distribution,
        String comments,
        Map<Object, InputLocationDTO> locations,
        InputLocationDTO location,
        InputLocationDTO nameLocation,
        InputLocationDTO urlLocation,
        InputLocationDTO distributionLocation,
        InputLocationDTO commentsLocation
) implements Serializable {
}
