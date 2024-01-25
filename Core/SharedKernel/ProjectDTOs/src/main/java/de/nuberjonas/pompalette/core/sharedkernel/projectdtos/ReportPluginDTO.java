package de.nuberjonas.pompalette.core.sharedkernel.projectdtos;

import java.util.List;
import java.util.Map;

public record ReportPluginDTO(
        //ConfigurationContainer
        String inherited,
        Object configuration,
        Map<Object, InputLocationDTO> locations,
        InputLocationDTO location,
        InputLocationDTO inheritedLocation,
        InputLocationDTO configurationLocation,

        //ReportPlugin
        String groupId,
        String artifactId,
        String version,
        List<ReportSetDTO> reportSets
) {
}
