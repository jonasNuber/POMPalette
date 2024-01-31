package de.nuberjonas.pompalette.core.sharedkernel.projectdtos.beans.plugin;

import de.nuberjonas.pompalette.core.sharedkernel.projectdtos.beans.dependency.DependencyDTO;

import java.io.Serializable;
import java.util.List;

public record PluginDTO(
        ConfigurationContainerDTO configurationContainer,
        String groupId,
        String artifactId,
        String version,
        String extensions,
        List<PluginExecutionDTO> executions,
        List<DependencyDTO> dependencies
) implements Serializable {
}
