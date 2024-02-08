package de.nuberjonas.pompalette.mapping.mavenmapping.build;

import de.nuberjonas.pompalette.core.sharedkernel.projectdtos.beans.build.BuildDTO;
import de.nuberjonas.pompalette.mapping.mappingapi.mapper.ListMapper;
import de.nuberjonas.pompalette.mapping.mappingapi.mapper.SuperClassMapper;
import org.apache.maven.model.Build;

import java.util.Optional;

public class BuildMapper {

    private BuildMapper(){ throw new AssertionError("Utility class, cannot be instantiated"); }

    public static BuildDTO mapToDTO(Build build){
        return Optional.ofNullable(build)
                .map(src -> new BuildDTO(
                        BuildBaseMapper.mapToDTO(src),
                        src.getSourceDirectory(),
                        src.getScriptSourceDirectory(),
                        src.getTestSourceDirectory(),
                        src.getOutputDirectory(),
                        src.getTestOutputDirectory(),
                        ListMapper.mapList(src.getExtensions(), ExtensionMapper::mapToDTO)
                ))
                .orElse(null);
    }

    public static Build mapToModel(BuildDTO buildDTO){
        if(buildDTO == null){
            return null;
        }

        var build = new Build();

        if(buildDTO.buildBase() != null){
            build = SuperClassMapper.mapFields(BuildBaseMapper.mapToModel(buildDTO.buildBase()), Build.class);
        }

        build.setSourceDirectory(buildDTO.sourceDirectory());
        build.setScriptSourceDirectory(buildDTO.scriptSourceDirectory());
        build.setTestSourceDirectory(buildDTO.testSourceDirectory());
        build.setOutputDirectory(buildDTO.outputDirectory());
        build.setTestOutputDirectory(buildDTO.testOutputDirectory());
        build.setExtensions(ListMapper.mapList(buildDTO.extensions(), ExtensionMapper::mapToModel));

        return build;
    }
}
