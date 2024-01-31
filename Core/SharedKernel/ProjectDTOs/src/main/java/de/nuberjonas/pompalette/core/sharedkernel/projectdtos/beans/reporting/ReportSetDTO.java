package de.nuberjonas.pompalette.core.sharedkernel.projectdtos.beans.reporting;

import de.nuberjonas.pompalette.core.sharedkernel.projectdtos.beans.plugin.ConfigurationContainerDTO;

import java.io.Serializable;
import java.util.List;

public record ReportSetDTO(
        ConfigurationContainerDTO configurationContainer,
        String id,
        List<String> reports
) implements Serializable {
}
