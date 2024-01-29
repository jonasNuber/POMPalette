package de.nuberjonas.pompalette.core.sharedkernel.projectdtos.beans.plugin;

import de.nuberjonas.pompalette.core.sharedkernel.projectdtos.beans.input.InputLocationDTO;

import java.util.List;
import java.util.Map;

public record PluginExecutionDTO(
        //ConfigurationContainer
        boolean inherited,
        Object configuration,
        Map<Object, InputLocationDTO> locations,
        InputLocationDTO location,
        InputLocationDTO inheritedLocation,
        InputLocationDTO configurationLocation,
        boolean inheritanceApplied,

        //PluginExecution
        String id,
        String phase,
        List<String> goals
) {
}
