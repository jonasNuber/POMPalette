package org.nuberjonas.pompalette.mapping.mavenmapping.dependency;

import org.apache.maven.model.Dependency;
import org.nuberjonas.pompalette.core.sharedkernel.projectdtos.beans.dependency.DependencyDTO;
import org.nuberjonas.pompalette.mapping.mappingapi.mapper.ListMapper;
import org.nuberjonas.pompalette.mapping.mavenmapping.input.InputLocationMapper;

import java.util.Optional;

public class DependencyMapper {

    private DependencyMapper(){
        throw new AssertionError("Utility class, cannot be instantiated");
    }

    public static DependencyDTO mapToDTO(Dependency dependency){
        return Optional.ofNullable(dependency)
                .map(src -> new DependencyDTO(
                        src.getGroupId(),
                        src.getArtifactId(),
                        src.getVersion(),
                        src.getType(),
                        src.getClassifier(),
                        src.getScope(),
                        src.getSystemPath(),
                        ListMapper.mapList(src.getExclusions(), ExclusionMapper::mapToDTO),
                        src.isOptional(),
                        InputLocationMapper.mapToInputLocationDTOMap(src),
                        InputLocationMapper.mapToDTO(src.getLocation("")),
                        InputLocationMapper.mapToDTO(src.getLocation("groupId")),
                        InputLocationMapper.mapToDTO(src.getLocation("artifactId")),
                        InputLocationMapper.mapToDTO(src.getLocation("version")),
                        InputLocationMapper.mapToDTO(src.getLocation("type")),
                        InputLocationMapper.mapToDTO(src.getLocation("classifier")),
                        InputLocationMapper.mapToDTO(src.getLocation("scope")),
                        InputLocationMapper.mapToDTO(src.getLocation("systemPath")),
                        InputLocationMapper.mapToDTO(src.getLocation("exclusions")),
                        InputLocationMapper.mapToDTO(src.getLocation("optional"))))
                .orElse(null);
    }

    public static Dependency mapToModel(DependencyDTO dependencyDTO){
        if(dependencyDTO == null){
            return null;
        }

        var dependency = new Dependency();
        dependency.setGroupId(dependencyDTO.groupId());
        dependency.setArtifactId(dependencyDTO.artifactId());
        dependency.setVersion(dependencyDTO.version());
        dependency.setType(dependencyDTO.type());
        dependency.setClassifier(dependencyDTO.classifier());
        dependency.setScope(dependencyDTO.scope());
        dependency.setSystemPath(dependencyDTO.systemPath());
        dependency.setExclusions(ListMapper.mapList(dependencyDTO.exclusions(), ExclusionMapper::mapToModel));
        dependency.setOptional(dependencyDTO.optional());

        if (dependencyDTO.locations() != null) {
            dependencyDTO.locations().forEach((key, value) ->
                    dependency.setOtherLocation(key, InputLocationMapper.mapToModel(value))
            );
        }

        dependency.setLocation("", InputLocationMapper.mapToModel(dependencyDTO.location()));
        dependency.setLocation("groupId", InputLocationMapper.mapToModel(dependencyDTO.groupIdLocation()));
        dependency.setLocation("artifactId", InputLocationMapper.mapToModel(dependencyDTO.artifactIdLocation()));
        dependency.setLocation("version", InputLocationMapper.mapToModel(dependencyDTO.versionLocation()));
        dependency.setLocation("type", InputLocationMapper.mapToModel(dependencyDTO.typeLocation()));
        dependency.setLocation("classifier", InputLocationMapper.mapToModel(dependencyDTO.classifierLocation()));
        dependency.setLocation("scope", InputLocationMapper.mapToModel(dependencyDTO.scopeLocation()));
        dependency.setLocation("systemPath", InputLocationMapper.mapToModel(dependencyDTO.systemPathLocation()));
        dependency.setLocation("exclusions", InputLocationMapper.mapToModel(dependencyDTO.exclusionsLocation()));
        dependency.setLocation("optional", InputLocationMapper.mapToModel(dependencyDTO.optionalLocation()));

        return dependency;
    }
}
