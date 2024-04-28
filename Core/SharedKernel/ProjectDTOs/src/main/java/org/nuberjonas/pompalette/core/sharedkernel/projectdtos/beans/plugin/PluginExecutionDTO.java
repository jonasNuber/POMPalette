package org.nuberjonas.pompalette.core.sharedkernel.projectdtos.beans.plugin;

import java.io.Serializable;
import java.util.List;

public record PluginExecutionDTO(
        ConfigurationContainerDTO configurationContainer,
        String id,
        String phase,
        List<String> goals
) implements Serializable {
}
