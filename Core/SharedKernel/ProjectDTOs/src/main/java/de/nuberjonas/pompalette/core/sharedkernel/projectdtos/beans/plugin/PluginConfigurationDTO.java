package de.nuberjonas.pompalette.core.sharedkernel.projectdtos.beans.plugin;

import java.io.Serializable;

public record PluginConfigurationDTO(
        PluginContainerDTO pluginContainer,
        PluginManagementDTO pluginManagement
) implements Serializable {
}
