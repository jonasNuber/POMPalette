package de.nuberjonas.pompalette.core.sharedkernel.projectdtos;

import java.util.List;
import java.util.Map;

public record BuildDTO(
        //PluginContainer
        List<PluginDTO> plugins,
        Map<Object, InputLocationDTO> locations,
        InputLocationDTO location,
        InputLocationDTO pluginsLocation,

        //PluginConfiguration
        PluginManagementDTO pluginManagement,

        //BuildBase
        String defaultGoal,
        List<ResourceDTO> resources,
        List<ResourceDTO> testResources,
        String directory,
        String finalName,
        List<String> filters,

        //Build
        String sourceDirectory,
        String scriptSourceDirectory,
        String testSourceDirectory,
        String outputDirectory,
        String testOutputDirectory,
        List<ExtensionDTO> extensions
) {
}
