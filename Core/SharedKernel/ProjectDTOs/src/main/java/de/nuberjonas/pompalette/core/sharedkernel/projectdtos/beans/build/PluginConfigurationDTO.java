package de.nuberjonas.pompalette.core.sharedkernel.projectdtos.beans.build;

import de.nuberjonas.pompalette.core.sharedkernel.projectdtos.beans.plugin.PluginManagementDTO;

import java.io.Serializable;

public record PluginConfigurationDTO(
        PluginContainerDTO pluginContainer,
        PluginManagementDTO pluginManagement
) implements Serializable {
}
