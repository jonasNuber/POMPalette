package de.nuberjonas.pompalette.core.sharedkernel.projectdtos.beans.reporting;

import de.nuberjonas.pompalette.core.sharedkernel.projectdtos.beans.input.InputLocationDTO;

import java.util.List;
import java.util.Map;

public record ReportingDTO(
        boolean excludeDefaults,
        String outputDirectory,
        List<ReportPluginDTO> plugins,
        Map<Object, InputLocationDTO> locations,

        InputLocationDTO location,
        InputLocationDTO excludeDefaultsLocation,
        InputLocationDTO outputDirectoryLocation,
        InputLocationDTO pluginsLocation
) {
}
