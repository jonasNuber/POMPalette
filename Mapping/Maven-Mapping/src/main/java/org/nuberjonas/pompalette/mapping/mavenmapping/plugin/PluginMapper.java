package org.nuberjonas.pompalette.mapping.mavenmapping.plugin;

import org.apache.maven.model.Plugin;
import org.nuberjonas.pompalette.core.sharedkernel.projectdtos.beans.plugin.PluginDTO;
import org.nuberjonas.pompalette.mapping.mappingapi.mapper.utilities.ListMapper;
import org.nuberjonas.pompalette.mapping.mappingapi.mapper.utilities.SuperClassMapper;
import org.nuberjonas.pompalette.mapping.mavenmapping.dependency.DependencyMapper;

import java.util.Optional;

public class PluginMapper {

    private PluginMapper(){ throw new AssertionError("Utility class, cannot be instantiated"); }

    public static PluginDTO mapToDTO(Plugin plugin){
        return Optional.ofNullable(plugin)
                .map(src -> new PluginDTO(
                        ConfigurationContainerMapper.mapToDTO(src),
                        src.getGroupId(),
                        src.getArtifactId(),
                        src.getVersion(),
                        src.isExtensions(),
                        ListMapper.mapList(src.getExecutions(), PluginExecutionMapper::mapToDTO),
                        ListMapper.mapList(src.getDependencies(), DependencyMapper::mapToDTO)))
                .orElse(null);
    }

    public static Plugin mapToModel(PluginDTO pluginDTO){
        if(pluginDTO == null){
            return null;
        }

        var plugin = new Plugin();

        if(pluginDTO.configurationContainer() != null){
            plugin = SuperClassMapper.mapFields(ConfigurationContainerMapper.mapToModel(pluginDTO.configurationContainer()), Plugin.class);
        }

        plugin.setGroupId(pluginDTO.groupId());
        plugin.setArtifactId(pluginDTO.artifactId());
        plugin.setVersion(pluginDTO.version());
        plugin.setExtensions(pluginDTO.extensions());
        plugin.setExecutions(ListMapper.mapList(pluginDTO.executions(), PluginExecutionMapper::mapToModel));
        plugin.setDependencies(ListMapper.mapList(pluginDTO.dependencies(), DependencyMapper::mapToModel));

        return plugin;
    }
}
