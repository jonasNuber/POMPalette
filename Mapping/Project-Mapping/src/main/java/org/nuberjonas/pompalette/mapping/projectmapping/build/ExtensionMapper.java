package org.nuberjonas.pompalette.mapping.projectmapping.build;

import org.apache.maven.model.Extension;
import org.nuberjonas.pompalette.core.sharedkernel.projectdtos.beans.build.ExtensionDTO;
import org.nuberjonas.pompalette.mapping.projectmapping.input.InputLocationMapper;

import java.util.Optional;

public class ExtensionMapper {

    private ExtensionMapper(){ throw new AssertionError("Utility class, cannot be instantiated"); }

    public static ExtensionDTO mapToDTO(Extension extension){
        return Optional.ofNullable(extension)
                .map(src -> new ExtensionDTO(
                        src.getGroupId(),
                        src.getArtifactId(),
                        src.getVersion(),
                        InputLocationMapper.mapToInputLocationDTOMap(src),
                        InputLocationMapper.mapToDTO(src.getLocation("")),
                        InputLocationMapper.mapToDTO(src.getLocation("groupId")),
                        InputLocationMapper.mapToDTO(src.getLocation("artifactId")),
                        InputLocationMapper.mapToDTO(src.getLocation("version"))
                ))
                .orElse(null);
    }

    public static Extension mapToModel(ExtensionDTO extensionDTO){
        if(extensionDTO == null){
            return null;
        }

        var extension = new Extension();
        extension.setGroupId(extensionDTO.groupId());
        extension.setArtifactId(extensionDTO.artifactId());
        extension.setVersion(extensionDTO.version());

        if (extensionDTO.locations() != null) {
            extensionDTO.locations().forEach((key, value) ->
                    extension.setOtherLocation(key, InputLocationMapper.mapToModel(value))
            );
        }

        extension.setLocation("", InputLocationMapper.mapToModel(extensionDTO.location()));
        extension.setLocation("groupId", InputLocationMapper.mapToModel(extensionDTO.groupIdLocation()));
        extension.setLocation("artifactId", InputLocationMapper.mapToModel(extensionDTO.artifactIdLocation()));
        extension.setLocation("version", InputLocationMapper.mapToModel(extensionDTO.versionLocation()));

        return extension;
    }
}
