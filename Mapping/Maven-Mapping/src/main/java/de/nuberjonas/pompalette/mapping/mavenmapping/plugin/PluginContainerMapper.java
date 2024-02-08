package de.nuberjonas.pompalette.mapping.mavenmapping.plugin;

import de.nuberjonas.pompalette.core.sharedkernel.projectdtos.beans.plugin.PluginContainerDTO;
import de.nuberjonas.pompalette.mapping.mappingapi.mapper.ListMapper;
import de.nuberjonas.pompalette.mapping.mavenmapping.input.InputLocationMapper;
import org.apache.maven.model.PluginContainer;

import java.util.Optional;

public class PluginContainerMapper {

    private PluginContainerMapper(){ throw new AssertionError("Utility class, cannot be instantiated"); }

    public static PluginContainerDTO mapToDTO(PluginContainer pluginContainer){
        return Optional.ofNullable(pluginContainer)
                .map(src -> new PluginContainerDTO(
                        ListMapper.mapList(src.getPlugins(), PluginMapper::mapToDTO),
                        InputLocationMapper.mapToInputLocationDTOMap(src),
                        InputLocationMapper.mapToDTO(src.getLocation("")),
                        InputLocationMapper.mapToDTO(src.getLocation("plugins"))
                ))
                .orElse(null);
    }

    public static PluginContainer mapToModel(PluginContainerDTO pluginContainerDTO){
        if(pluginContainerDTO == null){
            return null;
        }

        var pluginContainer = new PluginContainer();
        pluginContainer.setPlugins(ListMapper.mapList(pluginContainerDTO.plugins(), PluginMapper::mapToModel));

        if (pluginContainerDTO.locations() != null) {
            pluginContainerDTO.locations().forEach((key, value) ->
                    pluginContainer.setOtherLocation(key, InputLocationMapper.mapToModel(value))
            );
        }

        pluginContainer.setLocation("", InputLocationMapper.mapToModel(pluginContainerDTO.location()));
        pluginContainer.setLocation("plugins", InputLocationMapper.mapToModel(pluginContainerDTO.pluginsLocation()));

        return pluginContainer;
    }
}
