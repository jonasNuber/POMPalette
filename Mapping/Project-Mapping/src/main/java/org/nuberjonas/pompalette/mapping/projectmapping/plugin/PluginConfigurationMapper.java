package org.nuberjonas.pompalette.mapping.projectmapping.plugin;

import org.apache.maven.model.PluginConfiguration;
import org.nuberjonas.pompalette.core.sharedkernel.projectdtos.beans.plugin.PluginConfigurationDTO;
import org.nuberjonas.pompalette.mapping.mappingapi.mapper.utilities.SuperClassMapper;

import java.util.Optional;

public class PluginConfigurationMapper {

    private PluginConfigurationMapper(){ throw new AssertionError("Utility class, cannot be instantiated"); }

    public static PluginConfigurationDTO mapToDTO(PluginConfiguration pluginConfiguration){
        return Optional.ofNullable(pluginConfiguration)
                .map(src -> new PluginConfigurationDTO(
                        PluginContainerMapper.mapToDTO(src),
                        PluginManagementMapper.mapToDTO(src.getPluginManagement())
                ))
                .orElse(null);
    }

    public static PluginConfiguration mapToModel(PluginConfigurationDTO pluginConfigurationDTO){
        if(pluginConfigurationDTO == null){
            return null;
        }

        var pluginConfiguration = new PluginConfiguration();

        if(pluginConfigurationDTO.pluginContainer() != null){
            pluginConfiguration = SuperClassMapper.mapFields(PluginContainerMapper.mapToModel(pluginConfigurationDTO.pluginContainer()), PluginConfiguration.class);
        }

        pluginConfiguration.setPluginManagement(PluginManagementMapper.mapToModel(pluginConfigurationDTO.pluginManagement()));

        return pluginConfiguration;
    }
}
