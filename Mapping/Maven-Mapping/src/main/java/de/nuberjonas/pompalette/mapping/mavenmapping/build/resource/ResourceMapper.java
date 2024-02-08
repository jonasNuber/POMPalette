package de.nuberjonas.pompalette.mapping.mavenmapping.build.resource;

import de.nuberjonas.pompalette.core.sharedkernel.projectdtos.beans.build.resource.ResourceDTO;
import de.nuberjonas.pompalette.mapping.mappingapi.mapper.SuperClassMapper;
import org.apache.maven.model.Resource;

import java.util.Optional;

public class ResourceMapper {

    private ResourceMapper(){ throw new AssertionError("Utility class, cannot be instantiated"); }

    public static ResourceDTO mapToDTO(Resource resource){
        return Optional.ofNullable(resource)
                .map(src -> new ResourceDTO(
                        FileSetMapper.mapToDTO(src),
                        src.getTargetPath(),
                        src.isFiltering(),
                        src.getMergeId()
                ))
                .orElse(null);
    }

    public static Resource mapToModel(ResourceDTO resourceDTO){
        if(resourceDTO == null){
            return null;
        }

        var resource = new Resource();

        if(resourceDTO.fileSet() != null){
            resource = SuperClassMapper.mapFields(FileSetMapper.mapToModel(resourceDTO.fileSet()), Resource.class);
        }

        resource.setTargetPath(resourceDTO.targetPath());
        resource.setFiltering(resourceDTO.filtering());
        resource.setMergeId(resourceDTO.mergeId());

        return resource;
    }
}
