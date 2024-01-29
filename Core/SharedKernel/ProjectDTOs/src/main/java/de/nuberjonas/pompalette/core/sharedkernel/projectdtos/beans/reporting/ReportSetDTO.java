package de.nuberjonas.pompalette.core.sharedkernel.projectdtos.beans.reporting;

import de.nuberjonas.pompalette.core.sharedkernel.projectdtos.beans.input.InputLocationDTO;

import java.util.List;
import java.util.Map;

public record ReportSetDTO(
        //ConfigurationContainer
        boolean inherited,
        Object configuration,
        Map<Object, InputLocationDTO> locations,
        InputLocationDTO location,
        InputLocationDTO inheritedLocation,
        InputLocationDTO configurationLocation,
        boolean inheritanceApplied,

        //ReportSet
        String id,
        List<String> reports
) {
}
