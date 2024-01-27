package de.nuberjonas.pompalette.core.sharedkernel.projectdtos.beans.plugin;

import de.nuberjonas.pompalette.core.sharedkernel.projectdtos.beans.input.InputLocationDTO;

import java.util.List;
import java.util.Map;

public record PluginManagementDTO(
        //PluginContainer
        List<PluginDTO> plugins,
        Map<Object, InputLocationDTO> locations,
        InputLocationDTO location,
        InputLocationDTO pluginsLocation
) {
}
