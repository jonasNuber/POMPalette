package org.nuberjonas.pompalette.mapping.projectmapping.plugin;

import org.apache.maven.model.PluginExecution;
import org.nuberjonas.pompalette.core.sharedkernel.projectdtos.beans.plugin.PluginExecutionDTO;
import org.nuberjonas.pompalette.mapping.mappingapi.mapper.utilities.SuperClassMapper;

import java.util.Optional;

public class PluginExecutionMapper {

    private PluginExecutionMapper(){ throw new AssertionError("Utility class, cannot be instantiated"); }

    public static PluginExecutionDTO mapToDTO(PluginExecution pluginExecution){
        return Optional.ofNullable(pluginExecution)
                .map(src -> new PluginExecutionDTO(
                        ConfigurationContainerMapper.mapToDTO(src),
                        src.getId(),
                        src.getPhase(),
                        src.getGoals()))
                .orElse(null);
    }

    public static PluginExecution mapToModel(PluginExecutionDTO pluginExecutionDTO){
        if(pluginExecutionDTO == null){
            return null;
        }

        var pluginExecution = new PluginExecution();

        if(pluginExecutionDTO.configurationContainer() != null){
            pluginExecution = SuperClassMapper.mapFields(ConfigurationContainerMapper.mapToModel(pluginExecutionDTO.configurationContainer()), PluginExecution.class);
        }

        pluginExecution.setId(pluginExecutionDTO.id());
        pluginExecution.setPhase(pluginExecutionDTO.phase());
        pluginExecution.setGoals(pluginExecutionDTO.goals());

        return pluginExecution;
    }
}
