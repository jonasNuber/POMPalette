package de.nuberjonas.pompalette.core.sharedkernel.projectdtos.beans.plugin;

import de.nuberjonas.pompalette.core.sharedkernel.projectdtos.beans.dependency.DependencyDTO;
import de.nuberjonas.pompalette.core.sharedkernel.projectdtos.beans.input.InputLocationDTO;

import java.util.List;
import java.util.Map;

public record PluginDTO(
        //ConfigurationContainer
        boolean inherited,
        Object configuration,
        Map<Object, InputLocationDTO> locations,
        InputLocationDTO location,
        InputLocationDTO inheritedLocation,
        InputLocationDTO configurationLocation,
        boolean inheritanceApplied,

        //Plugin
        String groupId,
        String artifactId,
        String version,
        String extensions,
        List<PluginExecutionDTO> executions,
        List<DependencyDTO> dependencies
) {
}
