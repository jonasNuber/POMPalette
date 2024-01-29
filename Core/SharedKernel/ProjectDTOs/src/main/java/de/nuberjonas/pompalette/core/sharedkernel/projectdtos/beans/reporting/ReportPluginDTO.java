package de.nuberjonas.pompalette.core.sharedkernel.projectdtos.beans.reporting;

import de.nuberjonas.pompalette.core.sharedkernel.projectdtos.beans.input.InputLocationDTO;

import java.util.List;
import java.util.Map;

public record ReportPluginDTO(
        //ConfigurationContainer
        boolean inherited,
        Object configuration,
        Map<Object, InputLocationDTO> locations,
        InputLocationDTO location,
        InputLocationDTO inheritedLocation,
        InputLocationDTO configurationLocation,
        boolean inheritanceApplied,

        //ReportPlugin
        String groupId,
        String artifactId,
        String version,
        List<ReportSetDTO> reportSets
) {
}
