package de.nuberjonas.pompalette.core.sharedkernel.projectdtos;

import java.util.List;
import java.util.Map;

public record PluginDTO(
        //ConfigurationContainer
        String inherited,
        Object configuration,
        Map<Object, InputLocationDTO> locations,
        InputLocationDTO location,
        InputLocationDTO inheritedLocation,
        InputLocationDTO configurationLocation,

        //Plugin
        String groupId,
        String artifactId,
        String version,
        String extensions,
        List<PluginExecutionDTO> executions,
        List<DependencyDTO> dependencies,
        Object goals
) {
}
