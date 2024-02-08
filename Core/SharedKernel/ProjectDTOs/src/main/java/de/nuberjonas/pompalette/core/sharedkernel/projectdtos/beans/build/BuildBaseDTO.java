package de.nuberjonas.pompalette.core.sharedkernel.projectdtos.beans.build;

import de.nuberjonas.pompalette.core.sharedkernel.projectdtos.beans.build.resource.ResourceDTO;
import de.nuberjonas.pompalette.core.sharedkernel.projectdtos.beans.plugin.PluginConfigurationDTO;

import java.io.Serializable;
import java.util.List;

public record BuildBaseDTO(
        PluginConfigurationDTO pluginConfiguration,
        String defaultGoal,
        List<ResourceDTO> resources,
        List<ResourceDTO> testResources,
        String directory,
        String finalName,
        List<String> filters
) implements Serializable {
}
