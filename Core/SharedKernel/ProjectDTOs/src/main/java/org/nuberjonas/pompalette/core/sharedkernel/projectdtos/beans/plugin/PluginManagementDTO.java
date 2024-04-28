package org.nuberjonas.pompalette.core.sharedkernel.projectdtos.beans.plugin;

import java.io.Serializable;

public record PluginManagementDTO(
        PluginContainerDTO pluginContainer
) implements Serializable {
}
