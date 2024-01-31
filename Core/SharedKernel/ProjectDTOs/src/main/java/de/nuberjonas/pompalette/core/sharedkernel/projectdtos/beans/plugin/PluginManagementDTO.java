package de.nuberjonas.pompalette.core.sharedkernel.projectdtos.beans.plugin;

import de.nuberjonas.pompalette.core.sharedkernel.projectdtos.beans.build.PluginContainerDTO;

import java.io.Serializable;

public record PluginManagementDTO(
        PluginContainerDTO pluginContainer
) implements Serializable {
}
