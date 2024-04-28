package org.nuberjonas.pompalette.core.sharedkernel.projectdtos.beans.management;

import org.nuberjonas.pompalette.core.sharedkernel.projectdtos.beans.input.InputLocationDTO;

import java.io.Serializable;
import java.util.Map;

public record IssueManagementDTO(
        String system,
        String url,
        Map<Object, InputLocationDTO> locations,
        InputLocationDTO location,
        InputLocationDTO systemLocation,
        InputLocationDTO urlLocation
) implements Serializable {
}
