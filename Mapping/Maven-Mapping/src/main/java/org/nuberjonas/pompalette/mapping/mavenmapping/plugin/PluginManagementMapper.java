package org.nuberjonas.pompalette.mapping.mavenmapping.plugin;

import org.apache.maven.model.PluginManagement;
import org.nuberjonas.pompalette.core.sharedkernel.projectdtos.beans.plugin.PluginContainerDTO;
import org.nuberjonas.pompalette.core.sharedkernel.projectdtos.beans.plugin.PluginManagementDTO;
import org.nuberjonas.pompalette.mapping.mappingapi.mapper.utilities.ListMapper;
import org.nuberjonas.pompalette.mapping.mavenmapping.input.InputLocationMapper;

import java.util.Optional;

public class PluginManagementMapper {

    private PluginManagementMapper(){ throw new AssertionError("Utility class, cannot be instantiated"); }

    public static PluginManagementDTO mapToDTO(PluginManagement pluginManagement){
        return Optional.ofNullable(pluginManagement)
                .map(src -> new PluginManagementDTO(
                        new PluginContainerDTO(
                                ListMapper.mapList(src.getPlugins(), PluginMapper::mapToDTO),
                                InputLocationMapper.mapToInputLocationDTOMap(src),
                                InputLocationMapper.mapToDTO(src.getLocation("")),
                                InputLocationMapper.mapToDTO(src.getLocation("plugins"))
                        )
                ))
                .orElse(null);
    }

    public static PluginManagement mapToModel(PluginManagementDTO pluginManagementDTO){
        if(pluginManagementDTO == null || pluginManagementDTO.pluginContainer() == null){
            return null;
        }

        var pluginManagement = new PluginManagement();
        pluginManagement.setPlugins(ListMapper.mapList(pluginManagementDTO.pluginContainer().plugins(), PluginMapper::mapToModel));

        if (pluginManagementDTO.pluginContainer().locations() != null) {
            pluginManagementDTO.pluginContainer().locations().forEach((key, value) ->
                    pluginManagement.setOtherLocation(key, InputLocationMapper.mapToModel(value))
            );
        }

        pluginManagement.setLocation("", InputLocationMapper.mapToModel(pluginManagementDTO.pluginContainer().location()));
        pluginManagement.setLocation("plugins", InputLocationMapper.mapToModel(pluginManagementDTO.pluginContainer().pluginsLocation()));

        return pluginManagement;
    }
}
