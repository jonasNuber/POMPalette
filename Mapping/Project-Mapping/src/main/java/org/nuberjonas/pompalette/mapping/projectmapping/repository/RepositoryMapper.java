package org.nuberjonas.pompalette.mapping.projectmapping.repository;

import org.apache.maven.model.Repository;
import org.nuberjonas.pompalette.core.sharedkernel.projectdtos.beans.repository.RepositoryDTO;
import org.nuberjonas.pompalette.mapping.mappingapi.mapper.utilities.SuperClassMapper;

import java.util.Optional;

public class RepositoryMapper {

    private RepositoryMapper() { throw new AssertionError("Utility class, cannot be instantiated"); }

    public static RepositoryDTO mapToDTO(Repository repository){
        return Optional.ofNullable(repository)
                .map(src -> new RepositoryDTO(
                        RepositoryBaseMapper.mapToDTO(src),
                        RepositoryPolicyMapper.mapToDTO(src.getReleases()),
                        RepositoryPolicyMapper.mapToDTO(src.getSnapshots())
                ))
                .orElse(null);
    }

    public static Repository mapToModel(RepositoryDTO repositoryDTO){
        if(repositoryDTO == null){
            return null;
        }

        var repository = new Repository();

        if(repositoryDTO.repositoryBase() != null){
            repository = SuperClassMapper.mapFields(RepositoryBaseMapper.mapToModel(repositoryDTO.repositoryBase()), Repository.class);
        }

        repository.setReleases(RepositoryPolicyMapper.mapToModel(repositoryDTO.releases()));
        repository.setSnapshots(RepositoryPolicyMapper.mapToModel(repositoryDTO.snapshots()));

        return repository;
    }
}
