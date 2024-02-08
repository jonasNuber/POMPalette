package org.nuberjonas.pompalette.mapping.mavenmapping.management;

import org.apache.maven.model.Relocation;
import org.nuberjonas.pompalette.core.sharedkernel.projectdtos.beans.management.RelocationDTO;
import org.nuberjonas.pompalette.mapping.mavenmapping.input.InputLocationMapper;

import java.util.Optional;

public class RelocationMapper {

    private RelocationMapper(){ throw new AssertionError("Utility class, cannot be instantiated"); }

    public static RelocationDTO mapToDTO(Relocation relocation){
        return Optional.ofNullable(relocation)
                .map(src -> new RelocationDTO(
                        src.getGroupId(),
                        src.getArtifactId(),
                        src.getVersion(),
                        src.getMessage(),
                        InputLocationMapper.mapToInputLocationDTOMap(src),
                        InputLocationMapper.mapToDTO(src.getLocation("")),
                        InputLocationMapper.mapToDTO(src.getLocation("groupId")),
                        InputLocationMapper.mapToDTO(src.getLocation("artifactId")),
                        InputLocationMapper.mapToDTO(src.getLocation("version")),
                        InputLocationMapper.mapToDTO(src.getLocation("message"))
                ))
                .orElse(null);
    }

    public static Relocation mapToModel(RelocationDTO relocationDTO){
        if(relocationDTO == null){
            return null;
        }

        var relocation = new Relocation();
        relocation.setGroupId(relocationDTO.groupId());
        relocation.setArtifactId(relocationDTO.artifactId());
        relocation.setVersion(relocationDTO.version());
        relocation.setMessage(relocationDTO.message());

        if (relocationDTO.locations() != null) {
            relocationDTO.locations().forEach((key, value) ->
                    relocation.setOtherLocation(key, InputLocationMapper.mapToModel(value))
            );
        }

        relocation.setLocation("", InputLocationMapper.mapToModel(relocationDTO.location()));
        relocation.setLocation("groupId", InputLocationMapper.mapToModel(relocationDTO.groupIdLocation()));
        relocation.setLocation("artifactId", InputLocationMapper.mapToModel(relocationDTO.artifactIdLocation()));
        relocation.setLocation("version", InputLocationMapper.mapToModel(relocationDTO.versionLocation()));
        relocation.setLocation("message", InputLocationMapper.mapToModel(relocationDTO.messageLocation()));

        return relocation;
    }
}
