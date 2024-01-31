package de.nuberjonas.pompalette.core.sharedkernel.projectdtos.beans.plugin;

import de.nuberjonas.pompalette.core.sharedkernel.projectdtos.beans.input.InputLocationDTO;

import java.io.Serializable;
import java.util.Map;

public record ConfigurationContainerDTO(
        boolean inherited,
        Object configuration,
        Map<Object, InputLocationDTO> locations,
        InputLocationDTO location,
        InputLocationDTO inheritedLocation,
        InputLocationDTO configurationLocation,
        boolean inheritanceApplied
) implements Serializable {
}
