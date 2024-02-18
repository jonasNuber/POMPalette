package org.nuberjonas.pompalette.mapping.projectmapping.model;

import org.apache.maven.model.Parent;
import org.nuberjonas.pompalette.core.sharedkernel.projectdtos.beans.model.ParentDTO;
import org.nuberjonas.pompalette.mapping.projectmapping.input.InputLocationMapper;

import java.util.Optional;

public class ParentMapper {

    private ParentMapper(){ throw new AssertionError("Utility class, cannot be instantiated"); }

    public static ParentDTO mapToDTO(Parent parent){
        return Optional.ofNullable(parent)
                .map(src -> new ParentDTO(
                        src.getGroupId(),
                        src.getArtifactId(),
                        src.getVersion(),
                        src.getRelativePath(),
                        InputLocationMapper.mapToInputLocationDTOMap(src),
                        InputLocationMapper.mapToDTO(src.getLocation("")),
                        InputLocationMapper.mapToDTO(src.getLocation("groupId")),
                        InputLocationMapper.mapToDTO(src.getLocation("artifactId")),
                        InputLocationMapper.mapToDTO(src.getLocation("version")),
                        InputLocationMapper.mapToDTO(src.getLocation("relativePath"))
                ))
                .orElse(null);
    }

    public static Parent mapToModel(ParentDTO parentDTO){
        if(parentDTO == null){
            return null;
        }

        var parent = new Parent();
        parent.setGroupId(parentDTO.groupId());
        parent.setArtifactId(parentDTO.artifactId());
        parent.setVersion(parentDTO.version());
        parent.setRelativePath(parentDTO.relativePath());

        if (parentDTO.locations() != null) {
            parentDTO.locations().forEach((key, value) ->
                    parent.setOtherLocation(key, InputLocationMapper.mapToModel(value))
            );
        }

        parent.setLocation("", InputLocationMapper.mapToModel(parentDTO.location()));
        parent.setLocation("groupId", InputLocationMapper.mapToModel(parentDTO.groupIdLocation()));
        parent.setLocation("artifactId", InputLocationMapper.mapToModel(parentDTO.artifactIdLocation()));
        parent.setLocation("version", InputLocationMapper.mapToModel(parentDTO.versionLocation()));
        parent.setLocation("relativePath", InputLocationMapper.mapToModel(parentDTO.relativePathLocation()));

        return parent;
    }
}
