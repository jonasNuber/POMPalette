package de.nuberjonas.pompalette.core.sharedkernel.projectdtos.beans.reporting;

import de.nuberjonas.pompalette.core.sharedkernel.projectdtos.beans.plugin.ConfigurationContainerDTO;

import java.io.Serializable;
import java.util.List;

public record ReportPluginDTO(
        ConfigurationContainerDTO configurationContainer,
        String groupId,
        String artifactId,
        String version,
        List<ReportSetDTO> reportSets
) implements Serializable {
}
