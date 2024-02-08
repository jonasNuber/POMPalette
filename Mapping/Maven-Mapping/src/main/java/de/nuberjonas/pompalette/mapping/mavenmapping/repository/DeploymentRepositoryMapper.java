package de.nuberjonas.pompalette.mapping.mavenmapping.repository;

import de.nuberjonas.pompalette.core.sharedkernel.projectdtos.beans.repository.DeploymentRepositoryDTO;
import de.nuberjonas.pompalette.mapping.mappingapi.mapper.SuperClassMapper;
import org.apache.maven.model.DeploymentRepository;

import java.util.Optional;

public class DeploymentRepositoryMapper {

    private DeploymentRepositoryMapper(){ throw new AssertionError("Utility class, cannot be instantiated"); }

    public static DeploymentRepositoryDTO mapToDTO(DeploymentRepository deploymentRepository){
        return Optional.ofNullable(deploymentRepository)
                .map(src -> new DeploymentRepositoryDTO(
                        RepositoryMapper.mapToDTO(src),
                        src.isUniqueVersion()
                ))
                .orElse(null);
    }

    public static DeploymentRepository mapToModel(DeploymentRepositoryDTO repositoryDTO){
        if(repositoryDTO == null){
            return null;
        }

        var deploymentRepository = new DeploymentRepository();

        if(repositoryDTO.repository() != null){
            deploymentRepository = SuperClassMapper.mapFields(RepositoryMapper.mapToModel(repositoryDTO.repository()), DeploymentRepository.class);
        }

        deploymentRepository.setUniqueVersion(repositoryDTO.uniqueVersion());

        return deploymentRepository;
    }
}
