package de.nuberjonas.pompalette.core.sharedkernel.projectdtos;

import java.util.List;
import java.util.Map;

public record ReportingDTO(
        String excludeDefaults,
        String outputDirectory,
        List<ReportPluginDTO> plugins,
        Map<Object, InputLocationDTO> locations,

        InputLocationDTO location,
        InputLocationDTO excludeDefaultsLocation,
        InputLocationDTO outputDirectoryLocation,
        InputLocationDTO pluginsLocation
) {
}
