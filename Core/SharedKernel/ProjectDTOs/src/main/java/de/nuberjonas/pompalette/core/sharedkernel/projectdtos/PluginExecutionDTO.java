package de.nuberjonas.pompalette.core.sharedkernel.projectdtos;

import java.util.List;
import java.util.Map;

public record PluginExecutionDTO(
        //ConfigurationContainer
        String inherited,
        Object configuration,
        Map<Object, InputLocationDTO> locations,
        InputLocationDTO location,
        InputLocationDTO inheritedLocation,
        InputLocationDTO configurationLocation,

        //PluginExecution
        String id,
        String phase,
        int priority,
        List<String> goals
) {
}
