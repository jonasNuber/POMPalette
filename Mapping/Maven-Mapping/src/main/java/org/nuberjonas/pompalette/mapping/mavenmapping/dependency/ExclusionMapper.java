package org.nuberjonas.pompalette.mapping.mavenmapping.dependency;

import org.apache.maven.model.Exclusion;
import org.nuberjonas.pompalette.core.sharedkernel.projectdtos.beans.dependency.ExclusionDTO;
import org.nuberjonas.pompalette.mapping.mavenmapping.input.InputLocationMapper;

import java.util.Optional;

public class ExclusionMapper {

    private ExclusionMapper(){
        throw new AssertionError("Utility class, cannot be instantiated");
    }

    public static ExclusionDTO mapToDTO(Exclusion exclusion){
        return Optional.ofNullable(exclusion)
                .map(src ->  new ExclusionDTO(
                        src.getGroupId(),
                        src.getArtifactId(),
                        InputLocationMapper.mapToInputLocationDTOMap(src),
                        InputLocationMapper.mapToDTO(src.getLocation("")),
                        InputLocationMapper.mapToDTO(src.getLocation("groupId")),
                        InputLocationMapper.mapToDTO(src.getLocation("artifactId"))))
                .orElse(null);
    }

    public static Exclusion mapToModel(ExclusionDTO exclusionDTO){
        if(exclusionDTO == null){
            return null;
        }

        var exclusion = new Exclusion();

        exclusion.setGroupId(exclusionDTO.groupId());
        exclusion.setArtifactId(exclusionDTO.artifactId());

        if (exclusionDTO.locations() != null) {
            exclusionDTO.locations().forEach((key, value) ->
                    exclusion.setOtherLocation(key, InputLocationMapper.mapToModel(value))
            );
        }

        exclusion.setLocation("", InputLocationMapper.mapToModel(exclusionDTO.location()));
        exclusion.setLocation("groupId", InputLocationMapper.mapToModel(exclusionDTO.groupIdLocation()));
        exclusion.setLocation("artifactId", InputLocationMapper.mapToModel(exclusionDTO.artifactIdLocation()));

        return exclusion;
    }
}
