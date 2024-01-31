package de.nuberjonas.pompalette.core.sharedkernel.projectdtos.beans.plugin;

import de.nuberjonas.pompalette.core.sharedkernel.projectdtos.beans.input.InputLocationDTO;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public record PluginContainerDTO(
        List<PluginDTO> plugins,
        Map<Object, InputLocationDTO> locations,
        InputLocationDTO location,
        InputLocationDTO pluginsLocation
) implements Serializable {
}
