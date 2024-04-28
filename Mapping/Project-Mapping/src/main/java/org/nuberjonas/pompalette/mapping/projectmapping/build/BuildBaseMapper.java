package org.nuberjonas.pompalette.mapping.projectmapping.build;

import org.apache.maven.model.BuildBase;
import org.nuberjonas.pompalette.core.sharedkernel.projectdtos.beans.build.BuildBaseDTO;
import org.nuberjonas.pompalette.mapping.mappingapi.mapper.utilities.ListMapper;
import org.nuberjonas.pompalette.mapping.mappingapi.mapper.utilities.SuperClassMapper;
import org.nuberjonas.pompalette.mapping.projectmapping.build.resource.ResourceMapper;
import org.nuberjonas.pompalette.mapping.projectmapping.plugin.PluginConfigurationMapper;

import java.util.Optional;

public class BuildBaseMapper {

    private BuildBaseMapper(){ throw new AssertionError("Utility class, cannot be instantiated"); }

    public static BuildBaseDTO mapToDTO(BuildBase buildBase){
        return Optional.ofNullable(buildBase)
                .map(src -> new BuildBaseDTO(
                        PluginConfigurationMapper.mapToDTO(src),
                        src.getDefaultGoal(),
                        ListMapper.mapList(src.getResources(), ResourceMapper::mapToDTO),
                        ListMapper.mapList(src.getTestResources(), ResourceMapper::mapToDTO),
                        src.getDirectory(),
                        src.getFinalName(),
                        src.getFilters()
                ))
                .orElse(null);
    }

    public static BuildBase mapToModel(BuildBaseDTO buildBaseDTO){
        if(buildBaseDTO == null){
            return null;
        }

        var buildBase = new BuildBase();

        if(buildBaseDTO.pluginConfiguration() != null){
            buildBase = SuperClassMapper.mapFields(PluginConfigurationMapper.mapToModel(buildBaseDTO.pluginConfiguration()), BuildBase.class);
        }

        buildBase.setDefaultGoal(buildBaseDTO.defaultGoal());
        buildBase.setResources(ListMapper.mapList(buildBaseDTO.resources(), ResourceMapper::mapToModel));
        buildBase.setTestResources(ListMapper.mapList(buildBaseDTO.testResources(), ResourceMapper::mapToModel));
        buildBase.setDirectory(buildBaseDTO.directory());
        buildBase.setFinalName(buildBaseDTO.finalName());
        buildBase.setFilters(buildBaseDTO.filters());

        return buildBase;
    }
}
