package org.nuberjonas.pompalette.mapping.projectmapping.repository;

import org.apache.maven.model.RepositoryPolicy;
import org.nuberjonas.pompalette.core.sharedkernel.projectdtos.beans.repository.RepositoryPolicyDTO;
import org.nuberjonas.pompalette.mapping.projectmapping.input.InputLocationMapper;

import java.util.Optional;

public class RepositoryPolicyMapper {

    private RepositoryPolicyMapper(){ throw new AssertionError("Utility class, cannot be instantiated"); }

    public static RepositoryPolicyDTO mapToDTO(RepositoryPolicy repositoryPolicy){
        return Optional.ofNullable(repositoryPolicy)
                .map(src -> new RepositoryPolicyDTO(
                        src.isEnabled(),
                        src.getUpdatePolicy(),
                        src.getChecksumPolicy(),
                        InputLocationMapper.mapToInputLocationDTOMap(src),
                        InputLocationMapper.mapToDTO(src.getLocation("")),
                        InputLocationMapper.mapToDTO(src.getLocation("enabled")),
                        InputLocationMapper.mapToDTO(src.getLocation("updatePolicy")),
                        InputLocationMapper.mapToDTO(src.getLocation("checksumPolicy"))
                ))
                .orElse(null);
    }

    public static RepositoryPolicy mapToModel(RepositoryPolicyDTO repositoryPolicyDTO){
        if(repositoryPolicyDTO == null){
            return null;
        }

        var repositoryPolicy = new RepositoryPolicy();
        repositoryPolicy.setEnabled(repositoryPolicyDTO.enabled());
        repositoryPolicy.setUpdatePolicy(repositoryPolicyDTO.updatePolicy());
        repositoryPolicy.setChecksumPolicy(repositoryPolicyDTO.checksumPolicy());

        if (repositoryPolicyDTO.locations() != null) {
            repositoryPolicyDTO.locations().forEach((key, value) ->
                    repositoryPolicy.setOtherLocation(key, InputLocationMapper.mapToModel(value))
            );
        }

        repositoryPolicy.setLocation("", InputLocationMapper.mapToModel(repositoryPolicyDTO.location()));
        repositoryPolicy.setLocation("enabled", InputLocationMapper.mapToModel(repositoryPolicyDTO.enabledLocation()));
        repositoryPolicy.setLocation("updatePolicy", InputLocationMapper.mapToModel(repositoryPolicyDTO.updatePolicyLocation()));
        repositoryPolicy.setLocation("checksumPolicy", InputLocationMapper.mapToModel(repositoryPolicyDTO.checksumPolicyLocation()));

        return repositoryPolicy;
    }
}
